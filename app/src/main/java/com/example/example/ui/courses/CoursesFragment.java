package com.example.example.ui.courses;

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

import com.example.example.adapters.CourseViewAdapter;
import com.example.example.R;
import com.example.example.models.Course;
import com.example.example.databinding.FragmentHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * This fragment displays a list of courses and provides functionality for adding new courses.
 * It uses a RecyclerView to display the courses and a FloatingActionButton for adding new courses.
 */
public class CoursesFragment extends Fragment {
    RecyclerView recyclerView;
    List<Course> courseList;
    CourseViewAdapter myRecyclerViewAdapter;
    TextView addCourseIndicator;
    private FragmentHomeBinding binding;

    /**
     * Called to have the fragment instantiate its user interface view.
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return Return the View for the fragment's UI, or null.
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CoursesViewModel homeViewModel =
                new ViewModelProvider(this).get(CoursesViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        addCourseIndicator = root.findViewById(R.id.addCourseIndicator);

        recyclerView = root.findViewById(R.id.courses_list_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        loadData();

        myRecyclerViewAdapter = new CourseViewAdapter(CoursesFragment.this, courseList, getContext());
        recyclerView.setAdapter(myRecyclerViewAdapter);

        FloatingActionButton fab = root.findViewById(R.id.coursesFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog();
            }
        });

        return root;
    }

    /**
     * Create a dialog to add a new course.
     */
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

                myRecyclerViewAdapter = new CourseViewAdapter(CoursesFragment.this, courseList, getContext());
                recyclerView.setAdapter(myRecyclerViewAdapter);
                saveData(courseList);
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

    /**
     * Save the list of courses to SharedPreferences.
     * @param courseList The list of courses to be saved.
     */
    public void saveData(List<Course> courseList) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences courses", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(courseList);
        editor.putString("course list", json);
        editor.apply();
    }

    /**
     * Load the course list data from the shared preferences.
     */
    private void loadData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("shared preferences courses", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("course list", null);
        Type type = new TypeToken<ArrayList<Course>>() {}.getType();
        courseList = gson.fromJson(json, type);

        if(courseList == null) {
            courseList = new ArrayList<>();
        }
    }

    /**
     * Set the visibility of the add course indicator to invisible.
     */
    public void setAddCourseIndicatorInvisible() {
        addCourseIndicator.setVisibility(View.INVISIBLE);
    }

    /**
     * Set the visibility of the add course indicator to visible.
     */
    public void setAddCourseIndicatorVisible() {
        addCourseIndicator.setVisibility(View.VISIBLE);
    }

    /**
     * Called when the view previously created by onCreateView has been detached from the fragment.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}