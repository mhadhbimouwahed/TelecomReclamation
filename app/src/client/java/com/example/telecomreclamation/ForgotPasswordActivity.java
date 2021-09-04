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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText reset_email;
    private TextView reset_password;
    private ProgressBar progressBar_reset;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    CollectionReference collectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        collectionReference=firestore.collection("CreatedUsers");

        reset_email=findViewById(R.id.reset_email);
        reset_password=findViewById(R.id.reset_password);
        progressBar_reset=findViewById(R.id.reset_progress_bar);

        reset_password.setOnClickListener(x->{
            if (reset_email.getText().toString().equals("")){
                reset_email.setError("This fiald cannot be empty");
            }else{
                progressBar_reset.setVisibility(View.VISIBLE);
                ResetPassword();
                progressBar_reset.setVisibility(View.INVISIBLE);
            }
        });


    }

    private void ResetPassword() {
        collectionReference
                .whereEqualTo("ClientEmail",reset_email.getText().toString())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        if (task.getResult().isEmpty()){
                            AlertDialog.Builder builder=new AlertDialog.Builder(this);
                            builder.create();
                            builder.setTitle("Error");
                            builder.setMessage("please check the email address again");
                            builder.setPositiveButton("Okay",((dialogInterface, i) -> dialogInterface.dismiss()));
                            builder.show();

                        }else{
                            for (QueryDocumentSnapshot documentSnapshot:task.getResult()){
                                firebaseAuth.sendPasswordResetEmail(documentSnapshot.getString("ClientEmail").toString())
                                        .addOnCompleteListener(task1 -> {
                                           if (task1.isSuccessful()){
                                               AlertDialog.Builder builder=new AlertDialog.Builder(this);
                                               builder.create();
                                               builder.setTitle("Success");
                                               builder.setMessage("the reset information is sent, please check your inbox and continue the procedure from there");
                                               builder.setPositiveButton("Okay",((dialogInterface, i) -> dialogInterface.dismiss()));
                                               builder.show();
                                               reset_email.setText("");
                                           }else{
                                               Toast.makeText(getApplicationContext(), "please check your internet connection", Toast.LENGTH_SHORT).show();
                                           }
                                        })
                                        .addOnFailureListener(e1->{
                                            Log.d("ERRO_SENDING_RESET_INFORMATION",e1.getMessage());
                                        });
                            }
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                   Log.d("ERROR_READING_FROM_DATABASE",e.getMessage());
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar_reset.setVisibility(View.INVISIBLE);
    }
}