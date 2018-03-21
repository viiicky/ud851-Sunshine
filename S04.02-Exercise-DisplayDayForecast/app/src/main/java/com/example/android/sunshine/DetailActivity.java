package com.example.android.sunshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import static android.content.Intent.EXTRA_TEXT;

public class DetailActivity extends AppCompatActivity {

    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";

    private TextView mWeatherDisplayTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mWeatherDisplayTextView = (TextView) findViewById(R.id.tv_display_weather);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_TEXT)) {
            mWeatherDisplayTextView.setText(intent.getStringExtra(EXTRA_TEXT));
        }
    }
}