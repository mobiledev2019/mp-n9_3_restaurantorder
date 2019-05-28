package com.e15cn2.restaurantorder.screen.main.admin.statistics;

import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;

import com.e15cn2.restaurantorder.R;
import com.e15cn2.restaurantorder.application.AppContext;
import com.e15cn2.restaurantorder.data.model.Cart;
import com.e15cn2.restaurantorder.data.repository.CartRepository;
import com.e15cn2.restaurantorder.data.source.remote.CartRemoteDataSource;
import com.e15cn2.restaurantorder.databinding.FragmentStatisticsAdminBinding;
import com.e15cn2.restaurantorder.screen.base.BaseFragment;
import com.e15cn2.restaurantorder.screen.main.admin.statistics.monthly.MonthlyStatisticsFragment;
import com.e15cn2.restaurantorder.utils.ActivityUtils;
import com.e15cn2.restaurantorder.utils.MyValueFormatter;
import com.e15cn2.restaurantorder.utils.YearXAxisFormatter;
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
import java.util.Calendar;
import java.util.List;

public class AdminStatisticsFragment extends BaseFragment<FragmentStatisticsAdminBinding>
        implements AdminStatisticsContract.View,
        OnChartValueSelectedListener, SearchView.OnQueryTextListener {
    private AdminStatisticsContract.Presenter mPresenter;
    private MenuItem mSearchItem;
    private int mYear;
    private List<Cart> mCarts;
    private static final String[] mMonths = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

    public static AdminStatisticsFragment newInstance() {
        return new AdminStatisticsFragment();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_statistics_admin;
    }

    @Override
    protected void initData() {
        setHasOptionsMenu(true);
        mPresenter = new AdminStatisticsPresenter(
                CartRepository.getInstance(CartRemoteDataSource.getInstance()), this);
        initBarChart();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mYear == 0) {
            statisticsCurrentYear();
        } else {
            statisticsByYearSearching();
        }
    }

    @Override
    public void onSuccess(List<Cart> carts) {
        setData(new YearXAxisFormatter().getMonths().length, carts);
        mCarts = carts;
    }

    @Override
    public void showError(Exception e) {
        //dev
    }

    @Override
    public void showMessage(String msg) {
        //TODO handle detail event
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;
        if (e.getY() > 0) {
            ActivityUtils.replaceFragment(
                    getFragmentManager(),
                    R.id.frame_main,
                    MonthlyStatisticsFragment.newInstance(
                            mCarts, String.valueOf(mYear), mMonths[(int) e.getX()]
                    )
            );
        }
    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onCreateOptionsMenu(android.view.Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.admin_statistics, menu);
        mSearchItem = menu.findItem(R.id.action_statistics);
        SearchView searchView = (SearchView) mSearchItem.getActionView();
        searchView.setQueryHint(AppContext.getInstance().getString(R.string.text_search_statistics));
        searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        mPresenter.getCartsByYear(Integer.parseInt(s));
        mYear = Integer.parseInt(s);
        mSearchItem.collapseActionView();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return true;
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
        binding.chart.setNoDataText(AppContext.getInstance().getString(R.string.text_tap_to_show));
        ValueFormatter xAxisFormatter = new YearXAxisFormatter();

        final XAxis xAxis = binding.chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 month
        xAxis.setLabelCount(12);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return new YearXAxisFormatter().getAxisLabel(value, xAxis);
            }
        });

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

    private void setData(int count, List<Cart> carts) {
        List<Float> monthValues = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            double cartValue = 0;
            for (Cart cart : carts) {
                if (cart.getTime().contains(mYear + "-" + mMonths[i])) {
                    cartValue = cartValue + cart.getPrice();
                }
            }
            monthValues.add((float) (cartValue));
        }

        List<BarEntry> values = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            values.add(new BarEntry(i, monthValues.get(i)));
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

    private void statisticsByYearSearching() {
        mPresenter.getCartsByYear(mYear);
    }

    private void statisticsCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        mPresenter.getCartsByYear(year);
    }
}
