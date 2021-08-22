package com.example.telecomreclamation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText email_forgot;
    private TextView send_reset;
    private ProgressBar progressBar_forgot;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email_forgot=findViewById(R.id.email_forgot_password);
        send_reset=findViewById(R.id.resetPassword);
        progressBar_forgot=findViewById(R.id.progress_bar_forgot_password);
        firebaseAuth=FirebaseAuth.getInstance();

        send_reset.setOnClickListener(x->{
            if (email_forgot.getText().toString().equals("")){
                email_forgot.setError("This fiald cannot be empty");
            }else{
                progressBar_forgot.setVisibility(View.VISIBLE);
                sendReset();
                progressBar_forgot.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void sendReset() {

        firebaseAuth.sendPasswordResetEmail(email_forgot.getText().toString())
                .addOnCompleteListener(task->{
                    if (task.isSuccessful()){
                        AlertDialog.Builder builder=new AlertDialog.Builder(this);
                        builder.create();
                        builder.setTitle("Email sent successfully");
                        builder.setMessage("open you email and continue the procedure from there");
                        builder.setPositiveButton("Okay",((dialog, which) -> dialog.dismiss()));
                        builder.show();
                        email_forgot.setText("");
                    }else{
                        AlertDialog.Builder builder=new AlertDialog.Builder(this);
                        builder.create();
                        builder.setTitle("Error");
                        builder.setMessage("please check your email address");
                        builder.setPositiveButton("Okay",((dialog, which) -> dialog.dismiss()));
                        builder.show();

                        Toast.makeText(this, "please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(failure->{
                    Log.d("ERROR_SENDING_RESET_INFORMATION",failure.getMessage());
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        progressBar_forgot.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        progressBar_forgot.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressBar_forgot.setVisibility(View.INVISIBLE);
    }
}