package com.example.telecomreclamation;

import androidx.appcompat.app.AppCompatActivity;

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
        signup_signup=findViewById(R.id.signup_signup);
        progressBar_signup=findViewById(R.id.progress_bar_signup);

        firestore=FirebaseFirestore.getInstance();
        collectionReference=firestore.collection("ApprovedUsers");
        collectionReference1=firestore.collection("CreatedUsers");


        adapter_client_name=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,client_name);
        adapter_client_name.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        full_name.setAdapter(adapter_client_name);

        adapter_client_email=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,client_email);
        adapter_client_email.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        email_signup.setAdapter(adapter_client_email);


        signup_signup.setOnClickListener(x->{

        });


        full_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                collectionReference
                        .document(adapterView.getAdapter().getItem(i).toString())
                        .collection("Emails")
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()){
                                if (task.getResult().isEmpty()){

                                }else{

                                }
                            }else{

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

    @Override
    protected void onStart() {
        super.onStart();
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