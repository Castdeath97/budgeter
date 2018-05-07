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

/**
 * @author Ammar Hasan 150454388 April 2018
 * Class Purpose: This class contains the NewBudget
 * activity functionality, handles addition of budgets
 */
public class NewBudgetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_budget);
    }

    /**
     * This method is executed when button_save_budget
     * is clicked to save user budget
     * @param view the view provided by the button
     */
    public void saveBudget(View view) {

        // get firebase database reference and Authentication object
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();

        // find all buttons and fields
        EditText mAllocatedText = findViewById(R.id.text_number_allocated);
        EditText mBudgetNameText = findViewById(R.id.text_budget_name);

        // If either fields are empty, alert user and returb
        if(mAllocatedText.getText().toString().trim().isEmpty()
                || mBudgetNameText.getText().toString().trim().isEmpty()){
            Toast.makeText(NewBudgetActivity.this,
                    getResources().getString(R.string.empty_field), Toast.LENGTH_LONG).show();
            return;
        }

        final double allocatedAmount = Double.parseDouble(mAllocatedText.getText().toString());

        // get budget name

        final String budgetName = mBudgetNameText.getText().toString();

        // used get current user
        final FirebaseUser user = mFirebaseAuth.getCurrentUser();;

        // Attach a listener to read the data (user info)
        databaseReference.child(user.getUid()).addListenerForSingleValueEvent(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        // get user info and update
                        User userInfo = dataSnapshot.getValue(User.class);

                        // add budget, report any errors

                        try{
                            userInfo.addBudget(new Budget(budgetName, allocatedAmount));
                        }
                        catch (IllegalArgumentException e){
                            Toast.makeText(NewBudgetActivity.this, e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                            return;
                        }

                        // update value in database using user id
                        databaseReference.child(user.getUid()).setValue(userInfo);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(NewBudgetActivity.this,
                                getResources().getString(R.string.db_error_text)
                                        + databaseError.getCode(),
                                Toast.LENGTH_LONG).show();
                    }
                });

        // return back to budget fragment in the navigation activity
        finish();
    }
}
