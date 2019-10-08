package com.mredrock.cyxbs.freshman.DataBean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by FengHaHa on2018/8/17 0017 23:43
 */
public class OnlineBean {

    /**
     * array : [{"areaName":"重庆铜梁","array1":[{"code":"198472776","areaName":"重庆铜梁老乡群"}]},{"areaName":"重庆永川","array1":[{"code":"467050041","areaName":"重庆永川老乡群"}]},{"areaName":"重庆巫溪","array1":[{"code":"143884210","areaName":"重庆巫溪老乡群"}]},{"areaName":"重庆纂江","array1":[{"code":"109665788","areaName":"重庆纂江老乡群"}]},{"areaName":"重庆涪陵","array1":[{"code":"199748999","areaName":"重庆涪陵老乡群"}]},{"areaName":"重庆黔江","array1":[{"code":"102897346","areaName":"重庆黔江老乡群"}]},{"areaName":"重庆梁平","array1":[{"code":"85423833","areaName":"重庆梁平老乡群"}]},{"areaName":"重庆大足","array1":[{"code":"462534986","areaName":"重庆大足老乡群"}]},{"areaName":"重庆彭水","array1":[{"code":"283978475","areaName":"重庆彭水老乡群"}]},{"areaName":"重庆开州","array1":[{"code":"5657168","areaName":"重庆开州老乡群"}]},{"areaName":"重庆长寿","array1":[{"code":"594337683","areaName":"重庆长寿老乡群"}]},{"areaName":"重庆奉节","array1":[{"code":"50078959","areaName":"重庆奉节老乡群"}]},{"areaName":"重庆南川","array1":[{"code":"423494314","areaName":"重庆南川老乡群"}]},{"areaName":"重庆忠县","array1":[{"code":"115637967","areaName":"重庆忠县老乡群"}]},{"areaName":"重庆武隆","array1":[{"code":"123122421","areaName":"重庆武隆老乡群"}]},{"areaName":"重庆石柱","array1":[{"code":"289615375","areaName":"重庆石柱老乡群"}]},{"areaName":"重庆丰都","array1":[{"code":"343292119","areaName":"重庆丰都老乡群"}]},{"areaName":"重庆云阳","array1":[{"code":"118971621","areaName":"重庆云阳老乡群"}]},{"areaName":"重庆璧山","array1":[{"code":"112571803","areaName":"重庆璧山老乡群"}]}]
     * index : 老乡群
     */

    private String index;
    @SerializedName("array")
    private List<GroupBean> groupList;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public List<GroupBean> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<GroupBean> groupList) {
        this.groupList = groupList;
    }

    public static class GroupBean {
        /**
         * areaName : 重庆铜梁
         * array1 : [{"code":"198472776","areaName":"重庆铜梁老乡群"}]
         */

        @SerializedName("name")
        private String areaName;
        @SerializedName("array1")
        private List<NumberBean> groupNumberList;

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public List<NumberBean> getGroupNumberList() {
            return groupNumberList;
        }

        public void setGroupNumberList(List<NumberBean> groupNumberList) {
            this.groupNumberList = groupNumberList;
        }

        public static class NumberBean {
            /**
             * code : 198472776
             * areaName : 重庆铜梁老乡群
             */

            @SerializedName("code")
            private String groupNumber;
            @SerializedName("areaName")
            private String groupName;

            public String getGroupNumber() {
                return groupNumber;
            }

            public void setGroupNumber(String groupNumber) {
                this.groupNumber = groupNumber;
            }

            public String getGroupName() {
                return groupName;
            }

            public void setGroupName(String groupName) {
                this.groupName = groupName;
            }
        }
    }
}
