package com.api.auto.pojo;

/**
 * @ClassName: WriteBackData
 * @Description: TODO
 * @author: Doge_fang
 * @date: 2020/3/23  19:21
 *
 * 将回写信息封装为对象，最后全部一次性回写
 */
public class WriteBackData {
    private String sheetName;
    private String caseId;
    private String cellName;
    private String result;

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCellName() {
        return cellName;
    }

    public void setCellName(String cellName) {
        this.cellName = cellName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
