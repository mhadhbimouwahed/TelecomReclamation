package com.example.telecomreclamation.ui.sendComplaint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.telecomreclamation.LoginActivity;
import com.example.telecomreclamation.MapsActivity;
import com.example.telecomreclamation.databinding.FragmentSendComplaintBinding;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;


public class SendComplaintFragment extends Fragment {

    private static final String TAG = "SendComplaintFragment";
    private static final int ERROR_DIALOG_REQUEST = 9001;
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
            }else if (!isServicesOkay()){
                Toast.makeText(getContext().getApplicationContext(), "i don't have permissions", Toast.LENGTH_SHORT).show();
            }else{
                startActivity(new Intent(getContext().getApplicationContext(), MapsActivity.class));
            }
        });




        return root;
    }




    public boolean isServicesOkay(){
        Log.d(TAG,"checking if services okay");
        int available= GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getContext().getApplicationContext());
        if (available == ConnectionResult.SUCCESS){
            Toast.makeText(getContext().getApplicationContext(), "Google play services working", Toast.LENGTH_SHORT).show();
            return true;
        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            AlertDialog.Builder builder=new AlertDialog.Builder(getContext().getApplicationContext());
            builder.create();
            builder.setTitle("Error");
            builder.setMessage("your device has blocked the location");
            builder.setPositiveButton("Okay",((dialogInterface, i) -> dialogInterface.dismiss()));
            builder.show();
        }else{
            Toast.makeText(getContext().getApplicationContext(), "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}