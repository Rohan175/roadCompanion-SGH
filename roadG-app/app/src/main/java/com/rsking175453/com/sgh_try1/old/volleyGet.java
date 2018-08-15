package com.rsking175453.com.sgh_try1.old;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rsking175453.com.sgh_try1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class volleyGet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_get);

        String url = "http://192.168.1.103/text.php";
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading...");
        progressDialog.show();
//
//        JsonObjectRequest jsonRequest = new JsonObjectRequest
//                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // the response is already constructed as a JSONObject!
//                        Log.v("Debug","hello" + response);
//                        try {
//
//                            //response = response.getJSONObject("folderName");
//                            String site = response.getString("folderName");
//                            TextView t1 = (TextView)findViewById(R.id.textView5);
//                            t1.setText(site);
//                              //      network = response.getString("network");
//                            System.out.println("Site: "+site+"\nNetwork: ");

//
//                            JSONArray array = response.getJSONArray("students");
//
//                            // Loop through the array elements
//                            for(int i=0;i<array.length();i++){
//                                // Get current json object
//                                JSONObject student = array.getJSONObject(i);
//
//                                // Get the current student (json object) data
//                                String firstName = student.getString("firstname");
//                                String lastName = student.getString("lastname");
//                                String age = student.getString("age");
//
//                                // Display the formatted json data in text view
//                                mTextView.append(firstName +" " + lastName +"\nage : " + age);
////                                mTextView.append("\n\n");
//                            progressDialog.dismiss();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.printStackTrace();
//                    }
//                });
//
//        Volley.newRequestQueue(this).add(jsonRequest);

// String Get Request
     //   String url = "http://192.168.1.103/text.php";
//
//        // Request a string response
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.v("Debug",response);
//                        // Result handling
//                        System.out.println(response.substring(0,100));
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                // Error handling
//                System.out.println("Something went wrong!");
//                error.printStackTrace();
//
//            }
//        });
//
//    // Add the request to the queue
//        Volley.newRequestQueue(this).add(stringRequest);


        Map<String, String>  params = new HashMap<String, String>();
        params.put("email", "kaushikjadav602@gmail.com");
        params.put("password", "123456");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                new JSONObject(params), new Response.Listener<JSONObject>() {

//        //Post request
       //url = "https://roadg.herokuapp.com/login";
        //StringRequest postRequest = new StringRequest(Request.Method.POST, url,
          //      new Response.Listener<String>()
            //    {
         //           @Override
                    public void onResponse(JSONObject response) {
                        // response
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("email", "kaushikjadav602@gmail.com");
                params.put("password", "123456");

                return params;
            }
        };
        Volley.newRequestQueue(this).add(jsonObjectRequest);
        //queue.add(postRequest);

    }

 }

