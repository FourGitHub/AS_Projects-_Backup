package com.example.four.webviewtest.POJO;

import java.util.List;

/**
 * Created by Administrator on 2018/2/9 0009.
 */

public class Person2 {
    private List<String> names;

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

//    public void useGSONParseJson2(String json2) {
//        Gson gson = new Gson();
//        Person2 person2 = gson.fromJson(json2, Person2.class);
//        List<String> names = person2.getNames();
//        for (int i = 0; i < names.size(); i++) {
//            String name = names.get(i);
//            Log.i(...);
//        }
//    }
}
