package com.rsking175453.com.sgh_try1;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


/*



Main Acivity for this app is NavigationMain Activity


*/

public class MainActivity extends AppCompatActivity {


    private Button btnCapturePicture, btnRecordVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


            btnCapturePicture = (Button) findViewById(R.id.btnCapturePicture);

            btnCapturePicture.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent i = new Intent(MainActivity.this,input.class);
                    startActivity(i);
                }
            });

        }


}
