package com.example.example;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.example.schedule_fragments.AssignmentsFragment;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<Assignment> mData;
    private LayoutInflater mInflater;

    public MyRecyclerViewAdapter(Context context, List<Assignment> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
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
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mClassName;
        TextView mTitle;
        TextView mDate;

        public ViewHolder(View itemView) {
            super(itemView);
            mClassName = itemView.findViewById(R.id.className);
            mTitle = itemView.findViewById(R.id.assignmentTitle);
            mDate = itemView.findViewById(R.id.dueDate);
        }
    }
}
