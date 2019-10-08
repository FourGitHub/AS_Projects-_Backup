package com.mredrock.cyxbs.freshman.DataBean;

import com.google.gson.annotations.SerializedName;
import com.mredrock.cyxbs.freshman.DataBean.SaveBean.DescribeBean;

import java.util.List;

/**
 * Created by FengHaHa on2018/8/14 0014 17:04
 */
public class Description {

    /**
     * index : 入学必备
     * describe : [{"content":"仅供测试：录取通知书的描述\n第二行描述\n第三行描述","id":1,"name":"录取通知书","property":"必需"},{"content":"仅供测试：身份证的描述\n换行测试：第二行内容\n换行测试：第三行内容","id":2,"name":"身份证","property":"必需"},{"content":"仅供测试：高考准考证的描述\n换行测试：第二行内容\n换行测试：第三行内容","id":4,"name":"高考准考证","property":"必需"},{"content":"仅供测试：《新生适应性资料》学习心得的描述\n换行测试：第二行内容\n换行测试：第三行内容","id":5,"name":"《新生适应性资料》学习心得","property":"必需"},{"content":"仅供测试：同版近期证件照15张的描述\n换行测试：第二行内容\n换行测试：第三行内容","id":6,"name":"同版近期证件照15张","property":"必需"},{"content":"仅供测试：《学生管理与学生自律协议书》的描述\n换行测试：第二行内容\n换行测试：第三行内容","id":7,"name":"《学生管理与学生自律协议书》","property":"必需"},{"content":"仅供测试：《致2018级新生的一封信》的描述\n换行测试：第二行内容\n换行测试：第三行内容","id":8,"name":"《致2018级新生的一封信》","property":"必需"},{"content":"仅供测试：社会实践报告的描述\n换行测试：第二行内容\n换行测试：第三行内容","id":9,"name":"社会实践报告","property":"必需"},{"content":"仅供测试：团员证的描述\n换行测试：第二行内容\n换行测试：第三行内容","id":10,"name":"团员证","property":"必需"},{"content":"仅供测试：转团组织关系资料的描述\n换行测试：第二行内容\n换行测试：第三行内容","id":11,"name":"转团组织关系资料","property":"必需"},{"content":"仅供测试：学籍档案的描述\n换行测试：第二行内容\n换行测试：第三行内容","id":12,"name":"学籍档案","property":"非必需"},{"content":"仅供测试：本人户口本内页的描述\n换行测试：第二行内容\n换行测试：第三行内容","id":13,"name":"本人户口本内页","property":"非必需"},{"content":"仅供测试：《常住人口登记表》的描述\n换行测试：第二行内容\n换行测试：第三行内容","id":14,"name":"《常住人口登记表》","property":"非必需"},{"content":"仅供测试：本人户口迁移证的描述\n换行测试：第二行内容\n换行测试：第三行内容","id":15,"name":"本人户口迁移证","property":"非必需"},{"content":"仅供测试：免冠一寸登记表的描述\n换行测试：第二行内容\n换行测试：第三行内容","id":16,"name":"免冠一寸登记表","property":"非必需"},{"content":"仅供测试：党员档案的描述\n换行测试：第二行内容\n换行测试：第三行内容","id":17,"name":"党员档案","property":"非必需"},{"content":"仅供测试：转党组织关系相关资料的描述\n换行测试：第二行内容\n换行测试：第三行内容","id":18,"name":"转党组织关系相关资料","property":"非必需"},{"content":"仅供测试：困难证明的原件和复印件的描述\n换行测试：第二行内容\n换行测试：第三行内容","id":19,"name":"困难证明的原件和复印件","property":"非必需"}]
     */

    private String index;
    @SerializedName("describe")
    private List<DescribeBean> data;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public List<DescribeBean> getData() {
        return data;
    }

    public void setData(List<DescribeBean> data) {
        this.data = data;
    }

    /**
     * Created by FengHaHa on2018/8/15 0015 16:41
     */

}
