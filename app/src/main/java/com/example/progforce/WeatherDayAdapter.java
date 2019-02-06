package com.example.progforce;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.progforce.data.WeatherDB;
import com.example.progforce.math_operation.Operations;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherDayAdapter extends RecyclerView.Adapter<WeatherDayAdapter.WeatherViewHolder> {

    public interface WeatherDayListener {
        void onWeatherDay(WeatherDB weatherDay);
    }

    private List<WeatherDB> items;
    private Context context;
    private WeatherDayListener listener;

    public WeatherDayAdapter( List<WeatherDB> weatherDays,Context ctext,WeatherDayListener listener) {
        items = weatherDays;
        context = ctext;
        this.listener = listener;
    }

    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_list_item, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final WeatherViewHolder holder, final int position) {
        final WeatherDB item = items.get(position);
        Calendar day = item.getDate();
        Double tempMax = item.getMaxTemp();
        Double tempMin = item.getMinTemp();
        Double weather = item.getWind();

        int dOfWeek = day.get(Calendar.DAY_OF_WEEK);
        String dayOfWeek = Operations.getDayOfWeek(dOfWeek);

        int dOfMonth = day.get(Calendar.MONTH);
        String dayOfMonth = Operations.getMoth(dOfMonth);
        Integer dom = day.get(Calendar.DAY_OF_MONTH);
        Integer tMax = tempMax.intValue();
        Integer tMin = tempMin.intValue();

        holder.maxTempView.setText(Integer.toString(tMax)+"°");
        holder.minTempView.setText(Integer.toString(tMin)+"°");
        holder.descriptionView.setText(item.getDescription());
        holder.dayOfWeekView.setText(dayOfWeek + dom +" " + dayOfMonth);
        Picasso.with(context).load(item.getIcon()).into(holder.imageView);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onWeatherDay(item);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void swapData(List<WeatherDB> data){
        items = data;
        notifyDataSetChanged();
    }

    public static class WeatherViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.weather_description)
        TextView descriptionView;

        @BindView(R.id.high_temperature)
        TextView maxTempView;

        @BindView(R.id.low_temperature)
        TextView minTempView;

        @BindView(R.id.day_of_week)
        TextView dayOfWeekView;

        @BindView(R.id.weather_icon)
        ImageView imageView;

        @BindView(R.id.id_for_listener)
        ConstraintLayout constraintLayout;

        WeatherViewHolder(View viewItem) {
            super(viewItem);
            ButterKnife.bind(this, viewItem);
        }
    }
}
