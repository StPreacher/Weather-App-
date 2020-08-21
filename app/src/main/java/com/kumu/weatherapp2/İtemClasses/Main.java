package com.kumu.weatherapp2.İtemClasses;

import com.google.gson.annotations.SerializedName;

public class Main {

    @SerializedName("temp")
    private Double temp;

    @SerializedName("feels_like")
    private Double feelsLike;

    @SerializedName("temp_min")
    private Double tempMin;

    @SerializedName("temp_max")
    private Double tempMax;

    @SerializedName("pressure")
    private Double pressure;

    @SerializedName("humidity")
    private Double humidty;


    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(Double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getHumidty() {
        return humidty;
    }

    public void setHumidty(Double humidty) {
        this.humidty = humidty;
    }
}
