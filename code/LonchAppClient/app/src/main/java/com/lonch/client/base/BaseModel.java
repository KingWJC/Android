package com.lonch.client.base;

import com.lonch.client.http.Http;
import com.lonch.client.http.HttpService;

/**
 * Created by bai on 2018/04/10.
 * 23:13
 * com.example.gs.mvpdemo.base
 */

public class BaseModel {
    protected static HttpService httpService;

    //初始化httpService
    static {
        httpService = Http.getInstance().getApiService(HttpService.class);
    }

}
