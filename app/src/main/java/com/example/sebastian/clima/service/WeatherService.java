package com.example.sebastian.clima.service;

import android.bluetooth.le.ScanRecord;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.sebastian.clima.data.Channel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by sebastian on 27/02/2017.
 */

public class WeatherService {
    private WeatherServiceCallback callback;
    private String location;
    private Exception error;

    public String getLocation() {
        return location;
    }

    public WeatherService(WeatherServiceCallback callback) {
        this.callback = callback;
    }

    public void refreshWeather (final String location){
        this.location = location;
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\")", strings[0]);
                String endpoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));
                try {
                    URL url = new URL(endpoint);
                    URLConnection connection = url.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    return result.toString();
                } catch (Exception e) {
                    error = e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                if (s==null && error!=null){
                    callback.ServerFailure(error);
                    return;
                }

                try {
                    JSONObject data = new JSONObject(s);
                    JSONObject queryResults = data.optJSONObject("query");
                    int count = queryResults.optInt("count");
                    if (count==0){
                        callback.ServerFailure(new LocatioWeatherException("No se encontró información para" + location));
                        return;
                    }
                    Channel channel = new Channel();
                    channel.popuate(queryResults.optJSONObject("results").optJSONObject("channel"));

                    callback.ServerSucces(channel);
                } catch (JSONException e) {
                    callback.ServerFailure(e);
                }
            }
        }.execute(location);
    }

    public class LocatioWeatherException extends Exception {
        public LocatioWeatherException(String detailMessage) {
            super(detailMessage);
        }
    }

}
