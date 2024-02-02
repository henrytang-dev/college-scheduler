package com.example.example.ui.slideshow;

import static java.security.AccessController.getContext;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.example.databinding.FragmentSlideshowBinding;

import com.example.example.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

public class TodoFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    FloatingActionButton floatingActionButton;

    ToDoListAdapter toDoListAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private TodoViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel = new ViewModelProvider(this).get(TodoViewModel.class);

        recyclerView = binding.recyclerViewMain; // Use the binding to access views
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        toDoListAdapter = new ToDoListAdapter(getContext());
        recyclerView.setAdapter(toDoListAdapter);

        FloatingActionButton floatingActionButton = root.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTodoDialog();
            }
        });

        // Observe the taskList LiveData
        viewModel.getTaskList().observe(getViewLifecycleOwner(), tasks -> {
            // Update your adapter or UI here
            toDoListAdapter.setTasks(tasks);
        });

        return root;
    }

    private void createTodoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();

        // Inflate the dialog layout
        View dialogView = inflater.inflate(R.layout.create_task_dialog, null);
        EditText taskNameText = dialogView.findViewById(R.id.taskNameText);
        RadioGroup buttons = (RadioGroup)dialogView.findViewById(R.id.radioGroup);

        DatePicker dueDatePicker = dialogView.findViewById(R.id.taskDatePicker);

        // Set the inflated view to the builder
        builder.setView(dialogView);

        builder.setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String taskName = taskNameText.getText().toString();
                int priority = 1;
                int checkedId = buttons.getCheckedRadioButtonId();
                if (checkedId == R.id.radButton1) {
                    priority = 1;
                } else if (checkedId == R.id.radButton2) {
                    priority = 2;
                } else if (checkedId == R.id.radButton3) {
                    priority = 3;
                }
                String day = String.valueOf(dueDatePicker.getDayOfMonth());
                String month = String.valueOf(dueDatePicker.getMonth()+1);
                String year = String.valueOf(dueDatePicker.getYear());

                String date = month + "/" + day + "/" + year;

                Task task = new Task(taskName, priority, date);
                viewModel.addTask(task);

            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        // Create and show the dialog
        Dialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

