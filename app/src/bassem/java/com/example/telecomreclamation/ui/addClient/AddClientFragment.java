package com.example.telecomreclamation.ui.addClient;

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
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.telecomreclamation.databinding.FragmentAddClientBinding;

import java.util.HashMap;
import java.util.UUID;


public class AddClientFragment extends Fragment {

    private AddClientViewModel addClientViewModel;
    private FragmentAddClientBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){

        addClientViewModel=
                new ViewModelProvider(this).get(AddClientViewModel.class);

        binding=FragmentAddClientBinding.inflate(inflater,container,false);

        View root=binding.getRoot();

        final EditText client_name=binding.clientName;
        final EditText client_email=binding.clientEmail;
        final EditText client_password=binding.clientPassword;
        final TextView save_client=binding.saveClient;

        save_client.setOnClickListener(x->{
            if (client_email.getText().toString().equals("")){
                client_email.setError("This field cannot be empty");
            }else if(client_password.getText().toString().equals("")){
                client_password.setError("This field cannot be empty");
            }else if(client_name.getText().toString().equals("")){
                client_name.setError("This fiald cannot be empty");
            }else{
                String uuid= UUID.randomUUID().toString();
                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put("clientID", uuid);
                hashMap.put("clientPassword",client_password.getText().toString());
                hashMap.put("ClientEmail",client_email.getText().toString());
                hashMap.put("ClientName",client_name.getText().toString());
                
                HashMap<String,String> clientName=new HashMap<>();
                clientName.put("client_name",client_name.getText().toString());

                
                addClientViewModel.collectionReference.document(client_name.getText().toString()).set(clientName)
                        .addOnCompleteListener(task->{
                            if (task.isSuccessful()){
                                addClientViewModel.collectionReference.document(client_name.getText().toString()).collection("Emails").document(uuid).set(hashMap)
                                        .addOnCompleteListener(secondTask->{
                                            if (task.isSuccessful()){
                                                Toast.makeText(getContext().getApplicationContext(), "client added successfully", Toast.LENGTH_SHORT).show();
                                                client_name.setText("");
                                                client_email.setText("");
                                                client_password.setText("");
                                            }else{
                                                Toast.makeText(getContext().getApplicationContext(), "failed to add a client", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(secondFailure->{
                                    Log.d("ERROR_ADDING_CLIENT",secondFailure.getMessage());
                                });
                            }else{
                                Toast.makeText(getContext().getApplicationContext(), "failed to add client name", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(failure->{
                            Log.d("ERROR_ADDING_CLIENT_NAME",failure.getMessage());
                });
            }
        });

        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding=null;
    }
}
