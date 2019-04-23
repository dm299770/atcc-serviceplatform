/**
  * Copyright 2019 bejson.com 
  */
package com.acv.cloud.domain.body.req.login;

/**
 * Auto-generated: 2019-03-12 14:36:32
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class LoginParams {

    private Data data;
    public void setData(Data data) {
         this.data = data;
     }
     public Data getData() {
         return data;
     }

    @Override
    public String toString() {
        return "LoginParams{" +
                "data=" + data +
                '}';
    }
}