package com.example.myapplication;

import com.alibaba.fastjson.JSON;
import com.example.myapplication.bean.testjson.ApplianceBean;
import com.example.myapplication.bean.testjson.ApplianceShowBean;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuechou.zhang
 * @since 2021/7/19
 */
public class UniteTest {


    @Test
    public void runJsonTest() {
        ApplianceBean applianceBean1 = new ApplianceBean();
        ApplianceBean applianceBean2 = new ApplianceBean();
        List<String> actionList1 = new ArrayList<>();
        actionList1.add("switch1");
        actionList1.add("sensor1");
        actionList1.add("lighter1");
        actionList1.add("air_condition1");
        List<String> actionList2 = new ArrayList<>();
        actionList2.add("switch2");
        actionList2.add("sensor2");
        actionList2.add("lighter2");
        actionList2.add("air_condition2");

        applianceBean1.setActions(actionList1);
        applianceBean2.setActions(actionList2);

        applianceBean1.setApplianceId("applianceBean1");
        applianceBean2.setApplianceId("applianceBean2");

        applianceBean1.setApplianceType("type 1");
        applianceBean2.setApplianceType("type 2");

        //====================show bean=============
        ApplianceShowBean applianceShowBean1 = new ApplianceShowBean();
        ApplianceShowBean applianceShowBean2 = new ApplianceShowBean();

        applianceShowBean1.setAppliance(applianceBean1);
        applianceShowBean2.setAppliance(applianceBean2);

        applianceShowBean1.setApplianceId("applianceShowBean1");
        applianceShowBean2.setApplianceId("applianceShowBean2");

        applianceShowBean1.setType("type applianceShowBean1");
        applianceShowBean2.setType("type applianceShowBean2");

        ApplianceBean applianceBean3 = new ApplianceBean();
        applianceBean3.setApplianceId("applianceBean3");
        ApplianceBean applianceBean4 = new ApplianceBean();
        applianceBean4.setApplianceId("applianceBean4");
        ApplianceBean applianceBean5 = new ApplianceBean();
        applianceBean5.setApplianceId("applianceBean5");
        ApplianceBean applianceBean6 = new ApplianceBean();
        applianceBean6.setApplianceId("applianceBean6");
        ApplianceBean applianceBean7 = new ApplianceBean();
        applianceBean7.setApplianceId("applianceBean7");

        List<ApplianceBean> applianceBeanList = new ArrayList<>();
        applianceBeanList.add(applianceBean3);
        applianceBeanList.add(applianceBean4);
        applianceBeanList.add(applianceBean5);
        applianceBeanList.add(applianceBean6);
        applianceBeanList.add(applianceBean7);
        applianceShowBean1.setSubApplianceList(applianceBeanList);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("1", JSON.toJSONString(applianceShowBean1));
            jsonObject.put("2", JSON.toJSONString(applianceShowBean2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String jsonStr = jsonObject.toString();
        System.out.println(jsonStr);

        //反转回去
        JSONObject jsonObject1;
        try {
            jsonObject1 = new JSONObject(jsonStr);
            String jsonObject2 = jsonObject1.optString("1");
            String jsonObject3 = jsonObject1.optString("2");
            ApplianceShowBean applianceShowBean = JSON.parseObject(jsonObject2, ApplianceShowBean.class);
            ApplianceShowBean applianceShowBean3 = JSON.parseObject(jsonObject3, ApplianceShowBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("hello");
    }
}
