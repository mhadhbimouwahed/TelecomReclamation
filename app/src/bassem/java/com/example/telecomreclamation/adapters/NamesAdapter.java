package com.example.telecomreclamation.adapters;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telecomreclamation.ClientEmails;

import com.example.telecomreclamation.ClientNames;
import com.example.telecomreclamation.MoreInformationActivity;
import com.example.telecomreclamation.R;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class NamesAdapter extends RecyclerView.Adapter<NamesAdapter.NamesViewHolder> {


    Context context;
    static ArrayList<ClientNames> list;

    public NamesAdapter(Context context, ArrayList<ClientNames> list){
        this.context=context;
        this.list=list;
    }


    @NonNull
    @Override
    public NamesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.client,parent,false);
        return new NamesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NamesViewHolder holder, int position) {
        ClientNames clientNames=list.get(position);
        holder.clientNameTextView.setText(clientNames.getClientName());


    }


    @Override
    public int getItemCount() {
        return list.size();

    }

    public class NamesViewHolder extends RecyclerView.ViewHolder {

        TextView seeMoreInfo;
        TextView clientNameTextView;

        LinearLayout expandable_layout;
        FirebaseFirestore firestore;
        CollectionReference collectionReference;




        public NamesViewHolder(@NonNull View itemView) {
            super(itemView);

            seeMoreInfo=itemView.findViewById(R.id.seeMoreInformation);
            clientNameTextView=itemView.findViewById(R.id.clientNameTextView);


            firestore=FirebaseFirestore.getInstance();
            collectionReference=firestore.collection("ApprovedUsers");





            seeMoreInfo.setOnClickListener(x->{
                Intent intent=new Intent(context.getApplicationContext(), MoreInformationActivity.class);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("CLIENT_NAME",clientNameTextView.getText().toString());
                context.startActivity(intent);
            });


        }
    }
}
