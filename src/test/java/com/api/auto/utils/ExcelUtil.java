package com.api.auto.utils;

import com.api.auto.pojo.Case;
import com.api.auto.pojo.Rest;
import com.api.auto.pojo.WriteBackData;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: EcelUtil
 * @Description: TODO
 * @author: Doge_fang
 * @date: 2020/3/23  11:05
 */
public class ExcelUtil {
    public static Map<String,Integer> caseIdRownumMapping = new HashMap<String, Integer>();
    public static Map<String,Integer> cellNameCellnumMapping = new HashMap<String, Integer>();


    static {
        loadRwnumAndCellnumMapping("src/test/resources/apitest_case_v4.xlsx","用例");
    }

    /**
     * 获取caseId以及它对应的行索引
     * 获取cellName以及它对应的列索引
     * @param excelPath
     * @param sheetName
     */
    private static void loadRwnumAndCellnumMapping(String excelPath,String sheetName) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(excelPath));
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheet(sheetName);
            //获取第一行（标题行）
            Row titleRow = sheet.getRow(0);
            if(titleRow !=null&&!isEmptyRow(titleRow)){
                short lastCellNum = titleRow.getLastCellNum();
                //循环处理标题行的每一列
                for(int i=0;i <lastCellNum;i++){
                    Cell cell = titleRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    String title = cell.getStringCellValue();
                    title = title.substring(0,title.indexOf("("));
                    //获得标题行的每列列号
                    int column = cell.getAddress().getColumn();
                    //将标题和对应列号放入集合
                    cellNameCellnumMapping.put(title,column);
                }
            }
            //从第二行开始，获取所有的数据行
            int lastRowNum = sheet.getLastRowNum();
            //循环拿到每个数据行
            for(int i=1;i<=lastRowNum;i++){
                Row dataRow = sheet.getRow(i);
                Cell firstCellOfRow = dataRow.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                firstCellOfRow.setCellType(CellType.STRING);
                //获得caseId列
                String casId = firstCellOfRow.getStringCellValue();
                int rowNum = dataRow.getRowNum();
                caseIdRownumMapping.put(casId,rowNum);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 回写接口响应报文
     * @param excelPath
     * @param sheetName
     * @param caseId
     * @param cellName
     */
    public static void writeBackData(String excelPath,String sheetName,String caseId,String cellName,String result){
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(new File(excelPath));
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheet(sheetName);
            Integer rownum = caseIdRownumMapping.get(caseId);
            Row row = sheet.getRow(rownum);
            Integer cellnumm = cellNameCellnumMapping.get(cellName);
            //设置格式
            Cell cell = row.getCell(cellnumm, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            cell.setCellType(CellType.STRING);
            //单元格中设值,传入的result
            cell.setCellValue(result);
            //最后写入
            outputStream = new FileOutputStream(new File(excelPath));
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
                try {
                    if(inputStream != null) {
                        inputStream.close();
                    }
                    if(outputStream != null){
                        outputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }


    /**
     * 获取excel中连续的测试数据
     * @param excelpath  excel路径
     * @param startrow  开始的行号
     * @param endrow
     * @param startcell  开始的列号
     * @param endcell
     * @return
     */
    public static Object[][] datas(String excelpath, int startrow, int endrow, int startcell, int endcell) {
        Object[][] datas = null;
        try {
            //获取workbook对象
            Workbook workbook = WorkbookFactory.create(new File(excelpath));
            //获取sheet对象
            Sheet sheet = workbook.getSheet("用例");
            //获取行,将excel用例内容转为一个几行几列的数组
            datas = new Object[endrow - startrow + 1][endcell - startcell + 1];
            //循环遍历数组
            for (int i = startrow; i < endrow; i++) {
                //获取行
                Row row = sheet.getRow(i - 1);
                for (int j = startcell; j < endcell; j++) {
                    //获取列
                    Cell cell = row.getCell(j - 1);
                    //将列设置为字符串类型
                    cell.setCellType(CellType.STRING);
                    String value = cell.getStringCellValue();
                    datas[i - startrow][j - startcell] = value;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datas;
    }

    /**
     * 获取excel中指定的行列数据
     * @param excelpath
     * @param rows  需要取数据的行范围
     * @param cells 需要取数据的列范围
     * @return
     */
    public static Object[][] datas(String excelpath,int[] rows,int[] cells){
        Object[][] datas = null;
        try {
            //获取workbook对象
            Workbook workbook = WorkbookFactory.create(new File(excelpath));
            //获取sheet对象
            Sheet sheet = workbook.getSheet("用例");
            //定义保存数据的数组
            datas = new Object[rows.length][cells.length];
            //通过循环获取每一行
            for(int i =0;i<rows.length;i++){
                //根据行索引取出一行
                Row row = sheet.getRow(rows[i] - 1);
                for(int j =0;j<cells.length;j++){
                    //获取列
                    Cell cell = row.getCell(cells[j] - 1);
                    //将列设置为字符串类型
                    cell.setCellType(CellType.STRING);
                    String values = cell.getStringCellValue();
                    datas[i][j]=values;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datas;
    }


    /**
     * 解析Excel中指定表单的数据，封装为对象
     * @param excelPath
     * @param sheetName
     */
    public static <T> void excelLoad(String excelPath, String sheetName,Class<T> clazz){
        //加载类
        //Class<Case> caseClass = Case.class;
        try {
            //创建workbook对象
            Workbook wokbook = WorkbookFactory.create(new File(excelPath));
            Sheet sheet = wokbook.getSheet(sheetName);
            //获取第一行标题行
            Row titleRow = sheet.getRow(0);
            //获取最后一列的列号
            int lastCellNum = titleRow.getLastCellNum();
            String[] fields = new String[lastCellNum];
            //循环处理每一列，取出每一列里面的字段名，保存到数组
            for(int i=0;i<lastCellNum;i++){
                //根据列索引获取对应的列
                Cell cell = titleRow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                //设置列的类型为字符串
                cell.setCellType(CellType.STRING);
                //获取标题列的值
                java.lang.String title = cell.getStringCellValue();
                //截取列名括号前的英文即可
                title = title.substring(0, title.indexOf("("));
                //存入数组
                fields[i] = title;
            }
            //获取最后一行行索引
            int lastRowNum = sheet.getLastRowNum();
            //循环处理每一行数据
            for(int i=1;i<=lastRowNum;i++){
                //实例化对象
                Object object = clazz.getDeclaredConstructor().newInstance();
                //拿到一个数据行
                Row datarow = sheet.getRow(i);
                //判断Excel是否有空行
                if(datarow==null||isEmptyRow(datarow)){
                    continue;
                }
                //拿到此数据行上面的每一列，将数据封装到cs对象
                for(int j =0;j<lastCellNum;j++){
                    Cell cell = datarow.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    String value = cell.getStringCellValue();
                    //获取要反射的方法名,set+标题英文名，与Case类的set方法名一致
                    String methodName = "set" + fields[i];
                    //获取要反射的方法对象
                    Method method = clazz.getMethod(methodName, String.class);
                    //完成反射调用,数据全部封装到对象中
                    method.invoke(object,value);

                }
                //判断对象类型
                if(object instanceof Case){
                    Case cs = (Case) object;
                    //将对象添加到集合里
                    CaseUtil.cases.add(cs);
                }else if(object instanceof Rest){
                    Rest rs = (Rest) object;
                    RestUtil.rests.add(rs);
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private static boolean isEmptyRow(Row datarow) {
        short lastCellNum = datarow.getLastCellNum();
        for(int i=0;i<lastCellNum;i++){
            Cell cell = datarow.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            cell.setCellType(CellType.STRING);
            String values = cell.getStringCellValue();
            if(values !=null&&values.trim().length()>0){
                return false;
            }
        }
        return true;
    }

    /**
     * 批量数据回写
     * @param excelpath
     */
    public static List<WriteBackData> writeBackDatas = new ArrayList<WriteBackData>();
    public static void batchWriteBackDatas(String excelpath){
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = new FileInputStream(new File(excelpath));
            Workbook workbook = WorkbookFactory.create(inputStream);
            for (WriteBackData writeBackData:writeBackDatas) {
                //获取sheetname
                String sheetName = writeBackData.getSheetName();
                Sheet sheet = workbook.getSheet(sheetName);
                String caseId = writeBackData.getCaseId();
                Integer rownum = caseIdRownumMapping.get(caseId);
                Row row = sheet.getRow(rownum);
                String cellName = writeBackData.getCellName();
                Integer cellnum = cellNameCellnumMapping.get(cellName);
                Cell cell = row.getCell(cellnum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                cell.setCellType(CellType.STRING);
                String result = writeBackData.getResult();
                cell.setCellValue(result);
            }
            outputStream = new FileOutputStream(new File(excelpath));
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

                try {
                    if(outputStream != null) {
                        outputStream.close();
                    }
                    if(inputStream != null){
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

    }
        

}
