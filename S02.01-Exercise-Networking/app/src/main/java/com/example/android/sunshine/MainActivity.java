/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.sunshine;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import static com.example.android.sunshine.data.SunshinePreferences.PREF_CITY_NAME;
import static com.example.android.sunshine.data.SunshinePreferences.getPreferredWeatherLocation;
import static com.example.android.sunshine.utilities.NetworkUtils.buildUrl;
import static com.example.android.sunshine.utilities.NetworkUtils.getResponseFromHttpUrl;
import static com.example.android.sunshine.utilities.OpenWeatherJsonUtils.getSimpleWeatherStringsFromJson;

public class MainActivity extends AppCompatActivity {

    private TextView mWeatherTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        /*
         * Using findViewById, we get a reference to our TextView from xml. This allows us to
         * do things like set the text of the TextView.
         */
        mWeatherTextView = (TextView) findViewById(R.id.tv_weather_data);

        loadWeatherData();
    }

    private void loadWeatherData() {
        new FetchWeatherTask().execute(getPreferredWeatherLocation(this));
    }

    private class FetchWeatherTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... params) {
            String[] weatherData = new String[0];

            if (params.length > 0) {
                try {
                    weatherData = getSimpleWeatherStringsFromJson(MainActivity.this,
                            getResponseFromHttpUrl(buildUrl(params[0])));
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return weatherData;
        }

        @Override
        protected void onPostExecute(String[] weatherData) {
            for (String weatherDatum: weatherData) {
                mWeatherTextView.append(weatherDatum + "\n\n\n");
            }
        }
    }
}