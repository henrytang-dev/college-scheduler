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
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.example.adapters.MyRecyclerViewAdapter;
import com.example.example.models.Assignment;
import com.example.example.models.Course;
import com.example.example.models.Exam;
import com.example.example.adapters.ExamViewAdapter;
import com.example.example.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * This fragment displays a list of exams and provides functionality for adding and sorting exams.
 * It uses a RecyclerView to display the exams and a FloatingActionButton for adding new exams.
 * The fragment also includes a Spinner for sorting exams by date.
 */
public class ExamsFragment extends Fragment {

    RecyclerView recyclerView;

    ExamViewAdapter myRecyclerViewAdapter;
    List<Exam> examList;
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
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_exams, container, false);

        recyclerView = root.findViewById(R.id.exam_list_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        loadData();
        courseList = Course.loadCourseListData(getContext());
        for(Exam exam : examList) {
            exam.setCourse(Course.getCourseFromName(exam.getExamName(), courseList));
        }

        myRecyclerViewAdapter = new ExamViewAdapter(getContext(), examList, ExamsFragment.this);
        recyclerView.setAdapter(myRecyclerViewAdapter);


        FloatingActionButton fab = root.findViewById(R.id.fab_add_exam);
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
     * Create and show the dialog for adding a new exam.
     */
    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();

        // Inflate the dialog layout
        View dialogView = inflater.inflate(R.layout.create_exam_dialog, null);
        Spinner examNameSpinner = dialogView.findViewById(R.id.examNameSpinner);
        ArrayAdapter<String> classAdapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_spinner_item, Course.setupClassArray(courseList));

        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        examNameSpinner.setAdapter(classAdapter);

        EditText locationText = dialogView.findViewById(R.id.locationText);


        DatePicker datePicker = dialogView.findViewById(R.id.examDatePicker);
        TimePicker timePicker = dialogView.findViewById(R.id.timePicker);

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
                        String examName = examNameSpinner.getSelectedItem().toString();
                        if(examName.equals("Pick a class")) {
                            Toast.makeText(getContext(), "Please pick a class", Toast.LENGTH_LONG).show();
                        } else {
                            String location = locationText.getText().toString();

                            String day = String.valueOf(datePicker.getDayOfMonth());
                            String month = String.valueOf(datePicker.getMonth()+1);
                            String year = String.valueOf(datePicker.getYear());

                            String hour = String.valueOf(timePicker.getHour());
                            String minute = String.format("%02d", timePicker.getMinute());

                            String time = hour + ":" + minute;

                            Course course = Course.getCourseFromName(examName, courseList);

                            String date = month + "/" + day + "/" + year;

                            Exam exam = new Exam(examName, location, date, time, course);
                            examList.add(exam);

                            myRecyclerViewAdapter = new ExamViewAdapter(getContext(), examList, ExamsFragment.this);
                            recyclerView.setAdapter(myRecyclerViewAdapter);
                            myRecyclerViewAdapter.sortDate();

                            dialog.dismiss();

                        }
                    }
                });
            }
        });


        alertDialog.show();
    }

    /**
     * Save the list of exams to SharedPreferences.
     *
     * @param list The list of exams to be saved.
     */
    public void saveData(List<Exam> list) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("exam list", json);
        editor.apply();
    }

    /**
     * Load the list of exams from SharedPreferences.
     */
    private void loadData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("exam list", null);
        Type type = new TypeToken<ArrayList<Exam>>() {}.getType();
        examList = gson.fromJson(json, type);

        if(examList == null) {
            examList = new ArrayList<>();
        }
    }

}