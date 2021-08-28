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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    private EditText full_name;
    private EditText email_signup;
    private EditText password_signup;
    private EditText confirm_password_signup;
    private TextView signup;
    private ProgressBar progress_bar_signup;

    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    CollectionReference collectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        full_name=findViewById(R.id.full_name);
        email_signup=findViewById(R.id.email_signup);
        password_signup=findViewById(R.id.password_signup);
        confirm_password_signup=findViewById(R.id.confirm_password_signup);
        signup=findViewById(R.id.signup_signup);
        progress_bar_signup=findViewById(R.id.progress_bar_signup);
        
        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        collectionReference=firestore.collection("AdminUsers");


        signup.setOnClickListener(x->{
           if(full_name.getText().toString().equals("")){
               full_name.setError("This field cannot be empty");
           }else if (email_signup.getText().toString().equals("")){
                email_signup.setError("This field cannot be empty");
            }else if(password_signup.getText().toString().equals("")){
                password_signup.setError("This field cannot be empty");
            }else if (confirm_password_signup.getText().toString().equals("")){
                confirm_password_signup.setError("This field cannot be empty");
            }else if(!password_signup.getText().toString().equals(confirm_password_signup.getText().toString())){
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.create();
                builder.setTitle("Error");
                builder.setMessage("Passwords must match");
                builder.setPositiveButton("Okay",((dialog, which) -> dialog.dismiss()));
                builder.show();
            }else{
               progress_bar_signup.setVisibility(View.VISIBLE);
                Signup();
                progress_bar_signup.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void Signup() {
        firebaseAuth.createUserWithEmailAndPassword(email_signup.getText().toString(),password_signup.getText().toString())
                .addOnCompleteListener(task->{
                    if (task.isSuccessful()){
                        HashMap<String,String> new_user=new HashMap<>();
                        new_user.put("UserID",firebaseAuth.getUid());
                        new_user.put("Email",email_signup.getText().toString());
                        new_user.put("FullName",full_name.getText().toString());
                        collectionReference.document(firebaseAuth.getUid()).set(new_user)
                                .addOnCompleteListener(secondTask->{
                                    if (secondTask.isSuccessful()){
                                        Toast.makeText(this, "user created successfully", Toast.LENGTH_SHORT).show();
                                        full_name.setText("");
                                        email_signup.setText("");
                                        password_signup.setText("");
                                        confirm_password_signup.setText("");
                                        startActivity(new Intent(this,NavActivity.class));

                                    }else{
                                        Toast.makeText(this, "couldn't save the user", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(secondFailure->{
                                    Log.d("ERROR_CREATING_USER",secondFailure.getMessage());
                        });
                    }else{
                        AlertDialog.Builder builder=new AlertDialog.Builder(this);
                        builder.create();
                        builder.setTitle("Error");
                        builder.setMessage("the account already exist!");
                        builder.setPositiveButton("Okay",((dialog, which) -> dialog.dismiss()));
                        builder.show();
                        Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(failure->{
                    Log.d("Error_SIGNUP",failure.getMessage());
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        progress_bar_signup.setVisibility(View.INVISIBLE);

    }
}