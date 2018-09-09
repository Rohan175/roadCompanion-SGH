package com.rsking175453.com.sgh_try1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        mCollapsingToolbarLayout.setTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("title");


        Intent i = getIntent();
        Bundle b = i.getExtras();
        int id = b.getInt("id");
        String url = i.getStringExtra("url");

        ImageView im = (ImageView)findViewById(R.id.imageView3);
        Glide.with(this).load(url)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(im);

        String area = i.getStringExtra("Area");
        String griv = i.getStringExtra("griv");
        String road = i.getStringExtra("road");
        String complainId = i.getStringExtra("complainId");
        String city = i.getStringExtra("city");
        String discriptiom = i.getStringExtra("Discriptiom");
        String status = i.getStringExtra("status");
        String comment = i.getStringExtra("comment");
        String officerName = i.getStringExtra("officerName");
        String Estimatedtime = i.getStringExtra("estimatedTime");
        final String email = i.getStringExtra("email");
        String time = i.getStringExtra("time");
        //time = "24-3-2018 8:30";

        TextView t = (TextView)findViewById(R.id.taluka_name);
        t.setText(area);
        TextView t2 = (TextView)findViewById(R.id.griTypeScroll);
        t2.setText(griv);
        TextView t3 = (TextView)findViewById(R.id.roadTypeScroll);
        t3.setText(road);
        TextView complainIdText = (TextView)findViewById(R.id.complain_id);
        complainIdText.setText(complainId);
        TextView t4 = (TextView)findViewById(R.id.statusScroll);
        t4.setText(status.toUpperCase());
        TextView t5 = (TextView)findViewById(R.id.district_name);
        t5.setText(city);


        TextView t6 = (TextView)findViewById(R.id.descriptions);
        t6.setText(discriptiom);


        TextView t7 = (TextView)findViewById(R.id.comment);
        t7.setText(comment);

        TextView roadName = (TextView)findViewById(R.id.roadName);
        roadName.setText(road);


        TextView t8 = (TextView)findViewById(R.id.officerName);
        t8.setText(officerName);
        Log.v("input2",Estimatedtime.toString());

        if(Estimatedtime == null || Estimatedtime.toString().toLowerCase() == "none"){
            Estimatedtime = "To be updated soon";
            Log.v("input2",Estimatedtime);
        }

        TextView t9 = (TextView)findViewById(R.id.estimatedDate);
        t9.setText(Estimatedtime);


        ImageView t10 = (ImageView)findViewById(R.id.emailOfficer);
        t10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);

                String uriText = "mailto:" + Uri.encode(email) +
                        "?subject=" + Uri.encode("Complain for a SGH app user") +
                        "&body=" + Uri.encode(" ");
                Uri uri = Uri.parse(uriText);

                emailIntent.setData(uri);

                startActivity(emailIntent);
            }
        });

        TextView t11 = (TextView)findViewById(R.id.submittedDate);

        if(time.isEmpty() || time ==null){
            time = "24-3-2018 8:30";
        }

        //String[] times = time.split("\\s+");
        //time = times[0];
        t11.setText(time.toUpperCase().substring(0,10));

//        TextView t4 = (TextView)findViewById(R.id.discription);
//        t3.setText(discriptiom);
//
//
//        TextView t5 = (TextView)findViewById(R.id.status);
//        t3.setText(status);




//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }
}
