package Weather;

import Logs.ReadAndWriteLog;

public class SunnyWeather implements WeatherState {


    @Override
    public void getWeatherStatus() throws InterruptedException {

        System.out.println(" weather status  is : Sunny  = = =  <  ()  > = = =  ");
        Thread.sleep(2000);

    }

}




