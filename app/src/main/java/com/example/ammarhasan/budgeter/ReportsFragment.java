package com.example.ammarhasan.budgeter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * @author Ammar Hasan 150454388 April 2018
 * Class Purpose: This class contains the Reports page
 * fragment of the navigation activity funtionality
 */
public class ReportsFragment extends Fragment {

    @Nullable
    @Override   // connects to layout file
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_reports, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // use to do activity stuff, use view to find stuff and use getActivity to get context
    }
}
