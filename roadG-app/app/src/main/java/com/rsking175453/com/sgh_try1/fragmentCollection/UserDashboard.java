package com.rsking175453.com.sgh_try1.fragmentCollection;

import android.Manifest;
import android.app.AlertDialog;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rsking175453.com.sgh_try1.HttpHandker;
import com.rsking175453.com.sgh_try1.R;
import com.rsking175453.com.sgh_try1.databsehandler;
import com.rsking175453.com.sgh_try1.old.TaskCanceler;
import com.rsking175453.com.sgh_try1.models.URLs;
import com.rsking175453.com.sgh_try1.models.User;
import com.rsking175453.com.sgh_try1.input;
import com.rsking175453.com.sgh_try1.models.Complain;
import com.rsking175453.com.sgh_try1.models.SingleItemModel;
import com.rsking175453.com.sgh_try1.sharedPreference;
import com.uploadcare.android.library.api.UploadcareClient;
import com.uploadcare.android.library.api.UploadcareFile;
import com.uploadcare.android.library.callbacks.UploadcareFileCallback;
import com.uploadcare.android.library.exceptions.UploadcareApiException;
import com.uploadcare.android.library.upload.FileUploader;
import com.uploadcare.android.library.upload.Uploader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.android.volley.VolleyLog.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserDashboard.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserDashboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserDashboard extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;
    private ProgressDialog pDialog;
    RecyclerView r1;
    LocationManager locationManager;
    String lat, lon;


    ArrayList<SingleItemModel> sampleData;
    ArrayList<SingleItemModel> sampleData2;
    private databsehandler db;
    private String mParam1;
    private String mParam2;
    private ConstraintLayout c;
    private ConstraintLayout c2;
    private OnFragmentInteractionListener mListener;
    private TextView msg;

    public UserDashboard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserDashboard.
     */
    // TODO: Rename and change types and number of parameters
    public static UserDashboard newInstance(String param1, String param2) {
        UserDashboard fragment = new UserDashboard();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fab_usermain, container, false);
        msg = (TextView) view.findViewById(R.id.EmptyMsg);
        c = (ConstraintLayout) view.findViewById(R.id.mempty);
        c2 = (ConstraintLayout) view.findViewById(R.id.hideThis);
        TextView userName = (TextView) view.findViewById(R.id.userNameData);

        if(sharedPreference.getInstance(getActivity()).getUser().gettype().toUpperCase() == "USER")
            userName.setText( "Welcome " + sharedPreference.getInstance(getActivity()).getUser().getUsername().toUpperCase());
        else
            userName.setText( sharedPreference.getInstance(getActivity()).getUser().gettype().toUpperCase() + "  "+sharedPreference.getInstance(getActivity()).getUser().getUsername().toUpperCase());

        r1 = (RecyclerView) view.findViewById(R.id.userRecycleView);
        r1.setHasFixedSize(true);

        sampleData2 = new ArrayList<>();
        //createDummyData();

        db = new databsehandler(getActivity());
        sampleData2 = db.getAllRecords();

        if(sampleData2.size() > 0 ){
            showRecyclerView();
            //Check if updated
        }else {
            getJsondata();
        }

        view.findViewById(R.id.complainFab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Location location1 = null;
                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                    buildAlertMessageNoGps();

                } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                    StringBuilder s = new StringBuilder();
//                    for(int k=0;k<5;k++){
                           location1 = getLocation();

//                        try {
//                            lat = String.valueOf(location1.getLatitude());
//                            lon = String.valueOf(location1.getLongitude());
//                            s.append("lat : "+lat+"\nlon : "+lon+"\n");
//
//                            Thread.sleep(2000);
//                            Toast.makeText(getActivity(),"lat : "+lat+"\nlon : "+lon+"\n", Toast.LENGTH_SHORT).show();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//
//                    Log.d(TAG, "onClick: latLong : " + s.toString());
//                    Toast.makeText(getActivity(),s.toString(), Toast.LENGTH_LONG).show();
                }

                if (location1 == null) {
                    Toast.makeText(getActivity(), "Location Not Found", Toast.LENGTH_LONG).show();
                } else {
                    lat = String.valueOf(location1.getLatitude());
                    lon = String.valueOf(location1.getLongitude());
                    Toast.makeText(getActivity(), "Latitude: " + lat + "\nLongitde:" + lon + "\naccuracy : " + location1.getAccuracy(), Toast.LENGTH_LONG).show();
                    Log.v("MyLocationListener","Latitude: " + lat + "\nLongitde:" + lon);
                    Intent i = new Intent(getActivity(),input.class);
                    i.putExtra("lat",lat);
                    i.putExtra("lon",lon);
                    float acc = location1.getAccuracy();
                    i.putExtra("acc",acc);

                    startActivity(i);
                }
            }
        });

        return view;
    }

    public class CustomLinearLayoutManager extends LinearLayoutManager {
        public CustomLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);

        }

        @Override
        public boolean canScrollVertically() {
            return false;
        }
    }


    public void requestRuntimePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }
    public void getJsondata(){
        //String url = "https://roadg.herokuapp.com/user/search/?key=2901&email=kaushikjadav602@gmail.com";
        //String url = "https://unpremeditated-usag.000webhostapp.com/index1.json";


        String url = URLs.User_complain + "?user_id="+sharedPreference.getInstance(getActivity()).getUser().getId();
        String url2 = URLs.Category ;
        Log.v("Links",url + url2);

         Handler handler = new Handler();
         TaskCanceler taskCanceler;

        GetContacts task = new GetContacts();
        //taskCanceler = new TaskCanceler(task);
        //handler.postDelayed(taskCanceler, 2*1000);
        task.execute(url,url2);
    }

    private class GetContacts extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Fetching Your Data...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(String... S) {
            HttpHandker sh = new HttpHandker();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(S[0]);

            Log.v("debug", "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
//
//                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONObject("data").getJSONArray("complaints");
                    Log.v("debug","hi");
                    Log.v("JSON",contacts.toString());
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("id");
                        String url = c.getString("url");
                        String Description = c.getString("description");
                        String Loclong = c.getString("location_longitude");
                        String Loclat = c.getString("location_latitude");
                        String area = c.getString("taluka");
                        String city = c.getString("district");
                        String state = c.getString("state");
                        String roadType = c.getString("road_type");
                        String grievType = c.getString("grievence_type");

                        String workstatus = c.getString("workstatus");
                        String timeStamp = c.getString("time");
                        int isEmergency = c.getInt("isEmergency");

                        JSONObject currentOfficer = c.getJSONObject("current_officer");
                        String officerName = currentOfficer.getString("name");
                        String officerId = currentOfficer.getString("id");
                        String email = currentOfficer.getString("email");

                        String estimatedTime = c.getString("esitmated_time");
                        String comment = c.getString("comment");


                        SingleItemModel s = new SingleItemModel(url,Description,Loclong,Loclat,area,city,state,roadType,grievType,workstatus,timeStamp,isEmergency,officerId,officerName,comment,estimatedTime,email,id);
                        db.insertRecord(s);
                        sampleData2.add(s);
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                if(getActivity()!=null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),
                                    "Please Check your Internet Conenction",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            }


            //Get category data

            HttpHandker sh2 = new HttpHandker();

            // Making a request to url and getting response
            String jsonStr2 = sh.makeServiceCall(S[1]);

            Log.v("category", "Response from url: " + jsonStr2);

            if (jsonStr2 != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr2);

                    if(jsonObj.getInt("status") == 0) {
                        JSONArray contacts = jsonObj.getJSONArray("data");

                        Log.v("category", contacts.toString());
                        Log.v("category","done");

                        ArrayList<String> s= new ArrayList<String>();
                        for(int i=0; i<contacts.length();i++){
                            s.add(contacts.getString(i));
                        }
                        s.add("Other");
                        Log.v("category",s.toString());
                        String array[] = new String[s.size()];
                        for(int j =0;j<s.size();j++){
                            array[j] = s.get(j);
                        }
                        sharedPreference.getInstance(getActivity()).setCategoryArray(array);


                    }
                    else {
                        Log.v("category","error");
                    }


                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                if(getActivity()!=null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),
                                    "Please Check your Internet Conenction",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            super.onPostExecute(result);
            if(!sampleData2.isEmpty()) {
                showRecyclerView();
            }
            else{

               // c2.setVisibility(View.GONE);
                c.setVisibility(View.VISIBLE);
            }


            if (pDialog.isShowing())
                pDialog.dismiss();

            requestRuntimePermission();
                Log.v("upload","uploading....."+sharedPreference.getImageUrl());

//                if(!sharedPreference.getInstance(getActivity()).getComplain().getImageUrl().equals("hello"));
//                    uploadOfflineStuff();


        }

    }

    private void showRecyclerView() {
        linearListDataAdapter adapter = new linearListDataAdapter(getActivity(), sampleData2);
        r1.setLayoutManager(new CustomLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        r1.setAdapter(adapter);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void uploadOfflineStuff(){

        Complain c= sharedPreference.getInstance(getActivity()).getComplain();


        final int emergency = c.getEmergency();
        Uri fileUri = Uri.parse(sharedPreference.getInstance(getActivity()).getImageUrl());

        Log.v("upload",sharedPreference.getImageUrl());

        Context context = getActivity();
        final String gri = c.getGriType();
        final String dis = c.getDiscription();
        final String locationData = c.getLatLoc();

        String[] locationdataA = locationData.split(",");
        final String lat2 = locationdataA[0];
        //final String  lang = locationdataA[1];
        final String  lang = "72.4545";

       // Log.v("locationDebug",locationdataA[0] + locationdataA[1]);

        UploadcareClient client =  new UploadcareClient("679938e7aa18badb637f", "aae07c7905214a43803c");

        Uploader uploader = new FileUploader(client, fileUri, context)
                .store(true);
        final String finalGri = gri;
        uploader.uploadAsync(new UploadcareFileCallback() {
            @Override
            public void onFailure(UploadcareApiException e) {
                Log.v("upload",e.toString());
                User user = sharedPreference.getInstance(getActivity()).getUser();
                String finalImageUrl = "https://api.uploadcare.com/files/cec882b6-eee3-47d5-9052-8b4a707ec9d8/";
                String url= URLs.URL_COMPLAIN+"?url="+finalImageUrl+"&description="+dis+"&grievence="+ finalGri +"&postedUserID="+user.getId()+"&latitude="+lat2+"&longitude="+lang+"&mode="+sharedPreference.getInstance(getActivity()).getValidate()+"&isemergency="+emergency;
                Log.v("input",url);
                // String url="https://roadg.herokuapp.com/roadPost/?url=goo.gl&description=abcd&locLong=1.234&locLat=2.345&area=pal&city=ahmedabad&state=gujarat&roadType=abc&GrievType=a&cat=c&postedUser=1";
                //\/String url ="https://unpremeditated-usag.000webhostapp.com/text.php";
                //new getData2().execute(url);


            }

            @Override
            public void onSuccess(UploadcareFile file) {
                Log.v("upload","file done"+ file.getUrl().toString());
                String finalImageUrl = file.getUrl().toString();
                User user = sharedPreference.getInstance(getActivity()).getUser();
                //String url = "https://unpremeditated-usag.000webhostapp.com/newtext.php/?url="+imageUrl+"&description="+dis+"&GrievType="+gri+"&roadType="+rt+"&cat="+cat+"&postedUser="+user.getId()+"&locLong="+lang+"&locLat="+lat2;
                String url= URLs.URL_COMPLAIN+"?url="+finalImageUrl+"&description="+dis+"&grievence="+ finalGri +"&postedUserID="+user.getId()+"&latitude="+lat2+"&longitude="+lang+"&mode="+sharedPreference.getInstance(getActivity()).getValidate()+"&isemergency="+emergency;
                Log.v("input",url);
                // String url="https://roadg.herokuapp.com/roadPost/?url=goo.gl&description=abcd&locLong=1.234&locLat=2.345&area=pal&city=ahmedabad&state=gujarat&roadType=abc&GrievType=a&cat=c&postedUser=1";
                //\/String url ="https://unpremeditated-usag.000webhostapp.com/text.php";
//                new getData2().execute(url);

            }
        });

    }

    protected void buildAlertMessageNoGps() {
        Log.v("tesing", "building alert dialog");
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        getActivity().startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),17);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
//                        Intent i = new Intent(getActivity(),navigationMain.class);
//                        startActivity(i);
                        Toast.makeText(getActivity(),
                                "User cancelled Location capture", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private Location getLocation() {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {

            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setPowerRequirement(Criteria.POWER_HIGH);
            criteria.setAltitudeRequired(false);
            criteria.setSpeedRequired(false);
            criteria.setCostAllowed(true);
            criteria.setBearingRequired(false);

            //API level 9 and up
            criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
            criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);

            //locationManager.requestLocationUpdates(locationManager.getBestProvider(criteria,true),);

            //locationManager.requestSingleUpdate("gps",locationListener,null);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

            if (location != null)
            {
                return location;
            }
            else  if (location1 != null)
            {
                return location1;
            }
            else  if (location2 != null)
            {
                return location2;
            }
            else{

                Toast.makeText(getActivity(),"Unable to Trace your location",Toast.LENGTH_SHORT).show();

            }

        }
        return null;
    }

}
