package com.kumu.weatherapp2.Services;

//http://api.openweathermap.org/data/2.5/weather?lat=38&lon=37&units=metric&appid=4ea9d33c8a01bb82fb80e844c5c72860
//4ea9d33c8a01bb82fb80e844c5c72860

import com.kumu.weatherapp2.Model.Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {

    @GET("weather")
    Call<Model> getData(@Query("lat") Double latitude,
                        @Query("lon") Double longitude,
                        @Query("units") String units,
                        @Query("appid") String apiKey);


}
