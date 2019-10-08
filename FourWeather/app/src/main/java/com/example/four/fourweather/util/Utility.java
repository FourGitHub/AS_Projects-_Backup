package com.example.four.fourweather.util;

import android.text.TextUtils;
import android.util.Log;

import com.example.four.fourweather.R;
import com.example.four.fourweather.db.City;
import com.example.four.fourweather.db.County;
import com.example.four.fourweather.db.Province;
import com.example.four.fourweather.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/2/19 0019.
 */

public class Utility {

    /**
     * 将返回的JSON数据解析成Weather实体类
     */
    private static final String TAG = "Utility";

    /**
     * 解析和保存服务器返回的省级数据保存到Province表中
     */
    public static boolean handleProvinceResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allProvinces = new JSONArray(response);
                for (int i = 0; i < allProvinces.length(); i++) {
                    JSONObject provinceObject = allProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析和保存服务器返回的市级数据保存到City表中
     */
    public static boolean handleCityResponse(String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCities = new JSONArray(response);
                for (int i = 0; i < allCities.length(); i++) {
                    JSONObject cityObject = allCities.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析和保存服务器返回的县级数据到Couny表中
     */
    public static boolean handleCountyResponse(String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCounties = new JSONArray(response);
                for (int i = 0; i < allCounties.length(); i++) {
                    JSONObject countyObject = allCounties.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * @param response
     * 返回的JSON数据大体提示如下
     * {
     *     "HeWeather6":[
     *     {
     *         "status":"ok",
     *         "basic" : {},
     *         "aqi" : {},
     *         "now" : {},
     *         "suggestion" : {},
     *         "daily_forecast":[]
     *     }
     *   ]
     * }
     * @return 将所需的JSON数据解析保存在一个Weather对象中，返回该Weather对象
     * {}可以看做一个JSONObject
     * []可以看做一个JSONArray
     * 关于我对这个方法中先使用JSONObject再使用GSON的理解，其一，肯定是因为GSON更方便。其二，是为了分离出
     * {
     *         "status":"ok",
     *         "basic" : {},
     *         "aqi" : {},
     *         "now" : {},
     *         "suggestion" : {},
     *         "daily_forecast":[]
     *  }
     *  这一部分的内容，由jsonArray.getJSONObject(0)就可以看出。因为定义的Weather类只能保存如上格式的JSON数据，
     *  而不是最初的返回格式。
     */
    public static Weather handleWeatherResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            Log.i(TAG, "哈哈哈allWeatherContentLength: " + jsonArray.length());
            return new Gson().fromJson(weatherContent, Weather.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int matchWeatherIocnCode(String weatherIocnCode) {
        switch (weatherIocnCode) {
            case "100":
                return R.drawable.icon_100;
            case "100n":
                return R.drawable.icon_100n;
            case "101":
                return R.drawable.icon_101;
            case "102":
                return R.drawable.icon_102;
            case "103":
                return R.drawable.icon_103;
            case "103n":
                return R.drawable.icon_103n;
            case "104":
                return R.drawable.icon_104;
            case "104n":
                return R.drawable.icon_104n;
            case "200":
                return R.drawable.icon_200;
            case "201":
                return R.drawable.icon_201;
            case "202":
                return R.drawable.icon_202;
            case "203":
                return R.drawable.icon_203;
            case "204":
                return R.drawable.icon_204;
            case "205":
                return R.drawable.icon_205;
            case "206":
                return R.drawable.icon_206;
            case "207":
                return R.drawable.icon_207;
            case "208":
                return R.drawable.icon_208;
            case "209":
                return R.drawable.icon_209;
            case "210":
                return R.drawable.icon_210;
            case "211":
                return R.drawable.icon_211;
            case "212":
                return R.drawable.icon_212;
            case "213":
                return R.drawable.icon_213;
            case "300":
                return R.drawable.icon_300;
            case "300n":
                return R.drawable.icon_300n;
            case "301":
                return R.drawable.icon_301;
            case "301n":
                return R.drawable.icon_301n;
            case "302":
                return R.drawable.icon_302;
            case "303":
                return R.drawable.icon_303;
            case "304":
                return R.drawable.icon_304;
            case "305":
                return R.drawable.icon_305;
            case "306":
                return R.drawable.icon_306;
            case "307":
                return R.drawable.icon_307;
            case "309":
                return R.drawable.icon_309;
            case "310":
                return R.drawable.icon_310;
            case "311":
                return R.drawable.icon_311;
            case "312":
                return R.drawable.icon_312;
            case "313":
                return R.drawable.icon_313;
            case "400":
                return R.drawable.icon_400;
            case "401":
                return R.drawable.icon_401;
            case "402":
                return  R.drawable.icon_402;
            case "403":
                return  R.drawable.icon_403;
            case "404":
                return  R.drawable.icon_404;
            case "405":
                return  R.drawable.icon_405;
            case "406":
                return  R.drawable.icon_406;
            case "406n":
                return  R.drawable.icon_406n;
            case "407":
                return  R.drawable.icon_407;
            case "407n":
                return  R.drawable.icon_407n;
            case "500":
                return  R.drawable.icon_500;
            case "501":
                return  R.drawable.icon_501;
            case "502":
                return  R.drawable.icon_502;
            case "503":
                return  R.drawable.icon_503;
            case "504":
                return  R.drawable.icon_504;
            case "507":
                return  R.drawable.icon_507;
            case "508":
                return  R.drawable.icon_508;
            case "900":
                return  R.drawable.icon_900;
            case "901":
                return  R.drawable.icon_901;
            default:
                return R.drawable.icon_999;
        }
    }

}

// 由Log得到返回的完整JSON数据格式如下,只是选择性的解析我想要的那部分数据。
