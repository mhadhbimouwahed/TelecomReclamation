package com.example.telecomreclamation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.w3c.dom.Text;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    TextView login;
    TextView forgotPassword;
    TextView signup;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    CollectionReference collectionReference;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        forgotPassword=findViewById(R.id.forgotPassword);
        signup=findViewById(R.id.signup);
        progressBar=findViewById(R.id.progress_bar);
        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        collectionReference=firestore.collection("AdminUsers");



        login.setOnClickListener(x->{
            if (email.getText().toString().equals("")){
                email.setError("This field cannot be empty");
            }else if(password.getText().toString().equals("")){
                password.setError("This field cannot be empty");
            }else{
                Login();
            }
        });

        forgotPassword.setOnClickListener(x->{

        });

        signup.setOnClickListener(x->{
            startActivity(new Intent(this,SignupActivity.class));
        });

    }

    private void Login() {
        progressBar.setVisibility(View.VISIBLE);
        collectionReference.whereEqualTo("Email",email.getText().toString()).get()
                .addOnCompleteListener(firstTask->{
                    if (firstTask.isSuccessful()){
                        for (QueryDocumentSnapshot documentSnapshot:firstTask.getResult()){
                            HashMap<String,Object> hashMap=new HashMap<>(documentSnapshot.getData());
                            if (hashMap.isEmpty()){
                                Toast.makeText(this, "You are not registered as an admin!!", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
                            }else{
                                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                                        .addOnCompleteListener(task->{
                                            if (task.isSuccessful()){
                                                progressBar.setVisibility(View.INVISIBLE);
                                                startActivity(new Intent(this,NavActivity.class));
                                                Toast.makeText(getApplicationContext(), "Loged in successfully", Toast.LENGTH_SHORT).show();

                                            }else{
                                                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                                                builder.create();
                                                builder.setTitle("Error");
                                                builder.setMessage("Please check your email address and your password");
                                                builder.setPositiveButton("Okay",((dialog, which) -> dialog.dismiss()));
                                                builder.show();
                                                Toast.makeText(getApplicationContext(), "the problem could be an internet connection", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.INVISIBLE);
                                            }
                                        }).addOnFailureListener(failure->{
                                    Log.d("ERROR_SIGNIN",failure.getMessage());
                                });
                            }
                        }
                    }else{
                        Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(firstFailure->{
                    Log.d("ERROR_LOGING_IN",firstFailure.getMessage());
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar.setVisibility(View.INVISIBLE);
        user=firebaseAuth.getCurrentUser();
        if (user!=null){
            startActivity(new Intent(this,NavActivity.class));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();
    }
}