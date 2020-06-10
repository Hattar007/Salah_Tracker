package com.somasyed.finaltest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

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


public class Rakah extends AppCompatActivity {
    final String TAG="Main";
    String GetData;
    String Getoption;
    String Getrakatno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rakah);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GetData="";
        Getoption="";
        Getrakatno="";
        ////////////////////////////////////////////////////////////
        Intent intent=getIntent();
        GetData=intent.getStringExtra("EXTRA_MESSAGE4");
        Getoption=intent.getStringExtra("EXTRA_MESSAGE5");
        Getrakatno= intent.getStringExtra("EXTRA_MESSAGE6");
        Toast toast = Toast.makeText(this,GetData, Toast.LENGTH_LONG);
        toast.show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference().child(GetData).child(Getoption);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(Getrakatno.equals("2")) {
                   /* int standing11 = Integer.valueOf(dataSnapshot.child("Rakat1").child("standing1time").getValue().toString());
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

                    Log.d(TAG, "onDataChange Rakah 1 time: " + rakat);

                    */

                    // Log.d(TAG, "onDataChange artist bowing: " + artist.bowingtime);
                    int rakat1 = Integer.valueOf(dataSnapshot.child("Rakat1").child("rakahtime").getValue().toString());
                    int rakat2 = Integer.valueOf(dataSnapshot.child("Rakat2").child("rakahtime").getValue().toString());


                    BarChart chart = findViewById(R.id.barchart2);
                    ArrayList<BarEntry> NoOfEmp = new ArrayList<>();

                    NoOfEmp.add(new BarEntry(rakat1, 0));
                    NoOfEmp.add(new BarEntry(rakat2, 1));
                   // NoOfEmp.add(new BarEntry(10, 2));
                    //NoOfEmp.add(new BarEntry(7, 3));

                    ArrayList<String> theyear = new ArrayList<>();
                    theyear.add("Rakah 1");
                    theyear.add("Rakah 2");
                    //theyear.add("Rakah 3");
                    //theyear.add("Rakah 4");

                    BarDataSet bardataset = new BarDataSet(NoOfEmp, "Rakah");
                    BarData data = new BarData(theyear, bardataset);
                    chart.animateY(2000);

                    // dataSet.setColor(getResources().getColor(R.color.red)); //resolved color


                    bardataset.setColors(ColorTemplate.PASTEL_COLORS);
                    bardataset.setBarSpacePercent(10f);

                    chart.setData(data);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






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
