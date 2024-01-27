package com.example.example;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.example.schedule_fragments.AssignmentsFragment;
import com.example.example.schedule_fragments.ExamsFragment;
import com.example.example.ui.gallery.GalleryFragment;

public class MyViewPagerAdapter extends FragmentStateAdapter {
    public MyViewPagerAdapter(@NonNull GalleryFragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new AssignmentsFragment();
            case 1:
                return new ExamsFragment();
            default:
                return new AssignmentsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
