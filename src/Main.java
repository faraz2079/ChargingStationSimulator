import CarChargingSimulator.ChargingStation;
import CarChargingSimulator.Simulator;
import Logs.ReadAndWriteLog;
import Weather.RainyWeather;
import Weather.SunnyWeather;
import Weather.WeatherState;
import Weather.WindyWeather;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.in;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        while (true) {
            System.out.println("Welcome to Charging Station Simulator");
            System.out.println("-- choose one option --");
            System.out.println("1.show all logs ");
            System.out.println("2.delete logs");
            System.out.println("3.start simulating");
            Scanner input = new Scanner(in);
            int x = input.nextInt();
            if (x == 1){
                ReadAndWriteLog.writeLog("user wants to see logs");
                ReadAndWriteLog.readLog();}
            else if (x ==2) {
                ReadAndWriteLog.writeLog("user wants delete logs");
                ReadAndWriteLog.deleteLog();}
            else {
                ReadAndWriteLog.writeLog(" -- user START Simulation --");
                Simulator simulator = new Simulator();
                System.out.println("select Weather status ");
                System.out.println("1. Rainy ");
                System.out.println("2. Sunny");
                System.out.println("3. Windy");
                int y = input.nextInt();
                WeatherState weatherState;
                switch (y){
                    case 1 :{
                        weatherState = new RainyWeather();
                        break;
                    }
                    case 2 :{
                        weatherState = new SunnyWeather();
                        break;
                    }
                    case 3 :{
                        weatherState = new WindyWeather();
                        break;
                    }
                    default:{
                         weatherState = new RainyWeather();
                        break;
                    }
                }
                simulator.setWeatherState(weatherState);
                simulator.initializeAndStart();
            }
        }

    }
}