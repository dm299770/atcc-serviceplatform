package com.acv.cloud.frame.constants;

public class RedisConstants {
    public static final String ACCOUNT_DEVICE = "account_device"; //用户与设备绑定关系头
    public static final String DEVICE_ACCOUNT = "device_account"; //设备最后请求登录用户头

    public static final String LOGIN_DEVICENO = "LOGIN_DEVICENO"; //jwt记录用户推送设备号
    public static final String LOGIN_DEVICETYPE = "LOGIN_DEVICETYPE"; //jwt记录用户推送设备型号

    public static  enum ALL_DEVICETYPES {IOS, Android, Oauth2, WeChart};//获取token设备类型

    public static final String REGISTER_HEAD = "Register";//注册验证码
    public static final String FORGOTPASSWORD_HEAD = "Forgot"; // 忘记密码


}
