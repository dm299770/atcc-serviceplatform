package com.acv.cloud.frame.util;

/**
 *各类验证码生成工具
 */
public class VcUtil {

    /**
     * 随机生成6位随机验证码
     * 方法说明
     * @Discription:扩展说明
     * @return
     * @return String
     * @Author: leo.
     */
    public static String createRandomVcode(){
        //验证码
        String vcode = "";
        for (int i = 0; i < 6; i++) {
            vcode = vcode + (int)(Math.random() * 9);
        }
        return vcode;
    }



    public static void main(String args[]){
        System.out.println(VcUtil.createRandomVcode());
    }

}
