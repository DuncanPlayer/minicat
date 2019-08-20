package com.messi.web.minicat.reflection;

import com.google.gson.Gson;
import com.messi.web.minicat.utils.JSONResult;
import java.lang.reflect.Method;

public class ReflectMethod {

    public static String reflectMethod(String info,Object[] params){
        String[] cm = info.split(",");
        String className = cm[0];
        String methodName = cm[1];
        Class<?>[] parameterTypes = null;
        Object result = "";
        try {
            Class<?> classObj = Class.forName(className);
            Method method = null;
            if (null == params){
                method = classObj.getMethod(methodName);
            }else {
                Method[] methods = classObj.getMethods();
                for (int i = 0;i < methods.length;i++){
                    if (methods[i].getName().equals(methodName)){
                        parameterTypes= methods[i].getParameterTypes();
                    }
                }
                method = classObj.getMethod(methodName,parameterTypes);
                for (int i = 0;i < parameterTypes.length;i++){
                    Object objParam = params[i];
                    Class<?> paramtype = parameterTypes[i];
                    //匹配
                    if (paramtype.getName().contains("String")){
                        params[i] = objParam.toString();
                    }else if (paramtype.getName().contains("Integer")){
                        params[i] = Integer.parseInt(objParam.toString());
                    }else if (paramtype.getName().contains("Long")){
                        params[i] = Long.parseLong(objParam.toString());
                    }
                }
            }
            method.setAccessible(true);

            Object obj = classObj.newInstance();
            if (null == params){
                result = method.invoke(obj);
            }else {
                result = method.invoke(obj,params);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Gson().toJson(new JSONResult(result));
    }


}
