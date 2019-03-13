package com.acv.cloud.frame.util;

import com.acv.cloud.frame.constants.OauthConstants;


public class StrUtils {

	/**
	 * 字符串加密
	 * @param value
	 * @return
	 */
	public static String encrypt(String value)  
	{  
	    StringBuffer sbu = new StringBuffer();  
	    char[] chars = value.toCharArray();   
	    for (int i = 0; i < chars.length; i++) {  
        	if(chars[i] > 96 && chars[i] < 122){
        		sbu.append((int)chars[i] - 31).append("_");  
        	}else if(chars[i] > 65 && chars[i] < 91){
        		sbu.append((int)chars[i] + 31).append("_");
        	}else if(chars[i] > 47 && chars[i] < 57){
        		sbu.append((int)chars[i] + 1).append("_");
        	}else if(chars[i] == 95){
        		sbu.append((int)chars[i] - 50).append("_");
        	}else{
        		sbu.append((int)chars[i]).append("_");
        	}
	    }  
	    StringBuffer strbuf = new StringBuffer(); 
		String[] strs = sbu.toString().split("_");
		for (int i = 0; i < chars.length; i++) { 
			strbuf.append((char) (Integer.parseInt(strs[i])));  
		}  
		return strbuf.toString(); 
	}
	/**
	 * 字符串解密
	 * @param value
	 * @return
	 */
	public static String decrypt(String value)  
	{  
		StringBuffer sbu = new StringBuffer();  
		char[] chars = value.toCharArray();   
		for (int i = 0; i < chars.length; i++) { 
			if(chars[i] > 96 && chars[i] < 122){
				sbu.append((int)chars[i] - 31).append("_");  
			}else if(chars[i] > 65 && chars[i] < 91){
				sbu.append((int)chars[i] + 31).append("_");
			}else if(chars[i] > 48 && chars[i] < 58){
				sbu.append((int)chars[i] + 1).append("_");
			}else if(chars[i] == 45){
        		sbu.append((int)chars[i] + 50).append("_");
        	}else{
				sbu.append((int)chars[i]).append("_");
			}
		} 
		StringBuffer strbuf = new StringBuffer(); 
		String[] strs = sbu.toString().split("_");
		for (int i = 0; i < chars.length; i++) { 
			strbuf.append((char) (Integer.parseInt(strs[i])));  
		}  
		return strbuf.toString();
	}
	
	/**
	 * 验证scope合法性  true为合法
	 * @param scope
	 * @return
	 */
	public static boolean checkScope(String scope) {
		String[] strs = OauthConstants.AUTHORITY_LIST.split(" ");
		for (String str : strs) {
			if (str.equals(scope)) {
				return true;
			}
		}
		return false;
	}
}
