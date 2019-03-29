package com.example.muzammilnawaz.flickrbrowser;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

enum DownloadStatus { IDLE,PROCESSING,NOT_INITIALISED,FAILED_OR_EMPTY,OK}

class GetRawData extends AsyncTask<String,Void,String> {
    private static final String TAG = "com.example.muzammilnawaz.flickrbrowser.GetRawData";
     private DownloadStatus mDownloadStatus;
     private final OnDownloadComplete mCallBack;

     interface  OnDownloadComplete{
         void onDownloadComplete(String data,DownloadStatus status);
     }

    public GetRawData(OnDownloadComplete callback) {
        this.mDownloadStatus = mDownloadStatus;
        
        mCallBack = callback;
    }

    void runInSameThread(String s)
    {
        Log.d(TAG, "runInSameThread: starts");
        onPostExecute(doInBackground(s));
        Log.d(TAG, "runInSameThread: ends");
    }
    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "onPostExecute: Parameter is");
        if( mCallBack!=null)
        mCallBack.onDownloadComplete(s,mDownloadStatus);
        Log.d(TAG, "onPostExecute: ends");

    }

    @Override
    protected String doInBackground(String... strings) {

        if(strings[0]==null)
        {
            mDownloadStatus = DownloadStatus.NOT_INITIALISED;
            return null;
        }
        StringBuilder result = new StringBuilder();
        BufferedReader reader = null;
        HttpURLConnection connection = null ;
        try{
            mDownloadStatus = DownloadStatus.PROCESSING;
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int response = connection.getResponseCode();
            Log.d(TAG, "doInBackground: Response code is "+response);




            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            for(String line = reader.readLine();line!=null;line=reader.readLine())
            {
                     result.append(line).append("\n");
            }

                      mDownloadStatus = DownloadStatus.OK;
                      return result.toString();

        }catch(MalformedURLException e)
        {
            Log.d(TAG, "doInBackground: Malformed Url "+e.getMessage());
        }catch(IOException e)
        {
            Log.d(TAG, "doInBackground: IOexceptionerror "+e.getMessage());
        }finally{
            if(connection!=null)
            {
                connection.disconnect();
            }
            try
            {
                reader.close();
            }catch(IOException e)
            {
                Log.d(TAG, "doInBackground: Reader close Error"+e.getMessage());
            }
        }
        return null;
    }
}
