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

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private TextView login;
    private TextView forgotPassword;
    private TextView signup;
    private ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth=FirebaseAuth.getInstance();

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
        /*firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "Logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),NavActivity.class));
                    }else{
                        AlertDialog.Builder builder=new AlertDialog.Builder(this);
                        builder.create();
                        builder.setTitle("Error");
                        builder.setMessage("please check your email and password");
                        builder.setPositiveButton("Okay",((dialogInterface, i) -> dialogInterface.dismiss()));
                        builder.show();
                        Toast.makeText(getApplicationContext(), "failed ", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d("ERROR_SIGNING_IN",e.getMessage());
                });*/
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