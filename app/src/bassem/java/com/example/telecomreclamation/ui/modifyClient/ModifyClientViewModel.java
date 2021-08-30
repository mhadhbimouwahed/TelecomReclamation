package com.example.telecomreclamation.ui.modifyClient;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ModifyClientViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ModifyClientViewModel(){
        mText=new MutableLiveData<>();
        mText.setValue("This is modify client fragment");
    }

    public LiveData<String> getText(){return mText;}
}
