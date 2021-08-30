package com.example.telecomreclamation.ui.seeClients;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.telecomreclamation.ClientNames;
import com.example.telecomreclamation.adapters.ClientAdapter;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class SeeClientsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    FirebaseFirestore firestore;
    CollectionReference collectionReference;

    ArrayList<ClientNames> list;
    ClientAdapter clientAdapter;

    public SeeClientsViewModel(){
        mText=new MutableLiveData<>();
        mText.setValue("This is see clients fragment");
        firestore=FirebaseFirestore.getInstance();
        collectionReference=firestore.collection("ApprovedUsers");

    }

    public LiveData<String> getText(){return mText;}
}
