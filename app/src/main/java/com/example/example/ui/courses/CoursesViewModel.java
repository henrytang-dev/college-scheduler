package com.example.example.ui.courses;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * This class is responsible for handling the data for the CoursesFragment.
 */
public class CoursesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    /**
     * Constructor for the CoursesViewModel class.
     */
    public CoursesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    /**
     * Get the text.
     * @return the mText
     */
    public LiveData<String> getText() {
        return mText;
    }
}