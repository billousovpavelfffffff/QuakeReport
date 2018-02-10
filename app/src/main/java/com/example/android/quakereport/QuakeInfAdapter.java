package com.example.android.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.android.quakereport.R.id.date;


//Тут всё слишком сложно, и я сам не до конца понимаю как это работает, но оно работает, поэтому лучше ничего не трогать.
//По сути это просто создание кастомного ArrayAdapter, когторый состоит из нескольких объектов.

public class QuakeInfAdapter extends ArrayAdapter<QuakeInf> {
    private static final String LOCATION_SEPARATOR = " of ";

    public QuakeInfAdapter(Context context, ArrayList<QuakeInf> earthquakes) {
        super(context, 0, earthquakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        QuakeInf currentQuake = getItem(position);


        TextView magTextView = (TextView) listItemView.findViewById(R.id.mag);
        String formattedMagnitude = formatMagnitude(currentQuake.getMag());
        // Display the magnitude of the current earthquake in that TextView
        magTextView.setText(formattedMagnitude);


        String originalLocation = currentQuake.getLocation();
        String primaryLocation;
        String locationOffset;
        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }
        TextView primaryLocationView = (TextView) listItemView.findViewById(R.id.location);
        primaryLocationView.setText(primaryLocation);

        TextView locationOffsetView = (TextView) listItemView.findViewById(R.id.near);
        locationOffsetView.setText(locationOffset);


        TextView dateTextView = (TextView) listItemView.findViewById(date);
        Date dateObject = new Date(currentQuake.getDate());
        String formattedDate = formatDate(dateObject);
        dateTextView.setText(formattedDate);


        TextView timeView = (TextView) listItemView.findViewById(R.id.time);

        String formattedTime = formatTime(dateObject);

        timeView.setText(formattedTime);


        return listItemView;
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }


    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }
}