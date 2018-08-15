package com.rsking175453.com.sgh_try1;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.rsking175453.com.sgh_try1.dialogs.changeLinkDialog;
import com.rsking175453.com.sgh_try1.fragmentCollection.UserDashboard;
import com.rsking175453.com.sgh_try1.fragmentCollection.contactsFragment;
import com.rsking175453.com.sgh_try1.fragmentCollection.playStoreTime;
import com.rsking175453.com.sgh_try1.map.MapsActivity;
import com.rsking175453.com.sgh_try1.models.User;
import com.rsking175453.com.sgh_try1.old.jsonParsingListView;

public class navigationMain extends AppCompatActivity {
    private static final String TAG = "navigationMain";
    private TextView mTextMessage;
    private Toolbar toolbar;



    private Fragment mfragment;
    private FragmentManager fragmentManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Log.d(TAG, "onNavigationItemSelected: " + item.getItemId());

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toolbar.setTitle(R.string.title_home);
                    UserDashboard fragment1 = new UserDashboard();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayout, fragment1);
                    transaction.commit();
                    break;

                case R.id.contact:
                    toolbar.setTitle("Contacts");
                    contactsFragment fragment4 = new contactsFragment();
                    FragmentTransaction transaction3 = getFragmentManager().beginTransaction();
                    transaction3.replace(R.id.frameLayout, fragment4);
                    transaction3.commit();

                    break;

                case R.id.navigation_dashboard:
                    toolbar.setTitle(R.string.title_dashboard);
                    playStoreTime fragment2 = new playStoreTime();
                    FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
                    Bundle b = new Bundle();
                    b.putString("taluka","ahmedabad");
                    fragment2.setArguments(b);
                    transaction2.replace(R.id.frameLayout, fragment2);
                    transaction2.commit();
                    break;
                case R.id.navigation_maps:
                    toolbar.setTitle(R.string.title_map);
                    Intent i = new Intent(navigationMain.this,MapsActivity.class);
                    i.putExtra("taluka","ahmedabad");
                    startActivity(i);

                    startActivity(i);

//                    gpsTracker gps = new gpsTracker(navigationMain.this);
//                    if(gps.canGetLocation()){
//
//                        double latitude = gps.getLatitude(); // returns latitude
//                        double longitude = gps.getLongitude(); // returns longitude
//-
//                        Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
//
//                    }else{
//                        gps.showSettingsAlert();
//                    }


                    break;
//
//                case R.id.navigation_report:
//                    toolbar.setTitle(R.string.title_report);
//                    webView wv = new webView();
//                    FragmentTransaction transaction3 = getFragmentManager().beginTransaction();
//                    transaction3.replace(R.id.frameLayout, wv);
//                    transaction3.commit();
//                    break;
                default:
                    return false;
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);


            if(sharedPreference.getInstance(this)==null){
                Log.d(TAG, "onCreate: sharedprefreence Null");
                startActivity(new Intent(this, LoginActivity2.class));
            }
            if (!sharedPreference.getInstance(this).isLoggedIn()) {
                finish();
                Log.d(TAG, "onCreate: not logged IN");
                startActivity(new Intent(this, LoginActivity2.class));

            }else {

                User user = sharedPreference.getInstance(this).getUser();
                Log.d(TAG, "onCreate: logged In as " + user.toString());
                setContentView(R.layout.activity_navigation_main);


                toolbar = (Toolbar) findViewById(R.id.toolbar);
                //toolbar.setTitle(user.gettype());
                //toolbar.setTitle(user.getUsername());
                setSupportActionBar(toolbar);

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                fragmentManager = getFragmentManager();
                UserDashboard fragmentN = new UserDashboard();
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.frameLayout, fragmentN).commit();
                mTextMessage = (TextView) findViewById(R.id.message);
                BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
                Menu menu = navigation.getMenu();

                Log.v("test",user.gettype().toLowerCase());
                if(!user.gettype().toLowerCase().equals("user")){
                    menu.removeItem(R.id.contact);
                }else{
                    Log.v("test","else");
                    menu.removeItem(R.id.navigation_dashboard);
                    menu.removeItem(R.id.navigation_maps);
                }

                navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


                //
                //        FragmentManager fragmentManager = getFragmentManager();
                //        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //        fragmentTransaction.replace(R.id.frameLayout, f);
                //        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                //        fragmentTransaction.commit();

                //playStoreTime firstFragment = new playStoreTime();
                //        UserDashboard firstFragment = new UserDashboard();
                //
                //        firstFragment.setArguments(getIntent().getExtras());
                //        getSupportFragmentManager().beginTransaction()
                //                .add(R.id.frameLayout, firstFragment).commit();

            }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dot_menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent i;
        switch (item.getItemId()) {
            case R.id.logout:
                sharedPreference.getInstance(this).logout();
                return true;

            case R.id.open1:

                sharedPreference.getInstance(this).setValidate(1);
                return true;
            case R.id.open2:

                sharedPreference.getInstance(this).setValidate(2);
                return true;

            case R.id.open3:

                sharedPreference.getInstance(this).setValidate(0);
//                i = new Intent(navigationMain.this,recycleTime1.class);
//                startActivity(i);
                return true;

            case R.id.open4:

                i = new Intent(navigationMain.this,jsonParsingListView.class);
                startActivity(i);
                return true;

            case R.id.link:

                    changeLinkDialog newFragment = new changeLinkDialog();
                    newFragment.show(getFragmentManager(),"hello");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Log.v("tesing", "doooo" + requestCode + " " + resultCode);
        if (requestCode == 17 ){//&& requestCode == RESULT_OK) {
            //continueInput = true;
            Log.v("tesing", "donme");
        }else{
            Log.v("tesing", "not donme");
        }
    }

}
