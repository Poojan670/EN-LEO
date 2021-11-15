package com.gamecodeschool.en_leo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gamecodeschool.en_leo.R;

public class NoInternetActivity extends AppCompatActivity {

    private TextView RetryText;
    private Button RetryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

        RetryText = findViewById(R.id.textRetry);
        RetryButton = findViewById(R.id.btnRetry);

        RetryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(NoInternetActivity.this, MainActivity.class));
                getIntent().addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            }
        });
    }
}
