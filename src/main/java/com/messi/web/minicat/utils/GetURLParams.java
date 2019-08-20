package com.messi.web.minicat.utils;

public class GetURLParams {


    public static Object[] getUrlparams(String uri) {
        //?
        String[] mark = {};
        //&
        String[] mark1 = {};
        try {
             mark= uri.split("\\?");
        }catch (Exception e){
            //没有参数
            return null;
        }
        try {
            //多个参数
            mark1 = mark[1].split("\\&");
        }catch (Exception e){
            return null;
        }

        if (mark1.length > 0) {
            Object[] paramsValue = new Object[mark1.length];
            for (int i = 0;i < mark1.length;i++) {
                String value = mark1[i].split("\\=")[1];
                paramsValue[i] = value;
            }
            return paramsValue;
        }
        return null;
    }

/*    public static void main(String[] args){
        getUrlparams("http://10.200.79.200:9099/usr/login");
    }*/
}
