package com.api.auto.pojo;

/**
 * @ClassName: Rest
 * @Description: TODO
 * @author: Doge_fang
 * @date: 2020/3/23  16:20
 */
public class Rest {
    private String ApiId;
    private String ApiName;
    private String Type;
    private String Url;

    public String getApiId() {
        return ApiId;
    }

    public void setApiId(String apiId) {
        ApiId = apiId;
    }

    public String getApiName() {
        return ApiName;
    }

    public void setApiName(String apiName) {
        ApiName = apiName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
