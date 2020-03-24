package com.api.auto.pojo;

/**
 * @ClassName: Case
 * @Description: TODO
 * @author: Doge_fang
 * @date: 2020/3/23  14:35
 *
 * 创建case类，将用例封装为对象
 */
public class Case {
    private String Caseid;
    private String Name;
    private String ApiId;
    private String Desc;
    private String Params;
    private String expectedResponseData;
    private String actualResponseData;

    public String getExpectedResponseData() {
        return expectedResponseData;
    }

    public void setExpectedResponseData(String expectedResponseData) {
        this.expectedResponseData = expectedResponseData;
    }

    public String getActualResponseData() {
        return actualResponseData;
    }

    public void setActualResponseData(String actualResponseData) {
        this.actualResponseData = actualResponseData;
    }

    public String getCaseid() {
        return Caseid;
    }

    public void setCaseid(String caseid) {
        Caseid = caseid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getApiId() {
        return ApiId;
    }

    public void setApiId(String apiId) {
        ApiId = apiId;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getParams() {
        return Params;
    }

    public void setParams(String params) {
        Params = params;
    }

    @Override
    public String toString() {
        return "Case{" +
                "Caseid='" + Caseid + '\'' +
                ", Name='" + Name + '\'' +
                ", ApiId='" + ApiId + '\'' +
                ", Desc='" + Desc + '\'' +
                ", Params='" + Params + '\'' +
                '}';
    }
}
