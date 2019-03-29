package com.example.muzammilnawaz.flickrbrowser;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.Placeholder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class PhotoDetailActivity extends BaseActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
       activateToolbar(true);


        Intent intent  = getIntent();
        Photo photo = (Photo) intent.getSerializableExtra(PHOTO_TRANSFER);
        if(photo!=null)
        {

            TextView phototitle = (TextView) findViewById(R.id.photo_title);
            TextView phototags =(TextView) findViewById(R.id.photo_tags);
            TextView photoAUTHOR = (TextView) findViewById(R.id.photo_author);
             phototitle.setText("TITLE: " + photo.getTitle());
             photoAUTHOR.setText("AUTHOR: "+ photo.getAuthor());
             phototags.setText("TAGS: "+ photo.getTags());

            ImageView image = (ImageView) findViewById(R.id.photo_image);
            Picasso.get().load(photo.getLink()).error(R.drawable.placeholder).placeholder((R.drawable.placeholder)).into(image);
        }

    }

}
