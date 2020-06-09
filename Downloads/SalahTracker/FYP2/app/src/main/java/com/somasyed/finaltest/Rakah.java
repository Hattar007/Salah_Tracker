package com.somasyed.finaltest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class Rakah extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rakah);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BarChart chart = findViewById(R.id.barchart2);
        ArrayList<BarEntry> NoOfEmp = new ArrayList<>();

        NoOfEmp.add(new BarEntry(5, 0));
        NoOfEmp.add(new BarEntry(7, 1));
        NoOfEmp.add(new BarEntry(10, 2));
        NoOfEmp.add(new BarEntry(7, 3));

        ArrayList<String> theyear = new ArrayList<>();
        theyear.add("Rakah 1");
        theyear.add("Rakah 2");
        theyear.add("Rakah 3");
        theyear.add("Rakah 4");


        BarDataSet bardataset = new BarDataSet(NoOfEmp, "Salah");
        BarData data = new BarData(theyear,bardataset);

        bardataset.setColors(ColorTemplate.PASTEL_COLORS);
        chart.setData(data);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.menu_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        if (id== R.id.notification) {
            startActivity(new Intent(getApplicationContext(), Profile.class));
            overridePendingTransition(0,0);
            return true;
        }
        return false;
    }
}
