package com.example.telecomreclamation.ui.deleteClient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.telecomreclamation.databinding.FragmentDeleteClientBinding;

public class DeleteClientFragment extends Fragment {

    private DeleteClientViewModel deleteClientViewModel;
    private FragmentDeleteClientBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){


        deleteClientViewModel=
                new ViewModelProvider(this).get(DeleteClientViewModel.class);

        binding=FragmentDeleteClientBinding.inflate(inflater,container,false);

        View root=binding.getRoot();

        return root;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        binding=null;
    }
}
