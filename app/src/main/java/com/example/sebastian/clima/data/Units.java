package com.example.sebastian.clima.data;

import org.json.JSONObject;

/**
 * Created by sebastian on 27/02/2017.
 */

public class Units implements JSONPopulator {
    private String temperature;

    public String getTemperature() {
        return temperature;
    }

    @Override
    public void popuate(JSONObject data) {
        temperature = data.optString("temperature");
    }
}
