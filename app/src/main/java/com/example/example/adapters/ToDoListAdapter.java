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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.example.R;
import com.example.example.models.Assignment;
import com.example.example.models.Exam;
import com.example.example.models.Task;
import com.example.example.ui.todo.TodoFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
    private List<Exam> examList;
    private List<Assignment> assignmentList;
    private TodoFragment fragment;
    private Context mContext;
    // Date formatter
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());


    /**
     * Constructor for the TaskAdapter that initializes the Context.
     * @param context The current context. Used to inflate the layout file.
     * @param taskList The list of tasks to display.
     */
    public ToDoListAdapter(Context context, List<Task> taskList) {
        mContext = context;

        examList = Exam.loadData(mContext);
        assignmentList = Assignment.loadData(mContext);

        taskList.addAll(examList);
        taskList.addAll(assignmentList);

        this.mTaskEntries = taskList;
    }


    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new TaskViewHolder that holds the View for each task
     */
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_row_item, parent, false);

        return new TaskViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        // Determine the values of the wanted data
        Task taskEntry = mTaskEntries.get(position);
        String description;
        if(taskEntry.getClass() == Assignment.class) {
            Assignment assignment = (Assignment) taskEntry;
            description = assignment.getClassName() + " - " + assignment.getAssignmentTitle();
        } else if (taskEntry.getClass() == Exam.class) {
            Exam exam = (Exam) taskEntry;
            description = exam.getExamName() + " - Exam";
        } else {
            description = "";
        }
        int priority = 0;

        try {
            priority = taskEntry.getPriority();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        //Set values
        holder.taskDescriptionView.setText(description);
        holder.updatedAtView.setText(taskEntry.getDate());

        GradientDrawable priorityCircle = (GradientDrawable) holder.priorityView.getBackground();
        // Get the appropriate background color based on the priority
        int priorityColor = getPriorityColor(priority);
        priorityCircle.setColor(priorityColor);
    }

    /**
     * Sorts the list of tasks by priority.
     */
    public void sortPriority() {

        mTaskEntries.clear();
        mTaskEntries.addAll(assignmentList);
        mTaskEntries.addAll(examList);

        sortDate();

        notifyDataSetChanged();
    }

    /**
     * Sorts the list of tasks by date.
     */
    private void sortDate() {
        Collections.sort(mTaskEntries, new Comparator<Task>() {
            @Override
            public int compare(Task a1, Task a2) {
                try {
                    Date a = a1.convertDate();
                    Date b = a2.convertDate();
                    return a.compareTo(b);
                } catch (ParseException e) {
                    Toast.makeText(mContext, "Date threw error", Toast.LENGTH_LONG).show();
                }
                return 0;
            }
        });
    }

    /**
     * Sorts the list of tasks by date.
     */
    public void sortAssignments() {
        mTaskEntries = new ArrayList<>(assignmentList);
        sortDate();
        notifyDataSetChanged();
    }

    /**
     * Sorts the list of tasks by date.
     */
    public void sortExams() {
        mTaskEntries = new ArrayList<>(examList);
        sortDate();
        notifyDataSetChanged();
    }


    /**
     * @param priority The priority of the task
     * @return The priority color
     */
    private int getPriorityColor(int priority) {
        int priorityColor = 0;

        switch (priority) {
            case 1:
                priorityColor = ContextCompat.getColor(mContext, R.color.assignment_red);
                break;
            case 2:
                priorityColor = ContextCompat.getColor(mContext, R.color.assignment_yellow);
                break;
            case 3:
                priorityColor = ContextCompat.getColor(mContext, R.color.assignment_green);
                break;
            default:
                break;
        }
        return priorityColor;
    }


    /**
     * Returns the number of items to display.
     * @return The number of items in the list of tasks
     */
    @Override
    public int getItemCount() {
        if (mTaskEntries == null) {
            return 0;
        }
        return mTaskEntries.size();
    }

    /**
     * Returns the list of tasks
     * @return The list of tasks
     */
    public List<Task> getTasks() {
        return mTaskEntries;
    }

    /**
     * Inner class for creating ViewHolders
     */
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
                    showDeleteConfirmationDialog();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Call the removeItem method in the adapter to delete the item
                        //showEditDialog(view.getContext(), position);
                    }
                }
            });

            view = itemView;
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

    /**
     * Removes the item at the specified position from the list of tasks.
     * @param position The position of the item to remove
     */
    public void removeItem(int position) {
        Task task = mTaskEntries.get(position);
        if(task.getClass() == Assignment.class) {
            assignmentList.remove((Assignment) task);
            Assignment.saveData(assignmentList, mContext);
        } else if(task.getClass() == Exam.class){
            examList.remove((Exam) task);
            Exam.saveData(examList, mContext);
        }
        mTaskEntries.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }
}