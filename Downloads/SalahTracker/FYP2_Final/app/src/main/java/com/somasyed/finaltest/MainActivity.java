package com.somasyed.finaltest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mAuth;

    private ProgressBar pbar;
    private int a = 0;
    private TextView textView;
    private Handler handler = new Handler();

    //
    private Spinner spinner1;
    String option;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//
        spinner1=findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Namaz, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);
//
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mAuth = FirebaseAuth.getInstance();
        textView = findViewById(R.id.tv);
        pbar = findViewById(R.id.p_Bar);
        Button button = findViewById(R.id.show_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, StartNamaz.class);
                intent.putExtra("EXTRA_MESSAGE", option);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.mainActivity);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.mainActivity:
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
                        startActivity(new Intent(getApplicationContext(), Time.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = pbar.getProgress();
                new Thread(new Runnable() {
                    public void run() {
                        while (a < 100) {
                            a += 10;
                            handler.post(new Runnable() {
                                public void run() {
                                    pbar.setProgress(a);
                                    textView.setText(a + "/" + pbar.getMax());
                                    if (a == 100)
                                        textView.setText(" Your Progess has been Completed");
                                }
                            });
                            try {
                                // Sleep for 50 ms to show progress you can change it as well.
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });*/




    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        option = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),option,Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
