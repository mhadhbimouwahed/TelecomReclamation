package com.example.telecomreclamation.ui.manageClients;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;


import com.example.telecomreclamation.R;
import com.example.telecomreclamation.databinding.FragmentManageClientsBinding;


import java.util.HashMap;
import java.util.UUID;


public class ManageClientFragment extends Fragment {

    private ManageClientViewModel manageClientViewModel;
    private FragmentManageClientsBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        manageClientViewModel =
                new ViewModelProvider(this).get(ManageClientViewModel.class);

        binding = FragmentManageClientsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final TextView navigate_to_modify=binding.navigateToModifyClient;
        final TextView navigate_to_delete=binding.navigateToDeleteClient;
        final TextView navigate_to_add=binding.navigateToAddClient;
        final TextView navigate_to_see=binding.navigateToSeeClients;

        navigate_to_see.setOnClickListener(x->{
            Navigation.findNavController(root).navigate(R.id.action_nav_gallery_to_seeClientsFragment);
        });

        navigate_to_modify.setOnClickListener(x->{
            Navigation.findNavController(root).navigate(R.id.action_nav_gallery_to_nav_modify_client);
        });

        navigate_to_delete.setOnClickListener(x->{
            Navigation.findNavController(root).navigate(R.id.action_nav_gallery_to_deleteClient);
        });

        navigate_to_add.setOnClickListener(x->{
            Navigation.findNavController(root).navigate(R.id.action_nav_gallery_to_nav_add_client);
        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}