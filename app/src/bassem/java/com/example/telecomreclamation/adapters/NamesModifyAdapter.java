package com.example.telecomreclamation.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telecomreclamation.ClientNames;
import com.example.telecomreclamation.ModifyActivity;
import com.example.telecomreclamation.R;

import java.util.ArrayList;

public class NamesModifyAdapter extends RecyclerView.Adapter<NamesModifyAdapter.NamesModifyViewHolder> {


    Context context;
    static ArrayList<ClientNames> list;

    public NamesModifyAdapter(Context context,ArrayList<ClientNames> list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public NamesModifyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.modify_client,parent,false);
        return new NamesModifyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NamesModifyViewHolder holder, int position) {
        ClientNames clientNames=list.get(position);
        holder.clientNameTextView_modify.setText(clientNames.getClientName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NamesModifyViewHolder extends RecyclerView.ViewHolder {

        EditText clientNameTextView_modify;
        TextView seeMoreInformation_modify;


        public NamesModifyViewHolder(@NonNull View itemView) {
            super(itemView);

            clientNameTextView_modify=itemView.findViewById(R.id.clientNameTextView_modify);
            seeMoreInformation_modify=itemView.findViewById(R.id.seeMoreInformation_modify);

            seeMoreInformation_modify.setOnClickListener(x->{
                Intent intent=new Intent(context.getApplicationContext(), ModifyActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("CLIENT_NAME_MODIFY",clientNameTextView_modify.getText().toString());
                context.startActivity(intent);
            });

        }
    }
}
