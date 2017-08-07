package com.qktang.rxAndroid.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;

/**
 * 提供各种拦截器
 * Created by qktang on 17-7-31.
 */
public class Interceptors {

    /**
     * 打印用
     */
    public static final class LoggerInterceptor implements Interceptor {
        private String tag;

        public LoggerInterceptor(String tag) {
            this.tag = tag;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            Log.i(tag, String.format("Sending request %s on %s%n%s\n%s",
                    request.url(), chain.connection(), request.headers(), request.body()));

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            Log.i(tag, String.format("Received response for %s in %.1fms%n%s\n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers(), request.body()));
            return response;
        }
    }

    /**
     * 缓存拦截器，自动修改请求头和响应头<br>
     * 需要多做处理，部分接口不需要缓存
     */
    public static final class CacheInterceptor implements Interceptor {
        private static final int MAX_AGE = 60;//一分钟
        private static final int MAX_SRALE = 31536000;//一年

        private Context context;

        public CacheInterceptor(Context context) {
            this.context = context.getApplicationContext();
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!isNetworkConnecting(context)) {
                //没有网络时强制从缓存里读取数据
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }

            Response response = chain.proceed(request);
            if (isNetworkConnecting(context)) {
                //max-age单位为s
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + MAX_AGE)
                        .build();
            } else {
                //max-stale单位为s
                response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + MAX_SRALE)
                        .build();
            }
            return response;
        }
    }

    /**
     * 加密拦截器，有的api可能需要在头部加上部分加密
     */
    public static final class EncryptInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            return null;
        }
    }

    private static final boolean isNetworkConnecting(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.isAvailable());
    }


    static class GzipRequestInterceptor implements Interceptor{
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            if (originalRequest.body() == null || originalRequest.header("Content-Encoding") != null) {
                return chain.proceed(originalRequest);
            }

            Request compressedRequest = originalRequest.newBuilder()
                    .header("Accept-Encoding","gzip")
//                    .header("Content-Encoding","gzip")
//                    .method(originalRequest.method(),gzip(originalRequest.body())) //服务器并非所有支持压缩请求
                    .build();
            return chain.proceed(compressedRequest);
        }

        private RequestBody gzip(final RequestBody body){
            return new RequestBody() {
                @Override
                public MediaType contentType() {
                    Log.d("GzipRequestInterceptor", "gzip!");
                    return body.contentType();
                }
                @Override
                public long contentLength() throws IOException {
                    return -1;
                }
                @Override
                public void writeTo(BufferedSink sink) throws IOException {
                    BufferedSink gzipSink = Okio.buffer(new GzipSink(sink));
                    body.writeTo(gzipSink);
                    gzipSink.close();
                }
            };
        }
    }

}
