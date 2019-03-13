package com.acv.cloud.services.message.impl;


import com.acv.cloud.services.message.MessageService;
import com.alibaba.fastjson.JSONObject;
import com.acv.cloud.jsonBean.message.MessageRequestParameter;
import com.acv.cloud.jsonBean.message.ReturnJsonMessage;
import com.acv.cloud.jsonBean.message.ReturnMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @description:消息中心查询消息
 * @author:@guo.zj
 */
@Service
public class MessageServiceImpl implements MessageService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public JSONObject message(MessageRequestParameter data) {
        JSONObject jsonObject = new JSONObject();
        ReturnJsonMessage returnJsonMessage=new ReturnJsonMessage();
        try {
            logger.info("请求体:" + data);
            //Finder finder = JSON.parseObject(request, FinderRequestParameter.class).getData();
            String messagetype = data.getData().getMessagetype();
            //电话号数据库使用时使用
            String phone= data.getData().getPhone();
            String serviceType = data.getData().getType();

            if(phone==null||"".equals(phone)){
                jsonObject.put("status", 201);
                jsonObject.put("msg", "传入参数有误");
                jsonObject.put("data","");
            }else if ("APP".equals(serviceType) && "13888888888".equals(phone)) {
                if ("all".equals(messagetype)) {
                    ReturnMessage[] returnMessage = new ReturnMessage[6];
                    //卡梅隆上线时使用
                    // for (int i=0;i<6;i++){
                    //    returnMessage[i]=new ReturnMessage();
                    // }
                    returnMessage[0] = new ReturnMessage();
                    returnMessage[1] = new ReturnMessage();
                    returnMessage[2] = new ReturnMessage();
                    returnMessage[3] = new ReturnMessage();
                    returnMessage[4] = new ReturnMessage();
                    returnMessage[5] = new ReturnMessage();

                    returnMessage[0].setTitle("警告");
                    returnMessage[0].setBody("尊敬的用户您好，英菲尼迪系统检测到您在2108-11-01超速两次，请注意安全驾驶。");
                    returnMessage[0].setDate("1/11");

                    returnMessage[1].setTitle("提醒");
                    returnMessage[1].setBody("尊敬的用户您好，英菲尼迪系统提醒您在2108-11-01需要进行汽车保养，请合理安排时间进行汽车保养");
                    returnMessage[1].setDate("1/11");

                    returnMessage[2].setTitle("系统");
                    returnMessage[2].setBody("尊敬的用户您好，英菲尼迪系统检测扫您的爱车在2108-11-01油量报警，请对您的爱车及时进行检查");
                    returnMessage[2].setDate("1/11");


                    returnMessage[3].setTitle("警告");
                    returnMessage[3].setBody("尊敬的用户您好，英菲尼迪系统检测到您在2108-11-05违章两次，请遵守交通规则");
                    returnMessage[3].setDate("5/11");

                    returnMessage[4].setTitle("警告");
                    returnMessage[4].setBody("尊敬的用户您好，英菲尼迪系统检测到您在2108-11-09闯红灯3次，请注意安全驾驶。");
                    returnMessage[4].setDate("9/11");

                    returnMessage[5].setTitle("系统");
                    returnMessage[5].setBody("尊敬的用户您好，今天双11，买买买");
                    returnMessage[5].setDate("11/11");
                    returnJsonMessage.setMessage(returnMessage);

                } else if ("warn".equals(messagetype)) {
                    ReturnMessage[] returnMessage = new ReturnMessage[3];
                    //卡梅隆上线时使用
                    // for (int i=0;i<6;i++){
                    //    returnMessage[i]=new ReturnMessage();
                    // }


                    returnMessage[0] = new ReturnMessage();
                    returnMessage[1] = new ReturnMessage();
                    returnMessage[2] = new ReturnMessage();

                    returnMessage[0].setTitle("警告");
                    returnMessage[0].setBody("尊敬的用户您好，英菲尼迪系统检测到您在2108-11-01超速两次，请注意安全驾驶。");
                    returnMessage[0].setDate("1/11");

                    returnMessage[1].setTitle("警告");
                    returnMessage[1].setBody("尊敬的用户您好，英菲尼迪系统检测到您在2108-11-05违章两次，请遵守交通规则。");
                    returnMessage[1].setDate("5/11");

                    returnMessage[2].setTitle("警告");
                    returnMessage[2].setBody("尊敬的用户您好，英菲尼迪系统检测到您在2108-11-09闯红灯3次，请注意安全驾驶。");
                    returnMessage[2].setDate("9/11");
                    returnJsonMessage.setMessage(returnMessage);

                } else if ("remind".equals(messagetype)) {
                    ReturnMessage[] returnMessage = new ReturnMessage[1];
                    //卡梅隆上线时使用
                    // for (int i=0;i<6;i++){
                    //    returnMessage[i]=new ReturnMessage();
                    // }

                    returnMessage[0] = new ReturnMessage();
                    returnMessage[0].setTitle("提醒");
                    returnMessage[0].setBody("尊敬的用户您好，英菲尼迪系统提醒您在2108-11-01需要进行汽车保养，请合理安排时间进行汽车保养");
                    returnMessage[0].setDate("1/11");
                    returnJsonMessage.setMessage(returnMessage);

                } else if ("system".equals(messagetype)) {
                    ReturnMessage[] returnMessage = new ReturnMessage[2];
                    //卡梅隆上线时使用
                    // for (int i=0;i<6;i++){
                    //    returnMessage[i]=new ReturnMessage();
                    // }
                    returnMessage[0] = new ReturnMessage();
                    returnMessage[1] = new ReturnMessage();

                    returnMessage[0].setTitle("系统");
                    returnMessage[0].setBody("尊敬的用户您好，英菲尼迪系统检测扫您的爱车在2108-11-01油量报警，请对您的爱车及时进行检查");
                    returnMessage[0].setDate("1/11");

                    returnMessage[1].setTitle("系统");
                    returnMessage[1].setBody("尊敬的用户您好，今天双11，买买买");
                    returnMessage[1].setDate("11/11");
                    returnJsonMessage.setMessage(returnMessage);
                }
                jsonObject.put("status", 200);
                jsonObject.put("msg", "消息查询成功");
                jsonObject.put("data",returnJsonMessage);
                logger.info("返回的json:{}", jsonObject);

                }else {
                jsonObject.put("status", 400);
                jsonObject.put("msg", "failed");
                jsonObject.put("data", "");
                logger.info("返回的json:{}", jsonObject);

            }
        }catch(Exception e) {
            e.printStackTrace();
            logger.error("系统内部错误!");
            jsonObject.put("status",500);
            jsonObject.put("msg", "服务器处理失败，系统内部错误");
            jsonObject.put("data","");
        }
        return jsonObject;
    }

}