package com.mredrock.cyxbs.freshman.DataBean;

import com.mredrock.cyxbs.freshman.DataBean.SaveBean.BaseBean;

/**
 * Created by Administrator on 2018/8/9 0009.
 */

public class  RuXueBean extends BaseBean {
    private boolean isDone;
    private boolean isZhanKai ;


    private boolean isEditable ;


    private boolean isWillDeleted;
    private boolean isCustom;
    private String itemName;
    private String itemMore;


    public RuXueBean(boolean isDone, boolean isZhanKai, String itemName, String itemMore, boolean isEditable, boolean isWillDeleted,boolean isCustom) {
        this.isDone = isDone;
        this.isZhanKai = isZhanKai;
        this.itemName = itemName;
        this.itemMore = itemMore;
        this.isEditable = isEditable;
        this.isWillDeleted = isWillDeleted;
        this.isCustom = isCustom;
    }
    @Override
    public BaseBean getBean() {
        return new RuXueBean(isDone(),isZhanKai(),itemName,itemMore,isEditable(),isWillDeleted(),isCustom);
    }

    public boolean isCustom() {
        return isCustom;
    }

    public void setCustom(boolean custom) {
        isCustom = custom;
    }
    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemMore() {
        return itemMore;
    }

    public void setItemMore(String itemMore) {
        this.itemMore = itemMore;
    }

    public boolean isZhanKai() {
        return isZhanKai;
    }

    public void setZhanKai(boolean zhanKai) {
        isZhanKai = zhanKai;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
    }

    public boolean isWillDeleted() {
        return isWillDeleted;
    }

    public void setWillDeleted(boolean willDeleted) {
        isWillDeleted = willDeleted;
    }


}
