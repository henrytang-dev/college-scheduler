package com.example.example;



import static java.security.AccessController.getContext;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.example.schedule_fragments.AssignmentsFragment;

import java.text.ParseException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;
/**
 * This class represents the RecyclerView adapter for managing the display of assignments in the application.
 * It handles addition, editing, and sorting of assignments and utilizes a custom ViewHolder for each item.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<Assignment> mData;
    private LayoutInflater mInflater;
    private AssignmentsFragment fragment;
    private ColorMapper colorMapper;

    /**
     * Constructor for the MyRecyclerViewAdapter.
     *
     * @param context   The context of the application.
     * @param data      The list of Assignment objects to be displayed.
     * @param fragment  The associated AssignmentsFragment.
     */
    public MyRecyclerViewAdapter(Context context, List<Assignment> data, AssignmentsFragment fragment) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.fragment = fragment;
        this.colorMapper = new ColorMapper(context);
    }

    /**
     * Called when RecyclerView needs a new ViewHolder instance.
     *
     * @param parent   The ViewGroup into which the new View will be added.
     * @param viewType The type of the new View.
     * @return A new ViewHolder that holds a View representing an assignment item.
     */
    @NonNull
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recylerview_assignment, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder   The ViewHolder to bind the data to.
     * @param position The position of the data in the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mClassName.setText(mData.get(position).getClassName());
        holder.mTitle.setText(mData.get(position).getAssignmentTitle());
        holder.mDate.setText(mData.get(position).getDueDate());

        String color = mData.get(position).getColor();
        holder.mCardView.setCardBackgroundColor(colorMapper.getColorResourceId(color));
    }

    /**
     * Sorts the list of assignments by due date and notifies the fragment to save the updated data.
     */
    public void sortDate() {
        Collections.sort(mData, new Comparator<Assignment>() {
            @Override
            public int compare(Assignment a1, Assignment a2) {
                try {
                    Date a = a1.convertDueDate();
                    Date b = a2.convertDueDate();
                    return a.compareTo(b);
                } catch (ParseException e) {
                    Toast.makeText(mInflater.getContext(), "Date threw error", Toast.LENGTH_LONG).show();
                }
                return 0;
            }
        });

        notifyDataSetChanged();
        fragment.saveData(mData);
    }

    /**
     * Sorts the list of assignments by class name and notifies the fragment to save the updated data.
     */
    public void sortClasses() {
        Collections.sort(mData, new Comparator<Assignment>() {
            @Override
            public int compare(Assignment a1, Assignment a2) {
                return a1.getClassName().compareTo(a2.getClassName());
            }
        });

        notifyDataSetChanged();
        fragment.saveData(mData);
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
     * Shows an edit dialog for modifying an existing assignment at the specified position.
     *
     * @param context  The context in which the dialog will be displayed.
     * @param position The position of the assignment to be edited.
     * @param mData    The list of assignments.
     */

    public void showEditDialog(Context context, int position, List<Assignment> mData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.create_assignment_dialog, null);
        builder.setView(dialogView);

        // Find and set up UI components in the dialog
        EditText classNameText = dialogView.findViewById(R.id.classNameText);
        EditText titleText = dialogView.findViewById(R.id.titleText);
        DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
        Spinner spinner = dialogView.findViewById(R.id.colorSpinner);

        // Get the existing Assignment object at the given position
        Assignment existingAssignment = mData.get(position);

        // Set the existing values in the EditText fields
        classNameText.setText(existingAssignment.getClassName());
        titleText.setText(existingAssignment.getAssignmentTitle());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.mInflater.getContext(), R.array.color_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        int spinnerPosition = adapter.getPosition(existingAssignment.getColor());
        spinner.setSelection(spinnerPosition);

        String[] dateComponents = existingAssignment.getDueDate().split("/");
        datePicker.updateDate(Integer.parseInt(dateComponents[2]), Integer.parseInt(dateComponents[0])-1 ,Integer.parseInt(dateComponents[1]));

        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String newClassName = classNameText.getText().toString();
                String newTitle = titleText.getText().toString();

                String day = String.valueOf(datePicker.getDayOfMonth());
                String month = String.valueOf(datePicker.getMonth()+1);
                String year = String.valueOf(datePicker.getYear());
                String color = spinner.getSelectedItem().toString();

                String date = month + "/" + day + "/" + year;

                existingAssignment.setAssignmentTitle(newTitle);
                existingAssignment.setClassName(newClassName);
                existingAssignment.setDueDate(date);
                existingAssignment.setColor(color);

                notifyItemChanged(position);
                fragment.saveData(mData);

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

    /**
     * The ViewHolder class represents each item in the RecyclerView.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mClassName;
        TextView mTitle;
        TextView mDate;
        CardView mCardView;
        ImageButton mCheckBtn;
        ImageButton mEditBtn;

        /**
         * Constructor for the ViewHolder class.
         *
         * @param itemView The View object representing an assignment item.
         */
        public ViewHolder(View itemView) {
            super(itemView);
            mClassName = itemView.findViewById(R.id.className);
            mTitle = itemView.findViewById(R.id.assignmentTitle);
            mDate = itemView.findViewById(R.id.dueDate);
            mCardView = itemView.findViewById(R.id.cardView);
            mCheckBtn = itemView.findViewById(R.id.checkBtn);
            mEditBtn = itemView.findViewById(R.id.editBtn);

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
