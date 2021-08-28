package com.example.telecomreclamation.ui.deleteComplaint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DeleteComplaintViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DeleteComplaintViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}