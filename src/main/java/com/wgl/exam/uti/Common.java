package com.wgl.exam.uti;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.security.MessageDigest;

public class Common {

    private static Common instance;

    public static Common getInstance() {
        if (instance == null) {
            instance = new Common();
        }
        return instance;
    }

    private Common() {

    }
    public static void createTitle(HSSFWorkbook workbook, HSSFSheet sheet,String[] titles) {
        HSSFRow row = sheet.createRow(0);
        //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度

        sheet.setColumnWidth(0, 10 * 256);
        for(int index=1;index<titles.length;index++) {
            sheet.setColumnWidth(index, 40 * 256);
           // sheet.setColumnWidth(1, 40 * 256);
        }

        //设置为居中加粗
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);

        HSSFCell cell;
        int i=0;
        for(String item:titles){
            cell = row.createCell(i);
            cell.setCellValue(item);
            cell.setCellStyle(style);
            i++;
        }



    }



    public static String EncoderByMd5(String str)
//            throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
//         MessageDigest md5 = MessageDigest.getInstance("MD5");
//        BASE64Encoder base64en = new BASE64Encoder();
//        //加密后的字符串
//        String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
//        return newstr;

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");

            byte[] byteArray = str.getBytes("UTF-8");

            byte[] md5Bytes = md5.digest(byteArray);
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = md5Bytes[i] & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
    }

}
