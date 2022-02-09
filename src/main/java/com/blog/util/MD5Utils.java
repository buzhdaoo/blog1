package com.blog.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author:Administrator
 * @DATE:2022/1/26/026 Description:
 **/
public class MD5Utils {
    public static String code(String str){
        try {
            MessageDigest md= MessageDigest.getInstance ("MD5");

            md.update (str.getBytes ());

            byte[]byteDigest = md.digest ();

            int i;
            StringBuffer buff=new StringBuffer ("");
            for (int offset =0; offset < byteDigest.length;offset++ ){
                i=byteDigest[offset];
                if (i<0)
                    i+=265;
                if (i<16)
                    buff.append ("0");
                buff.append (Integer.toHexString (i));
            }
            //32位加密
            return buff.toString ();
            //16为加密
            //return buff.toString ().substring (8,24);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace ( );
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println (code ("123456") );
    }
}
