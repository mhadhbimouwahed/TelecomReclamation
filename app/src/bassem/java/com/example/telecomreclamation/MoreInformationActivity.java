package com.example.telecomreclamation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telecomreclamation.adapters.EmailsAdapter;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class MoreInformationActivity extends AppCompatActivity {



    TextView nomClient;
    RecyclerView recyclerView_emails;

    FirebaseFirestore firestore;
    CollectionReference collectionReference;

    EmailsAdapter emailsAdapter;
    ArrayList<ClientEmails> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_information);

        nomClient=findViewById(R.id.nomclient);
        recyclerView_emails=findViewById(R.id.emailsRecyclerView);

        firestore=FirebaseFirestore.getInstance();
        collectionReference=firestore.collection("ApprovedUsers");

        recyclerView_emails.setHasFixedSize(true);
        recyclerView_emails.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        emailsAdapter=new EmailsAdapter(getApplicationContext(),list);
        recyclerView_emails.setAdapter(emailsAdapter);

    }


    @Override
    protected void onStart() {
        super.onStart();
        String nom_client=getIntent().getStringExtra("CLIENT_NAME");

        nomClient.setText(nom_client);
        collectionReference
                .document(nom_client)
                .collection("Emails")
                .get()
                .addOnCompleteListener(task->{
                    if (task.isSuccessful()){
                        if (task.getResult().isEmpty()){
                            Toast.makeText(getApplicationContext(), "There are no emails yet", Toast.LENGTH_SHORT).show();
                        }else{
                            for (QueryDocumentSnapshot documentSnapshot:task.getResult()){
                                HashMap<String,Object> hashMap=new HashMap<>(documentSnapshot.getData());
                                ClientEmails clientEmails=new ClientEmails(
                                        hashMap.get("ClientEmail"),
                                        hashMap.get("ClientName"),
                                        hashMap.get("ClientID")
                                );
                                list.add(clientEmails);
                                emailsAdapter.notifyDataSetChanged();
                            }
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.d("",e.getMessage());
                });

    }
}