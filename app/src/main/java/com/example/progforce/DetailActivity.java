package com.example.progforce;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.progforce.data.WeatherDAO;
import com.example.progforce.data.WeatherDB;
import com.example.progforce.math_operation.FirstDayViewOperations;
import com.example.progforce.math_operation.Operations;
import com.example.progforce.math_operation.OtherDaysOperations;
import com.example.progforce.network.WeatherAPI;
import com.example.progforce.network.net.ListWithCity;
import com.example.progforce.network.net.WeatherDay;
import com.google.android.gms.maps.model.LatLng;
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

public class DetailActivity extends AppCompatActivity {
    private final WeatherDAO weatherDAO = App.db.weatherDAO();

    String TAG = "WEATHER";
    WeatherAPI.ApiInterface api;
    Double latitude;
    Double longitude;
    private RecyclerView recListWeatherView;
    private RecyclerView.LayoutManager ll;
    private ConstraintLayout constraintLayout;
    private ConstraintLayout.LayoutParams lll;
    public WeatherDayAdapter adapter;
    ProgressBar loadingIndicator;
    TextView dateTextView;
    ImageView iconImageView;
    TextView descriptionTextView;
    TextView highTextView;
    TextView lowTextView;
    LinearLayout emptyView;
    LinearLayout noConnectionView;
    FrameLayout down;

    public WeatherDB insideWeaherDay1;
    public Integer day1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setapSettings(this);

        ll = new LinearLayoutManager(this);
        recListWeatherView.setLayoutManager(ll);
        loadingIndicator.setVisibility(VISIBLE);
        offAllView();

        adapter = new WeatherDayAdapter(Collections.emptyList(), this, this::insideWeatherDay);
        recListWeatherView.setAdapter(adapter);
        weatherDAO.allFavoriteWeathers().observe(this, this::processListWeather);
        weatherDAO.allFavoriteWeathers().observe(this, this::processFirstWeather);

        LatLng position = getIntent().getExtras().getParcelable("Pos");
        latitude = position.latitude;
        longitude = position.longitude;
        api = WeatherAPI.getClient().create(WeatherAPI.ApiInterface.class);
        getWeather(this);

    }

    public void getWeather(Context context) {
        Double lat = latitude;
        Double lng = longitude;
        String units = "metric";
        String key = WeatherAPI.KEY;
        Log.d(TAG, "OK");

        Call<ListWithCity> callForecast = api.getForecast(lat, lng, units, key);
        callForecast.enqueue(new Callback<ListWithCity>() {
            @Override
            public void onResponse(@NonNull Call<ListWithCity> call, @NonNull Response<ListWithCity> response) {

                List<WeatherDB> entities = new ArrayList<>();
                String city = response.body().getCity().getName();
                for (WeatherDay weatherDay : response.body().getItems()) {
                    entities.add(WeatherDB.from(weatherDay,city));
                }
                Log.e(TAG, "onResponse");

                day1 = response.body().getItems().get(0).getDate().get(Calendar.DAY_OF_MONTH);
                WeatherDB weatherDBForFirstDay = FirstDayViewOperations.initFirstDay(entities,day1);
                showDayOne(weatherDBForFirstDay);

                List<WeatherDB> weatherDBForOtherDays = OtherDaysOperations.getOtherDays(entities, day1);
                adapter = new WeatherDayAdapter(weatherDBForOtherDays, context, weatherDay -> {
                    insideWeatherDay(weatherDay);
                });
                recListWeatherView.setAdapter(adapter);
                weatherDAO.clearWeathers();
                weatherDAO.insert(weatherDBForFirstDay);
                weatherDAO.insert(weatherDBForOtherDays);
                onWeatherView();
                loadingIndicator.setVisibility(GONE);
            }

            @Override
            public void onFailure(Call<ListWithCity> call, Throwable t) {
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
        constraintLayout.setOnClickListener((View v) -> {
            insideWeatherDay(insideWeaherDay1);
        });
    }

    public void insideWeatherDay(WeatherDB insideWeaherDay) {

        String dayOfWeek = Operations.getDayOfWeek(insideWeaherDay.getDate().get(Calendar.DAY_OF_WEEK));
        String dayOfMoth = Operations.getMoth(insideWeaherDay.getDate().get(Calendar.MONTH));
        Integer number = insideWeaherDay.getDate().get(Calendar.DAY_OF_MONTH);
        String city = insideWeaherDay.getCity();
        String dayCount = dayOfWeek + " " + number + " " + dayOfMoth+", "+city;
        String icon = insideWeaherDay.getIcon();
        Integer tempMax = insideWeaherDay.getMaxTemp().intValue();
        Integer tempMin = insideWeaherDay.getMinTemp().intValue();
        Integer humidity = insideWeaherDay.getHumidity();
        Integer pressure = insideWeaherDay.getPressure().intValue();
        Integer wind = insideWeaherDay.getWind().intValue();
        String description = insideWeaherDay.getDescription();

        Intent intent = new Intent(this,InsideActivity.class);
        intent.putExtra("dayCount", dayCount);
        intent.putExtra("icon", icon);
        intent.putExtra("tempMax", tempMax);
        intent.putExtra("tempMin", tempMin);
        intent.putExtra("humidity", humidity);
        intent.putExtra("pressure", pressure);
        intent.putExtra("wind", wind);
        intent.putExtra("description", description);
        startActivity(intent);
    }

    public void showDayOne(WeatherDB weatherDB) {

        String dMoth = Operations.getMoth(weatherDB.getDate().get(Calendar.MONTH));
        String dWeek = Operations.getDayOfWeek(weatherDB.getDate().get(Calendar.DAY_OF_WEEK));
        Integer day = weatherDB.getDate().get(Calendar.DAY_OF_MONTH);
        String city = weatherDB.getCity();
        if(city==null){
            city ="";
        }else city = ", "+city;
        lowTextView.setText(weatherDB.getMinTemp().intValue() + "°");
        highTextView.setText(weatherDB.getMaxTemp().intValue() + "°");
        insideWeaherDay1 = weatherDB;
        dateTextView.setText(dWeek + day + " " + dMoth +city);
        Picasso.with(this).load(weatherDB.getIcon()).into(iconImageView);
        descriptionTextView.setText(weatherDB.getDescription());
    }

    private void processListWeather(List<WeatherDB> weatherDBS) {
        List<WeatherDB> db = weatherDBS;
        if (!weatherDBS.isEmpty()) {
            db.remove(db.get(0));
            adapter.swapData(db);
            }
        }

    private void processFirstWeather(List<WeatherDB> weatherDBS) {
        if (!weatherDBS.isEmpty()) {
            WeatherDB weatherOffline = FirstDayViewOperations.initFirstDay(weatherDBS, weatherDBS.get(0).getDate().get(Calendar.DAY_OF_MONTH));
            showDayOne(weatherOffline);
        }
    }

    public void setapSettings(Context context) {
        recListWeatherView = (RecyclerView) findViewById(R.id.recyclerview_forecast);
        recListWeatherView.setHasFixedSize(true);
        dateTextView = findViewById(R.id.date);
        iconImageView = findViewById(R.id.weather_icon);
        descriptionTextView = findViewById(R.id.weather_description);
        highTextView = findViewById(R.id.high_temperature);
        lowTextView = findViewById(R.id.low_temperature);
        constraintLayout = findViewById(R.id.start_details);
        down = findViewById(R.id.extra_details);
        noConnectionView = findViewById(R.id.no_connection_view);
        emptyView = findViewById(R.id.empty_view);
        loadingIndicator = findViewById(R.id.load_indicator);
    }

    public void offAllView() {
        constraintLayout.setVisibility(GONE);
        down.setVisibility(GONE);
        emptyView.setVisibility(GONE);
        noConnectionView.setVisibility(GONE);
    }

    public void onWeatherView() {
        constraintLayout.setVisibility(VISIBLE);
        down.setVisibility(VISIBLE);
        emptyView.setVisibility(GONE);
        noConnectionView.setVisibility(GONE);
    }

    public void onNoConnectionView() {
        constraintLayout.setVisibility(GONE);
        down.setVisibility(GONE);
        emptyView.setVisibility(GONE);
        noConnectionView.setVisibility(VISIBLE);
    }
        public void onVisibleView(){
            weatherDAO.allFavoriteWeathers();
            loadingIndicator.setVisibility(GONE);
            constraintLayout.setVisibility(VISIBLE);
            down.setVisibility(VISIBLE);
        }
}


