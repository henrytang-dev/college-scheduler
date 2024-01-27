package com.example.example.schedule_fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.example.Assignment;
import com.example.example.MyRecyclerViewAdapter;
import com.example.example.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class AssignmentsFragment extends Fragment {

    RecyclerView recyclerView;

    MyRecyclerViewAdapter myRecyclerViewAdapter;
    List<Assignment> assignmentList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_assignments, container, false);

        recyclerView = root.findViewById(R.id.assignment_list_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        Assignment mathHw = new Assignment("Linear Algebra", "Webwork", "5/6/24");
        assignmentList.add(mathHw);
        
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(getContext(), assignmentList);
        recyclerView.setAdapter(myRecyclerViewAdapter);

        FloatingActionButton fab = root.findViewById(R.id.fab_add);
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
        View dialogView = inflater.inflate(R.layout.create_assignment_dialog, null);
        EditText classNameText = dialogView.findViewById(R.id.classNameText);
        EditText assignmentTitleText = dialogView.findViewById(R.id.titleText);
        EditText dueDateText = dialogView.findViewById(R.id.dateText);

        // Set the inflated view to the builder
        builder.setView(dialogView);

        builder.setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String className = classNameText.getText().toString();
                String title = assignmentTitleText.getText().toString();
                String dueDate = dueDateText.getText().toString();

                Assignment hw = new Assignment(className, title, dueDate);
                assignmentList.add(hw);

                myRecyclerViewAdapter = new MyRecyclerViewAdapter(getContext(), assignmentList);
                recyclerView.setAdapter(myRecyclerViewAdapter);

            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        // Customize the dialog as needed, e.g., set title, positive/negative buttons, etc.

        // Create and show the dialog
        Dialog dialog = builder.create();
        dialog.show();
    }


}