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

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private TextView login;
    private TextView forgotPassword;
    private TextView signup;
    private ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    CollectionReference collectionReference;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        collectionReference=firestore.collection("CreatedUsers");

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        forgotPassword=findViewById(R.id.forgotPassword);
        signup=findViewById(R.id.signup);
        progressBar=findViewById(R.id.progress_bar);

        login.setOnClickListener(x->{
            if (email.getText().toString().equals("")){
                email.setError("This field cannot be empty");
            }else if(password.getText().toString().equals("")){
                password.setError("This field cannot be empty");
            }else{
                progressBar.setVisibility(View.VISIBLE);
                Login();
            }
        });


        signup.setOnClickListener(x->{
            startActivity(new Intent(getApplicationContext(),SignupActivity.class));
        });

        forgotPassword.setOnClickListener(x->{
            startActivity(new Intent(getApplicationContext(),ForgotPasswordActivity.class));
        });

    }

    private void Login() {
        collectionReference
                .whereEqualTo("ClientEmail",email.getText().toString())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        if (task.getResult().isEmpty()){
                            AlertDialog.Builder builder=new AlertDialog.Builder(this);
                            builder.create();
                            builder.setTitle("CONTACT THE ADMIN");
                            builder.setMessage("Please contact the admin so you could connect");
                            builder.setPositiveButton("Okay",((dialogInterface, i) -> dialogInterface.dismiss()));
                            builder.show();
                            email.setText("");
                            password.setText("");
                            progressBar.setVisibility(View.INVISIBLE);
                        }else{
                            for (QueryDocumentSnapshot documentSnapshot:task.getResult()){
                                firebaseAuth.signInWithEmailAndPassword(Objects.requireNonNull(documentSnapshot.getString("ClientEmail")),password.getText().toString())
                                        .addOnCompleteListener(task1 -> {
                                            if (task1.isSuccessful()){
                                                startActivity(new Intent(getApplicationContext(),NavActivity.class));
                                            }else{
                                                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                                                builder.create();
                                                builder.setTitle("Error");
                                                builder.setMessage("Please check you email and password");
                                                builder.setPositiveButton("Okay",((dialogInterface, i) -> dialogInterface.dismiss()));
                                                builder.show();
                                            }
                                        })
                                        .addOnFailureListener(e1->{
                                           Log.d("ERROR_SIGINING_CLIENT",e1.getMessage());
                                        });
                            }
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d("ERROR_READING_FROM_DATABASE",e.getMessage());
                    progressBar.setVisibility(View.INVISIBLE);
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar.setVisibility(View.INVISIBLE);
        user=firebaseAuth.getCurrentUser();
        if (user!=null){
            startActivity(new Intent(getApplicationContext(),NavActivity.class));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        progressBar.setVisibility(View.INVISIBLE);
    }
}