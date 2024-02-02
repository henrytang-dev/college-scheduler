package com.example.example.schedule_fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.example.models.Assignment;
import com.example.example.adapters.MyRecyclerViewAdapter;
import com.example.example.R;
import com.example.example.models.Course;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This fragment displays a list of assignments and provides functionality for adding, sorting, and editing assignments.
 * It uses a RecyclerView to display the assignments, and a FloatingActionButton for adding new assignments.
 * The fragment also includes a Spinner for sorting assignments by due date or class name.
 */
public class AssignmentsFragment extends Fragment {

    RecyclerView recyclerView;
    MyRecyclerViewAdapter myRecyclerViewAdapter;
    List<Assignment> assignmentList;
    List<Course> courseList;

    /**
     * Called to create and return the view hierarchy associated with the fragment.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate views.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return The root view of the fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_assignments, container, false);

        recyclerView = root.findViewById(R.id.assignment_list_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        loadData();
        courseList = Course.loadCourseListData(getContext());
        for(Assignment assignment : assignmentList) {
            assignment.setCourse(Course.getCourseFromName(assignment.getClassName(), courseList));
        }

        myRecyclerViewAdapter = new MyRecyclerViewAdapter(getContext(), assignmentList, AssignmentsFragment.this);
        recyclerView.setAdapter(myRecyclerViewAdapter);

        Spinner sortSpinner = root.findViewById(R.id.sortSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.sort_array,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(adapter);

        Button sortBtn = root.findViewById(R.id.sortBtn);
        sortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sortType = sortSpinner.getSelectedItem().toString();
                if(sortType.equals("Due Date")) {
                    myRecyclerViewAdapter.sortDate();
                } else if(sortType.equals("Classes")) {
                    myRecyclerViewAdapter.sortClasses();
                }
            }
        });

        FloatingActionButton fab = root.findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog();
            }
        });

        // Inflate the layout for this fragment
        return root;
    }

    /**
     * Create and show the dialog for adding a new assignment.
     */
    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();

        // Inflate the dialog layout
        View dialogView = inflater.inflate(R.layout.create_assignment_dialog, null);
        Spinner classSpinner = dialogView.findViewById(R.id.classSpinner);
        ArrayAdapter<String> classAdapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_spinner_item, Course.setupClassArray(courseList));

        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(classAdapter);

        EditText assignmentTitleText = dialogView.findViewById(R.id.titleText);
//        Spinner colorSpinner = dialogView.findViewById(R.id.colorSpinner);
//        ArrayAdapter<CharSequence> colorAdapter = ArrayAdapter.createFromResource(
//                getContext(),
//                R.array.color_array,
//                android.R.layout.simple_spinner_item
//        );
//
//        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        colorSpinner.setAdapter(colorAdapter);

        DatePicker dueDatePicker = dialogView.findViewById(R.id.datePicker);

        // Set the inflated view to the builder
        builder.setView(dialogView);

        builder.setPositiveButton(R.string.save, null);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button positiveButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String className = classSpinner.getSelectedItem().toString();
                        if(className.equals("Pick a class")) {
                            Toast.makeText(getContext(), "Please pick a class", Toast.LENGTH_LONG).show();
                        } else {
                            String title = assignmentTitleText.getText().toString();
                            String day = String.valueOf(dueDatePicker.getDayOfMonth());
                            String month = String.valueOf(dueDatePicker.getMonth()+1);
                            String year = String.valueOf(dueDatePicker.getYear());
                            Course course = Course.getCourseFromName(className, courseList);

                            String date = month + "/" + day + "/" + year;

                            Assignment hw = new Assignment(className, title, date, course);
                            assignmentList.add(hw);

                            myRecyclerViewAdapter = new MyRecyclerViewAdapter(getContext(), assignmentList, AssignmentsFragment.this);
                            recyclerView.setAdapter(myRecyclerViewAdapter);

                            saveData(assignmentList);

                            dialog.dismiss();

                        }
                    }
                });
            }
        });


        alertDialog.show();
    }
    /**
     * Save the list of assignments to SharedPreferences.
     *
     * @param list The list of assignments to be saved.
     */
    public void saveData(List<Assignment> list) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("assignment list", json);
        editor.apply();
    }

    /**
     * Load the list of assignments from SharedPreferences.
     */
    private void loadData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("assignment list", null);
        Type type = new TypeToken<ArrayList<Assignment>>() {}.getType();
        assignmentList = gson.fromJson(json, type);

        if(assignmentList == null) {
            assignmentList = new ArrayList<>();
        }
    }

}