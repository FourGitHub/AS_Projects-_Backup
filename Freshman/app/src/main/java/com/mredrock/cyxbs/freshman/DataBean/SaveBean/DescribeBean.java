package com.mredrock.cyxbs.freshman.DataBean.SaveBean;

/**
 * Created by FengHaHa on2018/8/15 0015 23:06
 */
public  class DescribeBean extends BaseBean {
    /**
     * content : 仅供测试：录取通知书的描述
     * 第二行描述
     * 第三行描述
     * id : 1
     * name : 录取通知书
     * property : 必需
     */

    private String content;
    private int id;
    private String name;
    private String property;


    //自定义属性
    private boolean isChecked;
    private boolean willBeDelete;
    private boolean isOpen;
    private boolean isCustom;



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isWillBeDelete() {
        return willBeDelete;
    }

    public void setWillBeDelete(boolean willBeDelete) {
        this.willBeDelete = willBeDelete;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isCustom() {
        return isCustom;
    }

    public void setCustom(boolean custom) {
        isCustom = custom;
    }



    public DescribeBean(String content, int id, String name, String property, boolean isChecked, boolean willBeDelete, boolean isOpen, boolean isCustom) {
        this.content = content;
        this.id = id;
        this.name = name;
        this.property = property;
        this.isChecked = isChecked;
        this.willBeDelete = willBeDelete;
        this.isOpen = isOpen;
        this.isCustom = isCustom;
    }

    @Override
    public BaseBean getBean() {
        return new DescribeBean(content, id, name, property, isChecked, willBeDelete, isOpen, isCustom);

    }
}
