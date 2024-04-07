package Weather;

import Logs.ReadAndWriteLog;

public class RainyWeather implements WeatherState {

    @Override
    public void getWeatherStatus() throws InterruptedException {
        System.out.println(" weather status  is : Rainy ");
        System.out.println(" =========   ===  ===  =====  ");
        System.out.println(" | | | | |   | |  | |  | | |  ");
        System.out.println(" | | | | |   | |  | |  | | |  ");
        System.out.println(" | | | | |   | |  | |  | | |  ");
        System.out.println(" | | | | |   | |  | |  | | |  ");
        System.out.println(" | | | | |   | |  | |  | | |  ");
        Thread.sleep(2000);
    }
}

