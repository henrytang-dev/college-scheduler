package com.example.example.ui.schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.example.adapters.MyViewPagerAdapter;
import com.example.example.databinding.FragmentGalleryBinding;
import com.google.android.material.tabs.TabLayout;

/**
 * This fragment displays a ViewPager2 with two tabs: one for assignments and one for exams.
 * It uses a TabLayout to display the tabs and a ViewPager2 to display the fragments.
 */
public class ScheduleFragment extends Fragment {

    private FragmentGalleryBinding binding;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    MyViewPagerAdapter myViewPagerAdapter;


    /**
     * Called to have the fragment instantiate its user interface view.
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ScheduleViewModel galleryViewModel =
                new ViewModelProvider(this).get(ScheduleViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        tabLayout = binding.tabLayout;
        viewPager = binding.viewPager;
        myViewPagerAdapter = new MyViewPagerAdapter(this);
        viewPager.setAdapter(myViewPagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

        return root;
    }


    /**
     * Called when the view previously created by onCreateView has been detached from the fragment.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
