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
 * 测试用例v3版本，增加读取excel不连续数据的方法，数据提供者调用方法读取指定数据行列范围
 */

public class RegisterCase_V3 {
    @Test(dataProvider = "datas")
    public void testDemo2(String mobilephone,String pwd){
        String url = "https://www.reg007.com/account/signup";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("mobilephone",mobilephone);
        params.put("pwd",pwd);
        HttpUtil.doPost(url,params);
    }

    /**
     * 调用ExcelUtil类的datas方法，获取用例中指定的行列数据
     * @return
     */
    public Object[][] datas(){
        int [] rows = {4,5,6};
        int [] cells = {6,7};
        Object[][] datas = ExcelUtil.datas("src/test/resources/apitest_case_v1.xlsx",rows,cells);
        return datas;
    }
}
