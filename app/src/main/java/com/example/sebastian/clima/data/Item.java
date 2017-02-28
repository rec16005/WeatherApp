package com.example.sebastian.clima.data;

import org.json.JSONObject;

/**
 * Created by sebastian on 27/02/2017.
 */

public class Item implements JSONPopulator {
    private Condition condition;

    public Condition getCondition() {
        return condition;
    }

    @Override
    public void popuate(JSONObject data) {
        condition=new Condition();
        condition.popuate(data.optJSONObject("condition"));

    }
}
