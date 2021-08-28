package com.example.telecomreclamation.ui.deleteComplaint;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.telecomreclamation.databinding.FragmentDeleteComplaintBinding;


public class DeleteComplaintFragment extends Fragment {

    private DeleteComplaintViewModel deleteComplaintViewModel;
    private FragmentDeleteComplaintBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        deleteComplaintViewModel =
                new ViewModelProvider(this).get(DeleteComplaintViewModel.class);

        binding = FragmentDeleteComplaintBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}