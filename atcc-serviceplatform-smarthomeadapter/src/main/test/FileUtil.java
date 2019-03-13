import com.bcloud.msg.http.HttpSender;
import com.acv.cloud.frame.util.SMSUtil;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class FileUtil {
    @Test
    public void test() {
        String filename = "123.png";
        System.out.println("---:" + com.acv.cloud.frame.util.FileUtil.getTypePart(filename));
        ;
    }

    @Test
    public void testSMSSend() {
        try {
            SMSUtil.sendSms("hy.qixinhl.com/msg", "nissanchina", "nissan@123", "18101671990", "test", true, "12345678", "001");
            HttpClient client = new HttpClient();
            PostMethod method = new PostMethod();

            String account = "nissanchina";
            String pswd = "nissan@123";
            boolean needstatus = true;
            String product = "";
            String extno = "";
            String mobiles = "18101671990";
            String content = "test";

            try {
                URI base = new URI("hy.qixinhl.com/msg", false);
                method.setURI(new URI(base, "HttpSendSM", false));
                //method.setURI(base);
                method.getParams().setParameter("http.method.retry-handler", new DefaultHttpMethodRetryHandler());
                method.getParams().setParameter("http.protocol.content-charset", "UTF-8");
                method.setRequestBody(new NameValuePair[]{new NameValuePair("account", account), new NameValuePair("pswd", pswd), new NameValuePair("mobile", mobiles), new NameValuePair("needstatus", String.valueOf(needstatus)), new NameValuePair("msg", URLEncoder.encode(content, "UTF-8")), new NameValuePair("product", product), new NameValuePair("extno", extno)});
                int result = client.executeMethod(method);
                if (result != 200) {
                    throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
                } else {
                    InputStream in = method.getResponseBodyAsStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    boolean var15 = false;

                    int len;
                    while ((len = in.read(buffer)) != -1) {
                        baos.write(buffer, 0, len);
                    }

                    String var17 = URLDecoder.decode(baos.toString(), "UTF-8");
                    //return var17;
                }
            } finally {
                method.releaseConnection();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSendSMS() throws Exception {
        String uri = "http://hy.qixinhl.com/msg/";
        String account = "nissanchina";
        String pswd = "nissan@123";
        boolean needstatus = true;
        String product = "";
        String extno = "";
        String mobiles = "15900798629";
        String content = "【尼桑中国】验证码为102400";

        String returnString = HttpSender.send(uri, account, pswd, mobiles, content, needstatus, product, extno);
        System.out.println(returnString);
    }

}
