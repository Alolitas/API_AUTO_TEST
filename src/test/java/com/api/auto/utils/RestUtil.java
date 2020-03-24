package com.api.auto.utils;

import com.api.auto.pojo.Rest;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: RestUtil
 * @Description: TODO
 * @author: Doge_fang
 * @date: 2020/3/23  16:19
 */
public class RestUtil {
    public static List<Rest> rests = new ArrayList<Rest>();
    static{
        ExcelUtil.excelLoad("src/test/resources/apitest_case_v3.xlsx","接口信息",Rest.class);
    }

    public static String getUrlByApiId(String apiId){
        for (Rest rest:rests) {
            if(rest.getApiId().equals(apiId)){
                return rest.getUrl();
            }
        }
        return "";
    }

    public static String getTypeByApiId(String apiId){
        for (Rest rest:rests) {
            if(rest.getApiId().equals(apiId)){
                return rest.getType();
            }
        }
        return "";
    }
}
