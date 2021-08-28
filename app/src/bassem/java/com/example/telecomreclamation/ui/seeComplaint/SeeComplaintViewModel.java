package ttt.ui.seeComplaint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SeeComplaintViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SeeComplaintViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}