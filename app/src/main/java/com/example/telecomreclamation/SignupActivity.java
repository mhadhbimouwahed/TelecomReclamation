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

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {
    
    private EditText email_signup;
    private EditText password_signup;
    private EditText confirmPassword_signup;
    private TextView signup_signup;
    private ProgressBar progressBar_singup;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    CollectionReference collectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        collectionReference=firestore.collection("Users");

        email_signup=findViewById(R.id.email_signup);
        password_signup=findViewById(R.id.password_signup);
        confirmPassword_signup=findViewById(R.id.confirm_password_signup);
        signup_signup=findViewById(R.id.signup_signup);
        progressBar_singup=findViewById(R.id.progress_bar_signup);
        
        
        signup_signup.setOnClickListener(x->{
            if (email_signup.getText().toString().equals("")){
                email_signup.setError("This field cannot be empty");
            }else if(password_signup.getText().toString().equals("")){
                password_signup.setError("This field cannot be empty");
            }else if(confirmPassword_signup.getText().toString().equals("")){
                confirmPassword_signup.setError("This field cannot be empty");
            }else if(!password_signup.getText().toString().equals(confirmPassword_signup.getText().toString())){
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.create();
                builder.setTitle("Password Error");
                builder.setMessage("Passwords do not match, please check again");
                builder.setPositiveButton("Okay",(dialog,which)->dialog.dismiss());
                builder.show();
            }else{
                Signup();
            }
        });

    }

    private void Signup() {
        firebaseAuth.createUserWithEmailAndPassword(email_signup.getText().toString(),password_signup.getText().toString())
                .addOnCompleteListener(task->{
                    if (task.isSuccessful()){

                        HashMap<String,String> new_user=new HashMap<>();
                        new_user.put("Email",email_signup.getText().toString());

                        new_user.put("UserID",firebaseAuth.getUid());
                        collectionReference.document(firebaseAuth.getUid()).set(new_user).addOnCompleteListener(secondTask->{
                           if (secondTask.isSuccessful()){

                               Toast.makeText(getApplicationContext(), "user saved successfully", Toast.LENGTH_SHORT).show();
                               email_signup.setText("");
                               password_signup.setText("");
                               confirmPassword_signup.setText("");
                           }else{
                               Toast.makeText(getApplicationContext(), "user is not saved successfully", Toast.LENGTH_SHORT).show();
                           }
                        }).addOnFailureListener(secondFailure->{
                            Log.d("Error saving new user in the database",secondFailure.getMessage());
                        });
                    }else{
                        AlertDialog.Builder builder=new AlertDialog.Builder(this);
                        builder.create();
                        builder.setTitle("Error creating an account");
                        builder.setMessage("Either the email is already in use or a connection problem");
                        builder.setPositiveButton("okay",((dialog, which) -> dialog.dismiss()));
                        builder.show();
                    }
                }).addOnFailureListener(failure->{
            Log.d("Error creating user",failure.getMessage());
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar_singup.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressBar_singup.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        progressBar_singup.setVisibility(View.INVISIBLE);
    }
}