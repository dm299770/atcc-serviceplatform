package com.acv.cloud.frame.constants.app;

import com.acv.cloud.frame.constants.AppResultConstants;

/**
 * Created by liyang on 2019/5/9 14:55.
 */
public class NotificationResultConstants extends AppResultConstants {
    public static final Integer RETURN_ERROR = 4001;//返回结果错误

    public static final Integer TYPE_ERROR = 4002;//类型异常

    public static final Integer PHONE_EMPTY = 4003;//手机号为空

    public static final Integer TYPE_EMPTY = 4004;//类型为空

    public static final Integer SMS_CONTENT_EMPTY = 4005;//短信内容为空

    public static final Integer CONTENT_EMPTY = 4006;//内容为空

    public static final Integer TITLE_EMPTY = 4007;//标题为空

    public static final Integer DEVICEDEID_ERROR = 4008;//DeviceId获取不一致

    public static final Integer RESULT_ERROR = 4009;//处理失败

    public static final Integer ID_EMPTY = 4010;//ID为空

    public static final Integer READFLAG_EMPTY = 4011;

    public static final String DEVICEDEID_ERROR_MSG = "DeviceId获取结果不一致";

    public final static String SUCCESS_MSG = "成功";

    public static final String RETURN_CODE_ERROR = "状态码返回异常";


}
