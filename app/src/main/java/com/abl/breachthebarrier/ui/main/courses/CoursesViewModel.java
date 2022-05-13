package com.abl.breachthebarrier.ui.main.courses;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CoursesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CoursesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Это фрагмент курсов");
    }

    public LiveData<String> getText() {
        return mText;
    }
}