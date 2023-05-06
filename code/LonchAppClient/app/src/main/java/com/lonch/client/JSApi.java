package com.lonch.client;

import android.webkit.JavascriptInterface;

import org.json.JSONException;

public class JSApi {
    private CallbackMethod method;

    public JSApi(CallbackMethod method) {
        this.method = method;
    }

    @JavascriptInterface
    public void webCallApp(String msg) throws JSONException {
        method.webCallApp(msg);
    }
}
