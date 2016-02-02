package com.treefrogapps.acelerometerdroptest.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.util.Log;

import com.treefrogapps.acelerometerdroptest.MVP;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Sensor Monitor Class - abstracted away from the Model and presenter layers
 * initialised in the model layer - uses a app context to out live Activities
 * that can come and go through their life cycle.
 */
public class SensorMonitor implements SensorEventListener {

    private static final String TAG = SensorMonitor.class.getSimpleName();

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Context mContext;

    private float[] mGravity = new float[3];
    private float[] mLinear_acceleration = new float[3];

    private float mCounter = 0;
    private ArrayList<SensorReadings> mSensorReadings = new ArrayList<>();
    private String mDirection = "";

    private MediaPlayer mPlayer;

    private long mLastUpdate;

    private final float alpha = 0.8f;

    private WeakReference<MVP.PresenterInterface> mPresenter;

    // constructor which is given a context and reference to the presenter interface layer to return results
    public SensorMonitor(Context context, MVP.PresenterInterface presenter){

        // create a SensorManager Instance - use app context as we want this to out live the activity
        // usually this would be tied to an activity
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mContext = context;

        mPresenter = new WeakReference<>(presenter);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        long currentTime = System.currentTimeMillis();

        if (currentTime - mLastUpdate > 100){

            mLastUpdate = currentTime;

            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){

                // high-pass filter - De-emphasize transient forces - just gravity, no acceleration
                mGravity[0] = alpha * mGravity[0] + (1 - alpha) * event.values[0];
                mGravity[1] = alpha * mGravity[1] + (1 - alpha) * event.values[1];
                mGravity[2] = alpha * mGravity[2] + (1 - alpha) * event.values[2];

                // low-pass filter - De-emphasize constant forces - without gravity, just acceleration
                mLinear_acceleration[0] = event.values[0] - mGravity[0];
                mLinear_acceleration[1] = event.values[1] - mGravity[1];
                mLinear_acceleration[2] = event.values[2] - mGravity[2];

                if (mCounter < 5){

                    mSensorReadings.add(new SensorReadings(mLinear_acceleration));

                    mCounter++;

                } else {

                    mDirection = SensorUtils.detectDirection(mSensorReadings);
                    mSensorReadings.clear();
                    mCounter = 0;
                }

                mPresenter.get().displayResults(event.values, mGravity, mLinear_acceleration, mDirection);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    /**
     * Methods called from the Model layer to register and unregister the SensorManager Listener
     */
    public void registerListener(){

        Log.d(TAG, "Register Listener");

        if (mSensorManager != null){
            mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);

            mPlayer = MediaPlayer.create(mContext, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        }
    }

    public void unregisterListener(){

        Log.d(TAG, "Unregister Listener");

        if (mSensorManager != null){
            mSensorManager.unregisterListener(this);

            if (mPlayer != null){
                mPlayer.release();
            }
        }
    }
}
