package com.example.telecomreclamation.ui.addClient;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddClientViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    FirebaseFirestore firestore;
    CollectionReference collectionReference;

    public AddClientViewModel(){
        mText=new MutableLiveData<>();
        mText.setValue("This is add client fragment");

        firestore=FirebaseFirestore.getInstance();
        collectionReference=firestore.collection("ApprovedUsers");
    }

    public LiveData<String> getText(){return mText;}

}
