package com.bless.studexp.ui.Groups;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GroupViewModel extends ViewModel {

    private MutableLiveData<String> mText;


    public GroupViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }


    public LiveData<String> getText() {
        return mText;
    }
}