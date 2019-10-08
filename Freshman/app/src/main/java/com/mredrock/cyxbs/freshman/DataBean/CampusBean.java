package com.mredrock.cyxbs.freshman.DataBean;

import com.google.gson.annotations.SerializedName;
import com.mredrock.cyxbs.freshman.DataBean.SaveBean.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2018/8/14 0014.
 */

public class CampusBean {


    /**
     * array : [{"content":"大西北背靠中心食堂，面朝着宁静苑4舍，同中心食堂一般，因着优越的地理位置成为了学生上课早餐首选。靠着北方师傅的一手好厨艺，虽说是地处川渝地区，但也能品味到一两点奢侈的北方滋味。不同于其他食堂的是，大西北食堂的空间较小，堂食的学生较少，更多的青睐来自于它极为迅敏的打包速度。\n推荐菜目：羊肉泡馍，大盘鸡盖饭","id":1,"name":"大西北","picture":[]},{"content":"红高粱食堂伫立于兴业苑食堂上方，红高粱食堂总具有自己的风格，不断推陈出新，在这里你可以品尝到新的变化！\n推荐菜目：冒菜，花溪米线，土耳其烤肉饭","id":2,"name":"红高粱","picture":["/picture/23ab201149194fab9f4cbf1bca4db208.jpg"]},{"content":"千喜鹤怕是重邮最具重庆味道的食堂了，不过这品味的不是麻辣鲜香的那一味，而是重庆高低井然错落有致的建筑风格，除去对着明理苑的正门，你同时还可以从延生内部和楼下的楼梯直上。倘若说在食堂中挑一个最喜欢的，那千喜鹤一定是高居榜首的那一个，相比于食堂，千喜鹤更像是一个美食广场，从西式快餐到中式早点，从日常正餐到饭后甜点，可谓是应有尽有。\n推荐菜目：火锅米线，豆浆记忆，盐酥鸡","id":3,"name":"千喜鹤","picture":["/picture/0b8d41bb2921469780d9e706aa993210.jpg","/picture/16edf12dd21b4a758b7bc9dd30ca6bc3.jpg","/picture/84e256ae4bdb4f9ba31c69413855c911.jpg"]},{"content":"兴业苑食堂作为老一派的食堂，与中心食堂一样，不断沉淀自己的重庆味道，色香味俱全的家烧菜肴，总有一款美味属于家的味道。\n推荐菜目：青椒炒肉丝，回锅肉，辣子鸡丁","id":4,"name":"兴业苑食堂","picture":["/picture/a70f805fc6254adbbc4f2b53e476e45f.jpg","/picture/9a2fde4918124eaebf9ae027587be46a.jpg","/picture/99b9317e591c4554bc258a8b18fa79dc.jpg"]},{"content":"被宁静苑和知行苑两相环绕，算的是颇为优越的地理位置。没有富丽堂皇的过分修饰，多的是一览无余的简单明了。亲切，日常，这两个词来形容延生食堂最合适不过了，亲切的是带有两三分家长里短的味道，日常的是朝朝暮暮交错间穿梭的身影。\n推荐菜目：冒菜，鲜榨豆浆，羊肉粉","id":5,"name":"延生食堂","picture":[]},{"content":"中心食堂：\n与雨红莲广场毗连，又与风华操场隔道相望的它，由于与二教距离甚近，也常常是同学们打包就餐的不二选择。它以低廉的价格和繁多的种类，在同学们的心中占有绝对地位。\n推荐菜目：早餐时段售卖的麻团，糍粑和荤素包子，糖醋里脊","id":6,"name":"中心食堂","picture":["/picture/7dce7ec4aff04caea1fd0487ed87623f.jpg"]}]
     * index : 学生食堂
     */
    private String name;
    private String index;
    @SerializedName("array")
    private List<DataBean> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }


    /**
     * Created by FengHaHa on2018/8/18 0018 16:37
     */
    public static class DataBean extends BaseBean {
        /**
         * content : 大西北背靠中心食堂，面朝着宁静苑4舍，同中心食堂一般，因着优越的地理位置成为了学生上课早餐首选。靠着北方师傅的一手好厨艺，虽说是地处川渝地区，但也能品味到一两点奢侈的北方滋味。不同于其他食堂的是，大西北食堂的空间较小，堂食的学生较少，更多的青睐来自于它极为迅敏的打包速度。
         * 推荐菜目：羊肉泡馍，大盘鸡盖饭
         * id : 1
         * name : 大西北
         * picture : []
         */
        private String property;
        private String content;
        private int id;
        private String name;
        @SerializedName("picture")
        private List<String> pictureList;

        public DataBean(String property, String content, int id, String name, List<String> pictureList) {
            this.property = property;
            this.content = content;
            this.id = id;
            this.name = name;
            this.pictureList = pictureList;
        }

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }

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

        public List<String> getPictureList() {
            return pictureList;
        }

        public void setPictureList(List<String> pictureList) {
            this.pictureList = pictureList;
        }

        @Override
        public BaseBean getBean() {
            return new DataBean(property,content,id,name,pictureList);
        }
    }
}
