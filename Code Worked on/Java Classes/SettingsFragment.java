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
import android.widget.Button;
import android.widget.EditText;
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
 * Class Purpose: This class contains the Settings page
 * fragment of the navigation activity funtionality
 */
public class SettingsFragment extends Fragment {

    private Activity mActivity;

    // used to keep activity to avoid callback issues
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }


    @Nullable
    @Override   // connects to layout file
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_settings, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // use to do activity stuff, use view to find stuff and use getActivity to get context

        // find fields
        final EditText lowBankValue = view.findViewById(R.id.text_low_bank_warn_value);
        final EditText savingTargetValue = view.findViewById(R.id.text_saving_target_value);

        // get firebase database reference and Authentication object
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        // get current user
        final FirebaseUser user = firebaseAuth.getCurrentUser();

        // Attach a listener to read the data (user info), read database to set current values
        databaseReference.child(user.getUid()).addValueEventListener (
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // get user info and update
                        User userInfo = dataSnapshot.getValue(User.class);

                        // set current values to fields
                        lowBankValue.setText(Double.toString(userInfo.getLowBankWarning()));
                        savingTargetValue.setText(Double.toString(userInfo.getTargetSaving()));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(mActivity,
                                getResources().getString(R.string.db_error_text)
                                        + databaseError.getCode(),
                                Toast.LENGTH_LONG).show();
                    }
                });

        // attach listener to button
        Button saveBtn = view.findViewById(R.id.button_save_settings);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Attach a listener to read the data (user info),
                // read database to set current values
                databaseReference.child(user.getUid()).addValueEventListener (
                        new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                // get user info and update
                                User userInfo = dataSnapshot.getValue(User.class);

                                // update fields
                                userInfo.setLowBankWarning(
                                        Double.parseDouble(lowBankValue.getText().toString()));
                                userInfo.setTargetSaving(
                                        Double.parseDouble(savingTargetValue.getText().toString()));

                                // update value in database using user id
                                databaseReference.child(user.getUid()).setValue(userInfo);
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
        });
    }
}
