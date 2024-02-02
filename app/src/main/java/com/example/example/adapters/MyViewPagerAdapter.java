package com.example.example.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.example.schedule_fragments.AssignmentsFragment;
import com.example.example.schedule_fragments.ExamsFragment;
import com.example.example.ui.schedule.ScheduleFragment;

/**
 * This class represents a ViewPager2 adapter responsible for handling the fragments displayed in a ViewPager.
 * It provides the appropriate fragment based on the selected position.
 */
public class MyViewPagerAdapter extends FragmentStateAdapter {

    /**
     * Constructor for the MyViewPagerAdapter.
     *
     * @param fragmentActivity The parent Fragment that will host the ViewPager.
     */
    public MyViewPagerAdapter(@NonNull ScheduleFragment fragmentActivity) {
        super(fragmentActivity);
    }

    /**
     * Called to instantiate the fragment for the given position.
     *
     * @param position The position of the fragment in the ViewPager.
     * @return A new Fragment instance corresponding to the specified position.
     */
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

    /**
     * Returns the total number of fragments to be displayed in the ViewPager.
     *
     * @return The total number of fragments.
     */
    @Override
    public int getItemCount() {
        return 2;
    }
}
