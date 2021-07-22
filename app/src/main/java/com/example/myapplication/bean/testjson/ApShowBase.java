package com.example.myapplication.bean.testjson;

import android.content.Context;
import android.util.Log;

import com.example.myapplication.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Author: jie.zhou
 * Date: 2021/4/25
 * Description:
 */
public class ApShowBase {

    public static final String TAG  = "APShow";
    protected List<ApplianceBean> subApplianceList;
    protected ApplianceBean mainAppliance;
    protected Context context;

    public ApShowBase() {
        context = Utils.getApplicationContext();
    }

    /************************************UI相关****************************************/

    /**
     * 列表页面布局是占一格还是两格
     * @return
     */
    public int spanSize() {
        return 1;
    }

    /**
     * item点击事件
     */
    protected void onRootViewClick() {

    }


    /************************************UI相关****************************************/


    /************************************数据相关****************************************/
    public void setData(ApplianceBean mainBean, List<ApplianceBean> applianceList) {
        this.subApplianceList = applianceList;
        this.mainAppliance = mainBean;
        onUpdateAttributeData();
    }

    protected boolean attrOnline;
    public boolean attrPower;
    protected void onUpdateAttributeData() {
        if(mainAppliance != null) {
            int subApplianceSize = 0;
            if(subApplianceList != null) {
                subApplianceSize = subApplianceList.size();
            }
            Log.d(TAG, "onUpdateAttributeData type: " + mainAppliance.getApplianceType() +
              ", subSize: " + subApplianceSize +
              ", id: " + mainAppliance.getApplianceId() +
              ", attribute: " + mainAppliance.getAttributes()
            );
            try {
                //{"onlineState":{"value":"online"},"powerState":{"value":"on"}}
                JSONObject attObj = new JSONObject(mainAppliance.getAttributes());
                JSONObject onlineState = attObj.optJSONObject("onlineState");
                JSONObject powerState = attObj.optJSONObject("powerState");

                String onlineStateValue = "";
                if(onlineState != null) {
                    onlineStateValue = onlineState.optString("value");
                }

                String powerStateValue = "";
                if(powerState != null) {
                    powerStateValue = powerState.optString("value");
                }

                if(onlineStateValue.equals("offline")) {
                    attrOnline = false;
                }
                else if(onlineStateValue.equals("online")){
                    attrOnline = true;
                }

                if(powerStateValue.equals("off")) {
                    attrPower = false;
                    mainAppliance.setPowerOn(false);
                }
                else if(powerStateValue.equals("on")) {
                    attrPower = true;
                    mainAppliance.setPowerOn(true);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            Log.w(TAG, "onUpdateAttributeData mainAppliance null");
        }
    }
    /************************************数据相关****************************************/
}
