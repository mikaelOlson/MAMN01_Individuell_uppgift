package com.example.myfirstapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class Accelerometer extends AppCompatActivity implements SensorEventListener {


    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    boolean haveSensor = false;
    TextView xValue, yValue, zValue, direction;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        xValue = (TextView) findViewById(R.id.x);
        yValue = (TextView) findViewById(R.id.y);
        zValue = (TextView) findViewById(R.id.z);
        direction = (TextView) findViewById(R.id.direction);



    }

    public void start(){

        if((mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null)){
            noSensorAlert();
        }

        else {
            mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            haveSensor = mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);


        }

    }

    public void stop(){
        if(haveSensor){
            mSensorManager.unregisterListener(this, mAccelerometer);
        }

    }

    public void onPause(){
        super.onPause();
        stop();
    }
    public void onResume(){
        super.onResume();
        start();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {


        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            float xVal = event.values[0];
            float yVal = event.values[1];
            float zVal = event.values[2];

            xValue.setText("X: " + Math.round(xVal));
            yValue.setText("Y: " + Math.round(yVal));
            zValue.setText("Z: " + Math.round(zVal));

            if(xVal > 0.5){
                direction.setText("LEFT");
            } else if(xVal < -0.5) {
                direction.setText("RIGHT");
            } else {
                direction.setText("Move the phone");
            }

        }



    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    public void noSensorAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Your device does not support this sensor.")
                .setCancelable(false)
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        alertDialog.show();
    }
}
