package com.example.myapplication.bean.testjson;

import android.util.Log;

import java.util.List;

/**
 * Date: 2021/4/25
 * Description:
 * @author jie.zhou
 */
public class ApplianceShowBean {

    private final static String TAG = "ApplianceShowBean";

    private ApplianceBean appliance;
    private String applianceId;
    private List<ApplianceBean> subApplianceList;
    private ApShowBase apView;
    /**
     * 设备类型
     */
    private String type;

    public ApplianceBean getAppliance() {
        return appliance;
    }
    public void setAppliance(ApplianceBean appliance) {
        this.appliance = appliance;
    }
    public String getApplianceId() {
        return applianceId;
    }
    public void setApplianceId(String applianceId) {
        this.applianceId = applianceId;
    }
    public List<ApplianceBean> getSubApplianceList() {
        return subApplianceList;
    }
    public void setSubApplianceList(List<ApplianceBean> subApplianceList) {
        this.subApplianceList = subApplianceList;
    }
    public ApShowBase getApView() {
        return apView;
    }
    public void setApView(ApShowBase apView) {
        this.apView = apView;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }


    public static ApplianceShowBean create(ApplianceBean mainBean) {
        ApplianceShowBean showBean = new ApplianceShowBean();

        showBean.setType(mainBean.getApplianceType());
        showBean.setAppliance(mainBean);
        showBean.setApplianceId(mainBean.getApplianceId());

        return showBean;
    }

    /**
     * 外部调用,更新apview数据
     */
    public void updateApAttribute(String apId, String attr) {
        //子装置更新
        if(apId.contains("_")) {
            try{
                String[] data = apId.split("_");
                int index = Integer.parseInt(data[1]);
                index = index - 1;
                ApplianceBean subBean = getSubApplianceList().get(index);
                subBean.setAttributes(attr);
            }
            catch (Exception e) {
                Log.e(TAG, "updateApAttribute error: " + e.toString());
            }
        }
        else {
            getAppliance().setAttributes(attr);
        }
        getApView().setData(getAppliance(), getSubApplianceList());
    }

}
