package com.drp.fuli.net;

import com.drp.fuli.App;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 使用单例
 *
 * @author DRP
 */

public class ApiService {

    /**
     * 读取超时时间
     */
    private static final int READ_TIME_OUT = 10;

    /**
     * 连接超时时间
     */
    private static final int CONNECT_TIME_OUT = 30;
    /**
     * 服务器地址（干货集中营的）
     */
    private static final String BASE_URL = "http://gank.io/api/";

    private final Boolean DEBUG = true;
    private final GirlApi mApi;

    private static class Holder {

        private static final ApiService INSTANCE = new ApiService();
    }

    public static ApiService getInstance() {
        return Holder.INSTANCE;
    }

    private ApiService() {
        mApi = init();
    }

    private GirlApi init() {
        File cacheFile = new File(App.application.getCacheDir(), "HttpCache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 20); // 20M

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(new CacheInterceptor())
                .addNetworkInterceptor(new CacheInterceptor())
                .cache(cache);

        if (DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(GirlApi.class);
    }

    public GirlApi getGirlApi() {
        return mApi;
    }
}
