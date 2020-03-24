package com.api.auto.utils;

import com.api.auto.pojo.Case;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: CaseUtil
 * @Description: TODO
 * @author: Doge_fang
 * @date: 2020/3/23  14:42
 */
public class CaseUtil {

    public static List<Case> cases = new ArrayList<Case>();
    static {
        //调用excelLoad方法将所有数据解析封装到Cases
        ExcelUtil.excelLoad("src/test/resources/apitest_case_v3.xlsx","用例",Case.class);
    }

    public static Object[][] getCaseDatasByApiId(String apiId,String[] cellNames){
        Class<Case> caseClass = Case.class;
        //保存指定接口编号的case对象的集合
        ArrayList<Case> caseList = new ArrayList<Case>();
        //通过循环找出指定接口编号对应的这些用例数据
        for (Case cs: CaseUtil.cases) {
            //循环处理
            if(cs.getApiId().equals(apiId)){
                caseList.add(cs);
            }

        }
        Object[][] datas = new Object[caseList.size()][cellNames.length];
        for(int i=0;i<caseList.size();i++){
            Case cs = caseList.get(i);
            for(int j=0;j <cellNames.length;j++){
                //要反射的方法名
                String methodName = "get" + cellNames;
                //获取到反射的方法对象
                Method method;
                try {
                    method = caseClass.getMethod(methodName);
                    Object values = method.invoke(cs);
                    datas[i][j] = values;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
        return datas;
    }
}
