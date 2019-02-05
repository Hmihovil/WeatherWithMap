package com.example.progforce;

import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.progforce.Data.WeatherDB;
import com.example.progforce.Data.WeatherDBforFirstDay;
import com.example.progforce.MathOperations.FirstDayViewOperations;
import com.example.progforce.MathOperations.Operations;
import com.example.progforce.MathOperations.OtherDaysOperations;
import com.example.progforce.Network.WeatherAPI;
import com.example.progforce.Network.net.WeatherDay;
import com.example.progforce.Network.net.WeatherList;
import com.example.progforce.Network.net2.WeatherDayForCity;
import com.example.progforce.Network.net2.WeatherListTwo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import io.reactivex.annotations.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class OneDayActivity extends AppCompatActivity {

    TextView dateView;
    TextView descriptionView;
    TextView maxTempView;
    TextView minTempView;
    ImageView iconView;
    TextView humidityView;
    TextView pressureView;
    TextView windView;
    WeatherAPI.ApiInterface api;
    ProgressBar loadingIndicator;
    String city;
    LinearLayout emptyView;
    LinearLayout noConnectionView;
    ConstraintLayout up;
    ConstraintLayout down;
    private static final String TAG = "WeatherCity" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_day);
        Intent intent = getIntent();
        city = intent.getExtras().getString("City");
        StartParams();
        OffAllView();
        loadingIndicator.setVisibility(VISIBLE);
        api = WeatherAPI.getClient().create(WeatherAPI.ApiInterface.class);
        getWeather(this);

    }

    public void getWeather(Context context) {
        String units = "metric";
        String key = WeatherAPI.KEY;

        Log.d(TAG, "OK");

        Call<WeatherListTwo> callForecast = api.getTownWeather(city, units, key);
        callForecast.enqueue(new Callback<WeatherListTwo>() {

            @Override
            public void onResponse(@NonNull Call<WeatherListTwo> call, @NonNull Response<WeatherListTwo> response) {

                List<WeatherDayForCity> listFromRetrofit = response.body().getItems();
                if (listFromRetrofit.isEmpty()){
                    OnEmptyView();
                    loadingIndicator.setVisibility(GONE);
                }else{
                Log.i("ПОГОДАААА", listFromRetrofit.toString());
                List<WeatherDayForCity> list = new ArrayList<>();
                List<Double> maxTemp = new ArrayList<>();
                List<Double> minTemp = new ArrayList<>();
                for (WeatherDayForCity weatherDay : response.body().getItems()) {
                    list.add(weatherDay);
                    maxTemp.add(weatherDay.getMain().getTempMax());
                    minTemp.add(weatherDay.getMain().getTempMin());
                }
                WeatherDayForCity printcity = response.body().getItems().get(0);
                printcity.getMain().setTempMin(Collections.min(minTemp));
                printcity.getMain().setTempMax(Collections.max(maxTemp));
                Log.e(TAG, "onResponse");
                GetInfo(printcity);
                OnWeatherView();
                loadingIndicator.setVisibility(GONE);}

            }


            @Override
            public void onFailure(Call<WeatherListTwo> call, Throwable t) {
                Log.e(TAG, "onFailure");
                Log.e(TAG, t.toString());
                OnNoConnectionView();
                loadingIndicator.setVisibility(GONE);
            }
        });
    }

    public void StartParams(){
        dateView = findViewById(R.id.date);
        descriptionView = findViewById(R.id.weather_description);
        maxTempView = findViewById(R.id.high_temperature);
        minTempView = findViewById(R.id.low_temperature);
        iconView = findViewById(R.id.weather_icon);
        humidityView = findViewById(R.id.humidity);
        pressureView = findViewById(R.id.pressure);
        windView = findViewById(R.id.wind_measurement);
        emptyView = findViewById(R.id.empty_view);
        up = findViewById(R.id.start_info);
        down = findViewById(R.id.extra_details);
        loadingIndicator = findViewById(R.id.pb_loading_indicator);
        noConnectionView = findViewById(R.id.no_connection_view);
    }

    public void GetInfo(WeatherDayForCity weatherDayForCity){

        String dayOfWeek = Operations.getDayOfWeek(weatherDayForCity.getDate().get(Calendar.DAY_OF_WEEK));
        String dayOfMoth = Operations.getMoth(weatherDayForCity.getDate().get(Calendar.MONTH));
        Integer number = weatherDayForCity.getDate().get(Calendar.DAY_OF_MONTH);
        String city = weatherDayForCity.getName();
        String dayCount = dayOfWeek + " " + number.toString() + " " + dayOfMoth +", "+city;
        String icon = weatherDayForCity.getWeather().get(0).getIcon();
        Integer tempMax = weatherDayForCity.getMain().getTempMax().intValue();
        Integer tempMin = weatherDayForCity.getMain().getTempMin().intValue();
        Integer humidity = weatherDayForCity.getMain().getHumidity();
        Integer pressure = weatherDayForCity.getMain().getPressure().intValue();
        Integer wind = weatherDayForCity.getWind().getSpeed().intValue();
        String description = weatherDayForCity.getWeather().get(0).getDescription();

        MakeView(dayCount,icon,tempMax,tempMin,humidity,pressure,wind,description);
    }

    public void MakeView(String dayCount, String icon,
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

    public void OnEmptyView(){
        up.setVisibility(GONE);
        down.setVisibility(GONE);
        emptyView.setVisibility(VISIBLE);
        noConnectionView.setVisibility(GONE);
    }

    public void OffEmptyView(){
        up.setVisibility(VISIBLE);
        down.setVisibility(VISIBLE);
        emptyView.setVisibility(GONE);
    }

    public void OffAllView(){
        up.setVisibility(GONE);
        down.setVisibility(GONE);
        emptyView.setVisibility(GONE);
        noConnectionView.setVisibility(GONE);
    }

    public void OnWeatherView(){
        up.setVisibility(VISIBLE);
        down.setVisibility(VISIBLE);
        emptyView.setVisibility(GONE);
        noConnectionView.setVisibility(GONE);
    }
    public void OnNoConnectionView(){
        up.setVisibility(GONE);
        down.setVisibility(GONE);
        emptyView.setVisibility(GONE);
        noConnectionView.setVisibility(VISIBLE);

    }
}
