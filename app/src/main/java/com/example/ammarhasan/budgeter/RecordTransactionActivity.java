package com.example.ammarhasan.budgeter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
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
 * Class Purpose: This class contains the user RecordTransactionActivity
 * activity functionality, handles addition of transcations
 */
public class RecordTransactionActivity extends AppCompatActivity {

    private EditText mAmountText;
    private FirebaseAuth mFirebaseAuth;
    private Button mRecordTransactionBtn;
    private Switch  mCreditDebitSwitch;
    private boolean credit;


    // reference to interact with firebase
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_transaction);

        // find all buttons and fields
        mRecordTransactionBtn = findViewById(R.id.button_record_transcation);
        Switch mCreditDebitSwitch = findViewById(R.id.switch_credt_debit);
        mAmountText = findViewById(R.id.text_number_amount);

        // get firebase database reference and Authentication object
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();

        credit = true; // set credit default

        // attach listener to switch
        mCreditDebitSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                       credit = false;
                } else {
                       credit = true;
                }
            }
        });

    }

    /**
     * This method is executed when button_record_transcation
     * is clicked to record user transaction
     * @param view the view provided by the button
     */
    public void recordTransaction(View view) {

        // get recorded amount
        final double amount = Double.parseDouble(mAmountText.getText().toString());

        // get current user
        final FirebaseUser user = mFirebaseAuth.getCurrentUser();

        // Attach a listener to read the data (user info)
        databaseReference.child(user.getUid()).addListenerForSingleValueEvent(
                new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // get user info and update
                User userInfo = dataSnapshot.getValue(User.class);

                // if user set it to credit add to amount, else subtract
                if (credit){
                    userInfo.bankAmount = userInfo.bankAmount + amount;

                } else{ // else substract
                    userInfo.bankAmount = userInfo.bankAmount - amount;
                }

                // update value in database using user id
                databaseReference.child(user.getUid()).setValue(userInfo);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(RecordTransactionActivity.this,
                        "The read failed: " + databaseError.getCode(),
                        Toast.LENGTH_LONG).show();
            }
        });

        // return back to home fragment in the navigation activity
        finish();
    }
}
