package com.rsking175453.com.sgh_try1;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.rsking175453.com.sgh_try1.fragmentCollection.MyLocationListener;
import com.rsking175453.com.sgh_try1.dialogs.dialogTime3;
import com.rsking175453.com.sgh_try1.models.URLs;
import com.rsking175453.com.sgh_try1.models.User;
import com.uploadcare.android.library.api.UploadcareClient;
import com.uploadcare.android.library.api.UploadcareFile;
import com.uploadcare.android.library.callbacks.UploadcareFileCallback;
import com.uploadcare.android.library.exceptions.UploadcareApiException;
import com.uploadcare.android.library.upload.FileUploader;
import com.uploadcare.android.library.upload.Uploader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

public class input extends AppCompatActivity {

    public LocationManager locationManager;
    MyLocationListener locationListener;
    String lat, lon;
    private User user;
    String dis = "No discription";
    Bitmap bitmap = null;

    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private String griString;
    public static final int MEDIA_TYPE_IMAGE = 1;

    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "Hackathon_App";
    private String lat2,lang;
    private Uri fileUri;
    private ProgressDialog pDialog;
    private ImageView imgPreview;
    private String finalImageUrl;
    private Switch switch1;
    private boolean continueInput = false;
    private ImageView imgPreviewMain;
    private int acc;

    public input() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Intent i = getIntent();
        lon = i.getStringExtra("lon");
        lat = i.getStringExtra("lat");
        acc = i.getIntExtra("acc",100);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {

            locationListener = new MyLocationListener(this, lat,lon,acc);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, locationListener);
        }
        captureImage();
        setContentView(R.layout.activity_input);


        imgPreview = (ImageView) findViewById(R.id.imgPreview);
        imgPreviewMain = (ImageView) findViewById(R.id.imagePreviewMain);
        Date currentTime = Calendar.getInstance().getTime();
        TextView d = (TextView)findViewById(R.id.time);
        final TextView loc = (TextView)findViewById(R.id.locationEdit);
        //switch1 = (Switch) findViewById(R.id.emergency);
        final EditText location = (EditText) findViewById(R.id.locationEdit);
        location.setText(lat+",\n"+lon);

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(locationManager != null) {

                    Toast.makeText(input.this,"LocationListner Stopped",Toast.LENGTH_LONG);
                    locationManager.removeUpdates(locationListener);
                    locationManager = null;
                }

            }
        });

        d.setText(currentTime.toString());
        final EditText textArea = (EditText) findViewById(R.id.textArea_information);

        imgPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, 1);
            }
        });



        textArea.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });


//        List<String> grivances =  new ArrayList<String>();
//        grivances.add("Pot_Holes");
//        grivances.add("Fallen_Tree");
//        grivances.add("Degraded_Roads");
//        grivances.add("Huge_Pits");
//        grivances.add("Cracks");
//        grivances.add("Leveling");
//        grivances.add("Safety_Issues");
//        grivances.add("Leveling");

        final String[] s= sharedPreference.getInstance(getApplicationContext()).getCategoryArray();
        ArrayList<String> gri = new ArrayList<String>();
        Collections.addAll(gri,s);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, gri);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner sItems = (Spinner) findViewById(R.id.grivance);
        final int length = gri.size();
        sItems.setAdapter(adapter);

        sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==length-1){
                    findViewById(R.id.otherEdit).setVisibility(View.VISIBLE);
                }else{
                    findViewById(R.id.otherEdit).setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button submit = (Button) findViewById(R.id.submit);
        Button cancel = (Button) findViewById(R.id.cancelLink);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                pDialog = new ProgressDialog(input.this);
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(false);
                pDialog.show();
                Log.v("input2","Submitting");


                String locationEdited = location.getText().toString();
                final String[] locationEditedA = locationEdited.split(",");

                 griString = sItems.getSelectedItem().toString();

                if(griString.toLowerCase()=="other"){
                    TextView t = (TextView)findViewById(R.id.otherEdit);
                    griString = t.getText().toString();
                }
                try {
                    griString = URLEncoder.encode(griString,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


                dis = textArea.getText().toString();

                if(dis.isEmpty())
                    dis="No Desciption";
                try {
                    dis = URLEncoder.encode(dis,"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                user = sharedPreference.getInstance(input.this).getUser();
                lang = locationEditedA[1];
                lat2 = locationEditedA[0];
//
//                final double lang = Double.parseDouble(locationListener.lat);
//                final double lat2 = Double.parseDouble(locationListener.lon);

                Log.v("MyLocationListener", " " + lang + " " + lat2);
                if(locationManager != null) {
                    locationManager.removeUpdates(locationListener);
                    locationManager = null;
                }

                final int emergency ;
                //if(switch1.isChecked()){
                if(false){
                    emergency = 1;
                }else{
                    emergency = 0;
                }


                boolean mobileDataEnabled = false; // Assume disabled
                ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                try {
                    Class cmClass = Class.forName(cm.getClass().getName());
                    Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
                    method.setAccessible(true);

                    mobileDataEnabled = (Boolean)method.invoke(cm);
                } catch (Exception e) {
                    //Do error handelling
                }

                WifiManager wifi = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                if (wifi.isWifiEnabled()|| mobileDataEnabled){
                      //679938e7aa18badb637f
                    new compressAndUploadImageAsync(getApplicationContext()).execute();

                }else{

                    if (pDialog.isShowing())
                        pDialog.dismiss();


                    Log.v("input","sharedPreference");
                    String message = "No internet Connection\nIt will be Auto-Uploaded when connection available.";

                    DialogFragment newFragment = new dialogTime3();
                    Bundle args = new Bundle();
                    args.putString("msg",message );
                    newFragment.setArguments(args);
                    newFragment.show(getFragmentManager(), "hello");
//                    sharedPreference.setImageUrl(path);
//                    //sharedPreference.setDate();
//                    sharedPreference.setDiscription(dis);
//                    sharedPreference.setGriType(gri);
//                    sharedPreference.setIsEmergency(emergency);
//                    sharedPreference.setLacLoc(lon+","+lat);
//                    Complain c = new Complain(path,dis,lon+","+lat,emergency,gri);
//                    sharedPreference.getInstance(getApplicationContext()).complainAdd(c);
//
//                    Log.v("upload","saved "+c.getImageUrl().toString());
//                    Log.v("upload","saved "+ sharedPreference.getInstance(getApplicationContext()).getComplain().getImageUrl());

                }
            }
        });


    }

    private class compressAndUploadImageAsync extends AsyncTask<Void,Void,Void>{

        private Context context;
        compressAndUploadImageAsync(Context context){
            this.context=context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Compressing Image...");
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
            fileUri =  Uri.parse(path);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.v("input2","uploading Image");
            pDialog.setMessage("Uploading Image....");
            UploadcareClient client =  new UploadcareClient("dd15c727ad3e318e308a", "8b57288d33a0b69aaaf1");

            Uploader uploader = new FileUploader(client, fileUri, context)
                    .store(true);
            final String finalGri = griString;
            Log.v("debug",finalGri);
            uploader.uploadAsync(new UploadcareFileCallback() {
                @Override
                public void onFailure(UploadcareApiException e) {
                    pDialog.dismiss();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.v("input2","error");
                            Toast.makeText(context,"Image Upload Failure",Toast.LENGTH_LONG);
                            Bundle b = new Bundle();
                            b.putString("msg","Problem During Uploading...");
                            DialogFragment dial = new dialogTime3();
                            dial.setArguments(b);
                            dial.show(getFragmentManager(),"Hello");
                        }
                    });

                    Log.v("input2",e.toString());
                }

                @Override
                public void onSuccess(UploadcareFile file) {

                    Log.v("input2","file done"+ file.getUrl().toString());
                    finalImageUrl = file.getUrl().toString();
                    sharedPreference.getInstance(getApplicationContext()).setValidate(2);
                    //String url = "https://unpremeditated-usag.000webhostapp.com/newtext.php/?url="+imageUrl+"&description="+dis+"&GrievType="+gri+"&roadType="+rt+"&cat="+cat+"&postedUser="+user.getId()+"&locLong="+lang+"&locLat="+lat2;
                    String url= URLs.URL_COMPLAIN+"?url="+finalImageUrl+"&description="+dis+"&grievence="+ finalGri +"&postedUserID="+user.getId()+"&latitude="+lat2+"&longitude="+lang+"&mode="+sharedPreference.getInstance(getApplicationContext()).getValidate()+"&isemergency=0";
                    Log.v("input2",url);

                    new getData().execute(url);

                }


            });
        }
    }

    private class getData extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Sending Data...");
        }

        @Override
        protected Void doInBackground(String... s) {
            HttpHandker sh = new HttpHandker();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(s[0]);

            Log.v("input2", "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    int status = jsonObj.getInt("status");
                    if (status == 0) {
                        finish();
                        Log.v("input2", "registered complaioed");
                        startActivity(new Intent(getApplicationContext(), navigationMain.class));


                    } else if(status == 1){


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

                    }else{


                        JSONObject error = jsonObj.getJSONObject("error");

                        final String message = error.getString("messages");

                        DialogFragment newFragment = new dialogTime3();
                        Bundle args = new Bundle();
                        args.putString("num",message );
                        newFragment.setArguments(args);
                        newFragment.show(getFragmentManager(), "hello");


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
                    Log.e("input2", "Json parsing error: " + e.getMessage());
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
                Log.v("input2", "Couldn't get json from server.");
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

    private void captureImage() {

            requestRuntimePermission();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            fileUri = Uri.fromFile(getOutputMediaFile(MEDIA_TYPE_IMAGE));
            if (Build.VERSION.SDK_INT >= 24) {
                try {
                    Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                    m.invoke(null);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //Uri photoURI = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".my.package.name.provider", createImageFile());

            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            //intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
        }

        public void requestRuntimePermission() {
            if (Build.VERSION.SDK_INT >= 23) {
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
            }
        }

        protected void onActivityResult(int requestCode, int resultCode, Intent data) {

                super.onActivityResult(requestCode, resultCode, data);
                if(requestCode==17 && requestCode== RESULT_OK){
                    continueInput = true;
                    Log.v("tesing","donme");
                }
                if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };

                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    BitmapFactory.Options options = new BitmapFactory.Options();

                    // downsizing image as it throws OutOfMemory Exception for larger
                    // images
                    options.inSampleSize = 1;
                    bitmap = BitmapFactory.decodeFile(picturePath,
                            options);

                    imgPreviewMain.setImageBitmap(bitmap);
                    imgPreview.setImageBitmap(bitmap);


                }else if (requestCode == 1) {
                    Intent i = new Intent(this,navigationMain.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(),
                            "User cancelled permission", Toast.LENGTH_SHORT)
                            .show();

                }
            if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
                if (resultCode == RESULT_OK) {

                    try {

                        imgPreviewMain.setVisibility(View.VISIBLE);
                        imgPreview.setVisibility(View.VISIBLE);
                        BitmapFactory.Options options = new BitmapFactory.Options();

                        // downsizing image as it throws OutOfMemory Exception for larger
                        // images
                        options.inSampleSize = 1;
                        bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                                options);

                        imgPreviewMain.setImageBitmap(bitmap);
                        imgPreview.setImageBitmap(bitmap);

//                        UploadcareWidget.getInstance().init("679938e7aa18badb637f", "aae07c7905214a43803c ");
//                        boolean storeUponUpload = true;
//                        UploadcareWidget.getInstance().selectFile(this, storeUponUpload, new UploadcareFileCallback() {
//                            @Override
//                            public void onFailure(UploadcareApiException e) {
//                                //handle errors.
//                            }
//
//                            @Override
//                            public void onSuccess(UploadcareFile file) {
//                                //successfully uploaded file to Uploadcare.
//                            }
//                        });

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                } else if (resultCode == RESULT_CANCELED) {

                    Intent i = new Intent(this,navigationMain.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(),
                            "User cancelled image capture", Toast.LENGTH_SHORT)
                            .show();
                } else {

                    Toast.makeText(getApplicationContext(),
                            "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }


    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {

            return true;
        } else {
            return false;
        }
    }

    private Location getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


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

                Toast.makeText(this,"Unable to Trace your location",Toast.LENGTH_SHORT).show();


            }

        }
        return null;
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),17);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                        Intent i = new Intent(getApplicationContext(),navigationMain.class);
                        startActivity(i);
                        Toast.makeText(getApplicationContext(),
                                "User cancelled Location capture", Toast.LENGTH_SHORT)
                                .show();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

}
