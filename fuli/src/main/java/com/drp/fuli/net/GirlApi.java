package com.drp.fuli.net;

import com.drp.fuli.model.GirlData;

import io.reactivex.Observable;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author durui
 * @date 2021/3/11
 * @description
 */
public interface GirlApi {

    @GET("data/福利/20/{page}")
    Observable<Result<GirlData>> fetchPrettyGirl(@Path("page") int page);
}
