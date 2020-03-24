package com.api.auto.cases;

import com.api.auto.utils.ExcelUtil;
import com.api.auto.utils.HttpUtil;
import org.testng.annotations.Test;

import java.util.HashMap;

/**
 * @ClassName: RegisterCase_V2
 * @Description: TODO
 * @author: Doge_fang
 * @date: 2020/3/23  12:26
 *
 * 测试用例v2版本，增加读取excel数据的方法，数据提供者调用方法自动获取excel中全部的测试数据
 */
public class RegisterCase_V2 {
    @Test(dataProvider = "datas")
    public void testDemo2(String mobilephone,String pwd){
        String url = "https://www.reg007.com/account/signup";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("mobilephone",mobilephone);
        params.put("pwd",pwd);
        HttpUtil.doPost(url,params);
    }

    /**
     * 数据提供者通过调用ExcelUtil中的datas方法，自动获取excel用例中的所有连续测试数据
     * @return
     */
    public Object[][] datas(){
        Object[][] datas = ExcelUtil.datas("src/test/resources/apitest_case_v1.xlsx",2,6,6,7);
        return datas;
    }
}
