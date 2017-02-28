package com.example.sebastian.clima.service;

import com.example.sebastian.clima.data.Channel;

/**
 * Created by sebastian on 27/02/2017.
 */

public interface WeatherServiceCallback {
    void ServerSucces(Channel channel);

    void ServerFailure(Exception exception);
}
