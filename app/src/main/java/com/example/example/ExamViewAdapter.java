package com.example.example;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.example.schedule_fragments.ExamsFragment;

import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ExamViewAdapter extends RecyclerView.Adapter<ExamViewAdapter.ViewHolder> {
    private List<Exam> mData;
    private LayoutInflater mInflater;

    private ExamsFragment fragment;

    public ExamViewAdapter(Context context, List<Exam> mData, ExamsFragment fragment) {
        this.mData = mData;
        this.mInflater = LayoutInflater.from(context);
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_exam, parent, false);
        return new ViewHolder(view);
    }

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
        time = String.valueOf(hour) + ":" + timeComponents[1] + " " + amPm;
        holder.mDateTime.setText(mData.get(position).getDate() + " " + time);

        String color = mData.get(position).getColor();

        switch (color){
            case "Red":
                holder.mCardView.setCardBackgroundColor(ContextCompat.getColor(this.mInflater.getContext(), R.color.assignment_red));
                break;
            case "Blue":
                holder.mCardView.setCardBackgroundColor(ContextCompat.getColor(this.mInflater.getContext(), R.color.assignment_blue));
                break;
            case "Purple":
                holder.mCardView.setCardBackgroundColor(ContextCompat.getColor(this.mInflater.getContext(), R.color.assignment_purple));
                break;
            case "Green":
                holder.mCardView.setCardBackgroundColor(ContextCompat.getColor(this.mInflater.getContext(), R.color.assignment_green));
                break;
            case "Pink":
                holder.mCardView.setCardBackgroundColor(ContextCompat.getColor(this.mInflater.getContext(), R.color.assignment_pink));
                break;
            case "Brown":
                holder.mCardView.setCardBackgroundColor(ContextCompat.getColor(this.mInflater.getContext(), R.color.assignment_brown));
                break;
            default:
                holder.mCardView.setCardBackgroundColor(ContextCompat.getColor(this.mInflater.getContext(), R.color.assignment_red));
                break;

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
    }

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

    public void showEditDialog(Context context, int position, List<Exam> mData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.create_exam_dialog, null);
        builder.setView(dialogView);

        // Find and set up UI components in the dialog
        EditText examNameText = dialogView.findViewById(R.id.examNameText);
        EditText locationText = dialogView.findViewById(R.id.locationText);
        DatePicker datePicker = dialogView.findViewById(R.id.examDatePicker);
        TimePicker timePicker = dialogView.findViewById(R.id.timePicker);
        Spinner spinner = dialogView.findViewById(R.id.examColorSpinner);

        // Get the existing Assignment object at the given position
        Exam existingExam = mData.get(position);

        // Set the existing values in the EditText fields
        examNameText.setText(existingExam.getExamName());
        locationText.setText(existingExam.getLocation());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.mInflater.getContext(), R.array.color_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        int spinnerPosition = adapter.getPosition(existingExam.getColor());
        spinner.setSelection(spinnerPosition);

        String[] dateComponents = existingExam.getDate().split("/");
        datePicker.updateDate(Integer.parseInt(dateComponents[2]), Integer.parseInt(dateComponents[0])-1 ,Integer.parseInt(dateComponents[1]));

        String[] timeComponents = existingExam.getTime().split(":");
        timePicker.setHour(Integer.parseInt(timeComponents[0]));
        timePicker.setMinute(Integer.parseInt(timeComponents[1]));
        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String newExamName = examNameText.getText().toString();
                String newLocation = locationText.getText().toString();

                String day = String.valueOf(datePicker.getDayOfMonth());
                String month = String.valueOf(datePicker.getMonth()+1);
                String year = String.valueOf(datePicker.getYear());
                String color = spinner.getSelectedItem().toString();

                String date = month + "/" + day + "/" + year;

                String hour = String.valueOf(timePicker.getHour());
                String minute = String.format("%02d", timePicker.getMinute());


                String time = hour + ":" + minute;

                existingExam.setExamName(newExamName);
                existingExam.setLocation(newLocation);
                existingExam.setDate(date);
                existingExam.setColor(color);
                existingExam.setTime(time);

                notifyItemChanged(position);
                sortDate();

            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        // Show the dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mExamName;
        TextView mLocation;
        TextView mDateTime;
        CardView mCardView;
        ImageButton mCheckBtn;
        ImageButton mEditBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            mExamName = itemView.findViewById(R.id.examName);
            mLocation = itemView.findViewById(R.id.location);
            mDateTime = itemView.findViewById(R.id.dateTime);
            mCardView = itemView.findViewById(R.id.examCardView);
            mCheckBtn = itemView.findViewById(R.id.examCheckBtn);
            mEditBtn = itemView.findViewById(R.id.examEditBtn);

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
