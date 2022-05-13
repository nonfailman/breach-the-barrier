package com.abl.breachthebarrier.ui.main.leaderboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LeaderboardViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public LeaderboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Это фрагмент списка лидеров");
    }

    public LiveData<String> getText() {
        return mText;
    }
}