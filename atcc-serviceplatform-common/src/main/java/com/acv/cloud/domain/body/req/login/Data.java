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
public class Data {

    private String type;
    private Attributes attributes;

    public Data(String type , Attributes attributes) {
        this.attributes = attributes;
    }

    public void setType(String type) {
         this.type = type;
     }
     public String getType() {
         return type;
     }

    public void setAttributes(Attributes attributes) {
         this.attributes = attributes;
     }
     public Attributes getAttributes() {
         return attributes;
     }

}