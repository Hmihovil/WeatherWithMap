package com.example.progforce;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMapLongClickListener,
        OnMapReadyCallback {

    private GoogleMap mMap;
    public Marker marker;
    String city;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng kiev = new LatLng(50,30);
        marker = mMap.addMarker(new MarkerOptions().position(kiev).draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(kiev));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                mMap.clear();
                marker = mMap.addMarker(new MarkerOptions().position(point));
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) { }

    @Override
    public void onMapClick(LatLng place) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem search = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_search_city) {
            Intent intent = new Intent(this, DetailActivity.class);
            LatLng position = marker.getPosition();
            intent.putExtra("Pos", position);
            startActivity(intent);
            return false;}

        if (id == R.id.item_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Intent intent = new Intent(getApplicationContext(),OneDayActivity.class);
                city = searchView.getQuery().toString();
                intent.putExtra("City", city );
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               return false;
            }
        });
    }
}

