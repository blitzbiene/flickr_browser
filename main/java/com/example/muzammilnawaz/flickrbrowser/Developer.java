package com.example.muzammilnawaz.flickrbrowser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Developer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        TextView title = (TextView) findViewById(R.id.developed);
        title.setText("DEVELOPED BY");
        TextView name = (TextView) findViewById(R.id.name);
        TextView mail = (TextView) findViewById(R.id.mail);
        name.setText("MUZAMMIL NAWAZ");
        mail.setText("muznawaz97@gmail.com");

    }
}
