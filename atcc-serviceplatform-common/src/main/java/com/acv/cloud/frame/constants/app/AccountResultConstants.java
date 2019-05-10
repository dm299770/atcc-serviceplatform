package com.acv.cloud.frame.constants.app;

import com.acv.cloud.frame.constants.AppResultConstants;
import org.omg.PortableInterceptor.INACTIVE;

/**
 * Created by liyang on 2019/5/7 18:01.
 * <p>
 * 充电扣费状态返回码
 */
public class AccountResultConstants extends AppResultConstants {

    public static final Integer DEDUCT_FAIL = 5001;//扣款失败

    public static final Integer BALANCE_INSUFFICIENT = 5002;//余额不足

    public static final Integer ORDER_DEBITED = 5003;//该订单已扣款

    public static final Integer USERID_EMPTY = 5004;//用户ID为空

    public static final Integer ORDERNUM_EMPTY = 5005;//订单号为空

    public static final Integer AMOUNT_EMPTY = 5006;//扣款金额为空

    public static final String BALANCE_INSUFFICIENT_MSG = "账户余额不足";

    public static final String ORDER_DEBITED_MSG = "该订单已重复扣款";

    public static final String DEDUCT_SUCCESS_MSG = "扣款成功";
}
