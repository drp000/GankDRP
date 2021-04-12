package com.drp.gankdrp.network;

import com.drp.gankdrp.model.net.GankCategory;
import com.drp.gankdrp.model.net.VersionEntity;
import com.drp.gankdrp.utils.Constants;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author KG on 2017/6/8.
 */

public interface Api {

    @GET("data/{category}/{size}/{page}")
    Observable<GankCategory> getGankListByCategory(@Path("category") String category, @Path("page") int page, @Path("size") int size);

    @GET(Constants.APP_VERSION_URL)
    Observable<VersionEntity> getNowVersion();
}
