package com.example.progforce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class InsideActivity extends AppCompatActivity {
    TextView dateView;
    TextView descriptionView;
    TextView maxTempView;
    TextView minTempView;
    ImageView iconView;
    TextView humidityView;
    TextView pressureView;
    TextView windView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside);
        startParams();
        getInfo();
    }

    public void startParams(){
        dateView = findViewById(R.id.date);
        descriptionView = findViewById(R.id.weather_description);
        maxTempView = findViewById(R.id.high_temperature);
        minTempView = findViewById(R.id.low_temperature);
        iconView = findViewById(R.id.weather_icon);
        humidityView = findViewById(R.id.humidity);
        pressureView = findViewById(R.id.pressure);
        windView = findViewById(R.id.wind_measurement);
    }

    public void getInfo(){

        String dayCount = getIntent().getExtras().getString("dayCount");
        String icon = getIntent().getExtras().getString("icon");
        Integer tempMax = getIntent().getExtras().getInt("tempMax");
        Integer tempMin = getIntent().getExtras().getInt("tempMin");
        Integer humidity = getIntent().getExtras().getInt("humidity");
        Integer pressure = getIntent().getExtras().getInt("pressure");
        Integer wind = getIntent().getExtras().getInt("wind");
        String description = getIntent().getExtras().getString("description");
        makeView(dayCount,icon,tempMax,tempMin,humidity,pressure,wind,description);
    }

    public void makeView(String dayCount, String icon,
                         Integer tempMax, Integer tempMin, Integer humidity, Integer pressure,
                         Integer wind, String description ){
        dateView.setText(dayCount);
        descriptionView.setText(description);
        maxTempView.setText(tempMax.toString()+"°");
        minTempView.setText(tempMin.toString()+"°");
        Picasso.with(this).load(icon).into(iconView);
        humidityView.setText(humidity.toString()+" %");
        pressureView.setText(pressure.toString() +" hPa");
        windView.setText(wind.toString()+" km/h");
    }
}
