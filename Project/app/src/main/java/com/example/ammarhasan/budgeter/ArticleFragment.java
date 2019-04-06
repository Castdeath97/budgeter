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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * @author Ammar Hasan 150454388 April 2018
 * Class Purpose: This class contains the Article page
 * fragment of the navigation activity funtionality, handles loading
 * of new articles using database
 */
public class ArticleFragment extends Fragment {

    private Activity mActivity;
    private Context mContext;

    // constants
    private static final int TEXT_SIZE = 17;
    private static final int TITLE_TEXT_SIZE = 19;
    private static final int MARGIN = 15;
    private static final int PADDING = 15;
    private static final String EXTRA = "articleId";

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

        // on resume update list of articles

        // find linear layout to fill with budgets
        final LinearLayout articlesLayout = getView().findViewById(R.id.linear_layout_articles);

        // reset it
        articlesLayout.removeAllViews();

        // Read database for articles

        // get firebase database reference and Authentication object
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference articleManagerRef = databaseReference.child("ArticleManager");

        // Attach a listener to read the data (user info)
        articleManagerRef.addListenerForSingleValueEvent (
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        // get articles to update article list
                        ArticleManager articles = dataSnapshot.getValue(ArticleManager.class);

                        // Add a new clickable linear layout for each article with info
                        if(articles.getArticles() != null) {
                            for (final Article a : articles.getArticles()) {

                                // create a new linear layout with attributes
                                LinearLayout articleLayout = new LinearLayout(mActivity);

                                // to set margins
                                LinearLayout.LayoutParams articleLayoutParams =
                                        new LinearLayout.LayoutParams
                                                (LinearLayout.LayoutParams.MATCH_PARENT,
                                                        LinearLayout.LayoutParams.WRAP_CONTENT);
                                articleLayoutParams.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);
                                articleLayout.setLayoutParams(articleLayoutParams);

                                // other attributes
                                articleLayout.setBackgroundColor
                                        (mContext.getResources().getColor(R.color.colorPrimaryDark));
                                articleLayout.setOrientation(LinearLayout.VERTICAL);
                                articleLayout.setPadding(PADDING, PADDING, PADDING, PADDING);
                                articleLayout.setClickable(true);
                                articleLayout.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View view) {
                                        // change activity when Layout is clicked
                                        // (use getActivity since this a fragment)
                                        Intent intent = new Intent(mActivity,
                                                ArticleActivity.class);

                                        // pass article id to intent
                                        intent.putExtra(EXTRA,
                                                Integer.toString(a.getArticleId()));
                                        startActivity(intent);
                                    }
                                });


                                // Add article title, author to layout

                                TextView articleTitleTextView = new TextView(mActivity);
                                articleTitleTextView.setText(mContext.getResources().
                                        getString(R.string.article_list_title, a.getTitle()));
                                articleTitleTextView.setTextSize(TITLE_TEXT_SIZE);
                                articleTitleTextView.setTextColor
                                        (ContextCompat.getColor(mContext, R.color.colorAccent));
                                articleLayout.addView(articleTitleTextView);


                                TextView articleAuthorTextView = new TextView(mActivity);
                                articleAuthorTextView.setText(mContext.getResources().getString(
                                        R.string.article_list_author, a.getAuthor()));
                                articleAuthorTextView.setTextSize(TEXT_SIZE);
                                articleAuthorTextView.setTextColor
                                        (ContextCompat.getColor(mContext, R.color.colorAccent));
                                articleLayout.addView(articleAuthorTextView);

                                // add layout to main layout
                                articlesLayout.addView(articleLayout);
                            }
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
        return  inflater.inflate(R.layout.fragment_article, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // use to do activity stuff, use view to find stuff and use getActivity to get context
    }
}
