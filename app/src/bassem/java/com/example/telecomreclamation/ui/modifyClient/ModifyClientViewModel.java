package com.example.telecomreclamation.ui.modifyClient;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.telecomreclamation.ClientNames;
import com.example.telecomreclamation.adapters.NamesAdapter;
import com.example.telecomreclamation.adapters.NamesModifyAdapter;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ModifyClientViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    NamesModifyAdapter namesModifyAdapter;
    ArrayList<ClientNames> list;
    FirebaseFirestore firestore;
    CollectionReference collectionReference;

    public ModifyClientViewModel(){
        mText=new MutableLiveData<>();
        mText.setValue("This is modify client fragment");

        firestore=FirebaseFirestore.getInstance();
        collectionReference=firestore.collection("ApprovedUsers");
    }

    public LiveData<String> getText(){return mText;}
}
