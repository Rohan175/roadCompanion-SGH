

package com.rsking175453.com.sgh_try1.models;

import java.util.Collections;

/**
 * Created by shail on 25/3/18.
 */

public class Complain{

    private int emergency;
    private String imageUrl;

    public int getEmergency() {
        return emergency;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDiscription() {
        return discription;
    }

    public String getLatLoc() {
        return latLoc;
    }

    public String getGriType() {
        return griType;
    }

    private String discription;
    private String latLoc;
    private String griType;

    public Complain(String imageUrl,String discription,String latLoc,int emergency,String griType){
        this.discription = discription;
        this.emergency = emergency;
        this.griType = griType;
        this.latLoc = latLoc;
        this.imageUrl = imageUrl;

    }

}
