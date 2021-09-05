package com.example.telecomreclamation.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telecomreclamation.ClientEmails;
import com.example.telecomreclamation.ModifyActivity;
import com.example.telecomreclamation.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class ModifyAdapter extends RecyclerView.Adapter<ModifyAdapter.ModifyViewHolder> {

    Context context;
    static ArrayList<ClientEmails> list;

    public ModifyAdapter(Context context,ArrayList<ClientEmails> list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public ModifyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.emails_modify,parent,false);
        return new ModifyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ModifyViewHolder holder, int position) {
        ClientEmails clientEmails=list.get(position);
        holder.idDeClient_modify.setText(clientEmails.getID());
        holder.nomDeClient_modify.setText(clientEmails.getName());
        holder.emailDeClient_modify.setText(clientEmails.getEmail());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ModifyViewHolder extends RecyclerView.ViewHolder {

        EditText emailDeClient_modify;
        TextView nomDeClient_modify;
        TextView idDeClient_modify;
        TextView modify_email;

        FirebaseFirestore firestore;
        CollectionReference collectionReference;

        public ModifyViewHolder(@NonNull View itemView) {
            super(itemView);

            emailDeClient_modify=itemView.findViewById(R.id.emailDeClient_modify);
            nomDeClient_modify=itemView.findViewById(R.id.nomDeClient_modify);
            idDeClient_modify=itemView.findViewById(R.id.idDeClient_modify);
            modify_email=itemView.findViewById(R.id.modify_email);

            firestore=FirebaseFirestore.getInstance();
            collectionReference=firestore.collection("ApprovedUsers");

            modify_email.setOnClickListener(x->{
                HashMap<String,Object> hashMap=new HashMap<>();
                hashMap.put("ClientEmail",emailDeClient_modify.getText().toString());

                collectionReference
                        .document(nomDeClient_modify.getText().toString())
                        .collection("Emails")
                        .document(idDeClient_modify.getText().toString())
                        .update(hashMap)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()){
                                Toast.makeText(context.getApplicationContext(), "email updated successfully", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context.getApplicationContext(), "failed to update the email, please check your internet connection", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(e -> {
                            Log.d("ERROR_UPDATING_EMAIL",e.getMessage());
                        });
            });
        }
    }
}
