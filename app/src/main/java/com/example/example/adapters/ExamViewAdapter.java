package com.example.example.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.example.ColorMapper;
import com.example.example.R;
import com.example.example.models.Course;
import com.example.example.models.Exam;
import com.example.example.schedule_fragments.ExamsFragment;

import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * This class represents the RecyclerView adapter for managing the display of exams in the application.
 * It handles addition, editing, and sorting of exams, and utilizes a custom ViewHolder for each item.
 */
public class ExamViewAdapter extends RecyclerView.Adapter<ExamViewAdapter.ViewHolder> {
    private List<Exam> mData;
    private LayoutInflater mInflater;
    private ExamsFragment fragment;

    private ColorMapper colorMapper;
    List<Course> courseList;


    /**
     * Constructor for the ExamViewAdapter.
     *
     * @param context   The context of the application.
     * @param mData     The list of Exam objects to be displayed.
     * @param fragment  The associated ExamsFragment.
     */
    public ExamViewAdapter(Context context, List<Exam> mData, ExamsFragment fragment) {
        this.mData = mData;
        this.mInflater = LayoutInflater.from(context);
        this.fragment = fragment;
        this.colorMapper = new ColorMapper(context);
        courseList = Course.loadCourseListData(context);
    }

    /**
     * Called when RecyclerView needs a new ViewHolder instance.
     *
     * @param parent   The ViewGroup into which the new View will be added.
     * @param viewType The type of the new View.
     * @return A new ViewHolder that holds a View representing an exam item.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_exam, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder   The ViewHolder to bind the data to.
     * @param position The position of the data in the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mExamName.setText(mData.get(position).getExamName());
        holder.mLocation.setText(mData.get(position).getLocation());
        String time = mData.get(position).getTime();
        String[] timeComponents = time.split(":");
        int hour = Integer.parseInt(timeComponents[0]);
        String amPm = "AM";
        if(hour > 12) {
            hour = hour % 12;
            amPm = "PM";
        }
        time = hour + ":" + timeComponents[1] + " " + amPm;
        holder.mDateTime.setText(mData.get(position).getDate() + " " + time);

        String color = mData.get(position).getColor();
        holder.mCardView.setCardBackgroundColor(colorMapper.getColorResourceId(color));
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in the adapter's data set.
     */
    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * Removes an item at the specified position and notifies the fragment to save the updated data.
     *
     * @param position The position of the item to be removed.
     */
    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
        fragment.saveData(mData);
    }

    /**
     * Sorts the list of exams by date and notifies the fragment to save the updated data.
     */
    public void sortDate() {
        Collections.sort(mData, new Comparator<Exam>() {
            @Override
            public int compare(Exam e1, Exam e2) {
                try {
                    Date a = e1.convertDate();
                    Date b = e2.convertDate();
                    return a.compareTo(b);
                } catch (ParseException e) {
                    Toast.makeText(mInflater.getContext(), "Date time threw error", Toast.LENGTH_LONG).show();
                }
                return 0;
            }
        });

        notifyDataSetChanged();
        fragment.saveData(mData);
    }

    /**
     * Shows an edit dialog for modifying an existing exam at the specified position.
     *
     * @param context  The context in which the dialog will be displayed.
     * @param position The position of the exam to be edited.
     * @param mData    The list of exams.
     */
    public void showEditDialog(Context context, int position, List<Exam> mData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.create_exam_dialog, null);
        builder.setView(dialogView);

        // Find and set up UI components in the dialog
        Spinner examNameSpinner = dialogView.findViewById(R.id.examNameSpinner);
        ArrayAdapter<String> classAdapter = new ArrayAdapter<String>
                (mInflater.getContext(), android.R.layout.simple_spinner_item, Course.setupClassArray(courseList));

        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        examNameSpinner.setAdapter(classAdapter);

        EditText locationText = dialogView.findViewById(R.id.locationText);
        DatePicker datePicker = dialogView.findViewById(R.id.examDatePicker);
        TimePicker timePicker = dialogView.findViewById(R.id.timePicker);

        // Get the existing Assignment object at the given position
        Exam existingExam = mData.get(position);

        int classSpinnerPosition = classAdapter.getPosition(existingExam.getExamName());
        examNameSpinner.setSelection(classSpinnerPosition);

        locationText.setText(existingExam.getLocation());

        String[] dateComponents = existingExam.getDate().split("/");
        datePicker.updateDate(Integer.parseInt(dateComponents[2]), Integer.parseInt(dateComponents[0])-1 ,Integer.parseInt(dateComponents[1]));

        String[] timeComponents = existingExam.getTime().split(":");
        timePicker.setHour(Integer.parseInt(timeComponents[0]));
        timePicker.setMinute(Integer.parseInt(timeComponents[1]));

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
                        String newExamName = examNameSpinner.getSelectedItem().toString();
                        if(newExamName.equals("Pick a class")) {
                            Toast.makeText(mInflater.getContext(), "Please pick a class", Toast.LENGTH_LONG).show();
                        } else {
                            String newLocation = locationText.getText().toString();

                            String day = String.valueOf(datePicker.getDayOfMonth());
                            String month = String.valueOf(datePicker.getMonth()+1);
                            String year = String.valueOf(datePicker.getYear());
                            Course course = Course.getCourseFromName(newExamName, courseList);

                            String date = month + "/" + day + "/" + year;

                            String hour = String.valueOf(timePicker.getHour());
                            String minute = String.format("%02d", timePicker.getMinute());


                            String time = hour + ":" + minute;

                            existingExam.setExamName(newExamName);
                            existingExam.setLocation(newLocation);
                            existingExam.setDate(date);
                            existingExam.setCourse(course);
                            existingExam.setTime(time);

                            notifyItemChanged(position);
                            sortDate();

                            dialog.dismiss();

                        }
                    }
                });
            }
        });



        // Show the dialog
        alertDialog.show();
    }

    /**
     * The ViewHolder class represents each item in the RecyclerView.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mExamName;
        TextView mLocation;
        TextView mDateTime;
        CardView mCardView;
        ImageButton mCheckBtn;
        ImageButton mEditBtn;

        /**
         * Constructor for the ViewHolder class.
         *
         * @param itemView The View object representing an exam item.
         */
        public ViewHolder(View itemView) {
            super(itemView);

            // Initialize UI components in the ViewHolder
            mExamName = itemView.findViewById(R.id.examName);
            mLocation = itemView.findViewById(R.id.location);
            mDateTime = itemView.findViewById(R.id.dateTime);
            mCardView = itemView.findViewById(R.id.examCardView);
            mCheckBtn = itemView.findViewById(R.id.examCheckBtn);
            mEditBtn = itemView.findViewById(R.id.examEditBtn);

            // Set up click listeners for check and edit buttons
            mCheckBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Call the removeItem method in the adapter to delete the item
                        removeItem(position);
                    }
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
    }

}
