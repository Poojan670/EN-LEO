package com.gamecodeschool.en_leo.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gamecodeschool.en_leo.R;

public class EventDetailActivity extends AppCompatActivity {

    public TextView eventtitle, eventdate, eventTime, eventdesc;
    public ImageView image;
    public String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        image = findViewById(R.id.imageView2);
        getIncomingIntent();
        image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(imageUrl));
                startActivity(intent);
            }
        });
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("event_image_url") && getIntent().hasExtra("event_title") && getIntent().hasExtra("event_date") && getIntent().hasExtra("event_time")){
            imageUrl = getIntent().getStringExtra("event_image_url");
            String title = getIntent().getStringExtra("event_title");
            String desc = getIntent().getStringExtra("event_description");
            String date = getIntent().getStringExtra("event_date");
            String time = getIntent().getStringExtra("event_time");
            setEvent(imageUrl, title, desc, date, time);
        }
    }

    private void setEvent(String imageUrl, String title, String desc, String date, String time){

        eventtitle = findViewById(R.id.textTitle);
        eventtitle.setText(title);

        eventdesc = findViewById(R.id.textDescription);
        eventdesc.setText("Event Description: " + desc);

        eventdate = findViewById(R.id.textDate);
        eventdate.setText("Event Date: " + date);

        eventTime = findViewById(R.id.textTime);
        eventTime.setText("Event Time: " + time);

        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(image);
    }
}
