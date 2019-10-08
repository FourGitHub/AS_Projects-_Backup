package com.mredrock.cyxbs.freshman.Network;

import com.mredrock.cyxbs.freshman.DataBean.CampusBean;
import com.mredrock.cyxbs.freshman.DataBean.Description;
import com.mredrock.cyxbs.freshman.DataBean.JunXunFCBean;
import com.mredrock.cyxbs.freshman.DataBean.OnlineBean;
import com.mredrock.cyxbs.freshman.DataBean.SexRatioBean;
import com.mredrock.cyxbs.freshman.DataBean.SubjectBean;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2018/8/8 0008.
 * 请求接口
 */

public interface Api {
//    @GET("data/describe/getamount")
//    Call<CampusInfoBean> getCampusInfo(@Query("index") String index);

    @GET("search/school/1")
    Observable<SexRatioBean> getSexRatioByCollege(@Query("name") String collegeName);

    @GET("search/school/2")
    Observable<SubjectBean> getMostDifficultSubject(@Query("name") String collegeName);

    @GET("search/school/getname")
    Call getAllCollegeNames();

    @GET("search/chatgroup/getgroup")
    Call getAllGroupNumbers(@Query("index") String groupType);

    @GET("search/chatgroup/abstractly")
    Observable<OnlineBean> getGroup(@Query("index") String groupType, @Query("key") String key);

    @GET("data/get/describe")
    Observable<Description> getDescribe(@Query("index") String type);

    @GET("data/get/junxun")
    Observable<JunXunFCBean> getJunxunFC();

    @GET("data/get/byindex")
    Observable<CampusBean> getCampus(@Query("index") String index, @Query("pagenum") int pageNum, @Query("pagesize") int pageSize);

    @GET("data/get/sushe")
    Observable<CampusBean> getDormitory(@Query("name") String dormitoryName);


}
