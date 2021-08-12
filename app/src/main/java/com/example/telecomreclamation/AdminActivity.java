package com.example.telecomreclamation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminActivity extends AppCompatActivity {

    Button signout;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        firebaseAuth=FirebaseAuth.getInstance();

        signout=findViewById(R.id.signout);


        signout.setOnClickListener(x->{
            user=firebaseAuth.getCurrentUser();
            if(user!=null){
                if (user.getEmail().equals("adminpage@gmail.com")){
                    firebaseAuth.signOut();
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    Toast.makeText(getApplicationContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}