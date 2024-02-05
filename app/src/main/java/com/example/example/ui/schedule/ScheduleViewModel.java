package com.example.example.ui.schedule;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * This class is responsible for handling the data for the ScheduleFragment.
 */
public class ScheduleViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    /**
     * Constructor for the ScheduleViewModel class.
     */
    public ScheduleViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    /**
     * Get the text.
     * @return the mText
     */
    public LiveData<String> getText() {
        return mText;
    }
}