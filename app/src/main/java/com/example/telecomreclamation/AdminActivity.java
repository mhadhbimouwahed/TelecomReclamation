package com.example.telecomreclamation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminActivity extends AppCompatActivity {
    
    private TextView logout;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        //firebaseAuth=FirebaseAuth.getInstance();
        
        
        logout=findViewById(R.id.logout);
        
        logout.setOnClickListener(x->{
            /*user=firebaseAuth.getCurrentUser();
            if (user!=null){
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }*/
        });
    }


}