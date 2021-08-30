package com.example.telecomreclamation.ui.deleteClient;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DeleteClientViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DeleteClientViewModel(){
        mText=new MutableLiveData<>();
        mText.setValue("This is delete client fragment");
    }

    public LiveData<String> getText(){return mText;}
}
