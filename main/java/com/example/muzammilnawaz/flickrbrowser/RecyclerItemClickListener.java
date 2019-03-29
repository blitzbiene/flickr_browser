package com.example.muzammilnawaz.flickrbrowser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

class RecyclerItemClickListener extends RecyclerView.SimpleOnItemTouchListener {
    private static final String TAG = "RecyclerItemClickListen";


    interface OnRecyclerItemClickListener {
        void onItemClick(View view,int position);
        void onItemLongClick(View view , int position);
    }


    private final OnRecyclerItemClickListener mListener;
    private final GestureDetectorCompat mGestureDetector;

    public RecyclerItemClickListener(Context context,final RecyclerView recyclerView,OnRecyclerItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetectorCompat(context,new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.d(TAG, "onSingleTapUp: called");
                View childView = recyclerView.findChildViewUnder(e.getX(),e.getY());
                if(childView!=null && mListener!=null)
                { Log.d(TAG, "onSingleTapUp: calling onitemlick");
                   mListener.onItemLongClick(childView,recyclerView.getChildAdapterPosition(childView));}
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                /*View childView = recyclerView.findChildViewUnder(e.getX(),e.getY());
                if(childView!=null && mListener!=null)
                    mListener.onItemLongClick(childView,recyclerView.getChildAdapterPosition(childView));*/
                Log.d(TAG, "onLongPress: called");
            }
        });
    }


    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        Log.d(TAG, "onInterceptTouchEvent: starts");
        if(mGestureDetector!=null)
        {
            boolean result = mGestureDetector.onTouchEvent(e);
            Log.d(TAG, "onInterceptTouchEvent: "+result);
            return result;
        }
        else
        {
            Log.d(TAG, "onInterceptTouchEvent: false");
            return false;
        }
        
    }
}
