package com.example.example.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.example.CourseViewAdapter;
import com.example.example.Exam;
import com.example.example.R;
import com.example.example.Course;
import com.example.example.databinding.FragmentHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    List<Course> courseList;

    CourseViewAdapter myRecyclerViewAdapter;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = root.findViewById(R.id.courses_list_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        loadData();

        FloatingActionButton fab = root.findViewById(R.id.coursesFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog();
            }
        });

        return root;
    }

    private void createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();

        // Inflate the dialog layout
        View dialogView = inflater.inflate(R.layout.create_course_dialog, null);
        EditText courseNameText = dialogView.findViewById(R.id.courseNameText);
        EditText courseLocationText = dialogView.findViewById(R.id.courseLocationText);
        EditText courseInstructorText = dialogView.findViewById(R.id.courseInstructor);
        Spinner spinner = dialogView.findViewById(R.id.courseColorSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.color_array,
                android.R.layout.simple_spinner_item
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        CheckBox checkBox_mon = dialogView.findViewById(R.id.checkbox_mon);
        CheckBox checkBox_tue = dialogView.findViewById(R.id.checkbox_tue);
        CheckBox checkBox_wed = dialogView.findViewById(R.id.checkbox_wed);
        CheckBox checkBox_thu = dialogView.findViewById(R.id.checkbox_thu);
        CheckBox checkBox_fri = dialogView.findViewById(R.id.checkbox_fri);
        CheckBox checkBox_sat = dialogView.findViewById(R.id.checkbox_sat);
        CheckBox checkBox_sun = dialogView.findViewById(R.id.checkbox_sun);

        TimePicker timePicker1 = dialogView.findViewById(R.id.timePicker1);
        TimePicker timePicker2 = dialogView.findViewById(R.id.timePicker2);
        builder.setView(dialogView);

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String courseName = courseNameText.getText().toString();
                String courseLocation = courseLocationText.getText().toString();
                String courseColor = spinner.getSelectedItem().toString();
                String courseInstructor = courseInstructorText.getText().toString();

                String startHour = String.valueOf(timePicker1.getHour());
                String startMinute = String.format("%02d", timePicker1.getMinute());

                String endHour = String.valueOf(timePicker2.getHour());
                String endMinute = String.format("%02d", timePicker2.getMinute());

                String startTime = startHour + ":" + startMinute;
                String endTime = endHour + ":" + endMinute;

                boolean[] courseDays = new boolean[]{
                        checkBox_mon.isChecked(),
                        checkBox_tue.isChecked(),
                        checkBox_wed.isChecked(),
                        checkBox_thu.isChecked(),
                        checkBox_fri.isChecked(),
                        checkBox_sat.isChecked(),
                        checkBox_sun.isChecked()
                };

                Course course = new Course(courseName, courseInstructor, courseLocation, courseDays, startTime, endTime, courseColor);
                courseList.add(course);
                Log.println(Log.INFO, "Course", course.toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void saveData(List<Course> courseList) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(courseList);
        editor.putString("course list", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("exam list", null);
        Type type = new TypeToken<ArrayList<Exam>>() {}.getType();
        courseList = gson.fromJson(json, type);

        if(courseList == null) {
            courseList = new ArrayList<>();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}