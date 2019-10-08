package com.mredrock.cyxbs.freshman.DataBean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2018/8/16 0016.
 */

public class CollegeGroupBean {

    /**
     * array : [{"name":"光电工程学院/重庆国际半导体学院","array1":[{"code":"仅供测试：QQ群号码21","name":"光电工程学院/重庆国际半导体学院群1"},
     * {"code":"仅供测试：QQ群号码22","name":"光电工程学院/重庆国际半导体学院群2"},{"code":"仅供测试：QQ群号码23","name":"光电工程学院/重庆国际半导体学院群3"},
     * {"code":"仅供测试：QQ群号码24","name":"光电工程学院/重庆国际半导体学院群4"}]}]
     * index : 学校群
     */

    @SerializedName("index")
    private String groupType;
    @SerializedName("array")
    private List<resultType> resultLists;

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public List<resultType> getResultLists() {
        return resultLists;
    }

    public void setResultLists(List<resultType> resultLists) {
        this.resultLists = resultLists;
    }

    public static class resultType {
        /**
         * name : 光电工程学院/重庆国际半导体学院
         * array1 : [{"code":"仅供测试：QQ群号码21","name":"光电工程学院/重庆国际半导体学院群1"},{"code":"仅供测试：QQ群号码22",
         * "name":"光电工程学院/重庆国际半导体学院群2"},{"code":"仅供测试：QQ群号码23","name":"光电工程学院/重庆国际半导体学院群3"},{"code":"仅供测试：QQ群号码24",
         * "name":"光电工程学院/重庆国际半导体学院群4"}]
         */

        @SerializedName("name")
        private String resultTypeName;
        @SerializedName("array1")
        private List<resultNumberLists> resultTypeLists;

        public String getResultTypeName() {
            return resultTypeName;
        }

        public void setResultTypeName(String resultTypeName) {
            this.resultTypeName = resultTypeName;
        }

        public List<resultNumberLists> getResultTypeLists() {
            return resultTypeLists;
        }

        public void setResultTypeLists(List<resultNumberLists> resultTypeLists) {
            this.resultTypeLists = resultTypeLists;
        }

        public static class resultNumberLists {
            /**
             * code : 仅供测试：QQ群号码21
             * name : 光电工程学院/重庆国际半导体学院群1
             */

            @SerializedName("code")
            private String groupNumber;
            @SerializedName("name")
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
