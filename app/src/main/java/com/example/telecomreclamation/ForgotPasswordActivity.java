package com.example.telecomreclamation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    
    private TextView resetPassword;
    private EditText email_forgot_password;
    FirebaseAuth firebaseAuth;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        firebaseAuth=FirebaseAuth.getInstance();
        
        resetPassword=findViewById(R.id.resetPassword);
        email_forgot_password=findViewById(R.id.email_forgot_password);
        
        resetPassword.setOnClickListener(x->{
            if (email_forgot_password.getText().toString().equals("")){
                email_forgot_password.setError("This field cannot be empty");
            }else{
                sendResetInformation();
            }
        });
    }

    private void sendResetInformation() {
        firebaseAuth.sendPasswordResetEmail(email_forgot_password.getText().toString())
                .addOnCompleteListener(task->{
                    if (task.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "an email was sent", Toast.LENGTH_SHORT).show();
                        email_forgot_password.setText("");
                    }else{
                        Toast.makeText(getApplicationContext(), "operation failed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(failure->{
            Toast.makeText(getApplicationContext(), "please check your internet connection", Toast.LENGTH_SHORT).show();
        });
        
    }
}