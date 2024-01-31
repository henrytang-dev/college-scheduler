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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.example.Assignment;
import com.example.example.Exam;
import com.example.example.ExamViewAdapter;
import com.example.example.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class ExamsFragment extends Fragment {

    RecyclerView recyclerView;

    ExamViewAdapter myRecyclerViewAdapter;
    List<Exam> examList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_exams, container, false);

        recyclerView = root.findViewById(R.id.exam_list_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        loadData();

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
    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();

        // Inflate the dialog layout
        View dialogView = inflater.inflate(R.layout.create_exam_dialog, null);
        EditText examNameText = dialogView.findViewById(R.id.examNameText);
        EditText locationText = dialogView.findViewById(R.id.locationText);
        Spinner spinner = dialogView.findViewById(R.id.examColorSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.color_array,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        DatePicker datePicker = dialogView.findViewById(R.id.examDatePicker);
        TimePicker timePicker = dialogView.findViewById(R.id.timePicker);

        // Set the inflated view to the builder
        builder.setView(dialogView);

        builder.setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String examName = examNameText.getText().toString();
                String location = locationText.getText().toString();

                String day = String.valueOf(datePicker.getDayOfMonth());
                String month = String.valueOf(datePicker.getMonth()+1);
                String year = String.valueOf(datePicker.getYear());

                String hour = String.valueOf(timePicker.getHour());
                String minute = String.format("%02d", timePicker.getMinute());

                String time = hour + ":" + minute;

                String color = spinner.getSelectedItem().toString();

                String date = month + "/" + day + "/" + year;

                Exam exam = new Exam(examName, location, date, time, color);
                examList.add(exam);

                myRecyclerViewAdapter = new ExamViewAdapter(getContext(), examList, ExamsFragment.this);
                recyclerView.setAdapter(myRecyclerViewAdapter);
                myRecyclerViewAdapter.sortDate();
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

    public void saveData(List<Exam> list) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("exam list", json);
        editor.apply();
    }

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