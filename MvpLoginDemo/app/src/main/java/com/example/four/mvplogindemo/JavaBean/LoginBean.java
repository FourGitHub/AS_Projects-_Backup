package com.example.four.mvplogindemo.JavaBean;

/**
 * Created by Administrator on 2018/6/15 0015.
 */

public class LoginBean {

    private int status;
    private String info;
    private int state;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private String stunum;

        public String getStunum() {
            return stunum;
        }

        public void setStunum(String stunum) {
            this.stunum = stunum;
        }
    }
}
