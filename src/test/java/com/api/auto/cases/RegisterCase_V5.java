package com.api.auto.cases;

import com.alibaba.fastjson.JSONObject;
import com.api.auto.utils.ExcelUtil;
import com.api.auto.utils.HttpUtil;
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
 */

public class RegisterCase_V5 {
    @Test(dataProvider = "datas")
    public void testDemo2(String apiFromCase,String parameter){
        //将“接口信息”的sheet行列放入数组,需要2~8行，1、4列
        int[] rows = {2,3,4,5,6,7,8};
        int[] cells = {1,4};
        //调用ExcelUtil的datas方法获得sheet中的接口地址
        Object[][] arrydatas = ExcelUtil.datas("src/test/resources/apitest_case_v3.xlsx", rows, cells);
        String url = "";
        String type = "";
        //遍历行
        for (Object[] rowdata:arrydatas) {
            //获取接口信息sheet中的接口编号
            String apiIdFromApiSheet = rowdata[0].toString();
            //与用例sheet中的接口编号、接口类型进行判断
            if(apiIdFromApiSheet.equals(apiFromCase)){
                url = rowdata[1].toString();
                type = rowdata[2].toString();
                break;
            }
        }
        //使用fastjson解析参数,放入Map集合
        Map<String,String> params = (Map<String, String>) JSONObject.parse(parameter);
        HttpUtil.doService(url,type,params);
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
