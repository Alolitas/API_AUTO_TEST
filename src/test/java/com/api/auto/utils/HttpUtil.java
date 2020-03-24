package com.api.auto.utils;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: HttpUtil
 * @Description: TODO
 * @author: Doge_fang
 * @date: 2020/3/23  9:14
 */


public class HttpUtil {


    /**
     * 发送post请求
     * @param url  请求的url
     * @param params  请求的参数放在map集合中
     * @return  返回post请求的响应报文
     */
    public static String doPost(String url, Map<String,String> params){
        HttpPost post = new HttpPost(url);
        //取出map中所有的参数名
        Set<String> keys = params.keySet();
        //通过循环将参数保存到集合
        ArrayList<BasicNameValuePair> paramters = new ArrayList<BasicNameValuePair>();
        for (String keyname:keys) {
            String values = params.get(keyname);
            paramters.add(new BasicNameValuePair(keyname,values));
        }
        String responseresult = "";
        try {
            //封装请求体
            post.setEntity(new UrlEncodedFormEntity(paramters,"utf-8"));
            //发起请求，获取响应信息
            CloseableHttpClient httpclient = HttpClients.createDefault();
            addCookieInRequestHeaderBeforeRequest(post);
            CloseableHttpResponse response = httpclient.execute(post);
            getAndStoreCookiesFromResponseHeader(response);
            //状态码
            int statusCode = response.getStatusLine().getStatusCode();
            //响应报文,响应格式为json，转换为String
            responseresult = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回响应报文
        return responseresult;

    }

    /**
     * 请求体中添加cookie
     * @param request
     */
    private static void addCookieInRequestHeaderBeforeRequest(HttpRequest request) {
        String jessionid = cookies.get("JESSIONID");
        if(jessionid !=null){
            request.addHeader("cookie",jessionid);
        }
    }

    /**
     * 获取响应头的JESSIONID
     * @param response
     */
   public static Map<String,String> cookies = new HashMap<String, String>();
    private static void getAndStoreCookiesFromResponseHeader(HttpResponse response) {
        //从响应头里取出名为set-cookie的响应头
        Header setCookieHeader = response.getFirstHeader("Set-cookie");
        //如果不为空
        if(setCookieHeader != null){
            //取出此响应头的值
            String cookievalue = setCookieHeader.getValue();
            if(cookievalue !=null && cookievalue.trim().length()>0){
                //以分号来切分
                String[] cookiepairs = cookievalue.split(";");
                if(cookiepairs !=null){
                    for (String cookiepair:cookiepairs) {
                        //如果包含了JESSIONID，则表示响应头里有会话id这个数据
                        if(cookiepair.contains("JSEEIONID")){
                            //保存到map
                            cookies.put("JESSIONID",cookiepair);
                        }
                    }
                }
            }
        }
    }

    /**
     * 发送get请求
     * @param url
     * @param params
     * @return
     */
    public static String doGet(String url,Map<String,String> params){
        Set<String> keys = params.keySet();
        int mark = 1;
        for (String key:keys) {
            if(mark == 1){
                url = url +("?"+key+"="+params.get(key));
            }else {
                url = url +("&"+key+"="+params.get(key));
            }
            mark++;
        }
        //指定接口提交方式
        HttpGet get = new HttpGet(url);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        addCookieInRequestHeaderBeforeRequest(get);
        String responseresult = "";
        try {
            //发起请求并拿到响应数据
            CloseableHttpResponse httpresponse = httpClient.execute(get);
            //拿到响应头的jessionid
            getAndStoreCookiesFromResponseHeader(httpresponse);
            int statusCode = httpresponse.getStatusLine().getStatusCode();
            responseresult = EntityUtils.toString(httpresponse.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseresult;

    }

    /**
     * 封装接口请求防方式，根据用例中的请求类型选择post或get
     * @param url
     * @param type
     * @param params
     * @return
     */
    public static String doService(String url,String type,Map<String,String> params){
        String result ="";
        //忽略大小写比较接口方式
        if("post".equalsIgnoreCase(type)){
            result = HttpUtil.doPost(url, params);
        }else if("get".equalsIgnoreCase(type)){
            result = HttpUtil.doGet(url, params);
        }
        return result;

    }


}
