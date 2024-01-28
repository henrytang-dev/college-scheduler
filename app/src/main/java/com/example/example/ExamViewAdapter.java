package com.example.example;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.example.schedule_fragments.ExamsFragment;

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
        holder.mDateTime.setText(mData.get(position).getDate() + " " + mData.get(position).getTime());

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
                        //showEditDialog(view.getContext(), position, mData);
                    }
                }
            });


        }
    }

}
