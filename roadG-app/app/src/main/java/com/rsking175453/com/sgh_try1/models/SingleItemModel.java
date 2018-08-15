package com.rsking175453.com.sgh_try1.models;

import android.content.Intent;

/**
 * Created by user on 08/03/2018.
*/
public class SingleItemModel {

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    private String id;
    private String locationLong;
    private String locationLat;
    private String taluka;
    private String district;
    private String estimatedTime;
    private String status;
    private String roadType;
    private String grivType;
    private String state;
    private String url;
    private String discription;
    private String time;
    private String complainId;
    private int isEmergency;
    private String officerName;
    private String officerId;

    public String getComplainId() {
        return complainId;
    }
    public String getComment() {
        return comment;
    }
    private String comment;
    public String getEmail() {
        return email;
    }
    private String email;
    public String getEstimatedTime() {
        return estimatedTime;
    }
    public String getTime() {
        return time;
    }
    public int getIsEmergency() {
        return isEmergency;
    }
    public String getOfficerName() {
        return officerName;
    }
    public String getOfficerId() {
        return officerId;
    }
    public String getLocationLong() {
        return locationLong;
    }
    public String getLocationLat() {
        return locationLat;
    }
    public String getTaluka() {
        return taluka;
    }
    public String getDistrict() {
        return district;
    }
    public String getStatus() {
        return status;
    }
    public String getRoadType() {
        return roadType;
    }
    public String getGrivType() {
        return grivType;
    }
    public String getState() {
        return state;
    }
    public String getDiscription() {
        return discription;
    }
    public String getUrl() {
        return url;
    }

    public SingleItemModel() {}

    public void setLocationLong(String locationLong) {
        this.locationLong = locationLong;
    }

    public void setLocationLat(String locationLat) {
        this.locationLat = locationLat;
    }

    public void setTaluka(String taluka) {
        this.taluka = taluka;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRoadType(String roadType) {
        this.roadType = roadType;
    }

    public void setGrivType(String grivType) {
        this.grivType = grivType;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setComplainId(String complainId) {
        this.complainId = complainId;
    }

    public void setIsEmergency(int isEmergency) {
        this.isEmergency = isEmergency;
    }

    public void setOfficerName(String officerName) {
        this.officerName = officerName;
    }

    public void setOfficerId(String officerId) {
        this.officerId = officerId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public SingleItemModel(String url, String discription, String locationLong, String locationLat, String area, String city, String state, String roadType, String grivType, String status, String time , int e, String id, String name, String comment, String estimatedTime, String email, String complainId) {

        this.url = url;
        this.discription=discription;
        this.locationLong=locationLong;
        this.locationLat=locationLat;
        this.taluka=area;
        this.district=city;
        this.state=state;
        this.roadType=roadType;
        this.grivType=grivType;
        this.status=status;
        this.officerId = id;
        this.officerName = name;
        this.isEmergency = e;
        this.time = time;
        this.comment = comment;
        this.complainId = complainId;
        this.email = email;
        this.estimatedTime = estimatedTime;
    }


    public void setUrl(String url) {
        this.url = url;
    }




}