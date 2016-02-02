package com.treefrogapps.acelerometerdroptest.utils;

/**
 * sensor readings
 */
public class SensorReadings {

    private float xDirection;
    private float yDirection;
    private float zDirection;

    public SensorReadings(float[] acceleration) {

        xDirection = acceleration[0];
        yDirection = acceleration[1];
        zDirection = acceleration[2];
    }

    public float getxDirection() {
        return xDirection;
    }

    public float getyDirection() {
        return yDirection;
    }

    public float getzDirection() {
        return zDirection;
    }
}
