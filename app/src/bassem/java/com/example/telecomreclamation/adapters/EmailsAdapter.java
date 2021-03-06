package com.example.telecomreclamation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telecomreclamation.ClientEmails;
import com.example.telecomreclamation.R;

import java.util.ArrayList;

public class EmailsAdapter extends RecyclerView.Adapter<EmailsAdapter.EmailsViewHolder> {


    Context context;
    static ArrayList<ClientEmails> list;

    public EmailsAdapter(Context context,ArrayList<ClientEmails> list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public EmailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.emails,parent,false);
        return new EmailsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EmailsViewHolder holder, int position) {
        ClientEmails clientEmails=list.get(position);
        holder.emailDeclient.setText(clientEmails.getEmail());
        holder.nomDeClient.setText(clientEmails.getName());
        holder.idDeClient.setText(clientEmails.getID());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class EmailsViewHolder extends RecyclerView.ViewHolder {

        TextView emailDeclient;
        TextView nomDeClient;
        TextView idDeClient;


        public EmailsViewHolder(@NonNull View itemView) {
            super(itemView);
            emailDeclient=itemView.findViewById(R.id.emailDeClient);
            nomDeClient=itemView.findViewById(R.id.nomDeClient);
            idDeClient=itemView.findViewById(R.id.idDeClient);
        }
    }
}
