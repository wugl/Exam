package com.wgl.exam.uti;

import com.wgl.exam.bean.ReturnWithData;
import com.wgl.exam.bean.ReturnWithoutData;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Common {

    private static Common instance;


    private static ReturnWithData RETURN_DATA_GET_FAIL;

    private static ReturnWithoutData RETURN_DATA_UPDATE_OK;
    private static ReturnWithoutData RETURN_DATA_UPDATE_FAIL;

    private static ReturnWithoutData RETURN_DATA_DELETE_OK;
    private static ReturnWithoutData RETURN_DATA_DELETE_FAIL;


    public static ReturnWithoutData RETURN_DATA_INSERT_OK;
    public static ReturnWithoutData RETURN_DATA_INSERT_FAIL;

    public static Common getInstance() {
        if (instance == null) {
            instance = new Common();
        }
        return instance;
    }

    private Common() {
        RETURN_DATA_GET_FAIL = new ReturnWithData("获取失败！", "fail", null);

        RETURN_DATA_UPDATE_OK = new ReturnWithoutData("更新失败！", "ok");
        RETURN_DATA_UPDATE_FAIL = new ReturnWithoutData("更新失败！", "fail");

        RETURN_DATA_DELETE_OK = new ReturnWithoutData("删除失败！", "ok");
        RETURN_DATA_DELETE_FAIL = new ReturnWithoutData("删除失败！", "fail");


        RETURN_DATA_INSERT_OK = new ReturnWithoutData("插入失败！", "ok");
        RETURN_DATA_INSERT_FAIL = new ReturnWithoutData("插入失败！", "fail");
    }

    public static ReturnWithData getReturnDataGetFail() {
        return RETURN_DATA_GET_FAIL;
    }

    public ReturnWithoutData getReturnDataUpdateOk() {
        return RETURN_DATA_UPDATE_OK;
    }

    public ReturnWithoutData getReturnDataUpdateFail() {
        return RETURN_DATA_UPDATE_FAIL;
    }

    public ReturnWithoutData getReturnDataDeleteOk() {
        return RETURN_DATA_DELETE_OK;
    }

    public ReturnWithoutData getReturnDataDeleteFail() {
        return RETURN_DATA_DELETE_FAIL;
    }

    public ReturnWithoutData getReturnDataInsertOk() {
        return RETURN_DATA_INSERT_OK;
    }

    public ReturnWithoutData getReturnDataInsertFail() {
        return RETURN_DATA_INSERT_FAIL;
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
