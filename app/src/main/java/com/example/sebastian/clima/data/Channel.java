package com.example.sebastian.clima.data;

import org.json.JSONObject;

/**
 * Created by sebastian on 27/02/2017.
 */


public class Channel implements JSONPopulator {

    private Item item;
    private Units units;

    public Item getItem() {
        return item;
    }

    public Units getUnits() {
        return units;
    }

    @Override
    public void popuate(JSONObject data) {

        units = new Units();
        units.popuate(data.optJSONObject("units"));

        item = new Item();
        item.popuate(data.optJSONObject("item"));



    }
}
