package com.example.telecomreclamation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;


public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText reset_email;
    private TextView reset_password;
    private ProgressBar reset_progress_bar;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    CollectionReference collectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        collectionReference=firestore.collection("AdminUsers");


        reset_email=findViewById(R.id.reset_email);
        reset_password=findViewById(R.id.reset_password);
        reset_progress_bar=findViewById(R.id.reset_progress_bar);


        reset_password.setOnClickListener(x->{
            if (reset_email.getText().toString().equals("")){
                reset_email.setError("This field cannot be empty");
            }else{
                reset_progress_bar.setVisibility(View.VISIBLE);
                resetPassword();
                reset_progress_bar.setVisibility(View.INVISIBLE);
            }
        });


    }

    private void resetPassword() {

        collectionReference.whereEqualTo("Email",reset_email.getText().toString()).get()
                .addOnCompleteListener(task->{
                    if (task.isSuccessful()){
                        if (task.getResult().isEmpty()){
                            AlertDialog.Builder builder=new AlertDialog.Builder(this);
                            builder.create();
                            builder.setTitle("Error");
                            builder.setMessage("the email address you entered is incorrect");
                            builder.setPositiveButton("Okay",((dialog, which) -> dialog.dismiss()));
                            builder.show();
                        }else{
                            for (QueryDocumentSnapshot documentSnapshot:task.getResult()){
                                HashMap<String,Object> hashMap=new HashMap<>(documentSnapshot.getData());


                                firebaseAuth.sendPasswordResetEmail(reset_email.getText().toString())
                                        .addOnCompleteListener(secondTask->{
                                            if (secondTask.isSuccessful()){
                                                Toast.makeText(getApplicationContext(), "check your inbox, continue the procedure from there", Toast.LENGTH_SHORT).show();
                                                reset_email.setText("");
                                            }else{
                                                Toast.makeText(getApplicationContext(), "failed to reset password, please check your internet connection", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(secondFailure->{
                                    Log.d("ERROR_SENDING_RESET_INFORMATION",secondFailure.getMessage());
                                });

                            }
                        }

                    }else{
                        Toast.makeText(getApplicationContext(), "please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(failure->{
                    Log.d("ERROR_READING_FROM_ADMINUSERS",failure.getMessage());
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        reset_progress_bar.setVisibility(View.INVISIBLE);
    }
}