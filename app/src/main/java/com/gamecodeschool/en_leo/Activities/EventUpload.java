package com.gamecodeschool.en_leo.Activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.gamecodeschool.en_leo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class EventUpload extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText mPostTitle, mPostImage;
    private EditText mPostDesc;
    private EditText mPostDate;
    private EditText mPostTime;
    private Button mSubmitButton, Logout;// Notify;
    private DatabaseReference mPostDatabase, newPost;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private Map<String, String> dataToSave;
    private static final int GALLERY_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventupload);

        mProgress = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        mStorage = FirebaseStorage.getInstance().getReference();

        mPostDatabase = FirebaseDatabase.getInstance().getReference().child(Constants.DATABASE_PATH_UPLOADS);
        newPost = FirebaseDatabase.getInstance().getReference();

        mPostImage = findViewById(R.id.imageURLEt);
        mPostTitle = findViewById(R.id.postTitleEt);
        mPostDesc = findViewById(R.id.descriptionEt);
        mPostDate = findViewById(R.id.editDate);
        mPostTime = findViewById(R.id.editTime);
        //Notify = findViewById(R.id.btnNotify);
        mSubmitButton = findViewById(R.id.submitPost);
        Logout = findViewById(R.id.btnLogOut);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        });
        /* Notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EventUpload.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(EventUpload.this,1,i, PendingIntent.FLAG_UPDATE_CURRENT);

                Notification notification = new Notification.Builder(EventUpload.this)
                        .setContentTitle("ENLEO")
                        .setContentText("We've got a new event for you. Please Check it out!")
                        .setSmallIcon(R.drawable.logoleo)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .build();

                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                assert notificationManager != null;
                notificationManager.notify(0,notification);
            }
        }); */
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(EventUpload.this , MainActivity.class));
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        }

    private void startPosting() {
        mProgress.setMessage("Posting...");
        mProgress.show();

        final String titleVal = mPostTitle.getText().toString().trim();
        final String descVal = mPostDesc.getText().toString().trim();
        final String imageVal = mPostImage.getText().toString().trim();
        final String dateVal = mPostDate.getText().toString().trim();
        final String timeVal = mPostTime.getText().toString().trim();

        if(!TextUtils.isEmpty(titleVal) && (!TextUtils.isEmpty(descVal)) && (!TextUtils.isEmpty(imageVal))) {
                    newPost = mPostDatabase.push();
                    dataToSave = new HashMap<>();
                    dataToSave.put("title", titleVal);
                    dataToSave.put("desc", descVal);
                    dataToSave.put("timestamp", String.valueOf(java.lang.System.currentTimeMillis()));
                    dataToSave.put("image", imageVal);
                    dataToSave.put("date", dateVal);
                    dataToSave.put("time", timeVal);
                    newPost.setValue(dataToSave);

                    mProgress.dismiss();

                    finish();
                    startActivity(new Intent(EventUpload.this, MainActivity.class));
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
