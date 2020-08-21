package com.kumu.weatherapp2.Model;




import com.google.gson.annotations.SerializedName;
import com.kumu.weatherapp2.İtemClasses.Coord;
import com.kumu.weatherapp2.İtemClasses.Main;
import com.kumu.weatherapp2.İtemClasses.Weather;
import com.kumu.weatherapp2.İtemClasses.Wind;

import java.util.List;

public class Model {

    @SerializedName("coord")
    private Coord coord;

    @SerializedName("main")
    private Main main;

    @SerializedName("wind")
    private Wind wind;

    @SerializedName("name")
    private String name;

    @SerializedName("weather")
    private List<Weather> weatherList;


    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }
}
