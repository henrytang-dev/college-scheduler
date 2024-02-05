package com.example.example.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.example.ColorMapper;
import com.example.example.R;
import com.example.example.models.Course;
import com.example.example.ui.courses.CoursesFragment;

import java.util.List;

public class CourseViewAdapter extends RecyclerView.Adapter<CourseViewAdapter.ViewHolder> {
    private List<Course> mData;
    private LayoutInflater mInflater;
    private CoursesFragment fragment;
    private Context mContext;
    private ColorMapper colorMapper;

    public CourseViewAdapter(CoursesFragment fragment, List<Course> data, Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(fragment.getContext());
        this.mData = data;
        this.fragment = fragment;
        this.colorMapper = new ColorMapper(context);
    }

    @NonNull
    @Override
    public CourseViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_course, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewAdapter.ViewHolder holder, int position) {
        holder.mCourseName.setText(mData.get(position).getCourseName());
        holder.mCourseLocation.setText(mData.get(position).getCourseLocation());
        boolean[] daysArr = mData.get(position).getCourseDays();

        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < daysArr.length; i++) {
            if (daysArr[i]) {
                if (result.length() > 0) {
                    result.append(", ");
                }
                result.append(days[i]);
            }
        }

        holder.mDaysOfWeek.setText(result.toString());
        String startTime = remove24Hour(mData.get(position).getCourseStartTime());
        String endTime = remove24Hour(mData.get(position).getCourseEndTime());


        String formattedTime = mContext.getString(R.string.class_time_format, startTime, endTime);
        holder.mClassTime.setText(formattedTime);
        holder.mCourseInstructor.setText(mData.get(position).getCourseInstructor());
        String color = mData.get(position).getCourseColor();
        Log.println(Log.INFO, "color", color);
        holder.mCardView.setCardBackgroundColor(colorMapper.getColorResourceId(color));

        if(getItemCount() > 0) {
            fragment.setAddCourseIndicatorInvisible();
        }
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
        fragment.saveData(mData);

        if(getItemCount() == 0) {
            fragment.setAddCourseIndicatorVisible();
        }
    }

    public void showEditDialog(Context context, int position, List<Course> mData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);

        View dialogView = inflater.inflate(R.layout.create_course_dialog, null);
        builder.setView(dialogView);

        EditText courseNameText = dialogView.findViewById(R.id.courseNameText);
        EditText courseLocationText = dialogView.findViewById(R.id.courseLocationText);
        EditText courseInstructorText = dialogView.findViewById(R.id.courseInstructor);
        Spinner spinner = dialogView.findViewById(R.id.courseColorSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                context,
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

        Course existingCourse = mData.get(position);

        // Set the existing values in the EditText fields
        courseNameText.setText(existingCourse.getCourseName());
        courseLocationText.setText(existingCourse.getCourseLocation());
        courseInstructorText.setText(existingCourse.getCourseInstructor());

        int spinnerPos = adapter.getPosition(existingCourse.getCourseColor());
        spinner.setSelection(spinnerPos);

        boolean[] courseDays = existingCourse.getCourseDays();
        checkBox_mon.setChecked(courseDays[0]);
        checkBox_tue.setChecked(courseDays[1]);
        checkBox_wed.setChecked(courseDays[2]);
        checkBox_thu.setChecked(courseDays[3]);
        checkBox_fri.setChecked(courseDays[4]);
        checkBox_sat.setChecked(courseDays[5]);
        checkBox_sun.setChecked(courseDays[6]);

        TimePicker timePicker1 = dialogView.findViewById(R.id.timePicker1);
        TimePicker timePicker2 = dialogView.findViewById(R.id.timePicker2);

        String[] startTime = existingCourse.getCourseStartTime().split(":");
        String[] endTime = existingCourse.getCourseEndTime().split(":");

        timePicker1.setHour(Integer.parseInt(startTime[0]));
        timePicker1.setMinute(Integer.parseInt(startTime[1]));

        timePicker2.setHour(Integer.parseInt(endTime[0]));
        timePicker2.setMinute(Integer.parseInt(endTime[1]));

        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String newCourseName = courseNameText.getText().toString();
                String newCourseLocation = courseLocationText.getText().toString();
                String newCourseInstructor = courseInstructorText.getText().toString();

                String newStartHour = String.valueOf(timePicker1.getHour());
                String newStartMinute = String.format("%02d", timePicker1.getMinute());

                String newEndHour = String.valueOf(timePicker2.getHour());
                String newEndMinute = String.format("%02d", timePicker2.getMinute());

                String newStartTime = newStartHour + ":" + newStartMinute;
                String newEndTime = newEndHour + ":" + newEndMinute;

                boolean[] newCourseDays = new boolean[]{
                        checkBox_mon.isChecked(),
                        checkBox_tue.isChecked(),
                        checkBox_wed.isChecked(),
                        checkBox_thu.isChecked(),
                        checkBox_fri.isChecked(),
                        checkBox_sat.isChecked(),
                        checkBox_sun.isChecked()
                };

                String newCourseColor = spinner.getSelectedItem().toString();

                existingCourse.setCourseName(newCourseName);
                existingCourse.setCourseLocation(newCourseLocation);
                existingCourse.setCourseInstructor(newCourseInstructor);
                existingCourse.setCourseDays(newCourseDays);
                existingCourse.setCourseStartTime(newStartTime);
                existingCourse.setCourseEndTime(newEndTime);
                existingCourse.setCourseColor(newCourseColor);

                notifyItemChanged(position);
                fragment.saveData(mData);
            }
            }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private String remove24Hour(String time) {
        String[] timeComponents = time.split(":");
        int hour = Integer.parseInt(timeComponents[0]);
        String amPm = "AM";
        if(hour > 12) {
            hour = hour % 12;
            amPm = "PM";
        }
        time = hour + ":" + timeComponents[1] + " " + amPm;
        return time;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mCourseName;
        TextView mCourseLocation;
        TextView mDaysOfWeek;
        TextView mClassTime;
        TextView mCourseInstructor;
        CardView mCardView;
        ImageButton mDeleteBtn;
        ImageButton mEditBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            mCourseName = itemView.findViewById(R.id.courseName);
            mCourseLocation = itemView.findViewById(R.id.courseLocation);
            mDaysOfWeek = itemView.findViewById(R.id.daysOfWeek);
            mClassTime = itemView.findViewById(R.id.classTime);
            mCourseInstructor = itemView.findViewById(R.id.courseInstructor);
            mCardView = itemView.findViewById(R.id.courseCardView);
            mDeleteBtn = itemView.findViewById(R.id.courseDeleteButton);
            mEditBtn = itemView.findViewById(R.id.courseEditButton);

            mDeleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDeleteConfirmationDialog();
                }
            });

            mEditBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Call the showEditDialog method in the adapter to open the edit dialog
                        showEditDialog(view.getContext(), position, mData);
                    }
                }
            });
        }

        public void showDeleteConfirmationDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

            builder.setTitle("Confirm Deletion");
            builder.setMessage("Are you sure you want to delete this todo item?");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Call the showEditDialog method in the adapter to open the edit dialog
                        removeItem(position);
                    }
                    dialog.dismiss();
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // User canceled the deletion, do nothing or provide feedback
                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

    }

}
