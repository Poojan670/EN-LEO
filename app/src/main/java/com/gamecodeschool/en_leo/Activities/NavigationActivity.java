package com.gamecodeschool.en_leo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gamecodeschool.en_leo.Data.EventRecyclerAdapter;
import com.gamecodeschool.en_leo.Model.Event;
import com.gamecodeschool.en_leo.Model.UserProfile;
import com.gamecodeschool.en_leo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference EventsDatabaseReference, UsersDatabaseReference;
    private FirebaseDatabase firebaseDatabase;
    private NavigationView navigationView;
    private RecyclerView recyclerView;
    private EventRecyclerAdapter eventRecyclerAdapter;
    private android.support.v4.app.FragmentTransaction fragmentTransaction;
    private List<Event> eventList;
    private LinearLayoutManager mLayoutManager;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        eventList = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        EventsDatabaseReference = firebaseDatabase.getReference().child("Events");
        EventsDatabaseReference.keepSynced(true);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);



        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        UsersDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());

        View headView = navigationView.getHeaderView(0);
        final TextView Username = headView.findViewById(R.id.username);
        final ImageView UserPic = headView.findViewById(R.id.image);
        final TextView Useremail = headView.findViewById(R.id.useremail);
        final TextView Userage = headView.findViewById(R.id.userage);

        UserPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(NavigationActivity.this, ProfileActivity.class));
            }
        });
        UsersDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);

                Username.setText("WELCOME, Leo " + userProfile.getUserFullName());
                Useremail.setText(userProfile.getUserEmail());
                Userage.setText(userProfile.getUserAge());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_logout){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(NavigationActivity.this , MainActivity.class));
        }else if(id == R.id.action_profile){
            Intent i = new Intent(getApplicationContext() , ProfileActivity.class);
            startActivity(i);
        }else if(id == R.id.action_refresh){
            finish();
            startActivity(new Intent(NavigationActivity.this, NavigationActivity.class));
            getIntent().addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.about_us){
            startActivity(new Intent(this, AboutUs.class));
        }else if(id == R.id.database_bloodbank){
            startActivity(new Intent(this, BloodBankDatabase.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

        protected void onStart () {
                super.onStart();

                clearData();
                EventsDatabaseReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Event event = dataSnapshot.getValue(Event.class);
                        eventList.add(event);

                        eventRecyclerAdapter = new EventRecyclerAdapter(NavigationActivity.this, eventList);
                        recyclerView.setAdapter(eventRecyclerAdapter);
                        eventRecyclerAdapter.notifyDataSetChanged();

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
    public void clearData(){

        final int size = eventList.size();

        if(size > 0)
        {
            eventList.clear();
        }
    }
}



