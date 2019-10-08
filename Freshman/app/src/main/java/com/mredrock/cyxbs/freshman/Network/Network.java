package com.mredrock.cyxbs.freshman.Network;

import com.mredrock.cyxbs.freshman.Utility.Const;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.internal.schedulers.SchedulerWhen;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/8/8 0008.
 * 获取Retrifit实例
 */

public class Network {
    private static Retrofit retrofit;

    public static Api getApi() {
        if (retrofit == null) {
            Retrofit.Builder builder = new Retrofit.Builder();
            retrofit = builder.baseUrl(Const.API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit.create(Api.class);
    }

    public static <T> void sendRequest(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        .subscribe(observer);
    }
}
