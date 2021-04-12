package com.drp.gankdrp.network;

import com.drp.gankdrp.App;
import com.drp.gankdrp.BuildConfig;
import com.drp.gankdrp.utils.Constants;

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
 * @author DRP
 */

public class ApiService {

    private final Api mApi;

    private static class Holder {
        private static final ApiService INSTANCE = new ApiService();
    }

    public static ApiService getInstance() {
        return Holder.INSTANCE;
    }

    private ApiService() {
        mApi = init();
    }

    private Api init() {
        File cacheFile = new File(App.application.getCacheDir(), "HttpCache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 20); // 20M

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .readTimeout(Constants.READ_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(Constants.CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(new CacheInterceptor())
                .addNetworkInterceptor(new CacheInterceptor())
                .cache(cache);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(Api.class);
    }

    public Api getApi() {
        return mApi;
    }
}
