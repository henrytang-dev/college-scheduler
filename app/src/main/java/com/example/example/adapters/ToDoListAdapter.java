package com.example.example.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.example.R;
import com.example.example.models.Task;
import com.example.example.ui.todo.TodoFragment;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * This TaskAdapter creates and binds ViewHolders, that hold the description and priority of a task,
 * to a RecyclerView to efficiently display data.
 */
public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.TaskViewHolder> {

    // Constant for date format
    private static final String DATE_FORMAT = "dd/MM/yyy";


    // Class variables for the List that holds task data and the Context
    private List<Task> mTaskEntries;
    private TodoFragment fragment;
    private Context mContext;
    // Date formatter
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());


    public ToDoListAdapter(Context context) {
        mContext = context;
    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_row_item, parent, false);

        return new TaskViewHolder(view);
    }



    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        // Determine the values of the wanted data
        Task taskEntry = mTaskEntries.get(position);
        String description = taskEntry.getName();
        int priority = taskEntry.getPriority();

        final int id = taskEntry.getId(); // get item id
        String updatedAt = taskEntry.getUpdatedAt();




        //Set values
        holder.taskDescriptionView.setText(description);
        holder.updatedAtView.setText(updatedAt);

        // Programmatically set the text and color for the priority TextView
        String priorityString = "" + priority; // converts int to String
        holder.priorityView.setText(priorityString);

        GradientDrawable priorityCircle = (GradientDrawable) holder.priorityView.getBackground();
        // Get the appropriate background color based on the priority
        int priorityColor = getPriorityColor(priority);
        priorityCircle.setColor(priorityColor);
    }


    private int getPriorityColor(int priority) {
        int priorityColor = 0;

        switch (priority) {
            case 1:
                priorityColor = ContextCompat.getColor(mContext, R.color.assignment_red);
                break;
            case 2:
                priorityColor = ContextCompat.getColor(mContext, R.color.assignment_blue);
                break;
            case 3:
                priorityColor = ContextCompat.getColor(mContext, R.color.assignment_green);
                break;
            default:
                break;
        }
        return priorityColor;
    }


    @Override
    public int getItemCount() {
        if (mTaskEntries == null) {
            return 0;
        }
        return mTaskEntries.size();
    }

    /**
     * When data changes, this method updates the list of taskEntries
     * and notifies the adapter to use the new values on it
     */
    public void setTasks(List<Task> taskEntries) {
        mTaskEntries = taskEntries;
        notifyDataSetChanged();
    }


    public List<Task> getTasks() {
        return mTaskEntries;
    }

    // Inner class for creating ViewHolders
    class TaskViewHolder extends RecyclerView.ViewHolder {

        // Class variables for the task description and priority TextViews
        TextView taskDescriptionView;
        TextView updatedAtView;
        TextView priorityView;
        ImageButton check;

        View view;




        // Constructor for the TaskViewHolders.

        public TaskViewHolder(View itemView) {
            super(itemView);


            taskDescriptionView = itemView.findViewById(R.id.taskDescription);
            updatedAtView = itemView.findViewById(R.id.taskUpdatedAt);
            priorityView = itemView.findViewById(R.id.priorityTextView);
            check = itemView.findViewById(R.id.taskDeleteButton);

            check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Call the removeItem method in the adapter to delete the item
                        removeItem(position);
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Call the removeItem method in the adapter to delete the item
                        showEditDialog(view.getContext(), position);
                    }
                }
            });

            view = itemView;
        }


    }

    public void showEditDialog(Context context, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.create_task_dialog, null);
        builder.setView(dialogView);

        // Find and set up UI components in the dialog
        EditText taskNameText = dialogView.findViewById(R.id.taskNameText);
        RadioGroup buttons = (RadioGroup)dialogView.findViewById(R.id.radioGroup);
        DatePicker dueDatePicker = dialogView.findViewById(R.id.taskDatePicker);

        // Get the existing Assignment object at the given position
        Task task = mTaskEntries.get(position);

        // Set the existing values in the EditText fields
        taskNameText.setText(task.getName());


        String[] dateComponents = task.getUpdatedAt().split("/");
        dueDatePicker.updateDate(Integer.parseInt(dateComponents[2]), Integer.parseInt(dateComponents[0])-1 ,Integer.parseInt(dateComponents[1]));

        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String newTaskName = taskNameText.getText().toString();

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

                task.setName(newTaskName);
                task.setUpdatedAt(date);
                task.setPriority(priority);

                notifyItemChanged(position);
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

    public void removeItem(int position) {
            mTaskEntries.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
    }
}