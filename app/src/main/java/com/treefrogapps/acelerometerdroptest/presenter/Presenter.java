package com.treefrogapps.acelerometerdroptest.presenter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.WindowManager;

import com.treefrogapps.acelerometerdroptest.MVP;
import com.treefrogapps.acelerometerdroptest.common.GenericPresenter;
import com.treefrogapps.acelerometerdroptest.model.Model;

import java.lang.ref.WeakReference;

/**
 * Presenter layer that handles all requests from the view layer, as well as responding
 * back to the view layer.  This abstracts the view layer away from any hard links to the data/model
 */
public class Presenter extends GenericPresenter<MVP.PresenterInterface, MVP.ModelInterface, Model>
        implements MVP.PresenterViewOperations, MVP.PresenterInterface {

    public static final String TAG = Presenter.class.getSimpleName();


    /**
     * Keep a constant record of the analysing state
     * use boolean so boolean remains resident in memory and not cached
     */
    public static volatile boolean isAnalysing;

    private WeakReference<MVP.ViewInterface> mViewInterface;


    @Override
    public void onCreate(MVP.ViewInterface view) {

        Log.d(TAG, "onCreate Called");

        mViewInterface = new WeakReference<>(view);

        super.onCreate(Model.class, this);

    }

    @Override
    public void onConfigurationChange(MVP.ViewInterface view) {

        Log.d(TAG, "OnConfigurationChange Called");

        mViewInterface = new WeakReference<>(view);

        if (isAnalysing){

            ((Activity) mViewInterface.get().getActivityContext()).getWindow()
                    .addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {

            ((Activity) mViewInterface.get().getActivityContext()).getWindow()
                    .clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }



    /**
     * Methods for gaining contexts from the Activity, which extends
     * Generic Activity which Implements this interface along with the Presenter interface
     * if Generic Activity doesn't implement the methods (declared abstract - so doesn't need to)
     * then any Sub Classes must implement them (non-abstract sub classes)
     *
     * @return a Context
     */
    @Override
    public Context getActivityContext() {
        return mViewInterface.get().getActivityContext();
    }

    @Override
    public Context getAppContext() {
        return mViewInterface.get().getAppContext();
    }

    @Override
    public FragmentManager getFragManager() {
        return mViewInterface.get().getFragManager();
    }


    @Override
    public void onDestroy(boolean isChangingConfigurations) {

        Log.d(TAG, "onDestroy Called");

        if (!isChangingConfigurations){

            Log.d(TAG, "App Shutting Down");

            getModel().onDestroy();
        }

    }

    @Override
    public void displayResults(float[] rawXYZ, float[] gravityXYZ, float[] accelerationXYZ) {

        if (mViewInterface != null){

            mViewInterface.get().displayResults(rawXYZ, gravityXYZ, accelerationXYZ);
        }
    }


    /**
     * Start and stop the SensorManager Manually
     */
    @Override
    public void startSensorAnalysing() {

        if(!isAnalysing){
            getModel().registerSensor();
            ((Activity) mViewInterface.get().getActivityContext()).getWindow()
                    .addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            isAnalysing = true;
        }
    }

    @Override
    public void stopSensorAnalysing() {

        if(isAnalysing){
            getModel().unregisterSensor();

            ((Activity) mViewInterface.get().getActivityContext()).getWindow()
                    .clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            isAnalysing = false;
        }
    }
}
