package com.example.telecomreclamation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SignupActivity extends AppCompatActivity {

    private Spinner full_name;
    private EditText email_signup;
    private EditText password_signup;
    private TextView signup_signup;
    private ProgressBar progressBar_signup;
    private EditText password_signup_confirm;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    CollectionReference collectionReference;
    CollectionReference createdUSers;


    public List<String> client_name=new ArrayList<>();
    ArrayAdapter<String> adapter_client_name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        email_signup=findViewById(R.id.email_signup);
        password_signup=findViewById(R.id.password_signup);
        password_signup_confirm=findViewById(R.id.password_signup_confirm);
        signup_signup=findViewById(R.id.signup_signup);
        progressBar_signup=findViewById(R.id.progress_bar_signup);
        full_name=findViewById(R.id.full_name);


        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        collectionReference=firestore.collection("ApprovedUsers");
        createdUSers=firestore.collection("CreatedUsers");


        adapter_client_name=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,client_name);
        adapter_client_name.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        full_name.setAdapter(adapter_client_name);


        signup_signup.setOnClickListener(x->{
            if (email_signup.getText().toString().equals("")){
                email_signup.setError("This field cannot be empty");
            }else if(password_signup.getText().toString().equals("")){
                password_signup.setError("This field cannot be empty");
            }else if (password_signup_confirm.getText().toString().equals("")){
                password_signup_confirm.setError("This field cannot be empty");
            }else if(!password_signup.getText().toString().equals(password_signup_confirm.getText().toString())){
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.create();
                builder.setTitle("Error");
                builder.setMessage("Passwords must match");
                builder.setPositiveButton("Okay",((dialogInterface, i) -> dialogInterface.dismiss()));
                builder.show();
            } else{
                Signup();
            }
        });

    }

    private void Signup() {
        String name=full_name.getSelectedItem().toString();
        collectionReference
                .document(name)
                .collection("Emails")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        if (!task.getResult().isEmpty()){
                            for (QueryDocumentSnapshot documentSnapshot:task.getResult()){

                                ArrayList<String> arrayList=new ArrayList<>();
                                arrayList.add(documentSnapshot.getString("ClientEmail"));

                                if (arrayList.contains(email_signup.getText().toString())){
                                    Toast.makeText(getApplicationContext(), "Houray", Toast.LENGTH_SHORT).show();
                                    firebaseAuth.createUserWithEmailAndPassword(email_signup.getText().toString(),password_signup.getText().toString())
                                            .addOnCompleteListener(task1 -> {
                                               if (task1.isSuccessful()){
                                                   HashMap<String,Object> user=new HashMap<>();
                                                   user.put("ClientID",firebaseAuth.getUid());
                                                   user.put("ClientSoc",full_name.getSelectedItem().toString());
                                                   user.put("ClientEmail",email_signup.getText().toString());
                                                   createdUSers.document(firebaseAuth.getUid()).set(user)
                                                           .addOnCompleteListener(task2 -> {
                                                               if (task2.isSuccessful()){
                                                                   Toast.makeText(getApplicationContext(), "Signed up successfully", Toast.LENGTH_SHORT).show();
                                                                   startActivity(new Intent(getApplicationContext(),NavActivity.class));
                                                               }else{
                                                                   Toast.makeText(getApplicationContext(), "failed to create user database", Toast.LENGTH_SHORT).show();
                                                               }
                                                           })
                                                           .addOnFailureListener(e -> {
                                                               Log.d("Signup",e.getMessage());
                                                           });
                                               }else{
                                                   Toast.makeText(getApplicationContext(), "failed to create an account", Toast.LENGTH_SHORT).show();
                                               }
                                            })
                                            .addOnFailureListener(e -> {
                                                Log.d("Signup",e.getMessage());
                                            });
                                    break;
                                }
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "There are no names yet", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "failed to get names", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {

                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar_signup.setVisibility(View.INVISIBLE);
        collectionReference
                .get()
                .addOnCompleteListener(task -> {
                   if (task.isSuccessful()){
                       if (task.getResult().isEmpty()){
                           Toast.makeText(getApplicationContext(), "There are no names yet", Toast.LENGTH_SHORT).show();
                       }else{
                           for (QueryDocumentSnapshot documentSnapshot:task.getResult()){
                               String nom=documentSnapshot.getString("client_name");
                               client_name.add(nom);
                           }
                           adapter_client_name.notifyDataSetChanged();
                       }
                   }else{
                       Toast.makeText(getApplicationContext(), "failed to load names", Toast.LENGTH_SHORT).show();
                   }
                })
                .addOnFailureListener(e -> {
                    Log.d("SIGNUP_ACIVITY",e.getMessage());
                });

    }
}