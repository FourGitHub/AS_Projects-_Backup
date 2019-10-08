package com.example.four.fourweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/2/20 0020.
 * "basic":{
 *     "city":"苏州"
 *     "id":"CN101190401"
 *     "updata":{
 *         "loc":"2018-2-20"
 *     }
 * }
 */

public class Basic {

    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update {

        @SerializedName("loc")
        public String updateTime;

    }

}
