package com.example.muzammilnawaz.flickrbrowser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrRecyclerViewAdapter.FlickrImageViewHolder> {
    private static final String TAG = "FlickrRecyclerViewAdapt";

    private List<Photo> mPhotoList;
    private Context mContext;

    public FlickrRecyclerViewAdapter( Context context,List<Photo> photoList) {
        mPhotoList = photoList;
        mContext = context;
    }









    @NonNull
    @Override
    public FlickrImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Log.d(TAG, "onCreateViewHolder: new view requested ");

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.browse,viewGroup,false);

        return new FlickrImageViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FlickrImageViewHolder flickrImageViewHolder, int i) {

             if(mPhotoList==null || mPhotoList.size()==0){
                 flickrImageViewHolder.thumbnail.setImageResource(R.drawable.placeholder);
                 flickrImageViewHolder.title.setText("No photos matched your search.\nSearch again.");
             }

          else{

        Photo photoitem = mPhotoList.get(i);
        Log.d(TAG, "onBindViewHolder: " + photoitem.getTitle() + "-->" + i);

        Picasso.get().load(photoitem.getImage())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(flickrImageViewHolder.thumbnail);

        flickrImageViewHolder.title.setText(photoitem.getTitle());}



    }

    @Override
    public int getItemCount() {

       // Log.d(TAG, "getItemCount: called");
        return ((mPhotoList!=null) && (mPhotoList.size()!=0)?mPhotoList.size():1);

    }

    void loadNewData(List<Photo> photos)
    {
        mPhotoList=photos;
        notifyDataSetChanged();
    }

    public Photo getPhoto(int position)
    {
        return ((mPhotoList!=null) && (mPhotoList.size()!=0)?mPhotoList.get(position):null);
    }




















    static class FlickrImageViewHolder extends RecyclerView.ViewHolder
    {


        private static final String TAG = "FlickrImageViewHolder";

        ImageView thumbnail = null;
        TextView title = null;

        public FlickrImageViewHolder(@NonNull View itemView) {

            //Log.d(TAG, "FlickrImageViewHolder: is called" );
            super(itemView);

            this.thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            this.title = (TextView) itemView.findViewById(R.id.thumbnail_title);
        }
        }


}
