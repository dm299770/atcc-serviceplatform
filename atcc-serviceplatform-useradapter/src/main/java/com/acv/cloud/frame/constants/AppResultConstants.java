package com.acv.cloud.frame.constants;

/**
 * @description:返回给APP端参数名
 * @author:@leo.
 */
public class AppResultConstants {
    /**
     * 具体的提示信息
     */
    public final static String MSG = "msg";
    /**
     * 返回data json数组数据
     */
    public final static String DATA = "data";
    /**
     * 错误代码 1成功、2失败
     */
    public final static String STATUS = "status";
    /**
     * 成功 200
     */
    public final static Integer SUCCESS_STATUS = 200;
    /**
     * 失败 400
     */
    public final static Integer FAIL_STATUS = 400;
    /**
     * 服务器异常 500
     */
    public final static Integer ERROR_STATUS = 500;
    /**
     * 服务器内部错误
     */
    public final static String SEVER_ERROR = "服务器内部错误";

    /**
     * 登录失效,请重新登录
     */
    public final static String LOGIN_ERROR = "登录异常";
    /**
     * 登出异常
     */
    public final static String LOGOUT_ERROR = "注销异常";
    /***
     * 无效url
     */
    public final static String NOTFOUND_URL = "未知url ";

    /***
     * 参数异常
     */
    public final static String PARAMER_ERROR = "参数异常";

    /***
     *车辆已绑定
     */

    public final static String  VIN_EXIST = "此车辆已绑定";

    /***
     * 无效url
     */
    public final static Integer NOTFOUND_ERROR = 404;

}
