package com.example.telecomreclamation.ui.modifyClient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.telecomreclamation.databinding.FragmentModifyClientBinding;

public class ModifyClientFragment extends Fragment {

    private ModifyClientViewModel modifyClientViewHolder;
    private FragmentModifyClientBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                         ViewGroup container, Bundle savedInstanceState){
        modifyClientViewHolder=
                new ViewModelProvider(this).get(ModifyClientViewModel.class);

        binding = FragmentModifyClientBinding.inflate(inflater,container,false);
        View root=binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}
