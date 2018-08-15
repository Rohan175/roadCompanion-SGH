package com.rsking175453.com.sgh_try1.models;

/**
 * Created by user on 08/03/2018.
*/
public class SingleItemModel {


    private String locationLong;
    private String locationLat;
    private String taluka;
    private String district;
    private String status;
    private String roadType;
    private String grivType;
    private String state;
    private String url;
    private String discription;
    private String time;

    private int isEmergency;
    private String officerName;
    private String officerId;

    public String getComplainId() {
        return complainId;
    }

    private String complainId;

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

    private String estimatedTime;




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

    public SingleItemModel() {
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

    public SingleItemModel(String url, String discription, String locationLong, String locationLat, String area, String city, String state, String roadType, String grivType,  String status,String time , int e, String id,String name,String comment, String estimatedTime,String email,String complainId) {

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


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}