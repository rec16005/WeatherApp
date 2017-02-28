package com.example.sebastian.clima;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sebastian.clima.data.Channel;
import com.example.sebastian.clima.data.Item;
import com.example.sebastian.clima.service.WeatherService;
import com.example.sebastian.clima.service.WeatherServiceCallback;

public class MainActivity extends AppCompatActivity implements WeatherServiceCallback {

    private ImageView iv_clima;
    private TextView tv_temp;
    private  TextView tv_condicion;
    private TextView tv_pais;
    private ProgressDialog dialog;
    private WeatherService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_clima = (ImageView)findViewById(R.id.iv_clima);
        tv_temp = (TextView)findViewById(R.id.tv_temp);
        tv_condicion = (TextView)findViewById(R.id.tv_condicion);
        tv_pais = (TextView)findViewById(R.id.tv_pais);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Cargando...");
        dialog.show();
        service = new WeatherService(this);
        service.refreshWeather("Guatemala, Guatemala");
    }

    @Override
    public void ServerSucces(Channel channel) {
        dialog.hide();

        Item item = channel.getItem();
        int resourceId = getResources().getIdentifier("drawable/"+ item.getCondition().getCode(), null, getPackageName());
        @SuppressWarnings("deprecation") Drawable weatherIconDrawable = getResources().getDrawable(resourceId);
        iv_clima.setImageDrawable(weatherIconDrawable);
        tv_pais.setText(service.getLocation());
        tv_temp.setText(item.getCondition().getTemperature()+" \u00b0"+ channel.getUnits().getTemperature());
        tv_condicion.setText(item.getCondition().getDescription());
    }

    @Override
    public void ServerFailure(Exception exception) {
        dialog.hide();
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
