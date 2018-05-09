package com.example.ammarhasan.budgeter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
 * Class Purpose: This class contains the Budget page
 * fragment of the navigation activity funtionality, handles creation and
 * management of budgets
 */
public class BudgetFragment extends Fragment {

    // constants
    private static final int TEXT_SIZE = 17;
    private static final int TITLE_TEXT_SIZE = 19;
    private static final int MARGIN = 15;
    private static final int PADDING = 15;

    private Activity mActivity;
    private Context mContext;

    // used to keep activity and context to avoid callback issues
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        mContext = this.getContext();
    }

    @Override
    public void onResume(){
        super.onResume();

        // on resume update list of budgets

        // find linear layout to fill with budgets
        final LinearLayout budgetsLayout = getView().findViewById(R.id.linear_layout_budgets);

        // reset it
        budgetsLayout.removeAllViews();

        // Read database for budgets

        // get firebase database reference and Authentication object
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();

        // get current user
        final FirebaseUser user = mFirebaseAuth.getCurrentUser();

        // Attach a listener to read the data (user info)
        databaseReference.child(user.getUid()).addListenerForSingleValueEvent (
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // get user info and update
                        User userInfo = dataSnapshot.getValue(User.class);

                        // Add a new clickable linear layout for each budget with info
                        for (final Budget b : userInfo.getBudgets()) {

                            // create a new linear layout with attributes
                            LinearLayout budgetLayout = new LinearLayout(mActivity);

                            // to set margins
                            LinearLayout.LayoutParams budgetLayoutParams =
                                    new LinearLayout.LayoutParams
                                            (LinearLayout.LayoutParams.MATCH_PARENT,
                                                    LinearLayout.LayoutParams.WRAP_CONTENT);
                            budgetLayoutParams.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);
                            budgetLayout.setLayoutParams(budgetLayoutParams);

                            // other attributes
                            budgetLayout.setBackgroundColor
                                    (mContext.getResources().getColor(R.color.colorPrimaryDark));
                            budgetLayout.setOrientation(LinearLayout.VERTICAL);
                            budgetLayout.setPadding(PADDING, PADDING, PADDING, PADDING);
                            budgetLayout.setClickable(true);
                            budgetLayout.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View view) {
                                    // change activity when Layout is clicked
                                    // (use getActivity since this a fragment)
                                    Intent intent = new Intent(mActivity,
                                            EditBudgetActivity.class);

                                    // pass budget name to intent
                                    intent.putExtra("budgetName", b.getName());
                                    startActivity(intent);
                                }
                            });


                            // Add budget name, allocated and remaining to layout
                            TextView budgetNameTextView = new TextView(mActivity);
                            budgetNameTextView.setText(mContext.getResources().
                                    getString(R.string.budget_list_name, b.getName()));
                            budgetNameTextView.setTextSize(TITLE_TEXT_SIZE);
                            budgetNameTextView.setTextColor
                                    (ContextCompat.getColor(mContext, R.color.colorAccent));
                            budgetLayout.addView(budgetNameTextView);


                            TextView budgetAllocatedTextView = new TextView(mActivity);
                            budgetAllocatedTextView.setText(mContext.getResources().getString(
                                    R.string.budget_list_allocated, b.getAllocated()));
                            budgetAllocatedTextView.setTextSize(TEXT_SIZE);
                            budgetAllocatedTextView.setTextColor
                                    (ContextCompat.getColor(mContext, R.color.colorAccent));
                            budgetLayout.addView(budgetAllocatedTextView);


                            TextView budgetRemainingTextView = new TextView(mActivity);
                            budgetRemainingTextView.setText(mContext.getResources().getString(
                                    R.string.budget_list_remaining, b.getRemaining()));
                            budgetRemainingTextView.setTextSize(TEXT_SIZE);
                            budgetRemainingTextView.setTextColor
                                    (ContextCompat.getColor(mContext, R.color.colorAccent));
                            budgetLayout.addView(budgetRemainingTextView);

                            // add layout to main layout
                            budgetsLayout.addView(budgetLayout);
                        }
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
        return  inflater.inflate(R.layout.fragment_budget, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // used to do activity stuff, use view to find stuff and use getActivity to get context
        Button mNewBudgetBtn = getView().findViewById(R.id.button_new_budget);

        // add listener to new budget button
        mNewBudgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newBudget(); // call new budget method to switch activity
            }
        });

    }

    /**
     * This method opens the add budget activity
     * (button_new_budget)
     */
    public void newBudget() {
        // change activity when button is clicked (use getActivity since this a fragment)
        startActivity(new Intent(mActivity, NewBudgetActivity.class));
    }
}
