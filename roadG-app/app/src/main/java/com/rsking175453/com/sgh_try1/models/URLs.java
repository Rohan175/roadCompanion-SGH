package com.rsking175453.com.sgh_try1.models;

public class URLs {

    public static String ROOT_URL = "https://roadg.herokuapp.com/";
    //private static final String ROOT_URL = "http://172.20.10.9:8000/";
    //public static String ROOT_URL = "http://192.168.43.149:8000/";

    public static  String URL_REGISTER = ROOT_URL + "Signup";
    public static  String URL_LOGIN= ROOT_URL + "Login";
    public static  String URL_COMPLAIN= ROOT_URL + "Complaint";
    public static  String User_complain= ROOT_URL + "UserComplaints";
    public static  String Sorted_data= ROOT_URL + "SortedData";
    public static  String Category = ROOT_URL + "TypesOfGrievances";
    public static  String STATUS_CHANGE= ROOT_URL + "ComplaintStatusChange";//user id ....complain id..officer id...new status
    public static String UPTATED_COMPLAIN = ROOT_URL + "UptatedComplaint";
    public static String BISAG_API = "https://ncog.gov.in/RNB_mob_data/get_road?code=4862&lat=";//23.4654&lon=";
    public static String SIR_API = "https://quiztime-induction.herokuapp.com/road_api/?flag=o&dbName=quizMania_roads&lat=";//23.121640&lon=72.617236";
    public static void changeRoot(String url){

        ROOT_URL = url;
        URL_REGISTER = url + "Signup";
        URL_LOGIN= url + "Login";
        URL_COMPLAIN= url + "Complaint";
        User_complain= url + "UserComplaints";
        Sorted_data= url + "SortedData";
        Category = url + "TypesOfGrievances";
        STATUS_CHANGE= url + "ComplaintStatusChange";
        UPTATED_COMPLAIN = url + "uptatedComplaint";
    }


}