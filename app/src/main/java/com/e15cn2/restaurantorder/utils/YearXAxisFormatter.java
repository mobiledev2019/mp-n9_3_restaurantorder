package com.e15cn2.restaurantorder.utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

public class YearXAxisFormatter extends ValueFormatter {
    private static final String[] mMonths = new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    };

    public YearXAxisFormatter() {
        // take parameters to change behavior of formatter
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        float percent = value / axis.mAxisRange;
        return mMonths[(int) (mMonths.length * percent)];
    }

    public String[] getMonths() {
        return mMonths;
    }
}
