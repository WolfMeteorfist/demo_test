package com.example.myapplication.bean.testjson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Author: jie.zhou
 * Date: 2021/4/25
 * Description:
 */
public class ApplianceBean {
    private List<String> actions;//控制方式,例如:TurnOn/TurnOff
    private String applianceId;//装置id
    private String applianceType;//装置类型,例如:SWITCH/SENSOR
    private String description;//装置描述,不重要
    private String friendlyName;//装置名称
    private String group;//家庭组, 一个家庭一套装置,只有扫码绑定时才会下发group,平时不会改变
    private String zone;//装置位置

    /**
     * {
     * "mode":{
     * "value":"COOL"
     * },
     * "onlineState":{
     * "value":"online"
     * },
     * "powerState":{
     * "value":"off"
     * },
     * "speedLevel":{
     * "value":2
     * },
     * "temperature":{
     * "value":"28"
     * }
     * }
     */
    private String attributes;//装置属性

    /**
     * "additionalApplianceDetails":{
     * "category":"wg2",
     * "icon":"https://images.tuyacn.com/smart/icon/1514032164_0.png",
     * "local_key":"29a859594dba764d",
     * "ownerID":"31495399",
     * "product_id":"ddy7a81zb1a9otk1",
     * "product_name":"无线智能Zigbee网关",
     * "status":[
     * <p>
     * ],
     * "sub":false,
     * "uid":"ay16167491938105ik6Q",
     * "uuid":"f97486756c2bbdef"
     * }
     */
    private AdditionalApplianceDetails additionalApplianceDetails;//装置额外数据,设置控制时需要回传


    //此处只用到了sub属性
    public static class AdditionalApplianceDetails {
        private boolean sub;

        public boolean isSub() {
            return sub;
        }

        public void setSub(boolean sub) {
            this.sub = sub;
        }
    }

    public boolean online() {
        JSONObject onlineStateObject;
        try {
            JSONObject jsonObject = new JSONObject(attributes);
            onlineStateObject = jsonObject.optJSONObject("onlineState");
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
        if (onlineStateObject == null) {
            return false;
        }

        return "online".equals(onlineStateObject.optString("value"));
    }

    private boolean isPowerOn;

    public List<String> getActions() {
        return actions;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
    }

    public String getApplianceId() {
        return applianceId;
    }

    public void setApplianceId(String applianceId) {
        this.applianceId = applianceId;
    }

    public String getApplianceType() {
        return applianceType;
    }

    public void setApplianceType(String applianceType) {
        this.applianceType = applianceType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFriendlyName() {
        return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public AdditionalApplianceDetails getAdditionalApplianceDetails() {
        return additionalApplianceDetails;
    }

    public void setAdditionalApplianceDetails(AdditionalApplianceDetails additionalApplianceDetails) {
        this.additionalApplianceDetails = additionalApplianceDetails;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public boolean isPowerOn() {
        return isPowerOn;
    }

    public void setPowerOn(boolean powerOn) {
        isPowerOn = powerOn;
    }

    @Override
    public String toString() {
        return "ApplianceBean{" +
            ", applianceId='" + applianceId + '\'' +
            ", applianceType='" + applianceType + '\'' +
            ", description='" + description + '\'' +
            ", friendlyName='" + friendlyName + '\'' +
            ", group='" + group + '\'' +
            ", zone='" + zone + '\'' +
            ", isPowerOn=" + isPowerOn +
            '}';
    }
}
