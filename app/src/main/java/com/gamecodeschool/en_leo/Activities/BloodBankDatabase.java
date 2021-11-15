package com.gamecodeschool.en_leo.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.gamecodeschool.en_leo.Data.BloodRecyclerAdapter;
import com.gamecodeschool.en_leo.Model.UserProfile;
import com.gamecodeschool.en_leo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BloodBankDatabase extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference UsersDatabaseReference;
    private FirebaseDatabase firebaseDatabase;
    private List<UserProfile> userList;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private BloodRecyclerAdapter bloodRecyclerAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloodbankdatabase);

        userList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);

        UsersDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        UsersDatabaseReference.keepSynced(true);


        clearData();
        UsersDatabaseReference.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                userList.add(userProfile);

                bloodRecyclerAdapter = new BloodRecyclerAdapter(BloodBankDatabase.this, userList);
                sortAscending();
                recyclerView.setAdapter(bloodRecyclerAdapter);
                bloodRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void onBackPressed(){
        finish();
    }
    public void clearData(){

        final int size = userList.size();

        if(size > 0)
        {
            userList.clear();
        }
    }
    public void sortAscending(){
        Collections.sort(userList, new Comparator<UserProfile>() {
            @Override
            public int compare(UserProfile o1, UserProfile o2) {
                return String.CASE_INSENSITIVE_ORDER.compare(o2.getUserBloodGroup(),o1.getUserBloodGroup());
            }
        });
    }

}
