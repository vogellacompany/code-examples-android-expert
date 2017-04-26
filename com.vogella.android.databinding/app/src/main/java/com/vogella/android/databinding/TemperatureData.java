package com.vogella.android.databinding;


public class TemperatureData  {
    private String location;
    private String celsius;

    public TemperatureData(String location, String celsius) {
        this.location = location;
        this.celsius = celsius;
    }

    public String getCelsius() {
        return celsius;
    }

    public String getLocation() {
        return location;
    }

    public  void setLocation(String location){
        this.location = location;
    }

    public void setCelsius(String celsius) {
        this.celsius = celsius;
    }

}
