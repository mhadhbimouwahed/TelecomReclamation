package com.example.telecomreclamation.ui.seeComplaint;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.telecomreclamation.databinding.FragmentSeeComplaintBinding;


public class SeeComplaintFragment extends Fragment {

    private SeeComplaintViewModel seeComplaintViewModel;
    private FragmentSeeComplaintBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        seeComplaintViewModel =
                new ViewModelProvider(this).get(SeeComplaintViewModel.class);

        binding = FragmentSeeComplaintBinding.inflate(inflater, container, false);
        View root = binding.getRoot();




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}