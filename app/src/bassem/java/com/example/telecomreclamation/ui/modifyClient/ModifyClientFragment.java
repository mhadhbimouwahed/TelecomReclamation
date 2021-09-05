package com.example.telecomreclamation.ui.modifyClient;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telecomreclamation.ClientNames;
import com.example.telecomreclamation.adapters.NamesModifyAdapter;
import com.example.telecomreclamation.databinding.FragmentModifyClientBinding;
import com.google.api.Distribution;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class ModifyClientFragment extends Fragment {

    private ModifyClientViewModel modifyClientViewHolder;
    private FragmentModifyClientBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                         ViewGroup container, Bundle savedInstanceState){
        modifyClientViewHolder=
                new ViewModelProvider(this).get(ModifyClientViewModel.class);

        binding = FragmentModifyClientBinding.inflate(inflater,container,false);


        View root=binding.getRoot();

        final RecyclerView namesRecyclerView_modify=binding.namesRecyclerViewModify;
        namesRecyclerView_modify.setHasFixedSize(true);
        namesRecyclerView_modify.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        modifyClientViewHolder.list=new ArrayList<>();
        modifyClientViewHolder.namesModifyAdapter=new NamesModifyAdapter(getContext().getApplicationContext(), modifyClientViewHolder.list);
        namesRecyclerView_modify.setAdapter(modifyClientViewHolder.namesModifyAdapter);



        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        modifyClientViewHolder.list.removeAll(modifyClientViewHolder.list);
        modifyClientViewHolder.collectionReference
                .get()
                .addOnCompleteListener(task -> {
                   if (task.isSuccessful()){
                       if (task.getResult().isEmpty()){
                           Toast.makeText(getContext().getApplicationContext(), "There are no clients to modify", Toast.LENGTH_SHORT).show();
                       }else{
                           for (QueryDocumentSnapshot documentSnapshot:task.getResult()){
                               ClientNames clientNames=new ClientNames(documentSnapshot.getString("client_name"));
                               modifyClientViewHolder.list.add(clientNames);
                               modifyClientViewHolder.namesModifyAdapter.notifyDataSetChanged();
                           }
                       }
                   }else{
                       Toast.makeText(getContext().getApplicationContext(), "please check your internet connection", Toast.LENGTH_SHORT).show();
                   }
                })
                .addOnFailureListener(e -> {
                    Log.d("ERROR_READING_FROM_DATABASE",e.getMessage());
                });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}
