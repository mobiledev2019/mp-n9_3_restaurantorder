package com.e15cn2.restaurantorder.screen.main.admin.add_item;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<String> {

    public SpinnerAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        if (position == 0) {
            view.setTextColor(Color.GRAY);
        } else view.setTextColor(Color.BLACK);
        view.setTypeface(Typeface.DEFAULT); //Change Typeface here
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        view.setTypeface(Typeface.DEFAULT); //Change Typeface here
        if (position == 0) {
            view.setTextColor(Color.GRAY);
        } else view.setTextColor(Color.BLACK);
        return view;
    }

    @Override
    public boolean isEnabled(int position) {
        if (position == 0) {
            return false;
        } else return true;
    }

}