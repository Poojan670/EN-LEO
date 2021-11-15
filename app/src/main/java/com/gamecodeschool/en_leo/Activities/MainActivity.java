package com.gamecodeschool.en_leo.Activities;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gamecodeschool.en_leo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.widget.Toast.makeText;


public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private Button Login;
    private TextView userRegistration;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private ProgressDialog progressDialog;
    private int counter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (CheckNetwork.isInternetAvailable(MainActivity.this))
        {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Name = findViewById(R.id.etName);
        Password = findViewById(R.id.etPassword);
        Login = findViewById(R.id.btnLogin);
        userRegistration = findViewById(R.id.tvRegister);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        final FirebaseUser user = firebaseAuth.getCurrentUser();

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });
        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });
            if(user != null){
                finish();
                startActivity(new Intent(this, NavigationActivity.class));
            }
        }else {
            Toast toast = makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_LONG);
            toast.show();
            startActivity(new Intent(MainActivity.this, NoInternetActivity.class));
        }
    }

    private void validate(final String userName, final String userPassword) {

        progressDialog.setMessage("Login in process");
        progressDialog.show();

        if(userName.equals("admin") && userPassword.equals("123456789")){
            progressDialog.dismiss();
            makeText(MainActivity.this, "Welcome to Admin Panel. Please insert all the details to post.", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(MainActivity.this, EventUpload.class));
        }else {
            firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        makeText(MainActivity.this, "WELCOME", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(MainActivity.this, NavigationActivity.class));
                    } else {
                        makeText(MainActivity.this, "Oops! You're not a LEO member.", Toast.LENGTH_SHORT).show();
                        counter--;
                        progressDialog.dismiss();
                        if (counter == 0) {
                            Login.setEnabled(false);
                        }
                    }
                }
            });
        }
    }
}