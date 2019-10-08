package com.example.four.fourweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/2/20 0020.
 * "daily_forecast":[
 * {
 *     "data":"2018-2-20"
 *     "tmp":{
 *         "max":"11"
 *         "min":"9"
 *     }
 *     "cond":{
 *         "txt_d":"阵雨"
 *     }
 * },
 * {
 *     "date":"2018-2-21"
 *     "tmp":{
 *         "max":"11"
 *         "min":"9"
 *     }
 *     "cond":{
 *         "txt_d":"阵雨"
 *     }
 * },
 * ...
 *]
 *
 * 返回的JSON数据类型是一个数组，针对这种特殊情况，只需要定义当日天气的实体类就可以了，然后在声明Weather实体类的时候用数组表示
 */

public class Forecast {

    @SerializedName("date")
    public String date;

    @SerializedName("tmp")
    public Temperature temperature;

    @SerializedName("cond")
    public More more;

    public class Temperature {

        public String max;

        public String min;

    }

    public class More {

        @SerializedName("txt_d")
        public String info;

    }

}
