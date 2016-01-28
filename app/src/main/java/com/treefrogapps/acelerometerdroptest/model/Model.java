package com.treefrogapps.acelerometerdroptest.model;

import android.util.Log;

import com.treefrogapps.acelerometerdroptest.MVP;
import com.treefrogapps.acelerometerdroptest.utils.SensorMonitor;

import java.lang.ref.WeakReference;

/**
 * Model Layer of MVP Pattern
 *
 * Does all the interacting with data - holds a WeakReference to
 * the Presenter layer through the MVP Interface, implement the SensorListener Interface
 */
public class Model implements MVP.ModelInterface{

    private static final String TAG = Model.class.getSimpleName();

    private WeakReference<MVP.PresenterInterface> mPresenter;

    private SensorMonitor mSensorMonitor;

    @Override
    public void onCreate(MVP.PresenterInterface presenter) {

        Log.d(TAG, "onCreate Called");

        mPresenter = new WeakReference<>(presenter);

        // initialise the Sensor Monitor class
        mSensorMonitor = new SensorMonitor(mPresenter.get().getAppContext(), mPresenter.get());

    }

    @Override
    public void onDestroy() {

        Log.d(TAG, "onDestroy Called");

        unregisterSensor();
    }


    /**
     * Methods to resister and unregister the SensorManager in the SensorMonitor class
     */
    @Override
    public void registerSensor() {
        mSensorMonitor.registerListener();
    }

    @Override
    public void unregisterSensor() {
        mSensorMonitor.unregisterListener();
    }
}
