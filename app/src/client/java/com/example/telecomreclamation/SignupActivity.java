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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SignupActivity extends AppCompatActivity {

    private Spinner full_name;
    private Spinner email_signup;
    private EditText password_signup;
    private TextView signup_signup;
    private ProgressBar progressBar_signup;
    private EditText password_signup_confirm;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    CollectionReference collectionReference;
    CollectionReference collectionReference1;

    public List<String> client_name=new ArrayList<>();
    public List<String> client_email=new ArrayList<>();
    ArrayAdapter<String> adapter_client_name;
    ArrayAdapter<String> adapter_client_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        full_name=findViewById(R.id.full_name);
        email_signup=findViewById(R.id.email_signup);
        password_signup=findViewById(R.id.password_signup);
        password_signup_confirm=findViewById(R.id.password_signup_confirm);
        signup_signup=findViewById(R.id.signup_signup);
        progressBar_signup=findViewById(R.id.progress_bar_signup);


        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        collectionReference=firestore.collection("ApprovedUsers");
        collectionReference1=firestore.collection("CreatedUsers");


        adapter_client_name=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,client_name);
        adapter_client_name.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        full_name.setAdapter(adapter_client_name);

        adapter_client_email=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,client_email);
        adapter_client_email.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);



        signup_signup.setOnClickListener(x->{
            if (password_signup.getText().toString().equals("")){
                password_signup.setError("This field cannot be empty");
            }else if (password_signup_confirm.getText().toString().equals("")){
                password_signup_confirm.setError("This field cannot be empty");
            }else if(!password_signup.getText().toString().equals(password_signup_confirm.getText().toString())){
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.create();
                builder.setTitle("Error");
                builder.setMessage("Passwords do not match, please check again");
                builder.setPositiveButton("Okay",((dialogInterface, i) -> dialogInterface.dismiss()));
                builder.show();
            }else{
                progressBar_signup.setVisibility(View.VISIBLE);
                Signup();

            }
        });


        full_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                email_signup.setAdapter(null);
                client_email.removeAll(client_email);
                collectionReference
                        .document(adapterView.getAdapter().getItem(i).toString())
                        .collection("Emails")
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()){
                                if (task.getResult().isEmpty()){
                                    Toast.makeText(getApplicationContext(), "There are no emails yet", Toast.LENGTH_SHORT).show();
                                }else{
                                    email_signup.setAdapter(adapter_client_email);
                                    for (QueryDocumentSnapshot documentSnapshot:task.getResult()){
                                        String email=documentSnapshot.getString("ClientEmail");
                                        client_email.add(email);
                                    }
                                    adapter_client_email.notifyDataSetChanged();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(), "failed to read from database", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(e -> {
                            Log.d("ERROR_READING_FROM_DATABASE",e.getMessage());
                        });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

    }

    private void Signup() {
        firebaseAuth
                .createUserWithEmailAndPassword(email_signup.getSelectedItem().toString(),password_signup.getText().toString())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        HashMap<String,Object> hashMap=new HashMap<>();
                        hashMap.put("ClientID",firebaseAuth.getUid());
                        hashMap.put("ClientName",full_name.getSelectedItem().toString());
                        hashMap.put("ClientEmail",email_signup.getSelectedItem().toString());
                        collectionReference1.document(firebaseAuth.getUid())
                                .set(hashMap)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "user created successfully", Toast.LENGTH_SHORT).show();
                                        password_signup.setText("");
                                        password_signup_confirm.setText("");
                                        startActivity(new Intent(getApplicationContext(),NavActivity.class));
                                    }else{
                                        Toast.makeText(getApplicationContext(), "failed to add user to database", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(e1->{
                                    Log.d("FAILED_TO_ADD_USER_TO_DATABASE",e1.getMessage());
                                });
                    }else{
                        AlertDialog.Builder builder=new AlertDialog.Builder(this);
                        builder.create();
                        builder.setTitle("Error");
                        builder.setMessage("user already exists, please check again");
                        builder.setPositiveButton("Okay",((dialogInterface, i) -> dialogInterface.dismiss()));
                        builder.show();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d("ERROR_CREATING_USER",e.getMessage());
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar_signup.setVisibility(View.INVISIBLE);
        collectionReference.get()
                .addOnCompleteListener(task -> {
                   if (task.isSuccessful()){
                      if (task.getResult().isEmpty()){
                          Toast.makeText(getApplicationContext(), "please contact the admin", Toast.LENGTH_SHORT).show();
                      }else{
                          for (QueryDocumentSnapshot documentSnapshot:task.getResult()){
                              String nom=documentSnapshot.getString("client_name");
                              client_name.add(nom);
                          }
                          adapter_client_name.notifyDataSetChanged();
                      }
                   }else{
                       Toast.makeText(getApplicationContext(), "failed to read from database", Toast.LENGTH_SHORT).show();
                   }
                })
                .addOnFailureListener(e -> {
                    Log.d("ERROR_READING_FROM_DATABASE",e.getMessage());
                });
    }
}