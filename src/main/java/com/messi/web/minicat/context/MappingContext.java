package com.messi.web.minicat.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MappingContext {

    /**
     * 静态属性，只会加载一次
     */
    public static class Singontle{
        private static Map<String,String> mapping;
        static {
            mapping = new ConcurrentHashMap<>();
        }
        public static Map<String,String> getInstance(){
            return mapping;
        }
    }

    public static Map<String,String> getInstance(){
        return Singontle.getInstance();
    }

    public static void put(String key,String value){
        Singontle.mapping.put(key,value);
    }

    public static String take(String key){
        return Singontle.mapping.get(key);
    }
}
