package com.rsking175453.com.sgh_try1.fragmentCollection;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rsking175453.com.sgh_try1.HttpHandker;
import com.rsking175453.com.sgh_try1.R;
import com.rsking175453.com.sgh_try1.models.URLs;
import com.rsking175453.com.sgh_try1.dialogs.dialogTime;
import com.rsking175453.com.sgh_try1.models.SectionDataModel;
import com.rsking175453.com.sgh_try1.models.SingleItemModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.android.volley.VolleyLog.TAG;


public class playStoreTime extends Fragment {


    View view;
    RecyclerView my_recycler_view;
    ArrayList<SingleItemModel> sampleData2;
    ArrayList<SectionDataModel> allSampleData;
    private ProgressDialog pDialog;
    String taluka = "chandkheda";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(getArguments()!=null){
            taluka = getArguments().getString("taluka");
            Log.v("taluka",taluka);
        }
        view = inflater.inflate(R.layout.fragment_play_store_time, container, false);
        allSampleData = new ArrayList<>();

        view.findViewById(R.id.filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new dialogTime();
                newFragment.show(getFragmentManager(),"hello");

            }
        });

        my_recycler_view = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        //String url= "https://unpremeditated-usag.000webhostapp.com/mostrecent.json";
        String url = URLs.Sorted_data+"?taluka="+taluka.toLowerCase();

        new getData().execute(url);


        return view;
    }


    private class getData extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(String... s) {
            HttpHandker sh = new HttpHandker();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(s[0]);

            Log.v("debug", "Response from url: " + jsonStr);

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

                            //SingleItemModel s3 = new SingleItemModel(url,Description,Loclong,Loclat,area,city,state,roadType,grievType,workstatus,timeStamp,isEmergency,officerId,officerName);

                            Log.v("debug",""+i+roadType);
                            sampleData3.add(s3);
                        }
                        dm.setAllItemsInSection(sampleData3);
                        allSampleData.add(dm);

                    }

                    Log.v("debug","hi");
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),
                                "Please check your internet Connection",
                                Toast.LENGTH_LONG)
                                .show();
                  }
                });

            }



            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(getActivity(), allSampleData);
            my_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            my_recycler_view.setAdapter(adapter);


        }

    }



}
