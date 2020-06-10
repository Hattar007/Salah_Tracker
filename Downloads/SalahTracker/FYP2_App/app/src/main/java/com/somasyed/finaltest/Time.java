package com.somasyed.finaltest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.Query;



import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class Time extends AppCompatActivity {

    String TAG="Main";
    String GetData;
    String Getoption;
    String Getrakatno;
    Rakat artist;
    private FirebaseAuth mAuth;
    int s1;
    int s2;
    int y;
    float x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        Button buttonstat = findViewById(R.id.show_viewRakah);
        GetData="";
        Getoption="";
        Getrakatno="";
        artist=new Rakat();
        buttonstat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Time.this, Rakah.class);
                intent.putExtra("EXTRA_MESSAGE4", GetData);
                intent.putExtra("EXTRA_MESSAGE5", Getoption);
                intent.putExtra("EXTRA_MESSAGE6", Getrakatno);
                startActivity(intent);
            }
        });



        mAuth = FirebaseAuth.getInstance();




        Intent intent=getIntent();
        GetData=intent.getStringExtra("EXTRA_MESSAGE");
        Getoption=intent.getStringExtra("EXTRA_MESSAGE1");
        Getrakatno= intent.getStringExtra("EXTRA_MESSAGE2");
        Toast toast = Toast.makeText(this,GetData, Toast.LENGTH_LONG);
        toast.show();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.time);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.mainActivity:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.startNamaz:
                        startActivity(new Intent(getApplicationContext(), StartNamaz.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), Profile.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.time:
                        return true;
                }
                return false;
            }
        });


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference().child(GetData).child(Getoption);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               if(Getrakatno.equals("2")) {
                   int standing11 = Integer.valueOf(dataSnapshot.child("Rakat1").child("standing1time").getValue().toString());
                   int standing12 = Integer.valueOf(dataSnapshot.child("Rakat2").child("standing1time").getValue().toString());
                    int standing1sum=standing11+standing12;
                    float standing1avg=(float)standing1sum/2;

                   int standing21 = Integer.valueOf(dataSnapshot.child("Rakat1").child("standing2time").getValue().toString());
                   int standing22 = Integer.valueOf(dataSnapshot.child("Rakat2").child("standing2time").getValue().toString());
                   int standing2sum=standing21+standing22;
                   float standing2avg=(float)standing2sum/2;

                   int bowing1 = Integer.valueOf(dataSnapshot.child("Rakat1").child("bowingtime").getValue().toString());
                   int bowing2 = Integer.valueOf(dataSnapshot.child("Rakat2").child("bowingtime").getValue().toString());
                   int bowing=bowing1+bowing2;
                   float bowingavg=(float)bowing/2;


                   int prostration11 = Integer.valueOf(dataSnapshot.child("Rakat1").child("prostration1time").getValue().toString());
                   int prostration12 = Integer.valueOf(dataSnapshot.child("Rakat2").child("prostration1time").getValue().toString());
                   int prostration1sum=prostration11+prostration12;
                   float prostration1avg=(float)prostration1sum/2;

                   int prostration21 = Integer.valueOf(dataSnapshot.child("Rakat1").child("prostration2time").getValue().toString());
                   int prostration22 = Integer.valueOf(dataSnapshot.child("Rakat2").child("prostration2time").getValue().toString());
                   int prostration2sum=prostration21+prostration22;
                   float prostration2avg=(float)prostration2sum/2;

                   int sitting11 = Integer.valueOf(dataSnapshot.child("Rakat1").child("sitting1time").getValue().toString());
                   int sitting12 = Integer.valueOf(dataSnapshot.child("Rakat2").child("sitting1time").getValue().toString());
                   int sitting1sum=sitting11+sitting12;
                   float sitting1avg=(float)sitting1sum/2;

                   int sitting21 = Integer.valueOf(dataSnapshot.child("Rakat1").child("sitting2time").getValue().toString());
                   int sitting22 = Integer.valueOf(dataSnapshot.child("Rakat2").child("sitting2time").getValue().toString());
                   int sitting2sum=sitting21+sitting22;
                   float sitting2avg=(float)sitting2sum/2;

                   int rakat=Integer.valueOf(dataSnapshot.child("Rakat1").child("rakahtime").getValue().toString());

                   y=s1+s2;
                   x=(float)y/2;
                   artist.bowingtime = s1;
                   Log.d(TAG, "onDataChange Rakah 1 time: " + rakat);
                  // Log.d(TAG, "onDataChange artist bowing: " + artist.bowingtime);


                   BarChart chart = findViewById(R.id.barchart1);
                   ArrayList<BarEntry> NoOfEmp = new ArrayList<>();
                   Log.d(TAG, "onCreate: s1==== " + s1);
                   Log.d(TAG, "onCreate: y==== " + y);
                   Log.d(TAG, "onCreate: bowing==== " + artist.bowingtime);

                   NoOfEmp.add(new BarEntry(standing1avg, 0));
                   NoOfEmp.add(new BarEntry(bowing, 1));
                   NoOfEmp.add(new BarEntry(standing2avg, 2));
                   NoOfEmp.add(new BarEntry(prostration1avg, 3));
                   NoOfEmp.add(new BarEntry(sitting1avg, 4));
                   NoOfEmp.add(new BarEntry(prostration2avg, 5));
                   NoOfEmp.add(new BarEntry(sitting2avg, 6));

                   ArrayList<String> theyear = new ArrayList<>();
                   theyear.add("Long Standing");
                   theyear.add("Bowing");
                   theyear.add("Short Standing");
                   theyear.add("1st Prostration");
                   theyear.add("1st Sitting");
                   theyear.add("2nd Prostration");
                   theyear.add("2nd Sitting");

                   BarDataSet bardataset = new BarDataSet(NoOfEmp, "Salah Positions");
                   BarData data = new BarData(theyear, bardataset);
                   chart.animateY(2000);

                   // dataSet.setColor(getResources().getColor(R.color.red)); //resolved color


                   bardataset.setColors(Collections.singletonList(getResources().getColor(R.color.colorAccent)));
                   bardataset.setBarSpacePercent(10f);

                   chart.setData(data);
               }

               }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        BarChart chart = findViewById(R.id.barchart1);
        ArrayList<BarEntry> NoOfEmp = new ArrayList<>();
        int x=10;
        Log.d(TAG, "onCreate: s1==== "+ s1);
        Log.d(TAG, "onCreate: y==== "+ y);
        Log.d(TAG, "onCreate: bowing==== "+ artist.bowingtime);

        NoOfEmp.add(new BarEntry(x, 0));
        NoOfEmp.add(new BarEntry(y, 1));
        NoOfEmp.add(new BarEntry(artist.bowingtime, 2));
        NoOfEmp.add(new BarEntry(7, 3));
        NoOfEmp.add(new BarEntry(7, 4));
        NoOfEmp.add(new BarEntry(4, 5));
        NoOfEmp.add(new BarEntry(9, 6));

        ArrayList<String> theyear = new ArrayList<>();
        theyear.add("Long Standing");
        theyear.add("Bowing");
        theyear.add("Short Standing");
        theyear.add("1st Prostration");
        theyear.add("1st Sitting");
        theyear.add("2nd Prostration");
        theyear.add("2nd Sitting");

        BarDataSet bardataset = new BarDataSet(NoOfEmp, "Salah Positions");
        BarData data = new BarData(theyear,bardataset);
        chart.animateY(2000);

        // dataSet.setColor(getResources().getColor(R.color.red)); //resolved color


        bardataset.setColors(Collections.singletonList(getResources().getColor(R.color.colorAccent)));
        bardataset.setBarSpacePercent(10f);

        chart.setData(data);


    }
}