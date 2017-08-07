package com.qktang.rxAndroid.api;

import android.content.Context;

import com.qktang.rxAndroid.api.service.LoginService;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by qktang on 2017/7/31.
 */
public class ApiFactory {

    private static final long CACHE_SIZE = 100 * 1024 * 1024;

    private static Context mContext;

    private static final ConcurrentHashMap<Class, ApiFactory> apiCache = new ConcurrentHashMap<>();

    private Retrofit mRetrofit;
    private Cache mCache;
    private OkHttpClient mOkHttpClient;
    private Object mService;

    public static <T> T getService(Class<T> classType) {
        ApiFactory apiFactory = apiCache.get(classType);
        if (apiFactory == null) {
            apiFactory = new ApiFactory(classType);
            apiCache.putIfAbsent(classType, apiFactory);
        }
        return (T) apiFactory.getService();
    }

    public static void setAppContext(Context context) {
        mContext = context.getApplicationContext();
    }

    public ApiFactory(Class classType) {
        initClient();
        Retrofit.Builder builder = new Retrofit.Builder()
//                .baseUrl(getBaseUrl(classType))
                .client(mOkHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        if (LoginService.class.equals(classType)) {
            builder.baseUrl(LoginService.BASE_URL);
        } else return;
        mRetrofit = builder.build();
        mService = mRetrofit.create(classType);

    }

    private void initClient() {

        mCache = new Cache(mContext.getCacheDir(), CACHE_SIZE);

        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptors.LoggerInterceptor("App"))  //应用拦截
//                .addInterceptor(new Interceptors.CacheInterceptor(mContext))//缓存
                .addInterceptor(new Interceptors.GzipRequestInterceptor()) //Gzip压缩
                .addNetworkInterceptor(new Interceptors.LoggerInterceptor("Network"))//网络拦截
//                .authenticator()  //验证拦截器
//                .certificatePinner()//添加证书用的
//                .connectionPool() //根据需求定义以恶连接池
                .connectTimeout(20, TimeUnit.SECONDS)//连接超时，默认10s
                .readTimeout(10, TimeUnit.SECONDS)//读取超时
                .writeTimeout(10,TimeUnit.SECONDS)// 写入超时，默认10s
//                .followRedirects(true) //是否自动重定向，默认true
                .build();


    }

    /**
     * 反射获取baseUrl
     * @param c 需要反射的类
     * @return baseUrl
     */
    public String getBaseUrl(Class c) {
        String url = null;
        try {
            Field baseUrlField = c.getField("BASE_URL");
            url = (String) baseUrlField.get(c);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return url;
    }

    public <T> T create(Class<T> t) {
        return mRetrofit.create(t);
    }

    public Object getService() {
        return mService;
    }

    public void clearCache() {
        if (mCache != null) {
            try {
                mCache.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
