
package com.rsking175453.com.sgh_try1.map;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.rsking175453.com.sgh_try1.HttpHandker;
import com.rsking175453.com.sgh_try1.R;
import com.rsking175453.com.sgh_try1.dialogs.dialogTime2;
import com.rsking175453.com.sgh_try1.models.SectionDataModel;
import com.rsking175453.com.sgh_try1.models.SingleItemModel;
import com.rsking175453.com.sgh_try1.models.URLs;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.android.volley.VolleyLog.TAG;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<SectionDataModel> allSampleData;
    private ProgressDialog pDialog;
    RecyclerView my_recycler_view;
    String taluka;
    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent i = getIntent();
        taluka = i.getStringExtra("taluka");

        allSampleData = new ArrayList<SectionDataModel>();
        Button b = (Button) findViewById(R.id.filter);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new dialogTime2();
                newFragment.show(getFragmentManager(),"hello");
            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);

        setUpClusterer();
    }

    // Adding new ClusterManager to group the cluster items (markers) based on zoom level
    // Declare a variable for the cluster manager.
    private ClusterManager<MyItem> mClusterManager;

    private void setUpClusterer() {
        // Position the map.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(23.036481, 72.575341), 13));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        //mClusterManager = new ClusterManager<MyItem>(this, mMap);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.

  //      mMap.setOnCameraIdleListener(mClusterManager);
//        mMap.setOnMarkerClickListener(mClusterManager);

        // Add cluster items (markers) to the cluster manager.
        String url = URLs.Sorted_data+"?taluka="+taluka.toLowerCase();
        Log.v("Mapurl",url);
        new getData().execute(url);
    }

    private void addItems() {

        // Set some lat/lng coordinates to start with.
//        double lat;
//        double lng;
//        LatLng location;

        // creating ArrayList to store location data for different locations
//        ArrayList<LatLng> location_data = new ArrayList<>();
//        ArrayList<String> place_data = new ArrayList<>();
//        ArrayList<String> status_data = new ArrayList<>();
//        ArrayList<String> type_data = new ArrayList<>();

//        // Location 1
//        location_data.add(new LatLng(23.1116, 72.5728));
//        place_data.add("Chandkheda");
//        status_data.add("Completed");
//        type_data.add("Pothole");
//
//        // Location 2
//        location_data.add(new LatLng(23.0568, 72.5936));
//        place_data.add("Shahibaug");
//        status_data.add("In Progress");
//        type_data.add("Tree Fall");
//
//        // Location 3
//        location_data.add(new LatLng(23.0304, 72.5178));
//        place_data.add("Satellite");
//        status_data.add("Approved");
//        type_data.add("Crack");
//
//        // Location 4
//        location_data.add(new LatLng(23.0134, 72.5624));
//        place_data.add("Paldi");
//        status_data.add("In Progress");
//        type_data.add("Water Logging");
//
//        // Location 5
//        location_data.add(new LatLng(23.0264, 72.5819));
//        place_data.add("Lal Darwaja");
//        status_data.add("Pending");
//        type_data.add("Emergency");
//
//        // Location 6
//        location_data.add(new LatLng(23.0552, 72.5585));
//        place_data.add("Naranpura");
//        status_data.add("Completed");
//        type_data.add("Crack");
//
//        // Location 7
//        location_data.add(new LatLng(23.1036, 72.6024));
//        place_data.add("Motera");
//        status_data.add("In Progress");
//        type_data.add("Pothole");
//
//        // Location 8
//        location_data.add(new LatLng(23.1072, 72.5722));
//        place_data.add("D-Cabin");
//        status_data.add("Pending");
//        type_data.add("Tree Fall");
//
//        // Location 9
//        location_data.add(new LatLng(23.0884, 72.5865));
//        place_data.add("Sabarmati");
//        status_data.add("Approved");
//        type_data.add("Crack");
//
//
//        // Location 10
//        location_data.add(new LatLng(23.0283, 72.5937));
//        place_data.add("Kalupur");
//        status_data.add("In Progress");
//        type_data.add("Pothole");

        // Adding the places on map
        for (int i = 0; i < allSampleData.size(); i++) {

            ArrayList<SingleItemModel> singleSectionItems = allSampleData.get(i).getAllItemsInSection();

            String status = allSampleData.get(i).getHeaderTitle();

            float color;

            Log.v("maps",status.toLowerCase());
            if (status.toLowerCase().equals("emergency complaints")) color = BitmapDescriptorFactory.HUE_RED;
            else if (status.toLowerCase().equals("pending complaints")) color = BitmapDescriptorFactory.HUE_YELLOW;
            else if (status.toLowerCase().equals("validated complaints")) color = BitmapDescriptorFactory.HUE_VIOLET ;
            else if (status.toLowerCase().equals("in progress complaints")) color = BitmapDescriptorFactory.HUE_GREEN;
            else color = BitmapDescriptorFactory.HUE_AZURE;

            for(int j = 0; j < singleSectionItems.size(); j++) {

                SingleItemModel singleItem = singleSectionItems.get(j);

                String title = singleItem.getRoadType();

                LatLng location = new LatLng(Double.parseDouble(singleItem.getLocationLat()), Double.parseDouble(singleItem.getLocationLong()) );
                String type = singleItem.getGrivType();

                if(j==0){
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                }

                MarkerOptions markerOptions = new MarkerOptions();

                // Setting position for the marker
                markerOptions.position(location);

                // Setting custom icon for the marker
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(color));
                //            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_warning_black_24dp));

                // Setting title and snippet for the info-window
                markerOptions.title(type);

                //            markerOptions.snippet("Problem : " + type + "\n" + "Status : " + status);
                markerOptions.snippet(title);

                // Adding the marker to the map
                //            mMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_warning_black_24dp)));
                if (status.toLowerCase() == "completed complaints")
                    markerOptions.visible(false);

                    mMap.addMarker(markerOptions);




                // Adding custom window adapter
                //mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapsActivity.this));

//                mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MyItem>() {
//                    @Override
//                    public boolean onClusterItemClick(MyItem myItem) {
//                        //myItem.onClusterItemClick(myItem);
//                        //mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapsActivity.this));
//                        return false;
//                    }
//                });

                //            mClusterManager.addItem(offsetItem);
            }
        }
    }

    private class getData extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
//            pDialog = new ProgressDialog(MapsActivity.this);
//            pDialog.setMessage("Please wait...");
//            pDialog.setCancelable(false);
//            pDialog.show();

        }

        @Override
        protected Void doInBackground(String... s) {
            HttpHandker sh = new HttpHandker();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(s[0]);

            Log.v("maps", "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
//
//                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("Grievences");
//
//                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c2 = contacts.getJSONObject(i);

                        String title = c2.getString("title");
                        SectionDataModel dm = new SectionDataModel();
                        dm.setHeaderTitle(title);
                        ArrayList<SingleItemModel> sampleData3 = new ArrayList<>();

                        JSONArray c = c2.getJSONArray("data");

                        for(int j=0;j<c.length();j++){
                            String id = c.getJSONObject(j).getString("id");
                            String url = c.getJSONObject(j).getString("url");
                            String Description = c.getJSONObject(j).getString("description");
                            String Loclong = c.getJSONObject(j).getString("location_longitude");
                            String Loclat = c.getJSONObject(j).getString("location_latitude");
                            String area = c.getJSONObject(j).getString("taluka");
                            String city = c.getJSONObject(j).getString("district");
                            String state = c.getJSONObject(j).getString("state");
                            String roadType = c.getJSONObject(j).getString("road_type");
                            String grievType = c.getJSONObject(j).getString("grievence_type");

                            String workstatus = c.getJSONObject(j).getString("workstatus");
                            String timeStamp = c.getJSONObject(j).getString("time");
                            int isEmergency = c.getJSONObject(j).getInt("isEmergency");

                            JSONObject currentOfficer = c.getJSONObject(j).getJSONObject("current_officer");
                            String officerName = currentOfficer.getString("name");
                            String officerId = currentOfficer.getString("id");

                            String email = currentOfficer.getString("email");

                            String estimatedTime = c.getJSONObject(j).getString("esitmated_time");
                            String comment = c.getJSONObject(j).getString("comment");


                            SingleItemModel s3 = new SingleItemModel(url,Description,Loclong,Loclat,area,city,state,roadType,grievType,workstatus,timeStamp,isEmergency,officerId,officerName,comment,estimatedTime,email,id);


                            Log.v("debug",""+i+roadType);
                            sampleData3.add(s3);
                        }
                        dm.setAllItemsInSection(sampleData3);
                        allSampleData.add(dm);

                    }

                    Log.v("debug","hi");
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());


                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");

            }



            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
//            if (pDialog.isShowing())
//                pDialog.dismiss();


            //yourcode
            addItems();

        }

    }

}




//package com.rsking175453.com.sgh_try1;
//
//import android.app.DialogFragment;
//import android.location.Address;
//import android.location.Geocoder;
//import android.support.v4.app.FragmentActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.maps.android.clustering.ClusterManager;
//
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//
//public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
//
//    private GoogleMap mMap;
//    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maps);
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//
//        Button b = (Button) findViewById(R.id.filter);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DialogFragment newFragment = new dialogTime();
//                newFragment.show(getFragmentManager(),"hello");
//            }
//        });
//
//
//    }
//
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        double lat, lon;
//        LatLng location;
//        String city;
//
//        lat = 23.1116;
//        lon = 72.5728;
//        city = "Chandkheda";
//        // Add a marker in Chandkheda and move the camera
//        location = new LatLng(lat, lon);
////        mMap.addMarker(new MarkerOptions().position(location).title("Marker in " + city)).setSnippet("Default Marker");
//        //mMap.addMarker(new MarkerOptions().position(location).title("Marker in " + city).snippet("Default Marker").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
//
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
//
////        IconGenerator icon = new IconGenerator(this);
////        icon.setColor(Color.CYAN);
////        addIcon(icon, "Custom color", new LatLng(lat, lon));
//
//        // mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapsActivity.this));
//
//        mMap.getUiSettings().setZoomControlsEnabled(true);
//
//        setUpClusterer();
//    }
//
//    // Adding new ClusterManager to group the cluster items (markers) based on zoom level
//    // Declare a variable for the cluster manager.
//    private ClusterManager<MyItem> mClusterManager;
//
//    private void setUpClusterer() {
//        // Position the map.
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(23.1116, 72.5728), 10));
//
//        // Initialize the manager with the context and the map.
//        // (Activity extends context, so we can pass 'this' in the constructor.)
//        mClusterManager = new ClusterManager<MyItem>(this, mMap);
//
//        // Point the map's listeners at the listeners implemented by the cluster
//        // manager.
//
//        mMap.setOnCameraIdleListener(mClusterManager);
//        mMap.setOnMarkerClickListener(mClusterManager);
//
//        // Add cluster items (markers) to the cluster manager.
//        addItems();
//    }
//
//
//
//    private void addItems() {
//
//        // Set some lat/lng coordinates to start with.
////        double lat;
////        double lng;
////        LatLng location;
//
//        // creating ArrayList to store location data for different locations
//        ArrayList<LatLng> location_data = new ArrayList<>();
//        ArrayList<String> place_data = new ArrayList<>();
//        ArrayList<String> status_data = new ArrayList<>();
//        ArrayList<String> type_data = new ArrayList<>();
//
//        // Location 1
//        location_data.add(new LatLng(23.1116, 72.5728));
//        place_data.add("Chandkheda");
//        status_data.add("Completed");
//        type_data.add("Pothole");
//
//        // Location 2
//        location_data.add(new LatLng(23.0568, 72.5936));
//        place_data.add("Shahibaug");
//        status_data.add("In Progress");
//        type_data.add("Tree Fall");
//
//        // Location 3
//        location_data.add(new LatLng(23.0304, 72.5178));
//        place_data.add("Satellite");
//        status_data.add("Approved");
//        type_data.add("Crack");
//
//        // Location 4
//        location_data.add(new LatLng(23.0134, 72.5624));
//        place_data.add("Paldi");
//        status_data.add("In Progress");
//        type_data.add("Water Logging");
//
//        // Location 5
//        location_data.add(new LatLng(23.0264, 72.5819));
//        place_data.add("Lal Darwaja");
//        status_data.add("Pending");
//        type_data.add("Emergency");
//
//        // Location 6
//        location_data.add(new LatLng(23.0552, 72.5585));
//        place_data.add("Naranpura");
//        status_data.add("Completed");
//        type_data.add("Crack");
//
//        // Location 7
//        location_data.add(new LatLng(23.1036, 72.6024));
//        place_data.add("Motera");
//        status_data.add("In Progress");
//        type_data.add("Pothole");
//
//        // Location 8
//        location_data.add(new LatLng(23.1072, 72.5722));
//        place_data.add("D-Cabin");
//        status_data.add("Pending");
//        type_data.add("Tree Fall");
//
//        // Location 9
//        location_data.add(new LatLng(23.0884, 72.5865));
//        place_data.add("Sabarmati");
//        status_data.add("Approved");
//        type_data.add("Crack");
//
//
//        // Location 10
//        location_data.add(new LatLng(23.0283, 72.5937));
//        place_data.add("Kalupur");
//        status_data.add("In Progress");
//        type_data.add("Pothole");
//
//        // Adding the places on map
//        for (int i = 0; i < 10; i++) {
//
//            String title = place_data.get(i);
//            String status = status_data.get(i);
//            LatLng location = location_data.get(i);
//            String type = type_data.get(i);
//
//            // Creating an instance of MarkerOptions
//            MarkerOptions markerOptions = new MarkerOptions();
//
//            // Setting position for the marker
//            markerOptions.position(location);
//
//            // Setting custom icon for the marker
//            float color;
//            if(type == "Emergency")   color = BitmapDescriptorFactory.HUE_RED;
//            else if(type == "Pothole")    color = BitmapDescriptorFactory.HUE_VIOLET;
//                else if(type == "Crack")  color = BitmapDescriptorFactory.HUE_YELLOW;
//                    else if(type == "Tree Fall")  color = BitmapDescriptorFactory.HUE_GREEN;
//                        else    color = BitmapDescriptorFactory.HUE_AZURE;
//
//            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(color));
////            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_warning_black_24dp));
//
//            // Setting title and snippet for the info-window
//            markerOptions.title(title);
////            markerOptions.snippet("Problem : " + type + "\n" + "Status : " + status);
//            markerOptions.snippet(status);
//
//            // Adding the marker to the map
////            mMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_warning_black_24dp)));
//            mMap.addMarker(markerOptions);
//
//            // Adding custom window adapter
//            //mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapsActivity.this));
//
////            double offset = i / 60d;
////            lat = lat + offset;
////            lng = lng + offset;
//
////            GeoPoint startpoint = new GeoPoint((int) lat, (int) lng);
////            Geocoder geocoder;
////            List<Address> addresses;
////            geocoder = new Geocoder(this, Locale.getDefault());
////            String state, title, snippet, city, address, knownName;
////            MyItem offsetItem;
////            int cnt = 1;
//
// //           IconGenerator icon = new IconGenerator(this);
////
////            try {
////                addresses = geocoder.getFromLocation(lat, lng, 1);
////                state = addresses.get(0).getAdminArea();
////                city = addresses.get(0).getLocality();
////                address = addresses.get(0).getAddressLine(0);
////                knownName = addresses.get(0).getFeatureName();
////                title = "Marker in "+ address;
////                snippet = "Marker No. " + String.valueOf(i+1);
////                offsetItem = new MyItem(lat, lng, title, snippet);
////
////                LatLng point = new LatLng(lat,lng);
////                // Creating an instance of MarkerOptions
////                MarkerOptions markerOptions = new MarkerOptions();
////
////                // Setting position for the marker
////                markerOptions.position(point);
////
////                // Setting custom icon for the marker
////                float color;
////                if(i == 0)  color = BitmapDescriptorFactory.HUE_AZURE;
////                else if(i > 0 && i < 5) color = BitmapDescriptorFactory.HUE_MAGENTA;
////                else color = BitmapDescriptorFactory.HUE_YELLOW;
////
////                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(color));
////
////                // Setting title for the infowindow
////                markerOptions.title(title);
////                markerOptions.snippet(snippet);
////
////                // Adding the marker to the map
////                mMap.addMarker(markerOptions);
////
////                // Adding custom window adapter
////                //mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapsActivity.this));
////
////            } catch (IOException e) {
////                e.printStackTrace();
////                offsetItem = new MyItem(lat, lng);
////
////            }
//
//           // offsetItem = new MyItem(lat, lng);
////            mClusterManager.onMarkerClick();
////            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
////                @Override
////                public boolean onMarkerClick(Marker marker) {
////                    mClusterManager.onMarkerClick(marker);
////                    //mClusterManager.setOnClusterItemClickListener(offsetItem);
////                    return false;
////                }
////            });
//
//            mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MyItem>() {
//                @Override
//                public boolean onClusterItemClick(MyItem myItem) {
//                    //myItem.onClusterItemClick(myItem);
////                    mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapsActivity.this));
//                    return false;
//                }
//            });
//
////            mClusterManager.addItem(offsetItem);
//        }
//    }
//}
