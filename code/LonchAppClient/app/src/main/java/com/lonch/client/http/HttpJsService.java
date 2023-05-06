package com.lonch.client.http;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Url;

public interface HttpJsService {
    //JS公共接口
//    @HTTP(method = "{method}")
    @POST("")
    Call<ResponseBody> jsGetData(@Url String url, @Header("ACCESS-TOKEN") String token, @HeaderMap Map<String, String> headers, @Body RequestBody loginRequest);
    @GET("")
    Call<ResponseBody> jsGetGetData(@Url String url, @Header("ACCESS-TOKEN") String token, @HeaderMap Map<String, String> headers);
    @Multipart
    @POST("")
    Call<ResponseBody> jsMultipart(@Url String url, @Header("ACCESS-TOKEN") String token, @HeaderMap Map<String, String> headers, @PartMap Map<String, RequestBody> requestBodyMap);
}
