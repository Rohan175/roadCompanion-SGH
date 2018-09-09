package com.rsking175453.com.sgh_try1;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.rsking175453.com.sgh_try1.models.Complain;
import com.rsking175453.com.sgh_try1.models.User;

public class sharedPreference {

    //the constants
    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
    private static final String SHARED_PREF_NAME2 = "simplifiedcodingsharedpref2";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_TYPE = "keygender";
    private static int validate = 0;
    private static final String KEY_ID = "keyid";
    private static String[] categoryArray = {"Pot Holes","Fallen_Tree"};
    private static sharedPreference mInstance;
    private static Context mCtx;

    private static final String imageUrl = "hello";
    private static String discription = null;

    public static String getImageUrl() {
        return imageUrl;
    }
    public static String getDiscription() {
        return discription;
    }

    public static void setDiscription(String discription) {
        sharedPreference.discription = discription;
    }

    public static String getDate() {
        return date;
    }

    public static void setDate(String date) {
        sharedPreference.date = date;
    }

    public static String getGriType() {
        return griType;
    }

    public static void setGriType(String griType) {
        sharedPreference.griType = griType;
    }

    public static String getLacLoc() {
        return lacLoc;
    }

    public static void setLacLoc(String lacLoc) {
        sharedPreference.lacLoc = lacLoc;
    }

    public static int getIsEmergency() {
        return isEmergency;
    }

    public static void setIsEmergency(int isEmergency) {
        sharedPreference.isEmergency = isEmergency;
    }

    private static String date = null;
    private static String griType = null;
    private static String lacLoc = null;
    private static int isEmergency = 0;



    private sharedPreference(Context context) {
        mCtx = context;
    }

    public static synchronized sharedPreference getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new sharedPreference(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_TYPE, user.gettype());
        editor.apply();
    }

    public void complainAdd(Complain c){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME2, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(String.valueOf(isEmergency), c.getEmergency());
        editor.putString(griType, c.getGriType());
        editor.putString(imageUrl, c.getImageUrl());
        editor.putString(lacLoc, c.getLatLoc());
        editor.putString(discription, c.getDiscription());
        editor.apply();
    }

    public void setValidate(int x){
        validate = x;
    }


    public  int getValidate(){
        return validate;
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    public void setCategoryArray(String[] s){
        categoryArray = s;
    }

    public String[] getCategoryArray(){
        return categoryArray;
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_TYPE, null)

        );
    }


    public Complain getComplain(){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Complain(
                sharedPreferences.getString(imageUrl, "https://api.uploadcare.com/files/cec882b6-eee3-47d5-9052-8b4a707ec9d8/"),
                sharedPreferences.getString(discription, "hello"),
                sharedPreferences.getString(lacLoc, "23.7334.72.789423"),
                sharedPreferences.getInt(String.valueOf(isEmergency), 0),
                sharedPreferences.getString(griType, "POT_HOLE")
        );
    }


    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Intent intent=new Intent(mCtx, LoginActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        mCtx.startActivity(intent);


    }
}