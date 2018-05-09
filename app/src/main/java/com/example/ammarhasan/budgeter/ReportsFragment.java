package com.example.ammarhasan.budgeter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * @author Ammar Hasan 150454388 April 2018
 * Class Purpose: This class contains the Reports page
 * fragment of the navigation activity funtionality
 */
public class ReportsFragment extends Fragment {

    private Activity mActivity;

    // used to keep activity to avoid callback issues
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onResume(){
        super.onResume();

        // on resume update fields

        // find fields
        final TextView bankValue = getView().findViewById(R.id.text_bank_value);
        final TextView daySpendValue = getView().findViewById(R.id.text_today_spend_value);
        final TextView projectedSpendValue = getView().findViewById(R.id.text_projected_spend_value);
        final TextView projectedSaveValue = getView().findViewById(R.id.text_projected_save_value);
        final TextView monthSpendValue = getView().findViewById(R.id.text_month_spend_value);


        // get firebase database reference and Authentication object
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();

        // get current user
        final FirebaseUser user = mFirebaseAuth.getCurrentUser();

        // Attach a listener to read the data (user info)
        databaseReference.child(user.getUid()).addValueEventListener (
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // get user info and update
                        User userInfo = dataSnapshot.getValue(User.class);

                        // update fields
                        bankValue.setText(Double.toString(userInfo.getBankAmount()));
                        projectedSpendValue.setText(Double.toString(userInfo.getProjectedSpend()));
                        daySpendValue.setText(Double.toString(userInfo.getDaySpend()));
                        monthSpendValue.setText(Double.toString(userInfo.getMonthSpend()));

                        // find save
                        projectedSaveValue.setText(Double.toString
                                (userInfo.getBankAmount() - userInfo.getProjectedSpend()));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(mActivity,
                                getResources().getString(R.string.db_error_text)
                                        + databaseError.getCode(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

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
