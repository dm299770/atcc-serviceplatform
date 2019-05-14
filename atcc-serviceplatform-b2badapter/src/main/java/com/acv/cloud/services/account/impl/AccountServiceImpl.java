package com.acv.cloud.services.account.impl;

import com.acv.cloud.dto.sys.UserInfo;
import com.acv.cloud.frame.constants.AppResultConstants;
import com.acv.cloud.frame.constants.app.AccountResultConstants;
import com.acv.cloud.frame.util.DateUtil;
import com.acv.cloud.mapper.account.AccountMapper;
import com.acv.cloud.mapper.user.TsUserMapper;
import com.acv.cloud.models.jsonBean.account.responseJson.TtChargeFlow;
import com.acv.cloud.models.jsonBean.account.responseJson.UserAccount;
import com.acv.cloud.services.account.AccountService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * PowerShare 接口
 * Created by liyang
 */
@Service
public class AccountServiceImpl implements AccountService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String BALANCE = "balance";
    private static final String CHARGE_TO = "PowerShare";//到达方
    private static final String ORDER_BALANCE = "账户余额";
    private static final String ORDER_SUCCESS = "账单查询成功";
    private static final String SERIAL_NUM = "id";


    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private TsUserMapper tsUserMapper;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
    public JSONObject deduct(String user_id, String money, String comment) throws Exception {
        logger.info("AccountService: deduct  params  money:" + money + ",comment:" + comment + ",userId:" + user_id);
        JSONObject json = new JSONObject();
        UserAccount userAccount = null;
        UserInfo userInfo = null;
        if (money == null || "".equals(money)) {
            json.put(AppResultConstants.STATUS, AccountResultConstants.AMOUNT_EMPTY);
            json.put(AppResultConstants.MSG, AccountResultConstants.PARAM_ERROR_MSG);
        } else if (user_id == null || "".equals(user_id)) {
            json.put(AppResultConstants.STATUS, AccountResultConstants.USERID_EMPTY);
            json.put(AppResultConstants.MSG, AccountResultConstants.PARAM_ERROR_MSG);
        } else if (comment == null || "".equals(comment)) {
            json.put(AppResultConstants.STATUS, AccountResultConstants.ORDERNUM_EMPTY);
            json.put(AppResultConstants.MSG, AccountResultConstants.PARAM_ERROR_MSG);
        } else {
            TtChargeFlow ttChargeFlow = accountMapper.selOrderNum(comment);
            //判断该订单号是否存在订单，如果有，则勿重复扣款，
            if (ttChargeFlow != null) {
                json.put(AppResultConstants.STATUS, AccountResultConstants.ORDER_DEBITED);
                json.put(AppResultConstants.MSG, AccountResultConstants.ORDER_DEBITED_MSG);
            } else {
                Integer direction = -1;//增减标识（扣款为减少，-1）
                String id = UUID.randomUUID().toString();//流水单号
                userAccount = accountMapper.selectBalance(user_id);
                BigDecimal amount = new BigDecimal(userAccount.getBalance());//当前账户余额
                BigDecimal moneyD = new BigDecimal(money);//扣款金额
                BigDecimal balanceD = amount.subtract(moneyD);//消费后的余额
                String balance = String.valueOf(balanceD);
                logger.info("AccountService: deduct params 扣款金额为:" + moneyD + ",账户余额为:" + amount + ",扣款后剩余金额:" + balanceD);
                //比较标识符
                int comTo = moneyD.compareTo(amount);
                logger.info("comTo:" + comTo);
                //下单修改时间
                String updateTime = DateUtil.getDate("yyyy/MM/dd HH:mm:ss");
                //扣费操作
                if (comTo == -1) {
                    accountMapper.upadteBalance(user_id, balance, updateTime);
                    //发起方查询
                    userInfo = tsUserMapper.findUserPhoneNum(user_id);
                    accountMapper.saveChargeFlow(id, user_id, Double.valueOf(String.valueOf(moneyD)), direction, updateTime, userInfo.getPhoneNum(), CHARGE_TO, comment);
                    json.put(AppResultConstants.STATUS, AppResultConstants.SUCCESS_STATUS);
                    json.put(AppResultConstants.MSG, AccountResultConstants.DEDUCT_SUCCESS_MSG);
                    json.put(BALANCE, balance);
                    json.put(SERIAL_NUM, id);

                } else if (comTo == 0) { //如果余额和消费金额相等，则修改余额为零
                    accountMapper.upadteBalance(user_id, "0", updateTime);
                    //发起方查询
                    userInfo = tsUserMapper.findUserPhoneNum(user_id);
                    accountMapper.saveChargeFlow(id, user_id, Double.valueOf(String.valueOf(moneyD)), direction, updateTime, userInfo.getPhoneNum(), CHARGE_TO, comment);
                    json.put(AppResultConstants.STATUS, AppResultConstants.SUCCESS_STATUS);
                    json.put(AppResultConstants.MSG, AccountResultConstants.DEDUCT_SUCCESS_MSG);
                    json.put(BALANCE, balance);
                    json.put(SERIAL_NUM, id);
                } else {
                    //余额不足
                    json.put(AppResultConstants.STATUS, AccountResultConstants.BALANCE_INSUFFICIENT);
                    json.put(AppResultConstants.MSG, AccountResultConstants.BALANCE_INSUFFICIENT_MSG);
                    json.put(BALANCE, balance);
                }
            }
        }
        return json;
    }

    @Override
    public JSONObject selAll(String user_id) {
        logger.info("AccountService: selAll  params userId:" + user_id);
        JSONObject json = new JSONObject();
        try {
            if (user_id.isEmpty() || "".equals(user_id)) {
                json.put(AppResultConstants.STATUS, AccountResultConstants.USERID_EMPTY);
                json.put(AppResultConstants.MSG, AccountResultConstants.PARAM_ERROR_MSG);
            } else {
                List<TtChargeFlow> ttChargeFlow = accountMapper.selectAll(user_id);
                json.put(AppResultConstants.STATUS, AppResultConstants.SUCCESS_STATUS);
                json.put(AppResultConstants.MSG, ORDER_SUCCESS);
                json.put("data", ttChargeFlow);
            }
        } catch (Exception e) {
            json.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);
            json.put(AppResultConstants.MSG, AppResultConstants.SEVER_ERROR);
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public JSONObject selBalance(String user_id) {
        logger.info("AccountService: selBalance  params userId:" + user_id);
        JSONObject json = new JSONObject();
        try {
            if (user_id.isEmpty() || "".equals(user_id)) {
                json.put(AppResultConstants.STATUS, AccountResultConstants.USERID_EMPTY);
                json.put(AppResultConstants.MSG, AccountResultConstants.PARAM_ERROR_MSG);
            } else {
                UserAccount userAccount = accountMapper.selBalance(user_id);
                String balance = userAccount.getBalance();
                json.put(AppResultConstants.STATUS, AppResultConstants.SUCCESS_STATUS);
                json.put(AppResultConstants.MSG, ORDER_BALANCE);
                json.put(BALANCE, balance);
            }
        } catch (Exception e) {
            e.printStackTrace();
            json.put(AppResultConstants.STATUS, AppResultConstants.ERROR_STATUS);
            json.put(AppResultConstants.MSG, AppResultConstants.SEVER_ERROR);
        }
        return json;
    }

}
