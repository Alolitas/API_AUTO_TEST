import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * @ClassName: testDemo1
 * @Description: TODO
 * @author: Doge_fang
 * @date: 2020/3/22  20:47
 *
 * post接口请求
 */

public class postDemo {
    public static void main(String[] args) throws IOException {
        //填写接口地址
        String url = "https://api.yiye.ai/real_time.html";
        //指定接口请求方式
        HttpPost post = new HttpPost();
        //准备测试数据
        String mobilephone = "15895806533";
        String pwd = "123456";
        //将数据封装到请求体
        ArrayList<BasicNameValuePair> parametes = new ArrayList<BasicNameValuePair>();
        parametes.add(new BasicNameValuePair("mobilephone",mobilephone));
        parametes.add(new BasicNameValuePair("pwd",pwd));
        post.setEntity(new UrlEncodedFormEntity(parametes,"utf-8"));
        //发起请求，获取接口响应信息（状态码，响应报文，或者某些特殊的响应头数据）
        CloseableHttpClient client = HttpClients.createDefault();//准备客户端
        //执行请求,相当于postman上点击发送按钮，然后赋值给 httprespnse对象接收
        CloseableHttpResponse httprespnse = client.execute(post);//发送请求
        //状态码
        int statusCode = httprespnse.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        //响应报文
        String responseresult = EntityUtils.toString(httprespnse.getEntity());
        System.out.println(responseresult);

    }
}
