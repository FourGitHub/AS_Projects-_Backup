package com.example.four.fourweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2018/2/20 0020.
 * {
 *     "HeWeather":[
 *     {
 *         "status" : "ok"
 *         "basic" : {},
 *         "aqi" : {},
 *         "now" : {},
 *         "suggesion" : {},
 *         "daily_forecast":{}
 *     }
 *    ]
 * }
 */

public class Weather {

    public String status;

    public Basic basic;

    public AQI aqi;

    public Now now;

    public Suggestion suggestion;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;

}
