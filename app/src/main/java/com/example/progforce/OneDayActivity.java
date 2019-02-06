package com.example.progforce;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.progforce.data.WeatherDAO;
import com.example.progforce.data.WeatherDB;
import com.example.progforce.math_operation.Operations;
import com.example.progforce.network.WeatherAPI;
import com.example.progforce.network.net.WeatherDay;
import com.example.progforce.network.net.WeatherList;
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
    private final WeatherDAO weatherDAO = App.db.weatherDAO();
    WeatherAPI.ApiInterface api;
    TextView dateView;
    TextView descriptionView;
    TextView maxTempView;
    TextView minTempView;
    ImageView iconView;
    TextView humidityView;
    TextView pressureView;
    TextView windView;
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
        startParams();

        loadingIndicator.setVisibility(VISIBLE);
        offAllView();
        api = WeatherAPI.getClient().create(WeatherAPI.ApiInterface.class);
        getWeather(this);
        weatherDAO.favoriteWeathers().observe(this, this::processFirstWeather);
    }

    public void getWeather(Context context) {
        String units = "metric";
        String key = WeatherAPI.KEY;
        Log.d(TAG, "OK");

        Call<WeatherList> callForecast = api.getTownWeather(city, units, key);
        callForecast.enqueue(new Callback<WeatherList>() {

            @Override
            public void onResponse(@NonNull Call<WeatherList> call, @NonNull Response<WeatherList> response) {

                List<WeatherDB> entities = new ArrayList<>();
                List<Double> maxTemp = new ArrayList<>();
                List<Double> minTemp = new ArrayList<>();
                for (WeatherDay weatherDay : response.body().getItems()) {
                    entities.add(WeatherDB.from(weatherDay, city));
                    maxTemp.add(weatherDay.getMain().getTempMax());
                    minTemp.add(weatherDay.getMain().getTempMin());
                }
                Log.e(TAG, "onResponse");

                WeatherDB printcity = entities.get(0);
                printcity.setMinTemp(Collections.min(minTemp));
                printcity.setMaxTemp(Collections.max(maxTemp));
                Log.e(TAG, "onResponse");
                getInfo(printcity);
                weatherDAO.clearWeathers();
                weatherDAO.insert(printcity);
                onWeatherView();
                loadingIndicator.setVisibility(GONE);
            }

            @Override
            public void onFailure(Call<WeatherList> call, Throwable t) {
                Log.e(TAG, "onFailure");
                Log.e(TAG, t.toString());
                List<WeatherDB> db = weatherDAO.allWeathersBlocking();
                if(db.isEmpty()){
                    loadingIndicator.setVisibility(GONE);
                    onNoConnectionView();
                }else {
                    onVisibleView();
                }
            }
        });
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
        emptyView = findViewById(R.id.empty_view);
        up = findViewById(R.id.start_info);
        down = findViewById(R.id.extra_details);
        loadingIndicator = findViewById(R.id.pb_loading_indicator);
        noConnectionView = findViewById(R.id.no_connection_view);
    }

    public void getInfo(WeatherDB weatherDayForCity){

        String dayOfWeek = Operations.getDayOfWeek(weatherDayForCity.getDate().get(Calendar.DAY_OF_WEEK));
        String dayOfMoth = Operations.getMoth(weatherDayForCity.getDate().get(Calendar.MONTH));
        Integer number = weatherDayForCity.getDate().get(Calendar.DAY_OF_MONTH);
        String city = weatherDayForCity.getCity();
        String dayCount = dayOfWeek + " " + number.toString() + " " + dayOfMoth+", "+city;
        String icon = weatherDayForCity.getIcon();
        Integer tempMax = weatherDayForCity.getMaxTemp().intValue();
        Integer tempMin = weatherDayForCity.getMinTemp().intValue();
        Integer humidity = weatherDayForCity.getHumidity();
        Integer pressure = weatherDayForCity.getPressure().intValue();
        Integer wind = weatherDayForCity.getWind().intValue();
        String description = weatherDayForCity.getDescription();

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

    public void onEmptyView(){
        up.setVisibility(GONE);
        down.setVisibility(GONE);
        emptyView.setVisibility(VISIBLE);
        noConnectionView.setVisibility(GONE);
    }

    public void offAllView(){
        up.setVisibility(GONE);
        down.setVisibility(GONE);
        emptyView.setVisibility(GONE);
        noConnectionView.setVisibility(GONE);
    }

    public void onWeatherView(){
        up.setVisibility(VISIBLE);
        down.setVisibility(VISIBLE);
        emptyView.setVisibility(GONE);
        noConnectionView.setVisibility(GONE);
    }
    public void onNoConnectionView(){
        up.setVisibility(GONE);
        down.setVisibility(GONE);
        emptyView.setVisibility(GONE);
        noConnectionView.setVisibility(VISIBLE);
    }

    private void processFirstWeather(WeatherDB weatherDBS) {
        if (!weatherDBS.equals(null)||!weatherDBS.equals("")||weatherDBS != null) {
            getInfo(weatherDBS);
        }
    }
    public void onVisibleView(){
        weatherDAO.allFavoriteWeathers();
        loadingIndicator.setVisibility(GONE);
        up.setVisibility(VISIBLE);
        down.setVisibility(VISIBLE);
    }
}
