package com.example.muzammilnawaz.flickrbrowser;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class GetFlickrJsonData extends AsyncTask<String,Void,List<Photo>>implements GetRawData.OnDownloadComplete {

    private static final String TAG = "GetFlickrJsonData";
    private List<Photo> mPhotoList = null;
    private String mBaseUrl;
    private String mLanguage;
    private boolean mMatchAll,runningonsamethread = false;

    private final OnDataAvailable mCallback;

    interface OnDataAvailable {
        void onDataAvailable(List<Photo> data, DownloadStatus status);
    }

    public GetFlickrJsonData( String baseUrl, String language, boolean matchAll, OnDataAvailable callback) {

        mBaseUrl = baseUrl;
        mLanguage = language;
        mMatchAll = matchAll;
        mCallback = callback;
    }



    @Override
    protected void onPostExecute(List<Photo> photos) {

        if(mCallback!=null){
            mCallback.onDataAvailable(mPhotoList, DownloadStatus.OK);}
        super.onPostExecute(photos);
    }

    @Override
    protected List<Photo> doInBackground(String... params) {

        String destinationUri = createUri(params[0],mLanguage,mMatchAll);

        GetRawData getRawData = new GetRawData(this);
        getRawData.runInSameThread(destinationUri);
        return null;
    }

    void executeOnSameThread(String searchCriteria) {
        runningonsamethread = true;
        Log.d(TAG, "executeOnSameThread: starts");
        String destinationUri = createUri(searchCriteria, mLanguage, mMatchAll);
        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(destinationUri);
        Log.d(TAG, "executeOnSameThread: ends");

    }

    String createUri(String searchCriteria, String Language, boolean MatchAll) {
        Log.d(TAG, "createUri: starts");
        return Uri.parse(mBaseUrl).buildUpon().appendQueryParameter("tags", searchCriteria).appendQueryParameter("tagmode", MatchAll ? "ALL" : "ANY").
                appendQueryParameter("lang", Language).appendQueryParameter("format", "json").appendQueryParameter("nojsoncallback", "1").build().toString();
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "onDownloadComplete: starts");

        if (status == DownloadStatus.OK) {
            mPhotoList = new ArrayList<>();
            try {
                JSONObject jsonData = new JSONObject(data);
                JSONArray itemsArray = jsonData.getJSONArray("items");

                for (int i = 0; i < itemsArray.length(); i++) {
                    JSONObject jsonPhoto = itemsArray.getJSONObject(i);
                    String title = jsonPhoto.getString("title");
                    String author = jsonPhoto.getString("author");
                    String authorId = jsonPhoto.getString("author_id");
                    String tags = jsonPhoto.getString("tags");

                    JSONObject jsonMedia = jsonPhoto.getJSONObject("media");
                    String photoUrl = jsonMedia.getString("m");

                    String link = photoUrl.replaceFirst("_m", "_b");

                    Photo photoObject = new Photo(title, author, authorId, link, tags, photoUrl);
                    mPhotoList.add(photoObject);
                    Log.d(TAG, "onDownloadComplete: " + photoObject.toString());
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, "onDownloadComplete: " + e.getMessage());
                status = DownloadStatus.FAILED_OR_EMPTY;
            }

            if(runningonsamethread  && mCallback!=null)
            {
                mCallback.onDataAvailable(mPhotoList,status);
            }
        }
        Log.d(TAG, "onDownloadComplete: ends");
    }
}
