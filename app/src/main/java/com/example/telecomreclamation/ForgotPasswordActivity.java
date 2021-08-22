package com.example.telecomreclamation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    
    private TextView resetPassword;
    private EditText email_forgot_password;
    private ProgressBar progress_bar_forgot_password;
    FirebaseAuth firebaseAuth;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        firebaseAuth=FirebaseAuth.getInstance();
        
        resetPassword=findViewById(R.id.resetPassword);
        email_forgot_password=findViewById(R.id.email_forgot_password);
        progress_bar_forgot_password=findViewById(R.id.progress_bar_forgot_password);
        
        resetPassword.setOnClickListener(x->{
            if (email_forgot_password.getText().toString().equals("")){
                email_forgot_password.setError("This field cannot be empty");
            }else{
                sendResetInformation();
            }
        });
    }

    private void sendResetInformation() {
        progress_bar_forgot_password.setVisibility(View.VISIBLE);
        firebaseAuth.sendPasswordResetEmail(email_forgot_password.getText().toString())
                .addOnCompleteListener(task->{
                    if (task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "an email was sent", Toast.LENGTH_SHORT).show();
                        email_forgot_password.setText("");
                        progress_bar_forgot_password.setVisibility(View.INVISIBLE);
                    }else{
                        Toast.makeText(getApplicationContext(), "operation failed, please check your internet connection", Toast.LENGTH_SHORT).show();
                        progress_bar_forgot_password.setVisibility(View.INVISIBLE);
                        AlertDialog.Builder builder=new AlertDialog.Builder(this);
                        builder.create();
                        builder.setTitle("Error");
                        builder.setMessage("The email you entered is incorrect");
                        builder.setPositiveButton("Okay",((dialog, which) -> dialog.dismiss()));
                        builder.show();
                    }
                }).addOnFailureListener(failure->{
            Toast.makeText(getApplicationContext(), "please check your internet connection", Toast.LENGTH_SHORT).show();
            progress_bar_forgot_password.setVisibility(View.INVISIBLE);
        });
        progress_bar_forgot_password.setVisibility(View.INVISIBLE);
        
    }

    @Override
    protected void onStart() {
        super.onStart();
        progress_bar_forgot_password.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        progress_bar_forgot_password.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progress_bar_forgot_password.setVisibility(View.INVISIBLE);
    }
}