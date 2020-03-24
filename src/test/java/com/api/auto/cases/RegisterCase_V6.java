package com.api.auto.cases;

import com.alibaba.fastjson.JSONObject;
import com.api.auto.utils.CaseUtil;
import com.api.auto.utils.ExcelUtil;
import com.api.auto.utils.HttpUtil;
import com.api.auto.utils.RestUtil;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * @ClassName: RegisterCase_V2
 * @Description: TODO
 * @author: Doge_fang
 * @date: 2020/3/23  12:26
 *
 *
 * 使用json格式编写测试用例的参数，并用fastjson解析
 * 修改了测试用例的编写，使用V3版本测试用例
 * 调用了接口请求方式的封装方法
 *
 */

public class RegisterCase_V6 {
    @Test(dataProvider = "datas")
    public void testDemo2(String apiId,String parameter){
        //url
        String url = RestUtil.getUrlByApiId(apiId);
        //type
        String type = RestUtil.getTypeByApiId(apiId);
        //使用fastjson解析参数,放入Map集合
        Map<String,String> params = (Map<String, String>) JSONObject.parse(parameter);
        HttpUtil.doService(url,type,params);
    }

    /**
     * 调用ExcelUtil类的datas方法，获取用例中指定的行列数据
     * @return
     */
    public Object[][] datas(){
        String[] cellNames = {"ApiId","Params"};
        Object[][] datas = CaseUtil.getCaseDatasByApiId("1", cellNames);
        return datas;
    }
}
