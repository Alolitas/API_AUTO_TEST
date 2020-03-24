package com.api.auto.cases;

import com.api.auto.utils.HttpUtil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;

/**
 * @ClassName: RegisterCase
 * @Description: TODO
 * @author: Doge_fang
 * @date: 2020/3/23  10:36
 */


public class RegisterCase_V1 {
    /**
     * 注册测试
     * @param mobilephone  由数据提供者提供测试数据
     * @param pwd  由数据提供者提供测试数据
     */
    @Test(dataProvider = "datas")
    public void testDemo(String mobilephone,String pwd){
        String url = "https://www.reg007.com/account/signup";
        //将测试数据封装为map集合
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("mobilephone",mobilephone);
        params.put("pwd",pwd);
        //调用请求方法发送请求
        HttpUtil.doPost(url,params);
    }

    /**
     * 数据提供者
     * @return 返回需要测试的多种数据
     */
    @DataProvider
    public Object[][] datas(){
        Object[][] datas = {
                            {"15895806533",""},
                            {"","123456"},
                            {"456","123456"},
                            {"15895806533","123"}

                            };
        return datas;
    }
}
