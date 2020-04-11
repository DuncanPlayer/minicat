package com.messi.web.minicat.reflection.test;

import com.google.gson.Gson;
import com.messi.web.minicat.utils.JSONResult;

import java.lang.reflect.Method;

public class ReflectUtils {

    /**
     * 动态方法调用
     * @param info
     * @param params
     * @return
     */
    public static String reflect(String info, Object[] params) {
        String[] cm = info.split(",");
        String className = cm[0];
        String methodName = cm[1];
        Object obj = null;
        Class objClass = null;
        Class<?> ownerClass = null;
        try {
            objClass = Class.forName(className);
            obj = objClass.newInstance();
            ownerClass = obj.getClass();
            Class[] argsClass = new Class[params.length];
            for (int i = 0, j = params.length; i < j; i++) {
                argsClass[i] = params[i].getClass();
            }
            Method method = ownerClass.getMethod(methodName, argsClass);
            Object result = method.invoke(obj,params);
            return new Gson().toJson(new JSONResult(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
