package com.example.four.fourweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2018/2/19 0019.
 * LitePal中的每一个实体类都必须要继承自DataSupport类
 *
 * @Province 代表省
 */

public class Province extends DataSupport {

    private int id;

    private String provinceName;

    private int provinceCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }
}
