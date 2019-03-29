package com.example.muzammilnawaz.flickrbrowser;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.GetChars;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements GetFlickrJsonData.OnDataAvailable,RecyclerItemClickListener.OnRecyclerItemClickListener {
    private static final String TAG = "MainActivity";


    private FlickrRecyclerViewAdapter mFlickrRecyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    activateToolbar(false);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFlickrRecyclerViewAdapter = new FlickrRecyclerViewAdapter(this,new ArrayList<Photo>());
         recyclerView.setAdapter(mFlickrRecyclerViewAdapter);

          recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,recyclerView,this));

      //  GetRawData getRawData = new GetRawData(this);
       // getRawData.execute("https://api.flickr.com/services/feeds/photos_public.gne?tags=thailand&format=json&nojsoncallback=1");


        Log.d(TAG, "onCreate: ends");
    }


    @Override
    protected void onResume() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String query_result = sharedPreferences.getString(FLICKR_QUERY,"quotes");
        if(query_result.length()>0) {
            GetFlickrJsonData getFlickrJsonData = new GetFlickrJsonData("https://api.flickr.com/services/feeds/photos_public.gne", "en-us", true, this);
            getFlickrJsonData.execute(query_result);
        }
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.d(TAG, "onCreateOptionsMenu() returned: " + true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent = new Intent(this,Developer.class);
            startActivity(intent);
            return true;
        }

        if(id == R.id.action_search)
        {
            Intent intent = new Intent(this,SearchActivity.class);
            startActivity(intent);
            return true;
        }
        Log.d(TAG, "onOptionsItemSelected() returned: returned"  );
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onDataAvailable(List<Photo> data, DownloadStatus status)
    {
        Log.d(TAG, "onDataAvailable: starts");
        if(status==DownloadStatus.OK)
        {


            mFlickrRecyclerViewAdapter.loadNewData(data);
            Log.d(TAG, "onDataAvailable: Data is"+data);
        }
        else
        {
            Log.d(TAG, "onDataAvailable: Status is "+ status);
        }
        Log.d(TAG, "onDataAvailable: ends");
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d(TAG, "onItemClick: starts");
        Toast.makeText(MainActivity.this,"Normal tap at position " +position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Log.d(TAG, "onItemLongClick: starts");
        //Toast.makeText(MainActivity.this, "Long tap on position "+position, Toast.LENGTH_LONG).show();
        Intent intent =new Intent(this,PhotoDetailActivity.class);
        intent.putExtra(PHOTO_TRANSFER,mFlickrRecyclerViewAdapter.getPhoto(position));
        startActivity(intent);

    }
}
