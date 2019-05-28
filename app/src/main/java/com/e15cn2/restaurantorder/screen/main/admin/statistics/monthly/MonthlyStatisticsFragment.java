package com.e15cn2.restaurantorder.screen.main.admin.statistics.monthly;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.WindowManager;

import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.application.AppContext;
import com.e15cn2.restaurantorder.data.model.Cart;
import com.e15cn2.restaurantorder.databinding.FragmentStatisticsAdminBinding;
import com.e15cn2.restaurantorder.screen.base.BaseFragment;
import com.e15cn2.restaurantorder.utils.DayAxisValueFormatter;
import com.e15cn2.restaurantorder.utils.MyValueFormatter;
import com.e15cn2.restaurantorder.utils.widget.XYMarkerView;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class MonthlyStatisticsFragment extends BaseFragment<FragmentStatisticsAdminBinding>
        implements OnChartValueSelectedListener {
    private static final String ARGUMENT_YEAR = "ARGUMENT_YEAR";
    private static final String ARGUMENT_MONTH = "ARGUMENT_MONTH";
    private static final String ARGUMENT_CARTS = "ARGUMENT_CARTS";
    private final String mDates[] = {
            "01", "02", "03", "04", "05", "06", "07",
            "08", "09", "10", "11", "12", "13", "14",
            "15", "16", "17", "18", "19", "20", "21",
            "22", "23", "24", "25", "26", "27", "28",
            "29", "30", "31"};

    public static MonthlyStatisticsFragment newInstance(List<Cart> carts, String year, String month) {
        MonthlyStatisticsFragment fragment = new MonthlyStatisticsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGUMENT_CARTS, (ArrayList<? extends Parcelable>) carts);
        args.putString(ARGUMENT_YEAR, year);
        args.putString(ARGUMENT_MONTH, month);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_statistics_admin;
    }

    @Override
    protected void initData() {
        initBarChart();
        if (getArguments() != null) {
            List<Cart> carts = getArguments().getParcelableArrayList(ARGUMENT_CARTS);
            String year = getArguments().getString(ARGUMENT_YEAR);
            String month = getArguments().getString(ARGUMENT_MONTH);
            setData(mDates.length, carts, year, month);
        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    private void initBarChart() {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding.chart.setOnChartValueSelectedListener(this);
        binding.chart.setDrawBarShadow(false);
        binding.chart.setDrawValueAboveBar(true);
        binding.chart.getDescription().setEnabled(false);
        // scaling can now only be done on x- and y-axis separately
        binding.chart.setPinchZoom(false);
        binding.chart.setDrawGridBackground(false);

        ValueFormatter xAxisFormatter = new DayAxisValueFormatter(binding.chart);
        final XAxis xAxis = binding.chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 month
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);

        ValueFormatter custom = new MyValueFormatter("đ");

        YAxis leftAxis = binding.chart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = binding.chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = binding.chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        XYMarkerView mv = new XYMarkerView(getActivity(), xAxisFormatter);
        mv.setChartView(binding.chart); // For bounds control
        binding.chart.setMarker(mv); // Set the marker to the chart
    }

    private void setData(int count, List<Cart> carts, String year, String month) {
        List<Float> dateValues = new ArrayList<>();
        for (int i = 1; i < count + 1; i++) {
            double cartValue = 0;
            for (Cart cart : carts) {
                if (cart.getTime().contains(year + "-" + month + "-" + mDates[i - 1])) {
                    cartValue = cartValue + cart.getPrice();
                }
            }
            dateValues.add((float) cartValue);
        }

        List<BarEntry> values = new ArrayList<>();
        for (int i = 1; i < count + 1; i++) {
            values.add(new BarEntry(i, dateValues.get(i - 1)));
        }
        BarDataSet dataSet;

        if (binding.chart.getData() != null &&
                binding.chart.getData().getDataSetCount() > 0) {
            dataSet = (BarDataSet) binding.chart.getData().getDataSetByIndex(0);
            dataSet.setValues(values);
            binding.chart.getData().notifyDataChanged();
            binding.chart.notifyDataSetChanged();

        } else {
            dataSet = new BarDataSet(values, AppContext.getInstance().getString(R.string.text_chart_income_fokllowing_cart));
            dataSet.setDrawIcons(false);
            dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

            List<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(dataSet);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);
            binding.chart.setData(data);
        }
    }
}
