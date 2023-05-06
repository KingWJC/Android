package com.lonch.client.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {
    private static GsonUtils instance;
    private Gson gson;

    private GsonUtils() {
        gson =new GsonBuilder().disableHtmlEscaping().create();
    }

    public static GsonUtils getInstance() {
        if (instance ==null) {
            instance =new GsonUtils();
        }
        return instance;
    }

    public String toJson(Object object){
        return gson.toJson(object);
    }

    public <T> T fromJson(String json, Class<T> classOfT){
        return gson.fromJson(json, classOfT);
    }
}
