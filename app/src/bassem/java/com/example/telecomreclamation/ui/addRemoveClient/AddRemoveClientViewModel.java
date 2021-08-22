package com.example.telecomreclamation.ui.addRemoveClient;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddRemoveClientViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddRemoveClientViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}