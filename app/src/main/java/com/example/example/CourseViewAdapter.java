package com.example.example;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.example.schedule_fragments.ExamsFragment;
import com.example.example.ui.home.HomeFragment;

import org.w3c.dom.Text;

import java.util.List;

public class CourseViewAdapter extends RecyclerView.Adapter<CourseViewAdapter.ViewHolder> {
    private List<Course> mData;
    private LayoutInflater mInflater;
    private HomeFragment fragment;

    private Context mContext;

    public CourseViewAdapter(HomeFragment fragment, List<Course> data, Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(fragment.getContext());
        this.mData = data;
        this.fragment = fragment;
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
        holder.mDaysOfWeek.setText(mData.get(position).getCourseDays().toString());
        String startTime = mData.get(position).getCourseStartTime();
        String endTime = mData.get(position).getCourseEndTime();
        String formattedTime = mContext.getString(R.string.class_time_format, startTime, endTime);
        holder.mClassTime.setText(formattedTime);
        holder.mCourseInstructor.setText(mData.get(position).getCourseInstructor());
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
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Call the removeItem method in the adapter to delete the item
//                        removeItem(position);
                    }
                }
            });

            mEditBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Call the showEditDialog method in the adapter to open the edit dialog
//                        showEditDialog(view.getContext(), position, mData);
                    }
                }
            });
        }

    }

}
