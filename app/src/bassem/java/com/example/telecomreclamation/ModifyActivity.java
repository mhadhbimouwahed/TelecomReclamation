package com.example.telecomreclamation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.telecomreclamation.adapters.EmailsAdapter;
import com.example.telecomreclamation.adapters.ModifyAdapter;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ModifyActivity extends AppCompatActivity {



    private RecyclerView emailsRecyclerView_modify;
    FirebaseFirestore firestore;
    CollectionReference collectionReference;

    ModifyAdapter modifyAdapter;
    ArrayList<ClientEmails> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        firestore=FirebaseFirestore.getInstance();
        collectionReference=firestore.collection("ApprovedUsers");

        emailsRecyclerView_modify=findViewById(R.id.emailsRecyclerView_modify);

        emailsRecyclerView_modify.setHasFixedSize(true);
        emailsRecyclerView_modify.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        list=new ArrayList<>();
        modifyAdapter=new ModifyAdapter(getApplicationContext(),list);
        emailsRecyclerView_modify.setAdapter(modifyAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String nomClient=getIntent().getStringExtra("CLIENT_NAME_MODIFY");
        collectionReference
                .document(nomClient)
                .collection("Emails")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        if (task.getResult().isEmpty()){
                            Toast.makeText(getApplicationContext(), "There are no emails yet", Toast.LENGTH_SHORT).show();
                        }else{
                            for (QueryDocumentSnapshot documentSnapshot:task.getResult()){
                                ClientEmails clientEmails=new ClientEmails(
                                        documentSnapshot.getString("ClientEmail"),
                                        documentSnapshot.getString("ClientName"),
                                        documentSnapshot.getString("ClientID")
                                );
                                list.add(clientEmails);
                                modifyAdapter.notifyDataSetChanged();
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
}