package com.example.weatherapptutorial;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherData {

    private String mTemperature;
    private String mIcon;
    private String mCity;
    private String mWeatherType;

    private String mWindSpeed;

    private String mPressure;

    private int mHumidity;
    private int mCondition;

    public static WeatherData fromJson(JSONObject jsonObject)
    {

        try
        {
            WeatherData weatherD = new WeatherData();
            weatherD.mCity = jsonObject.getString("name");
            weatherD.mCondition = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            weatherD.mWeatherType = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.mIcon = updateWeatherIcon(weatherD.mCondition);
            double tempResult = jsonObject.getJSONObject("main").getDouble("temp")-273.15;
            weatherD.mHumidity = (jsonObject.getJSONObject("main").getInt("humidity"));
            int roundedValue = (int)Math.rint(tempResult);
            weatherD.mTemperature = Integer.toString(roundedValue);
            weatherD.mWindSpeed = String.valueOf(jsonObject.getJSONObject("wind").getDouble("speed"));
            weatherD.mPressure = String.valueOf(jsonObject.getJSONObject("main").getInt("pressure"));

            return weatherD;
        }


         catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }


    private static String updateWeatherIcon(int condition)
    {
        if(condition>=0 && condition<=300)
        {
            return "thunderstorm1";
        }
        else if(condition >= 300 && condition <= 500)
        {
            return "light-rain";
        }
        else if(condition >= 500 && condition <= 600)
        {
            return "shower";
        }
       else  if(condition >= 600 && condition <= 700)
        {
            return "snow2";
        }
        else if(condition>=701 && condition<=771)
        {
            return "fog";
        }

        else if(condition>=772 && condition<=800)
        {
            return "overcast";
        }
       else if(condition>=801 && condition<=804)
       {
           return "cloudy";
       }
      else  if(condition>=900 && condition<=902)
       {
           return "thunderstorm1";
       }
        if(condition==903)
        {
            return "snow1";
        }
        if(condition==904)
        {
            return "sunny";
        }
        if(condition>=905 && condition<=1000)
        {
            return "thunderstorm2";
        }

        return "dunno";


    }

    public String getTemperature() {
        return mTemperature+"°C";
    }

    public String getIcon() {
        return mIcon;
    }

    public String getCity() {
        return mCity;
    }

    public String getWeatherType() {
        return mWeatherType;
    }

    public String getMoreData() { return ("Humidity: " + String.valueOf(mHumidity) + '%' + "\n\n Wind Speed: " + mWindSpeed + "Km/h \n\n Air Pressure: " + mPressure + "atm");}
}
