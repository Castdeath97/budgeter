package com.example.ammarhasan.budgeter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * @author Ammar Hasan 150454388 May 2018
 * Class Purpose: This class displays a given Article page
 */
public class ArticleActivity extends AppCompatActivity {

    // constants
    private static final String EXTRA = "articleId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        // find all fields
        final TextView authorText = findViewById(R.id.text_author);
        final TextView titleText = findViewById(R.id.text_title);
        final TextView articleText = findViewById(R.id.text_article_content);

        // get article object through article id provided by previous activity

        Bundle extras = getIntent().getExtras();
        final int articleId = Integer.parseInt(extras.getString(EXTRA));

        // look in database for article

        // get firebase database reference and Authentication object
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference articleManagerRef = databaseReference.child("ArticleManager");

        // Attach a listener to read the data (user info)
        articleManagerRef.addListenerForSingleValueEvent(
                new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        // get articles to update article list
                        ArticleManager articles = dataSnapshot.getValue(ArticleManager.class);
                        Article article = articles.findArticle(articleId);

                        titleText.setText(getResources().
                                getString(R.string.article_list_title, article.getTitle()));
                        authorText.setText(getResources()
                                .getString(R.string.article_list_author, article.getAuthor()));
                        articleText.setText(article.getPost());

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(ArticleActivity.this,
                                getResources().getString(R.string.db_error_text)
                                        + databaseError.getCode(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}
