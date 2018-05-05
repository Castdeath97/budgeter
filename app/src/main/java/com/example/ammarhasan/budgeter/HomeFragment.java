package com.example.ammarhasan.budgeter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * @author Ammar Hasan 150454388 April 2018
 * Class Purpose: This class contains the Home page
 * fragment of the navigation activity funtionality
 */
public class HomeFragment extends Fragment {

    private Button mAddTransBtn;

    @Nullable
    @Override   // connects to layout file
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // used to do activity stuff, use view to find stuff and use getActivity to get context

        mAddTransBtn = view.findViewById(R.id.button_add_transaction);

        // add listener to add transaction button
        mAddTransBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTransaction(); // call add transaction method to switch activity
            }
        });
    }

    /**
     * This method opens the transaction activity
     * (linked with button_add_transaction)
     */
    public void addTransaction() {
        // change activity when button is clicked (use getActivity since this a fragment)
        startActivity(new Intent(getActivity(), RecordTransactionActivity.class));
    }
}
