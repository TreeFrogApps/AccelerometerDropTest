package com.treefrogapps.acelerometerdroptest;

import com.treefrogapps.acelerometerdroptest.common.ContextView;
import com.treefrogapps.acelerometerdroptest.common.ModelOps;
import com.treefrogapps.acelerometerdroptest.common.PresenterOps;

/**
 * Model View Presenter Interfaces used with the corresponding classes
 * all methods for each class come from the interfaces.
 * <p/>
 * Each interface extends a common Interface - this could be used for other Activities
 * so code is not types more than once, all common interface methods are extended here
 * (interfaces can extend other interfaces just like classes can extend super classes)
 */
public interface MVP {


    /**
     * View Interface - Required View Operations needed
     */
    interface ViewInterface extends ContextView {

        void displayResults(float[] rawXYZ, float[] gravityXYZ, float[] accelerationXYZ);
    }


    /**
     * Presenter Interface - Provided View Operations needed by the View layer
     */
    interface  PresenterViewOperations extends PresenterOps<MVP.ViewInterface>{

        void startSensorAnalysing();

        void stopSensorAnalysing();
    }

    /**
     * Presenter Interface - Required Operations by the Presenter layer
     */
    interface  PresenterInterface extends ContextView {

        void displayResults(float[] rawXYZ, float[] gravityXYZ, float[] accelerationXYZ);

    }

    /**
     * Model Interface - Required Operations needed by the Model class/layer
     */
    interface  ModelInterface extends ModelOps<MVP.PresenterInterface> {

        void registerSensor();

        void unregisterSensor();
    }



}
