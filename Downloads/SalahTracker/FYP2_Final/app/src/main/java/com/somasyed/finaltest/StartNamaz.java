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
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class StartNamaz extends AppCompatActivity implements SensorEventListener {

    private ProgressBar pbar1, pbar2, pbar3, pbar4;
    private int a = 0;
    private TextView textView;
    private Handler handler1 = new Handler();

    //sensor
    TextView label;
    // EditText sentence;
    Button predict;
    public Handler handler;
    SensorManager sensorManager;
    Sensor accelerometer;
    String TAG="Main";
    TextView tvX,tvY,tvZ; //tvDPC;//txD;
    private BufferedWriter mW;
    File mFile;
    int i=0;
    Boolean startFlag;
    StringBuilder sb;
    Button start;
    //int prevX,prevY,prevZ;
    int myX,myY,myZ;
    Date currentTime;
    String filetime;
    String[] filetimearray;
    int rakat=1;
    int counter=0;
    String Label;
    Date activitytime;
//sensorend

    JSONObject object = new JSONObject();
    String option;


    public void jsoninit(JSONObject object) throws JSONException {
        object.put("Rakat",1);
        object.put("Position", "None");
        object.put("Namaz", "DefaultAny");
        object.put("Time", 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startnamaz);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.startNamaz);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.mainActivity:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.startNamaz:
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

        textView = findViewById(R.id.tv);
      pbar1 = findViewById(R.id.p_Bar_rakat1);
        pbar2 = findViewById(R.id.p_Bar_rakat2);
        pbar3 = findViewById(R.id.p_Bar_rakat3);
        pbar4 = findViewById(R.id.p_Bar_rakat4);

        Button button = findViewById(R.id.show_btndone);
        Button button2 = findViewById(R.id.show_btnstats);

        try {
            jsoninit(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent intent=getIntent();
       option=intent.getStringExtra("EXTRA_MESSAGE");
        Toast toast = Toast.makeText(this,option, Toast.LENGTH_LONG);
        toast.show();

        if(option.equals("Fajr"))
        {
            pbar3.setVisibility(View.GONE);
            pbar4.setVisibility(View.GONE);
        }
        else if(option.equals("Maghrib"))
        {
            pbar4.setVisibility(View.GONE);

        }



   /*     button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a = pbar1.getProgress();
                new Thread(new Runnable() {
                    public void run() {
                        while (a < 100) {
                            a += 10;
                            handler1.post(new Runnable() {
                                public void run() {
                                    pbar1.setProgress(a);
                                    textView.setText(a + "/" + pbar1.getMax());
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
        });
           */
//sensor----------------------------------------------------
        handler=new Handler();
        startFlag=false;
        sb=new StringBuilder();
        sb.append("angleX,").append("angleY,").append("angleZ,Time,\n");
       // tvX=findViewById(R.id.tvX);
        //tvY=findViewById(R.id.tvY);
        //tvZ=findViewById(R.id.tvZ);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener((SensorEventListener) StartNamaz.this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        TimelyData();
        //  tvDPC.setText("0");

        if (ContextCompat.checkSelfPermission(StartNamaz.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){

        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }
        }
        if (ContextCompat.checkSelfPermission(StartNamaz.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){

        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        }
 /*       start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentTime = Calendar.getInstance().getTime();
                filetimearray=currentTime.toString().split(" ");
                filetime=""+filetimearray[1]+filetimearray[2]+"_"+filetimearray[3];
                startFlag=true;
                toastMsg("Data Recording Started");
            }
        });

*/
//sensorend-------------------------------------------------
        label = findViewById(R.id.label);
     /*   predict = findViewById(R.id.predict);

        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("X", myX);
                    obj.put("Y", myY);
                    obj.put("Z", myZ);
                    sendMsg(obj);

                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });

*/

    }//On_create
    private void sendMsg(final JSONObject obj) {
        final Handler handler = new Handler();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket s = new Socket("192.168.100.58",5007);
                    OutputStream out = s.getOutputStream();
                    PrintWriter output = new PrintWriter(out);
                    output.println(obj.toString());
                    output.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    final String str = reader.readLine();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject obj = new JSONObject(str);
                                String lab = obj.getString("label");
                                label.setText(lab);
                                Label=lab;

                                Checkposition(Label);
                                /*if (lab.equalsIgnoreCase("0")) {
                                    label.setText("ham");
                                }
                                else {
                                    label.setText("spam");
                                }

                                 */
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
    //sensor
    private void TimelyData() {

        Timer timer = new Timer(); // creating timer

        // scheduling the task for repeated fixed-delay execution, beginning after the specified delay
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new TimerTask() {
                    @Override
                    public void run() {
                        getData();
                    }
                });

            }
        }, 800, 1000);


    }
    public void getData()
    {

        try {
            JSONObject obj = new JSONObject();
            obj.put("X", myX);
            obj.put("Y", myY);
            obj.put("Z", myZ);
            sendMsg(obj);

        }
        catch (JSONException e){
            e.printStackTrace();
        }



        if (startFlag) {
            if (myX<=50 && myY<=50 && myZ<=50 && myX>=(-50) && myY>=(-50) && myZ>=(-50)) {


                currentTime = Calendar.getInstance().getTime();
                String[] time = currentTime.toString().split(" ");

                sb.append("" + (int) myX + ",").append("" + (int) myY + ",").append("" + (int) myZ + ",").append(time[3] + ",\n");
                i++;


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // tvDPC.setText("" + i);
                    }
                });

            }
        }
    }

    public void toastMsg(String msg) {

        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();

    }
//    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    // @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){


            //Log.d(TAG, "onSensorChanged: X="+ sensorEvent.values[0]+" Y="+sensorEvent.values[1]+" Z="+sensorEvent.values[2]);


            myX=(int)(sensorEvent.values[0]*5);
            myY=(int)(sensorEvent.values[1]*5);
            myZ=(int)(sensorEvent.values[2]*5);
            //float dist= (float) sqrt(((prevX-myX)*(prevX-myX))+((prevY-myY)*(prevY-myY))+((prevZ-myZ)*(prevZ-myZ)));
            //txD.setText("DIST: "+dist);
         //   tvX.setText(""+myX);
           // tvY.setText(""+myY);
           // tvZ.setText(""+myZ);

        }
    }

    public void Checkposition(String Label)
    {

//

        if(option.equals("Fajr"))
        {
            int a=pbar1.getProgress();
            Log.d(TAG, "a: "+a+",Label = "+Label);
            Log.d(TAG, "counter: "+counter);
            if(Label.equals("Standing") && counter==0)
            {
                Log.d(TAG, "idr ata hai : "+Label+",counter"+counter);
//            a=a+10;
                if (rakat == 2){
                    pbar2.setProgress(14);
                }
                else{
                    pbar1.setProgress(17);
                }

                Date cDate = new Date();
                activitytime= cDate;
                Log.d(TAG, "activitytime: "+activitytime+"position = "+Label);
                counter=1;
                Log.d(TAG, Label);
            }
            else if(Label.equals("Bowing") && counter==1)
            {
                int difference=twoDatesBetweenTime(activitytime);
                Log.d(TAG, "idr ata hai : "+Label+",counter"+counter);
//            a=a+10;
                if (rakat == 2){
                    pbar2.setProgress(29);
                }
                else{
                    pbar1.setProgress(33);
                }
//            counter++;
                counter=2;
                Date cDate = new Date();
                activitytime= cDate;
                Log.d(TAG, "activitytime: "+activitytime+"position = "+Label);
            }
            else if(Label.equals("Standing") && counter==2)
            {
                int difference=twoDatesBetweenTime(activitytime);
                Date cDate = new Date();
                activitytime= cDate;
                Log.d(TAG, "idr ata hai : "+Label+",counter"+counter);
//            a=a+10;
                if (rakat == 2){
                    pbar2.setProgress(43);
                }
                else{
                    pbar1.setProgress(50);
                }
//            counter++;
                counter=3;

            }
            else if(Label.equals("Prostration") && counter==3)
            {
                Log.d(TAG, "idr ata hai first prostration : "+Label+",counter"+counter);
                int difference=twoDatesBetweenTime(activitytime);
                Date cDate = new Date();
                activitytime= cDate;
//            a=a+10;
                if (rakat == 2){
                    pbar2.setProgress(57);
                }
                else{
                    pbar1.setProgress(67);
                }//            counter++;
                counter=4;
            }
            else if(Label.equals("Sitting") && counter==4)
            {
                Log.d(TAG, "idr ata hai : "+Label+",counter"+counter);
                int difference=twoDatesBetweenTime(activitytime);
                Date cDate = new Date();
                activitytime= cDate;
//            a=a+10;
                if (rakat == 2){
                    pbar2.setProgress(71);
                }
                else{
                    pbar1.setProgress(83);
                }//            counter++;

                counter=5;
            }
            else if(Label.equals("Prostration") && counter==5)
            {
                Log.d(TAG, "idr ata hai : "+Label+",counter"+counter);
                int difference=twoDatesBetweenTime(activitytime);
                Date cDate = new Date();
                activitytime= cDate;
//            a=a+10;
                if (rakat == 2){
                    pbar2.setProgress(86);
                    counter=7;
                }
                else{
                    pbar1.setProgress(100);
                    counter=6;
                }//            counter++;
            }
            if (counter==6)
            {
                rakat++;
                counter=0;
            }
            if (Label.equals("Sitting") && counter==7 && rakat==2){
                pbar2.setProgress(100);
                Log.d(TAG, "namaz complete: ");
                int difference=twoDatesBetweenTime(activitytime);
            }

        }
        else if(option.equals("Maghrib"))
        {
            int a=pbar1.getProgress();
            Log.d(TAG, "a: "+a+",Label = "+Label);
            Log.d(TAG, "counter: "+counter);
            if(Label.equals("Standing") && counter==0)
            {
                Log.d(TAG, "idr ata hai : "+Label+",counter"+counter);
//            a=a+10;
                if (rakat == 2){
                    pbar2.setProgress(14);
                }
                else if(rakat==3)
                {
                    pbar3.setProgress(14);
                }
                else{
                    pbar1.setProgress(17);
                }

                Date cDate = new Date();
                activitytime= cDate;
                Log.d(TAG, "activitytime: "+activitytime+"position = "+Label);
                counter=1;
                Log.d(TAG, Label);
            }
            else if(Label.equals("Bowing") && counter==1)
            {
                int difference=twoDatesBetweenTime(activitytime);
                Log.d(TAG, "idr ata hai : "+Label+",counter"+counter);
//            a=a+10;
                if (rakat == 2){
                    pbar2.setProgress(29);
                }
                else if(rakat==3)
                {
                    pbar3.setProgress(29);
                }
                else{
                    pbar1.setProgress(33);
                }
//            counter++;
                counter=2;
                Date cDate = new Date();
                activitytime= cDate;
                Log.d(TAG, "activitytime: "+activitytime+"position = "+Label);
            }
            else if(Label.equals("Standing") && counter==2)
            {
                int difference=twoDatesBetweenTime(activitytime);
                Date cDate = new Date();
                activitytime= cDate;
                Log.d(TAG, "idr ata hai : "+Label+",counter"+counter);
//            a=a+10;
                if (rakat == 2){
                    pbar2.setProgress(43);
                }
                else if(rakat==3)
                {
                    pbar3.setProgress(43);
                }
                else{
                    pbar1.setProgress(50);
                }
//            counter++;
                counter=3;

            }
            else if(Label.equals("Prostration") && counter==3)
            {
                Log.d(TAG, "idr ata hai first prostration : "+Label+",counter"+counter);
                int difference=twoDatesBetweenTime(activitytime);
                Date cDate = new Date();
                activitytime= cDate;
//            a=a+10;
                if (rakat == 2){
                    pbar2.setProgress(57);
                }
                else if(rakat==3)
                {
                    pbar3.setProgress(57);
                }
                else{
                    pbar1.setProgress(67);
                }//            counter++;
                counter=4;
            }
            else if(Label.equals("Sitting") && counter==4)
            {
                Log.d(TAG, "idr ata hai : "+Label+",counter"+counter);
                int difference=twoDatesBetweenTime(activitytime);
                Date cDate = new Date();
                activitytime= cDate;
//            a=a+10;
                if (rakat == 2){
                    pbar2.setProgress(71);
                }
                else if(rakat==3)
                {
                    pbar3.setProgress(71);
                }
                else{
                    pbar1.setProgress(83);
                }//            counter++;

                counter=5;
            }
            else if(Label.equals("Prostration") && counter==5)
            {
                Log.d(TAG, "idr ata hai : "+Label+",counter"+counter);
                int difference=twoDatesBetweenTime(activitytime);
                Date cDate = new Date();
                activitytime= cDate;
//            a=a+10;
                if (rakat == 2){
                    pbar2.setProgress(86);
                    counter=7;
                }
                else if(rakat==3)
                {
                    pbar3.setProgress(86);
                }
                else{
                    pbar1.setProgress(100);
                    counter=6;
                }//            counter++;
            }
            if (counter==6)
            {

                rakat++;
                counter=0;
            }
            if (Label.equals("Sitting") && counter==7 ){
                int difference = twoDatesBetweenTime(activitytime);
                if(rakat==3) {
                    pbar3.setProgress(100);
                    Log.d(TAG, "namaz complete: ");
                }
                else if (rakat==2)
                {
                    pbar3.setProgress(100);
                    rakat++;
                }

            }

        }
        else // 4 Rakaat
        {
            int a=pbar1.getProgress();
            Log.d(TAG, "a: "+a+",Label = "+Label);
            Log.d(TAG, "counter: "+counter);
            if(Label.equals("Standing") && counter==0)
            {
                Log.d(TAG, "idr ata hai : "+Label+",counter"+counter);
//            a=a+10;
                if (rakat == 2){
                    pbar2.setProgress(14);
                }
                else if(rakat==3)
                {
                    pbar3.setProgress(17);
                }
                else if(rakat==4)
                {
                    pbar4.setProgress(14);
                }
                else{
                    pbar1.setProgress(17);
                }

                Date cDate = new Date();
                activitytime= cDate;
                Log.d(TAG, "activitytime: "+activitytime+"position = "+Label);
                counter=1;
                Log.d(TAG, Label);
            }
            else if(Label.equals("Bowing") && counter==1)
            {
                int difference=twoDatesBetweenTime(activitytime);
                Log.d(TAG, "idr ata hai : "+Label+",counter"+counter);
//            a=a+10;
                if (rakat == 2){
                    pbar2.setProgress(29);
                }
                else if(rakat==3)
                {
                    pbar3.setProgress(33);
                }
                else if(rakat==4)
                {
                    pbar4.setProgress(29);
                }
                else{
                    pbar1.setProgress(33);
                }
//            counter++;
                counter=2;
                Date cDate = new Date();
                activitytime= cDate;
                Log.d(TAG, "activitytime: "+activitytime+"position = "+Label);
            }
            else if(Label.equals("Standing") && counter==2)
            {
                int difference=twoDatesBetweenTime(activitytime);
                Date cDate = new Date();
                activitytime= cDate;
                Log.d(TAG, "idr ata hai : "+Label+",counter"+counter);
//            a=a+10;
                if (rakat == 2){
                    pbar2.setProgress(43);
                }
                else if(rakat==3)
                {
                    pbar3.setProgress(50);
                }
                else if(rakat==4)
                {
                    pbar4.setProgress(43);
                }
                else{
                    pbar1.setProgress(50);
                }
//            counter++;
                counter=3;

            }
            else if(Label.equals("Prostration") && counter==3)
            {
                Log.d(TAG, "idr ata hai first prostration : "+Label+",counter"+counter);
                int difference=twoDatesBetweenTime(activitytime);
                Date cDate = new Date();
                activitytime= cDate;
//            a=a+10;
                if (rakat == 2){
                    pbar2.setProgress(57);
                }
                else if(rakat==3)
                {
                    pbar3.setProgress(67);
                }
                else if(rakat==4)
                {
                    pbar4.setProgress(57);
                }
                else{
                    pbar1.setProgress(67);
                }//            counter++;
                counter=4;
            }
            else if(Label.equals("Sitting") && counter==4)
            {
                Log.d(TAG, "idr ata hai : "+Label+",counter"+counter);
                int difference=twoDatesBetweenTime(activitytime);
                Date cDate = new Date();
                activitytime= cDate;
//            a=a+10;
                if (rakat == 2){
                    pbar2.setProgress(71);
                }
                else if(rakat==3)
                {
                    pbar3.setProgress(83);
                }
                else if(rakat==4)
                {
                    pbar4.setProgress(71);
                }
                else{
                    pbar1.setProgress(83);
                }//            counter++;

                counter=5;
            }
            else if(Label.equals("Prostration") && counter==5)
            {
                Log.d(TAG, "idr ata hai : "+Label+",counter"+counter);
                int difference=twoDatesBetweenTime(activitytime);
                Date cDate = new Date();
                activitytime= cDate;
//            a=a+10;
                if (rakat == 2){
                    pbar2.setProgress(86);
                    counter=7;
                }
                else if(rakat==3)
                {
                    pbar3.setProgress(100);
                }
                else if(rakat==4)
                {
                    pbar4.setProgress(86);
                }
                else{
                    pbar1.setProgress(100);
                    counter=6;
                }//            counter++;
            }
            if (counter==6)
            {

                rakat++;
                counter=0;
            }
            if (Label.equals("Sitting") && counter==7 ){
                int difference = twoDatesBetweenTime(activitytime);
                if(rakat==4) {
                    pbar4.setProgress(100);
                    Log.d(TAG, "namaz complete: ");
                }
                else if (rakat==3)
                {
                    pbar3.setProgress(100);
                    rakat++;
                }
                else if (rakat==2)
                {
                    pbar2.setProgress(100);
                    rakat++;
                }

            }


        }


    }

    public int twoDatesBetweenTime(Date oldtime)
    {
        Log.d(TAG, "twoDatesBetweenTime:");
        // TODO Auto-generated method stub
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        Date cDate = new Date();
        Log.d(TAG, "cDate:"+cDate);

        Long timeDiff = cDate.getTime() - oldtime.getTime();
        Log.d(TAG, "timeDiff:"+timeDiff);
        int ss= (int) TimeUnit.MILLISECONDS.toSeconds(timeDiff);
        Log.d(TAG, "ss:"+ss);
        return ss;
    }

    @SuppressWarnings("unchecked")
    private void record(StringBuilder sb) {
        if (mW == null) {

            File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyFYPTraces");

            dir.mkdirs();
            mFile = new File(dir, new StringBuilder().append(getString(R.string.app_name)).append("_Data_"+filetime).append(".csv").toString());
            try {
                mW = new BufferedWriter(new FileWriter(mFile));
                mW.write(sb.toString());
                mW.flush();
                mW.close();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this,"ERORRR",Toast.LENGTH_LONG).show();
            }
            mFile.exists();
        }

    }



    @Override
    protected void onDestroy() {
        //record(sb);
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();

    }
}
