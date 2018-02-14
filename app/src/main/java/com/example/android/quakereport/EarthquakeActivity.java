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
package com.example.android.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    private QuakeInfAdapter quakeInfAdapter;
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=1&limit=20";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        EathquakeTast task = new EathquakeTast();
        task.execute(USGS_REQUEST_URL);

    }






    private class EathquakeTast extends AsyncTask<String, Void,  ArrayList<QuakeInf>> {
        @Override
        protected  ArrayList<QuakeInf> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            ArrayList<QuakeInf> result = QueryUtils.fetchEarthquakeData(USGS_REQUEST_URL);
            return result;
        }
        protected void onPostExecute( ArrayList<QuakeInf> resault) {
            if (quakeInfAdapter != null)
            quakeInfAdapter.clear();

            if (resault == null) {
                return;
            }
            updateUi(resault);
        }
    }




    private void updateUi (ArrayList<QuakeInf> earthquakes) {

        // Массив созданных землятресений
        ArrayList<QuakeInf> earthquake = earthquakes;

        // Найти ListView
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Создания нового ArrayAdapter
        quakeInfAdapter = new QuakeInfAdapter(this, earthquake);
        earthquakeListView.setAdapter(quakeInfAdapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                QuakeInf currentEarthquake = quakeInfAdapter.getItem(position);


                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());


                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                startActivity(websiteIntent);
            }
        });
    }
}
