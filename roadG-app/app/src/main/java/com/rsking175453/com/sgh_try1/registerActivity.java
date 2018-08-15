package com.rsking175453.com.sgh_try1;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.rsking175453.com.sgh_try1.dialogs.dialogForVerification;
import com.rsking175453.com.sgh_try1.models.URLs;
import com.rsking175453.com.sgh_try1.models.User;

import org.json.JSONException;
import org.json.JSONObject;

public class registerActivity extends AppCompatActivity {

    EditText editTextUsername, editTextEmail, editTextPassword,editTextPassword2;
    RadioGroup radioGroupGender;
    ProgressBar progressBar;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

//        //if the user is already logged in we will directly start the profile activity
//        if (sharedPreference.getInstance(this).isLoggedIn()) {
//            finish();
//            startActivity(new Intent(this, navigationMain.class));
//            return;
//        }

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextPassword2 = (EditText) findViewById(R.id.editTextPassword2);

        findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerUser();
            }
        });

        findViewById(R.id.textViewLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(registerActivity.this, LoginActivity2.class));
            }
        });

    }

    private void registerUser() {
        final String username = editTextUsername.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String password2 = editTextPassword2.getText().toString().trim();
        //final String type = editTextPassword.getText().toString().trim();
        //final String gender = ((RadioButton) findViewById(radioGroupGender.getCheckedRadioButtonId())).getText().toString();

        //first we will do the validations

        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Please enter username");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter your email");
            editTextEmail.requestFocus();
            return;
        }

//        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            editTextEmail.setError("Enter a valid email");
//            editTextEmail.requestFocus();
//            return;
//        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Enter a password");
            editTextPassword.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(password2)) {
            editTextPassword.setError("Enter a confirm password");
            editTextPassword.requestFocus();
            return;
        }



        if (!password.equals(password2)) {
            editTextPassword.setError("Passwords Dont match");
            editTextPassword.requestFocus();
            return;
        }

        //String url = "https://roadg.herokuapp.com/Signup?email=" + email + "&password=" + password + "&name" + username;
        String url = URLs.URL_REGISTER+"?email="+email+"&password=" + password + "&name=" + username + "&verified=0";
        Log.v("link",url);
        new getData().execute(url);
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_REGISTER,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        progressBar.setVisibility(View.GONE);
//
//                        try {
//                            //converting response to json object
//                            JSONObject obj = new JSONObject(response);
//
//                            //if no error in response
//                            if (!obj.getBoolean("error")) {
//                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
//
//                                //getting the user from the response
//                                JSONObject userJson = obj.getJSONObject("user");
//
//                                //creating a new user object
//                                User user = new User(
//                                        userJson.getInt("id"),
//                                        userJson.getString("username"),
//                                        userJson.getString("email"),
//                                        userJson.getString("gender")
//                                );
//
//                                //storing the user in shared preferences
//                                sharedPreference.getInstance(getApplicationContext()).userLogin(user);
//
//                                //starting the profile activity
//                                finish();
//                                startActivity(new Intent(getApplicationContext(), navigationMain.class));
//                            } else {
//                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("username", username);
//                params.put("email", email);
//                params.put("password", password);
//                params.put("type", type);
//                return params;
//            }
//        };

        //VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


    private class getData extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(registerActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(String... s) {
            HttpHandker sh = new HttpHandker();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(s[0]);

            Log.v("debugData", "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    //JSONArray contacts = jsonObj.getJSONArray("contacts");
                    int status = jsonObj.getInt("status");
                    Log.v("debugData",status+"");
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
                        sharedPreference.getInstance(getApplicationContext()).userLogin(user);

                        //starting the profile activity
                        finish();
                   //     Log.v("debug", "hi");


                    }else if(status == 2){
                        JSONObject data = jsonObj.getJSONObject("data");
                        int otp = data.getInt("otp");

                        StringBuilder x = new StringBuilder(s[0].substring(0,s[0].length()-1));

                        x.append('1');
                        Log.v("debugData",x.toString());

                        Bundle args = new Bundle();
                        args.putString("url", x.toString());
                        args.putString("otp", ""+otp);

                        Log.v("otp","" + otp);

                        DialogFragment otpdialog = new dialogForVerification();
                        otpdialog.setArguments(args);
                        otpdialog.show(getFragmentManager(), "hello");

                    }


                    else {


                        JSONObject error = jsonObj.getJSONObject("error");

                        final String message = error.getString("messages");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast.makeText(getApplicationContext(),
                                         message,
                                        Toast.LENGTH_LONG)
                                        .show();
                            }
                        });

                    }


                } catch (final JSONException e) {
                    Log.e("debug", "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {


                Log.v("debug", "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
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

        }


    }

}