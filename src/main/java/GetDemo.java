import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;

/**
 * @ClassName: GetDemo
 * @Description: TODO
 * @author: Doge_fang
 * @date: 2020/3/22  21:19
 */
public class GetDemo {
    public static void main(String[] args) throws IOException {
        //提供接口地址
        String url ="https://www.reg007.com/account/signup";
        //提供测试数据
        String mobilephone ="15895806533";
        String pwd = "123456";
        //get请求是将字符串拼接在url后面
        url = url +"?mobilephone=15895806533&pwd=123456";
        //指定接口提交方式
        HttpGet get = new HttpGet(url);
        //发送请求
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = client.execute(get);
        //拿到响应数据
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        String responseresult = EntityUtils.toString(httpResponse.getEntity());
        System.out.println(responseresult);
    }



}
