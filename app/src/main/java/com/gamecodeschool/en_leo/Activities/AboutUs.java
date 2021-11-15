package com.gamecodeschool.en_leo.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.gamecodeschool.en_leo.R;

public class AboutUs extends AppCompatActivity {

    public TextView a,b,c;
    public ImageView i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);

        a = findViewById(R.id.textView);
        b = findViewById(R.id.textView2);
        c = findViewById(R.id.textView3);
        i = findViewById(R.id.imageView3);
    }
}
