package com.example.telecomreclamation.ui.modifyClient;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ModifyClientViewHolder extends ViewModel {

    private MutableLiveData<String> mText;

    public ModifyClientViewHolder(){
        mText=new MutableLiveData<>();
        mText.setValue("This is modify client fragment");
    }

    public LiveData<String> getText(){return mText;}
}
