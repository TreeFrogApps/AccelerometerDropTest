package com.treefrogapps.acelerometerdroptest.utils;

import java.util.ArrayList;

/**
 * Sensor Utils
 */
public class SensorUtils {


    public static String detectDirection(ArrayList<SensorReadings> sensorReadingsList){

        String direction =  "";

        float xAverage = 0;
        float yAverage = 0;
        float zAverage = 0;

        for (int i = 0; i < sensorReadingsList.size(); i++){

            xAverage += sensorReadingsList.get(i).getxDirection();
            yAverage += sensorReadingsList.get(i).getyDirection();
            zAverage += sensorReadingsList.get(i).getzDirection();
        }

        xAverage /= 5;
        yAverage /= 5;
        zAverage /= 5;

        if(xAverage < -1) direction += "Right";
        if(xAverage > 1) direction += "Left";

        if(yAverage < -1) direction += " Back";
        if(xAverage > 1) direction += " Forward";

        if(zAverage < -1) direction += " Up";
        if(zAverage > 1) direction += " Down";

        return direction;
    }
}
