package com.api.auto.cases;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.api.auto.utils.ExcelUtil;
import com.api.auto.utils.HttpUtil;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: RegisterCase_V2
 * @Description: TODO
 * @author: Doge_fang
 * @date: 2020/3/23  12:26
 *
 * 测试用例v4版本，增加读取excel不连续数据的方法，数据提供者调用方法读取指定数据行列范围
 * 使用json格式编写测试用例的参数，并用fastjson解析
 */

public class RegisterCase_V4 {
    @Test(dataProvider = "datas")
    public void testDemo2(String parameters){
        String url = "https://www.reg007.com/account/signup";
        //使用fastjson解析参数,放入Map集合
        Map<String,String> params = (Map<String, String>) JSONObject.parse(parameters);
        HttpUtil.doPost(url,params);
    }

    /**
     * 调用ExcelUtil类的datas方法，获取用例中指定的行列数据
     * @return
     */
    public Object[][] datas(){
        int [] rows = {3,4,5};
        int [] cells = {6};
        Object[][] datas = ExcelUtil.datas("src/test/resources/apitest_case_v1.xlsx",rows,cells);
        return datas;
    }
}
