package com.rsking175453.com.sgh_try1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rsking175453.com.sgh_try1.fragmentCollection.UserDashboard;
import com.rsking175453.com.sgh_try1.fragmentCollection.playStoreTime;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Created by user on 09/08/2018.
 */

public class dialogForVerification extends DialogFragment {

    private ProgressDialog pDialog;
    private TextView t,t2;
    private String otpString,url;
    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view =  inflater.inflate(R.layout.verify_otp, container, false);
            Log.v("dialog","started");
            final TextInputEditText otp = (TextInputEditText) view.findViewById((R.id.otp));
            Button cancel =(Button) view.findViewById(R.id.cancelOTP);
            Button go =(Button) view.findViewById(R.id.confirmLink);
             t2 = (TextView) view.findViewById(R.id.verifyOTP);
         t = (TextView)view.findViewById(R.id.wrongOTP);


        cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

            otpString = getArguments().getString("otp");
            url = getArguments().getString("url");

            go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String otpTyped = otp.getText().toString();
                    Log.v("dialog","OTp dialog");

                    if(otpTyped.equals(otpString)) {

                        t2.setText("Processsing...");
                        new getData().execute(url);

                    }else{

                        t.setVisibility(VISIBLE);

                        t2.setVisibility(INVISIBLE);

                    }

                }
            });

            return view;
        }


    private class getData extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog

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

                    // Getting JSON Array node
                    //JSONArray contacts = jsonObj.getJSONArray("contacts");
                    int status = jsonObj.getInt("status");
                    if (status == 0) {
                        JSONObject data = jsonObj.getJSONObject("data");
                        int id = data.getInt("id");

                        String name = data.getString("name");
                        String email = data.getString("email");
                        String type = data.getString("user_type");

                        User user = new User(
                                id, name, email, type
                        );

                        //storing the user in shared preferences
                        sharedPreference.getInstance(getActivity()).userLogin(user);
                        //starting the profile activity
                        //finish();
                        //     Log.v("debug", "hi");


                    }

                    else {


                        JSONObject error = jsonObj.getJSONObject("error");

                        final String message = error.getString("messages");

                    }


                } catch (final JSONException e) {
                    Log.e("debug", "Json parsing error: " + e.getMessage());
                                    }
            } else {


                Log.v("debug", "Couldn't get json from server.");

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            t2.setText("Welcome....");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dismiss();
            //Show welcome page
            Intent i = new Intent(getActivity(), navigationMain.class);
            startActivity(i);

        }


    }
}
