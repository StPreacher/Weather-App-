package com.kumu.weatherapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.kumu.weatherapp2.Model.Model;
import com.kumu.weatherapp2.Services.WeatherAPI;
import com.kumu.weatherapp2.İtemClasses.Main;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //http://api.openweathermap.org/data/2.5/weather?lat=38&lon=37&units=metric&appid=4ea9d33c8a01bb82fb80e844c5c72860
    //4ea9d33c8a01bb82fb80e844c5c72860
    

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;

    private final String units = "metric";
    private final String apiKey = "4ea9d33c8a01bb82fb80e844c5c72860";
    private final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    String ICON_URL = "http://openweathermap.org/img/wn/";

    TextView tempText, mainText, tempMax, tempMin, feelsLikeText, humidtyText, pressureText, windSpeedText, windDegreeText, cityNameText;
    ImageView iconView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fusedLocationProviderClient = new FusedLocationProviderClient(this);
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(4000);
        locationRequest.setFastestInterval(2000);

        tempText = findViewById(R.id.tempText);
        mainText = findViewById(R.id.mainText);
        tempMax = findViewById(R.id.tempMax);
        tempMin = findViewById(R.id.tempMin);
        feelsLikeText = findViewById(R.id.feelsLikeText);
        humidtyText = findViewById(R.id.humidityText);
        pressureText = findViewById(R.id.pressureText);
        windSpeedText = findViewById(R.id.windSpeedText);
        windDegreeText = findViewById(R.id.windDegreeText);
        cityNameText = findViewById(R.id.cityNameText);
        iconView = findViewById(R.id.iconView);



        //İzin alma
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED&&
                ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {
            getLocation();
        }
    }

    //Hava detaylı bilgi alma
    private void getWeatherInfoDetails(double lat, double lon) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);
        Call<Model> call = weatherAPI.getData(lat, lon, units, apiKey);

        call.enqueue(new Callback<Model>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {

                if (response.isSuccessful()) {

                    int degree = (int) Math.round(response.body().getMain().getTemp());

                    tempText.setText(degree + "°C");
                    mainText.setText(response.body().getWeatherList().get(0).getMain());
                    tempMax.setText(" max " + response.body().getMain().getTempMax().toString() + "°C");
                    tempMin.setText(" min " + response.body().getMain().getTempMin().toString() + "°C");
                    feelsLikeText.setText(response.body().getMain().getFeelsLike().toString() + "°C");
                    humidtyText.setText("%" + response.body().getMain().getHumidty());
                    pressureText.setText(response.body().getMain().getPressure() + "Pa");
                    windSpeedText.setText(response.body().getWind().getSpeed() + "km/h");
                    windDegreeText.setText(Math.round(response.body().getWind().getDeg()) + "°C");
                    cityNameText.setText(response.body().getName());

                    System.out.println(ICON_URL + response.body().getWeatherList().get(0).getIcon() + "@2x.png");

                    Picasso.get().load(ICON_URL + response.body().getWeatherList().get(0).getIcon() + "@2x.png").into(iconView);


                }


            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                System.out.println(t.getLocalizedMessage());
            }
        });


    }

    //İzin verilirse ve verilmezse
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Toast.makeText(this, "You have to change your choice from settings for using this application!", Toast.LENGTH_SHORT).show();
            }
        }


    }

    //Longitude ve Latitude verilerini alma
    private void getLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

        } else {

            fusedLocationProviderClient.requestLocationUpdates(locationRequest,new LocationCallback(){
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    getWeatherInfoDetails(locationResult.getLastLocation().getLatitude(),locationResult.getLastLocation().getLongitude());
                }
            },getMainLooper());

        }

    }
}