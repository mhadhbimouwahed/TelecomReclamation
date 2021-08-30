package com.example.telecomreclamation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telecomreclamation.Client;
import com.example.telecomreclamation.ClientNames;
import com.example.telecomreclamation.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder> {


    Context context;
    static ArrayList<ClientNames> list;

    public ClientAdapter(Context context,ArrayList<ClientNames> list){
        this.context=context;
        this.list=list;
    }


    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.client,parent,false);
        return new ClientViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        ClientNames clientNames=list.get(position);
        holder.clientNameTextView.setText(clientNames.getClientName());

    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public class ClientViewHolder extends RecyclerView.ViewHolder {

        TextView seeMoreInfo;
        TextView clientNameTextView;
        FirebaseFirestore firestore;
        CollectionReference collectionReference;

        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);
            
            seeMoreInfo=itemView.findViewById(R.id.seeMoreInformation);
            clientNameTextView=itemView.findViewById(R.id.clientNameTextView);
            
            firestore=FirebaseFirestore.getInstance();
            collectionReference=firestore.collection("ApprovedUsers");
            
            seeMoreInfo.setOnClickListener(x->{
                Toast.makeText(context.getApplicationContext(), "see more information clicked", Toast.LENGTH_SHORT).show();
            });

        }
    }
}
