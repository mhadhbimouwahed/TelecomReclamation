package com.example.telecomreclamation.ui.seeClients;

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
import com.example.telecomreclamation.adapters.ClientAdapter;
import com.example.telecomreclamation.databinding.FragmentSeeClientsBinding;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class SeeClientsFragment extends Fragment {

    private SeeClientsViewModel seeClientsViewHolder;
    private FragmentSeeClientsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){

        seeClientsViewHolder=
                new ViewModelProvider(this).get(SeeClientsViewModel.class);

        binding=FragmentSeeClientsBinding.inflate(inflater,container,false);

        View root=binding.getRoot();

        final RecyclerView recyclerView=binding.clientsRecyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext()));
        seeClientsViewHolder.list=new ArrayList<>();
        seeClientsViewHolder.clientAdapter=new ClientAdapter(getContext().getApplicationContext(), seeClientsViewHolder.list);
        recyclerView.setAdapter(seeClientsViewHolder.clientAdapter);

        return root;

    }

    @Override
    public void onStart() {
        super.onStart();
        seeClientsViewHolder.collectionReference.get()
                .addOnCompleteListener(task->{
                    if (task.isSuccessful()){
                        if (task.getResult().isEmpty()){
                            Toast.makeText(getContext().getApplicationContext(), "There are no clients yet", Toast.LENGTH_SHORT).show();
                        }else{
                            for (QueryDocumentSnapshot documentSnapshot:task.getResult()){
                                HashMap<String,Object> hashMap=new HashMap<>(documentSnapshot.getData());

                                ClientNames clientNames=new ClientNames(hashMap.get("client_name").toString());
                                seeClientsViewHolder.list.add(clientNames);
                                seeClientsViewHolder.clientAdapter.notifyDataSetChanged();
                            }
                        }

                    }else{
                        Toast.makeText(getContext().getApplicationContext(), "please checj your internet connection", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(failure->{
                    Log.d("ERROR_READING_FROM_DATABASE",failure.getMessage());
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding=null;
    }
}
