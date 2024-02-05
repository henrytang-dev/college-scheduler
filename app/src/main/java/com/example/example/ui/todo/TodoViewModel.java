package com.example.example.ui.todo;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.example.models.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for handling the data for the TodoFragment.
 */
public class TodoViewModel extends AndroidViewModel {
    private MutableLiveData<List<Task>> taskList = new MutableLiveData<>();

    /**
     * Constructor for the TodoViewModel class.
     * @param application The application.
     */
    public TodoViewModel(@NonNull Application application) {
        super(application);
        // Initialize with an empty list
        taskList.setValue(new ArrayList<>());
    }

    /**
     * Get the taskList.
     * @return the taskList
     */
    public LiveData<List<Task>> getTaskList() {
        return taskList;
    }

    /**
     * Add a task to the list.
     * @param task The task to add to the list.
     */
    public void addTask(Task task) {
        List<Task> tasks = new ArrayList<>(taskList.getValue());
        tasks.add(task);
        taskList.setValue(tasks);
    }
}
