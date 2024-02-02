package com.example.example.ui.todo;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.example.models.Task;

import java.util.ArrayList;
import java.util.List;

public class TodoViewModel extends AndroidViewModel {
    private MutableLiveData<List<Task>> taskList = new MutableLiveData<>();

    public TodoViewModel(@NonNull Application application) {
        super(application);
        // Initialize with an empty list
        taskList.setValue(new ArrayList<>());
    }

    public LiveData<List<Task>> getTaskList() {
        return taskList;
    }

    public void addTask(Task task) {
        List<Task> tasks = new ArrayList<>(taskList.getValue());
        tasks.add(task);
        taskList.setValue(tasks);
    }
}
