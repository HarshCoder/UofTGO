package com.hvdevelopers.uoftgo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class PreGameScreen extends Activity {

    // The following are used for the shake detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    private TextView username;
    private TextView numMembers;
    private TextView numTeamBuild;
    private TextView numYouBuild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_game_screen);

        Bundle bundle = getIntent().getExtras();
        String userName = bundle.getString("username");

        username = (TextView)(findViewById(R.id.username));
        username.setText(userName);

        numMembers = (TextView)findViewById(R.id.numMembers);
        numMembers.setText("0");

        numTeamBuild = (TextView)findViewById(R.id.numTeamBuild);
        numTeamBuild.setText("0");

        numYouBuild = (TextView)findViewById(R.id.numYouBuild);
        numYouBuild.setText("0");

        // ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

            @Override
            public void onShake(int count) {
				/*
				 * The following method, "handleShakeEvent(count):" is a stub //
				 * method you would use to setup whatever you want done once the
				 * device has been shook.
				 */
                handleShakeEvent(count);
            }
        });
    }

    private void handleShakeEvent(int count){

        System.out.println("Shake");

        Intent openPreGame = new Intent(PreGameScreen.this, GameScreen.class);
        startActivity(openPreGame);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }
}
