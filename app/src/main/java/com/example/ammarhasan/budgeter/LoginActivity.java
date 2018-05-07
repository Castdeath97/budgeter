package com.example.ammarhasan.budgeter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * @author Ammar Hasan 150454388 April 2018
 * Class Purpose: This class contains the user login
 * activity functionality, handles logins and creation of user profiles
 */
public class LoginActivity extends AppCompatActivity {

    // fields and buttons
    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mLoginBtn;

    // Firebase auth object and listener
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mFirebaseAutListner;

    // reference to interact with firebase d
    private DatabaseReference databaseReference;

    @Override
    protected void onStart(){
        super.onStart();
        // auth state listener linked to auth obj
        mFirebaseAuth.addAuthStateListener(mFirebaseAutListner);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // get fields
        mEmailField = findViewById(R.id.text_email);
        mPasswordField = findViewById(R.id.text_password);
        mLoginBtn = findViewById(R.id.button_login);

        // get Firebase authenticator instance
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuth.signOut(); // clear token

        mFirebaseAutListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // on state change check if a user exists
                if(firebaseAuth.getCurrentUser() != null){
                    // go to home screen via intent
                    startActivity(
                            new Intent(LoginActivity.this, NavigationActivity.class));
                }
            }
        };

        // add listener to log in button
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                signIn(); // sign in via method
            }
        });

        // get firebase database reference and Authentication object
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    // signing in method called by button
    private void signIn(){
        // get text and email strings from fields
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) { // is pass/user empty?
            // if so alert user via toast
            Toast.makeText(LoginActivity.this, "A field is empty",
                    Toast.LENGTH_LONG).show();
        }

        else { // else sign in via auth
            mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // alert user on complete if combination is wrong
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Wrong combination",
                                        Toast.LENGTH_LONG).show();
                            }

                            else {
                                // else check if a profile exists for the user, if not create one

                                // get current user
                                final FirebaseUser user = mFirebaseAuth.getCurrentUser();

                                // Attach a listener to read the data
                                databaseReference.child(user.getUid())
                                        .addListenerForSingleValueEvent(
                                new ValueEventListener() {

                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        User userInfo = dataSnapshot.getValue(User.class);

                                        // null make a new one
                                        if (userInfo == null)
                                        {
                                            userInfo = new User();

                                            // update database using user id
                                            databaseReference.child
                                                    (user.getUid()).setValue(userInfo);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Toast.makeText(LoginActivity.this,
                                                getResources().getString(R.string.db_error_text)
                                                        + databaseError.getCode(),
                                                Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    });
        }
    }
}
