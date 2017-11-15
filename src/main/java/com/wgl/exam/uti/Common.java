package com.wgl.exam.uti;

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
