package com.example.muzammilnawaz.flickrbrowser;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class SearchActivity extends BaseActivity {
    private static final String TAG = "SearchActivity";

    private android.widget.SearchView mSearchView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.menu_search,menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (android.widget.SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        final SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());

        mSearchView.setSearchableInfo(searchableInfo);

        //Log.d(TAG, "onCreateOptionsMenu: "+getComponentName().toString());
        //Log.d(TAG, "onCreateOptionsMenu: hint is "+mSearchView.getQueryHint());
        //Log.d(TAG, "onCreateOptionsMenu:  searchable info is "+searchableInfo.toString());
        mSearchView.setIconified(false);
        Log.d(TAG, "onCreateOptionsMenu: returns");

        mSearchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Log.d(TAG, "onQueryTextSubmit: called");
                finish();
                mSearchView.clearFocus();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                sharedPreferences.edit().putString(FLICKR_QUERY,query).apply();


                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mSearchView.setOnCloseListener(new android.widget.SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                finish();
                mSearchView.clearFocus();
                return false;
            }
        });
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);



        activateToolbar(true);





        }


}
