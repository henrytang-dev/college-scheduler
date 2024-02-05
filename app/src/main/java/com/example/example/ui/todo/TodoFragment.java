package com.example.example.ui.todo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.example.adapters.ToDoListAdapter;
import com.example.example.models.Assignment;
import com.example.example.models.Exam;
import com.example.example.models.Task;
import com.example.example.databinding.FragmentSlideshowBinding;

import com.example.example.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * This fragment displays a list of tasks and provides functionality for adding, sorting, and editing tasks.
 * It uses a RecyclerView to display the tasks.
 * The fragment also includes a Spinner for sorting tasks by priority, assignments, or exams.
 */
public class TodoFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    FloatingActionButton floatingActionButton;
    ToDoListAdapter toDoListAdapter;
    RecyclerView recyclerView;
    List<Task> taskList;

    /**
     * Called to have the fragment instantiate its user interface view.
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        taskList = new ArrayList<>();

        recyclerView = binding.recyclerViewMain; // Use the binding to access views
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        toDoListAdapter = new ToDoListAdapter(getContext(), taskList);
        recyclerView.setAdapter(toDoListAdapter);
        toDoListAdapter.sortPriority();

        Spinner sortSpinner = root.findViewById(R.id.sortSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.sort_todo_array,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(adapter);

        Button sortBtn = root.findViewById(R.id.sortBtn);
        sortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sortType = sortSpinner.getSelectedItem().toString();
                if(sortType.equals("Priority")) {
                    toDoListAdapter.sortPriority();
                } else if(sortType.equals("Assignments")) {
                    toDoListAdapter.sortAssignments();
                } else if(sortType.equals("Exams")) {
                    toDoListAdapter.sortExams();
                }
            }
        });

        return root;
    }

//    private void createTodoDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        LayoutInflater inflater = getLayoutInflater();
//
//        // Inflate the dialog layout
//        View dialogView = inflater.inflate(R.layout.create_task_dialog, null);
//        EditText taskNameText = dialogView.findViewById(R.id.taskNameText);
//        RadioGroup buttons = (RadioGroup)dialogView.findViewById(R.id.radioGroup);
//
//        DatePicker dueDatePicker = dialogView.findViewById(R.id.taskDatePicker);
//
//        // Set the inflated view to the builder
//        builder.setView(dialogView);
//
//        builder.setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                String taskName = taskNameText.getText().toString();
//                int priority = 1;
//                int checkedId = buttons.getCheckedRadioButtonId();
//                if (checkedId == R.id.radButton1) {
//                    priority = 1;
//                } else if (checkedId == R.id.radButton2) {
//                    priority = 2;
//                } else if (checkedId == R.id.radButton3) {
//                    priority = 3;
//                }
//                String day = String.valueOf(dueDatePicker.getDayOfMonth());
//                String month = String.valueOf(dueDatePicker.getMonth()+1);
//                String year = String.valueOf(dueDatePicker.getYear());
//
//                String date = month + "/" + day + "/" + year;
//
//                //Task task = new Task(taskName, priority, date);
//                //viewModel.addTask(task);
//
//            }
//        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
//
//        // Create and show the dialog
//        Dialog dialog = builder.create();
//        dialog.show();
//    }

    /**
     * Called when the view previously created by onCreateView has been detached from the fragment.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

