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

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<Assignment> mData;
    private LayoutInflater mInflater;
    private AssignmentsFragment fragment;



    public MyRecyclerViewAdapter(Context context, List<Assignment> data, AssignmentsFragment fragment) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.fragment = fragment;
    }
    @NonNull
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recylerview_assignment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mClassName.setText(mData.get(position).getClassName());
        holder.mTitle.setText(mData.get(position).getAssignmentTitle());
        holder.mDate.setText(mData.get(position).getDueDate());

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

    public void sortDate() {
        Collections.sort(mData, new Comparator<Assignment>() {
            @Override
            public int compare(Assignment assignment, Assignment t1) {
                try {
                    Date a = assignment.convertDueDate();
                    Date b = t1.convertDueDate();
                    return a.compareTo(b);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        notifyDataSetChanged();
        fragment.saveData(mData);
    }
    public void sortClasses() {
        Collections.sort(mData, new Comparator<Assignment>() {
            @Override
            public int compare(Assignment assignment, Assignment t1) {
                return assignment.getClassName().compareTo(t1.getClassName());
            }
        });

        notifyDataSetChanged();
        fragment.saveData(mData);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void removeItem(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

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


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mClassName;
        TextView mTitle;
        TextView mDate;
        CardView mCardView;
        ImageButton mCheckBtn;
        ImageButton mEditBtn;

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
