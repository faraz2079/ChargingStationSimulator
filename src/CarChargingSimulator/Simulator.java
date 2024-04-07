package CarChargingSimulator;

import CarChargingSimulator.Car.Car;
import CarChargingSimulator.Car.ElectricalBatteryTypeA;
import CarChargingSimulator.Car.ElectricalBatteryTypeB;
import Exceptions.StationQueueIsFullException;
import Exceptions.TimeLimitForCarException;
import Logs.ReadAndWriteLog;
import Weather.WeatherState;

import java.util.ArrayList;
import java.util.List;

public class Simulator {
    private List<ChargingStation> stations;
    private WeatherState weatherState;
    private volatile List<Car> cars;

    public Simulator() {
        this.cars = new ArrayList<>();
        stations = new ArrayList<>();
    }

    public List<ChargingStation> getStations() {
        return stations;
    }

    public void setStations(List<ChargingStation> stations) {
        this.stations = stations;
    }

    public WeatherState getWeatherState() {
        return weatherState;
    }

    public void setWeatherState(WeatherState weatherState) throws InterruptedException {
        this.weatherState = weatherState;
        weatherState.getWeatherStatus();
    }

    static int i = 0;
    public void initializeAndStart() throws InterruptedException {
        ChargingStation chargingStationA = new ChargingStation("StationA", weatherState);
        ChargingStation chargingStationB = new ChargingStation("StationB", weatherState);
        ChargingStation chargingStationC = new ChargingStation("StationC", weatherState);
        Car carA = new Car("123", "Car A", new ElectricalBatteryTypeA(70));
        Car carB = new Car("456", "Car B", new ElectricalBatteryTypeB(80));
        Car carC = new Car("789", "Car C", new ElectricalBatteryTypeA(20));
        Car carD = new Car("101", "Car D", new ElectricalBatteryTypeA(30));
        Car carE = new Car("112", "Car E", new ElectricalBatteryTypeB(50));
        Car carF = new Car("131", "Car F", new ElectricalBatteryTypeA(10));
        Car carG = new Car("415", "Car G", new ElectricalBatteryTypeB(0));
        Car carH = new Car("161", "Car H", new ElectricalBatteryTypeB(0));
        Car carI = new Car("718", "Car I", new ElectricalBatteryTypeA(0));
        Location location = new Location("Location 1", new WaterPowerSlot(), chargingStationA);
        Location location2 = new Location("Location 2", new SolarPowerSlot(), chargingStationA);
        Location location3 = new Location("Location 3", new WindPowerSlot(), chargingStationA);
        Location location1B = new Location("Location 1", new WaterPowerSlot(), chargingStationB);
        Location location2B = new Location("Location 2", new SolarPowerSlot(), chargingStationB);
        Location location3B = new Location("Location 3", new WindPowerSlot(), chargingStationB);
        Location location1C = new Location("Location 1", new WaterPowerSlot(), chargingStationC);
        Location location2C = new Location("Location 2", new SolarPowerSlot(), chargingStationC);
        Location location3C = new Location("Location 3", new WindPowerSlot(), chargingStationC);
        cars.add(carA);
        cars.add(carB);
        cars.add(carC);
        cars.add(carD);
        cars.add(carE);
//        cars.add(carF);
//        cars.add(carG);
//        cars.add(carH);
//        cars.add(carI);
        chargingStationA.addLocation(location);
        chargingStationA.addLocation(location2);
        chargingStationA.addLocation(location3);
        chargingStationB.addLocation(location1B);
        chargingStationB.addLocation(location2B);
        chargingStationB.addLocation(location3B);
        chargingStationC.addLocation(location1C);
        chargingStationC.addLocation(location2C);
        chargingStationC.addLocation(location3C);
        stations.add(chargingStationA);
        stations.add(chargingStationB);
        stations.add(chargingStationC);
        cars.forEach(x -> {ReadAndWriteLog.writeLog("car" + x.getCarName() + " is created and ready ");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        stations.forEach(x ->
                {ReadAndWriteLog.writeLog("station" + x.getName() + " is created  ready with these locations : " +x.getLocations());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
        while (!cars.isEmpty()) {

            addCarToStation(cars.remove(0));


        }
        stations.get(i).startWorking();

    }

    /* the car arrived and check if all locations are occupied or the remaining time to wait is more than 15 minutes returns
    an exception
    */
    public void addCarToStation(Car car ) throws InterruptedException {
        ChargingStation station = stations.get(i);

        try {
            station.addCar(car);
        } catch (TimeLimitForCarException | StationQueueIsFullException e) {
            ReadAndWriteLog.writeLog(e.getMessage());
            station.startWorking();
            Thread.sleep(3000);

            if (i == 2) {
                i = 0 ;
                addCarToStation(car);
            } else {
                i++;
                addCarToStation(car);
            }

        }
        catch (Exception e) {
            ReadAndWriteLog.writeLog(e.getMessage());
        }



    }
}
