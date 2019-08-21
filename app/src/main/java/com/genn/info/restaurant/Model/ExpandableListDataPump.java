package com.genn.info.restaurant.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {

    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> cricket = new ArrayList<String>();
        cricket.add("USER1");
        cricket.add("USER2");
        cricket.add("USER3");
        cricket.add("USER4");
        cricket.add("USER5");
        cricket.add("USER6");
        cricket.add("USER7");



        expandableListDetail.put("USER PRIVILEGES CONSOLE", cricket);

        return expandableListDetail;
    }
}
