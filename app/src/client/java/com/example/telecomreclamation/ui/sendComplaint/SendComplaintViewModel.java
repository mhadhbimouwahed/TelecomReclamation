package com.example.telecomreclamation.ui.sendComplaint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SendComplaintViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SendComplaintViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}