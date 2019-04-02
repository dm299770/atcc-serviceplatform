package com.acv.cloud.controller.account;

import com.acv.cloud.dto.sys.UserInfo;
import com.acv.cloud.frame.annotation.CurrentUser;
import com.acv.cloud.frame.annotation.LoginRequired;
import com.acv.cloud.frame.constants.AppResultConstants;
import com.acv.cloud.models.jsonBean.account.requestJson.DeductParams;
import com.acv.cloud.models.jsonBean.vehicle.request.VehicleStateRequestParameter;
import com.acv.cloud.services.account.AccountService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * PowerShare 接口
 * Created by liyang on 2019/02/10.
 */
@Controller
@RequestMapping({"/emsp"})
public class AccountController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountService accountService;

    /**
     * 用户充电扣款
     */
    @LoginRequired
    @ResponseBody
    @RequestMapping(value = "account/deduct", method = RequestMethod.POST)
    public Object deduct(@CurrentUser UserInfo user, @RequestBody DeductParams dudectParams) {
        logger.info("AccountController:deduct params  money:" + dudectParams.getData().getMoney() + ",comment:" + dudectParams.getData().getComment());
        JSONObject jsonObject = null;
        try {
            jsonObject = accountService.deduct(user.getUserId(), dudectParams.getData().getMoney(),
                    dudectParams.getData().getComment());
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put(AppResultConstants.STATUS, AppResultConstants.FAIL_STATUS);
            jsonObject.put(AppResultConstants.MSG, "数据库操作失败，事物回滚了");
        }
        logger.info("请求体" + dudectParams.toString());
        return jsonObject;
    }

    /**
     * 账单查询
     *
     * @return
     */
    @LoginRequired
    @ResponseBody
    @RequestMapping(value = "account/selAll", method = RequestMethod.POST)
    public Object selAll(@CurrentUser UserInfo user) {
        logger.info(user.toString());
        JSONObject jsonObject = accountService.selAll(user.getUserId());
        return jsonObject;
    }

    /**
     * 账户余额
     *
     * @param user
     * @return
     */
    @LoginRequired
    @ResponseBody
    @RequestMapping(value = "account/selBalance", method = RequestMethod.POST)
    public Object selBalance(@CurrentUser UserInfo user) {
        logger.info(user.toString());
        JSONObject jsonObject = accountService.selBalance(user.getUserId());
        return jsonObject;
    }

    /**
     * 车辆状态
     *
     * @param data
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "account/EVVehicleState")
    public Object EVVehicle(@RequestBody VehicleStateRequestParameter data) {
        logger.info("AccountController: EVVehicleState  params vin号:" + data.getData().getVin() + ",model:" + data.getData().getModel());
        JSONObject jsonObject = accountService.vehicleState(data);
        return jsonObject;
    }

}