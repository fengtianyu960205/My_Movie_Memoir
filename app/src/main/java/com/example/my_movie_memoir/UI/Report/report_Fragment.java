package com.example.my_movie_memoir.UI.Report;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.core.Chart;
import com.anychart.core.cartesian.series.Bar;
import com.example.my_movie_memoir.MainActivity;
import com.example.my_movie_memoir.MovieDB.MovieDBViewModel;
import com.example.my_movie_memoir.MovieDB.MovieInfo;
import com.example.my_movie_memoir.R;
import com.example.my_movie_memoir.UI.MovieSearch.MyAdapter;
import com.example.my_movie_memoir.sp.SharedPreferenceUtil;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class report_Fragment extends Fragment {

    private GetCinemaPerPostcodeViewModel getCinemaPerPostcodeViewModel;
    private TextView report_input;
    private DatePicker date_picker_StartDate ,date_picker_EndDate;
    private Button report_pie_btn;
    private int perId;
    private Context context;
    private String startDate, endDate, year;
    private ArrayList<Integer> count = new ArrayList<>();
    private ArrayList<String>  postcode = new ArrayList<>();
    private ArrayList<String>  aa = new ArrayList<>();
    ArrayList<String> monthstr = new ArrayList<>();
    private ArrayList<Integer[]>  list;
    private AnyChartView pieChart;
    private Spinner spinner_Year;
    private Button report_bar_btn;
    private GetMoviePerMonthViewModel getMoviePerMonthViewModel;
    private ArrayList<Integer> cinemaCount = new ArrayList<>();
    private BarChart bargraph;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.report_fragment, container, false);


        report_input = view.findViewById(R.id.report_input);
        date_picker_StartDate = view.findViewById(R.id.date_picker_StartDate);
        date_picker_EndDate = view.findViewById(R.id.date_picker_EndDate);
        report_pie_btn = view.findViewById(R.id.report_pie_btn);
        spinner_Year = view.findViewById(R.id.spinner_Year);
        report_bar_btn = view.findViewById(R.id.report_bar_btn);
        pieChart = view.findViewById(R.id.pieChart);
        bargraph = view.findViewById(R.id.bargraph);
        year = spinner_Year.getSelectedItem().toString();
        getCinemaPerPostcodeViewModel = new
                ViewModelProvider(this).get(GetCinemaPerPostcodeViewModel.class);
        getMoviePerMonthViewModel =  new ViewModelProvider(this).get(GetMoviePerMonthViewModel.class);
        context = getActivity();
        ((MainActivity) getActivity()).setToolBarTitle("Report");
        SharedPreferenceUtil sp = SharedPreferenceUtil.getInstance(context);
        perId = sp.getInt("perId");

        date_picker_StartDate.init(2015, 8, 8, new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year,monthOfYear,dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                startDate = sdf.format(c);

            }
        });

        date_picker_EndDate.init(2020, 8, 8, new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year,monthOfYear,dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                endDate = sdf.format(c);

            }
        });

        report_pie_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getCinemaPerPostcodeViewModel.setGetCinemaPerPostcodeTask(perId,startDate,endDate);

                getCinemaPerPostcodeViewModel.getCinemaPerPostcode().observe(getViewLifecycleOwner(), new Observer<ArrayList<Integer[]>>() {
                    @Override
                    public void onChanged(ArrayList<Integer[]> integers) {
                        ArrayList<String>  temp = new ArrayList<>();
                        ArrayList<Integer> tempCount = new ArrayList<>();
                        for (int i = 0 ; i < integers.size() ; i++)
                        {

                            tempCount.add(integers.get(i)[1]);
                            String tempStr = integers.get(i)[0].toString();
                            temp.add(tempStr);
                            postcode = temp;
                            count = tempCount;

                        }
                        // call set pie chart method
                        setupPieChart();
                    }
                });

            }
        });

        report_bar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMoviePerMonthViewModel.setCinemaPerMonthTask(perId,year);
                getMoviePerMonthViewModel.getMoviePerMonth().observe(getViewLifecycleOwner(), new Observer<ArrayList<Integer[]>>() {
                    @Override
                    public void onChanged(ArrayList<Integer[]> integers) {
                        ArrayList<String> temp = new ArrayList<>();
                        ArrayList<Integer> tempCinemaCount = new ArrayList<>();
                        for (int i = 0 ; i < integers.size() ; i++)
                        {

                            tempCinemaCount.add(integers.get(i)[1]);
                            String tempStr = integers.get(i)[0].toString();
                            temp.add(tempStr);
                            temp = monthIntToMonth(temp);
                            monthstr = temp;
                            cinemaCount  = tempCinemaCount;

                        }
                        // call set bar graph method
                        setupbarGraph();

                    }
                });
            }
        });

        return view;
    }



    public void setupPieChart(){

        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();
        for (int i = 0; i < postcode.size(); i++ ){
            dataEntries.add(new ValueDataEntry(postcode.get(i),count.get(i)));

        }

        pie.data(dataEntries);
        pieChart.setChart(pie);

    }

    public void setupbarGraph(){

        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < cinemaCount.size(); i++ ){
            entries.add(new BarEntry(i,cinemaCount.get(i)));
        }

        BarDataSet barDataSet = new BarDataSet(entries,"watching movies per month ");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        Description description = new Description();
        description.setText("Month");
        bargraph.setDescription(description);
        BarData theData = new BarData(barDataSet);
        bargraph.setData(theData);

        XAxis xAxis = bargraph.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(monthstr));
        bargraph.animateY(2000);
        bargraph.invalidate();


    }
    public ArrayList<String> monthIntToMonth(ArrayList<String> monthInt) {
        for (int i = 0; i < monthInt.size(); i++) {
            String month = monthInt.get(i);
            switch (month) {
                case "1":
                    monthInt.set(i, "Jan");
                    break;
                case "2":
                    monthInt.set(i, "Feb");
                    break;
                case "3":
                    monthInt.set(i, "Mar");
                    break;
                case "4":
                    monthInt.set(i, "Apr");
                    break;
                case "5":
                    monthInt.set(i, "May");
                    break;
                case "6":
                    monthInt.set(i, "Jun");
                    break;
                case "7":
                    monthInt.set(i, "July");
                    break;
                case "8":
                    monthInt.set(i, "Aug");
                    break;
                case "9":
                    monthInt.set(i, "Sep");
                    break;
                case "10":
                    monthInt.set(i, "Oct");
                    break;
                case "11":
                    monthInt.set(i, "Nov");
                    break;
                case "12":
                    monthInt.set(i, "Dec");
                    break;
            }
        }
        return monthInt;
    }
}
