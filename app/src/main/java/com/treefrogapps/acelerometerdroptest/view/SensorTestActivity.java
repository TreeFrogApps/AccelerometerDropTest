package com.treefrogapps.acelerometerdroptest.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.treefrogapps.acelerometerdroptest.MVP;
import com.treefrogapps.acelerometerdroptest.R;
import com.treefrogapps.acelerometerdroptest.common.GenericActivity;
import com.treefrogapps.acelerometerdroptest.presenter.Presenter;


/**
 * Main View class Concrete implementation extends from Generic Activity
 */
public class SensorTestActivity extends GenericActivity<MVP.ViewInterface,
        MVP.PresenterViewOperations, Presenter> implements MVP.ViewInterface, View.OnClickListener {

    private static final String TAG = SensorTestActivity.class.getSimpleName();

    private Button  mStartButton, mStopButton;
    private TextView mRawX, mRawY, mRawZ, mGravX, mGravY, mGravZ, mAccelX, mAccelY, mAccelZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);

        Log.d(TAG, "onCreate Called");

        initUI();

        super.onCreate(this, Presenter.class);

    }


    public void initUI(){

        mStartButton = (Button) findViewById(R.id.startButton);
        mStartButton.setOnClickListener(this);

        mStopButton = (Button) findViewById(R.id.stopButton);
        mStopButton.setOnClickListener(this);

        mRawX = (TextView) findViewById(R.id.rawX);
        mRawY = (TextView) findViewById(R.id.rawY);
        mRawZ = (TextView) findViewById(R.id.rawZ);
        mGravX = (TextView) findViewById(R.id.gravX);
        mGravY = (TextView) findViewById(R.id.gravY);
        mGravZ = (TextView) findViewById(R.id.gravZ);
        mAccelX = (TextView) findViewById(R.id.accelX);
        mAccelY = (TextView) findViewById(R.id.accelY);
        mAccelZ = (TextView) findViewById(R.id.accelZ);

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.startButton :
                getPresenter().startSensorAnalysing();
                break;

            case R.id.stopButton :
                getPresenter().stopSensorAnalysing();
                break;

            default: break;
        }
    }

    @Override
    public void displayResults(float[] rawXYZ, float[] gravityXYZ, float[] accelerationXYZ) {

        if (Presenter.isAnalysing){

            mRawX.setText(String.valueOf(rawXYZ[0]));
            mRawY.setText(String.valueOf(rawXYZ[1]));
            mRawZ.setText(String.valueOf(rawXYZ[2]));

            mGravX.setText(String.valueOf(gravityXYZ[0]));
            mGravY.setText(String.valueOf(gravityXYZ[1]));
            mGravZ.setText(String.valueOf(gravityXYZ[2]));

            mAccelX.setText(String.valueOf(accelerationXYZ[0]));
            mAccelY.setText(String.valueOf(accelerationXYZ[1]));
            mAccelZ.setText(String.valueOf(accelerationXYZ[2]));
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        // App being changing configurations if isChangingConfigurations returns true.
        // Notify other layers if not true i.e. App is closing, to perform
        // any necessary clean up operations (unbind service)
        getPresenter().onDestroy(isChangingConfigurations());
    }


}
