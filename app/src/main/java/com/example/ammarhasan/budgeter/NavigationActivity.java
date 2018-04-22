package com.example.ammarhasan.budgeter;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class NavigationActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);


        // set created toolbar as appbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set menu button as up button
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);


        // find the drawer and the navigation views
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // replace content container with default import fragment
        FragmentManager fragmentManager =  getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, new HomeFragment());
        ft.commit();

        // add listener
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override  // if an item gets selected
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set the item as selected to persist highlight
                        menuItem.setChecked(true);

                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        int id = menuItem.getItemId(); // get id to compare
                        Fragment fragment = null;

                        // check id to switch to appropriate fragment
                        switch(id) {

                            case R.id.nav_about:
                                fragment = new AboutFragment();
                                break;

                            case R.id.nav_home:
                                fragment = new HomeFragment();
                                break;

                            case R.id.nav_reports:
                                fragment = new ReportsFragment();
                                break;

                            case R.id.nav_contacts:
                                fragment = new ContactsFragment();
                                break;

                            case R.id.nav_budget:
                                fragment = new BudgetFragment();
                                break;

                            case R.id.nav_articles:
                                fragment = new ArticleFragment();
                                break;
                        }

                        if(fragment != null){
                            // replace content container with fragment
                            FragmentManager fragmentManager =  getSupportFragmentManager();
                            FragmentTransaction ft = fragmentManager.beginTransaction();
                            ft.replace(R.id.content_frame, fragment);
                            ft.commit();
                        }
                        return true;
                    }
                });
    }


    @Override // override to allow menu btn to open drawer
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // if home btn is clicked open drawer
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START); // gravity for animation
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
