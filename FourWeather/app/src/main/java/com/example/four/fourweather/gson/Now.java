package com.example.four.fourweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/2/20 0020.
 * "now":{
 * "tmp":"29"
 * "cond":{
 * "txt":"阵雨"
 * }
 * }
 */

public class Now {

    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public More more;

    public class More {

        @SerializedName("txt")
        public String info;

    }

}
