package com.example.four.fourweather.gson;

/**
 * Created by Administrator on 2018/2/20 0020.
 * "aqi":{
 *     "city":{
 *         "aqi" : "44"
 *         "pm25" : "13"
 *     }
 * }
 */

public class AQI {

    public AQICity city;

    public class AQICity {

        public String aqi;

        public String pm25;

        public String qlty;

    }


}

