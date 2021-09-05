package com.example.telecomreclamation.ui.sendComplaint;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.telecomreclamation.databinding.FragmentSendComplaintBinding;


public class SendComplaintFragment extends Fragment {

    private SendComplaintViewModel homeViewModel;
    private FragmentSendComplaintBinding binding;
    TextView pickPlace;
    EditText description;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(SendComplaintViewModel.class);

        binding = FragmentSendComplaintBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        pickPlace=binding.pickPlace;
        description=binding.description;


        pickPlace.setOnClickListener(x->{
            if (description.getText().toString().equals("")){
                description.setError("This field cannot be empty");
            }else{
                //startActivity(new Intent(getContext().getApplicationContext(),MapsActivity.class));
            }
        });


        return root;



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}