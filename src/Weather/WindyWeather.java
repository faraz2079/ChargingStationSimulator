package Weather;

import Logs.ReadAndWriteLog;

public class WindyWeather implements WeatherState{
    @Override
    public void  getWeatherStatus() throws InterruptedException {
        Thread.sleep(2000);
        ReadAndWriteLog.writeLog("the Weather  Condition state is WINDY  now ");
        ReadAndWriteLog.writeLog("the winds are blowing ... ");
        ReadAndWriteLog.writeLog("~~~~ " +  "~~~~ " + "~~~~ ");
        ReadAndWriteLog.writeLog("~~~~ " +  "~~~~ " + "~~~~ ");
        ReadAndWriteLog.writeLog("~~~~ " +  "~~~~ " + "~~~~ ");
        ReadAndWriteLog.writeLog("~~~~ " +  "~~~~ " + "~~~~ ");
    }
        }





