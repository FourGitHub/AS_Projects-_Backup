package com.mredrock.cyxbs.freshman.DataBean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by FengHaHa on2018/8/18 0018 14:03
 */
public class SexRatioBean {
    public int getFemaleAmount() {
        return femaleAmount;
    }

    public void setFemaleAmount(int femaleAmount) {
        this.femaleAmount = femaleAmount;
    }

    public int getMaleAmount() {
        return maleAmount;
    }

    public void setMaleAmount(int maleAmount) {
        this.maleAmount = maleAmount;
    }

    @SerializedName("female_amount")
    private int femaleAmount;
    @SerializedName("male_amount")
    private int maleAmount;

}
