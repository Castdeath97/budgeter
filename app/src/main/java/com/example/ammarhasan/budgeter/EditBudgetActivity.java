package com.example.ammarhasan.budgeter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditBudgetActivity extends AppCompatActivity {

    // find all buttons and fields
    private EditText mAllocatedText;
    private EditText mBudgetNameText;

    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private Budget budget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_budget);

        // find all buttons and fields
        mAllocatedText = findViewById(R.id.text_number_allocated);
        mBudgetNameText = findViewById(R.id.text_budget_name);

        // get budget name from extras from previous activity
        Bundle extras = getIntent().getExtras();
        final String budgetName;
        budgetName = extras.getString("budgetName");
        mBudgetNameText.setText(budgetName);

        // Read database to fill fields

        mFirebaseAuth = FirebaseAuth.getInstance();

        // get current user
        final FirebaseUser user = mFirebaseAuth.getCurrentUser();

        // get firebase database reference and Authentication object
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        // Attach a listener to read the data (user info)
        mDatabaseReference.child(user.getUid()).addListenerForSingleValueEvent(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        // get user info and update
                        User userInfo = dataSnapshot.getValue(User.class);

                        // find budget
                        budget = userInfo.findBudget(budgetName);


                        // find the budget allocated amount and set
                        mAllocatedText.setText(Double.toString(budget.getAllocated()));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(EditBudgetActivity.this,
                                getResources().getString(R.string.db_error_text)
                                        + databaseError.getCode(),
                                Toast.LENGTH_LONG).show();
                    }
                });


    }

    /**
     * This method is executed when button_delete_budget
     * is clicked to delete a user budget
     * @param view the view provided by the button
     */
    public void deleteBudget(View view) {

        // used get current user
        final FirebaseUser user = mFirebaseAuth.getCurrentUser();

        // Attach a listener to read the data (user info)
        mDatabaseReference.child(user.getUid()).addListenerForSingleValueEvent(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        // get user info and update
                        User userInfo = dataSnapshot.getValue(User.class);

                        // remove seletcted budget
                        userInfo.removeBudget(budget);

                        // update value in database using user id if no error occured
                        mDatabaseReference.child(user.getUid()).setValue(userInfo);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(EditBudgetActivity.this,
                                getResources().getString(R.string.db_error_text)
                                        + databaseError.getCode(),
                                Toast.LENGTH_LONG).show();
                    }
                });
        // return back to budget fragment in the navigation activity
        finish();
    }

    /**
     * This method is executed when button_reset_budget
     * is clicked to reset user budget
     * @param view the view provided by the button
     */
    public void resetBudget(View view) {

        // used get current user
        final FirebaseUser user = mFirebaseAuth.getCurrentUser();

        // Attach a listener to read the data (user info)
        mDatabaseReference.child(user.getUid()).addListenerForSingleValueEvent(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // get user info and update
                        User userInfo = dataSnapshot.getValue(User.class);

                        // make sure to report any errors
                        try{
                            userInfo.resetBudget(budget.getName());

                            // check if current budget setup meets target, if not alert user
                            if((userInfo.getBankAmount() - userInfo.getProjectedSpend()) <
                                    userInfo.getTargetSaving()){
                                Toast.makeText(EditBudgetActivity.this,
                                        getResources().getString(R.string.saving_target_warn),
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (IllegalArgumentException e){
                            Toast.makeText(EditBudgetActivity.this, e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                            return;
                        }

                        // update value in database using user id if no error occured
                        mDatabaseReference.child(user.getUid()).setValue(userInfo);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(EditBudgetActivity.this,
                                getResources().getString(R.string.db_error_text)
                                        + databaseError.getCode(),
                                Toast.LENGTH_LONG).show();
                    }
                });
        // return back to budget fragment in the navigation activity
        finish();
    }


    /**
     * This method is executed when button_edit_budget
     * is clicked to edit user budget
     * @param view the view provided by the button
     */
    public void editBudget(View view) {

        // If either fields are empty, alert user and return
        if(mAllocatedText.getText().toString().trim().isEmpty()
                || mBudgetNameText.getText().toString().trim().isEmpty()){
            Toast.makeText(EditBudgetActivity.this,
                    getResources().getString(R.string.empty_field), Toast.LENGTH_LONG).show();
            return;
        }

        final double newAllocatedAmount = Double.parseDouble(mAllocatedText.getText().toString());

        // get budget name

        final String newBudgetName = mBudgetNameText.getText().toString();

        // used get current user
        final FirebaseUser user = mFirebaseAuth.getCurrentUser();

        // Attach a listener to read the data (user info)
        mDatabaseReference.child(user.getUid()).addListenerForSingleValueEvent(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        // get user info and update
                        User userInfo = dataSnapshot.getValue(User.class);

                        // make sure to catch and report any errors
                        try{
                            userInfo.updateBudget(budget.getName()
                                    ,newBudgetName, newAllocatedAmount);

                            // check if current budget setup meets target, if not alert user
                            if((userInfo.getBankAmount() - userInfo.getProjectedSpend()) <
                                    userInfo.getTargetSaving()){
                                Toast.makeText(EditBudgetActivity.this,
                                        getResources().getString(R.string.saving_target_warn),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (IllegalArgumentException e){
                            Toast.makeText(EditBudgetActivity.this, e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                            return;
                        }

                        // update value in database using user id if no error occured
                        mDatabaseReference.child(user.getUid()).setValue(userInfo);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(EditBudgetActivity.this,
                                getResources().getString(R.string.db_error_text)
                                        + databaseError.getCode(),
                                Toast.LENGTH_LONG).show();
                    }
                });
        // return back to budget fragment in the navigation activity
        finish();
    }

}
