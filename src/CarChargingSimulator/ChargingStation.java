package CarChargingSimulator;

import CarChargingSimulator.Car.Car;
import Exceptions.*;
import Logs.ReadAndWriteLog;
import Weather.WeatherState;

import java.time.LocalDateTime;
import java.util.*;

public class ChargingStation {
    private String name;
    private List<Location> locations;
    private Queue<Car> cars;

    private WeatherState weatherState;

    public ChargingStation(String name, WeatherState weatherState) {
        this.name = name;
        this.locations = new ArrayList<Location>();
        this.cars = new PriorityQueue();
        this.weatherState = weatherState;
    }

    //adding cars to the queue and predict the waiting time for cars then make right decision  , also  logs with arrival time
    public synchronized void addCar(Car car) throws InterruptedException, TimeLimitForCarException, StationQueueIsFullException {
        car.setArriveTime(LocalDateTime.now());
        ReadAndWriteLog.writeLog("\n"  );
        if (isAllLocationOccupied()) throw new StationQueueIsFullException(this.toString()); // full exception
        if (cars.size() * 8 < 15) {//each car takes 8 minutes to be charged and the fixed amount time is 15 minutes
            this.cars.add(car);
            ReadAndWriteLog.writeLog("- - - - - - - - - - - - - - - - --  ");
            ReadAndWriteLog.writeLog("car " + car + " arrived  in Station's queue " + this.name + " in : " + car.getArriveTime());
            ReadAndWriteLog.writeLog("the waiting time is  : " + cars.size() * 2 + " minutes");
            ReadAndWriteLog.writeLog("- - - - - - - - - - - - - - - - --  ");
        } else {
            throw new TimeLimitForCarException(car.getCarName());

        }
    }

    public void addLocation(Location location) {
        this.locations.add(location);
    }

    public void getWeatherState() throws InterruptedException {
        weatherState.getWeatherStatus();
    }

    public void setWeatherState(WeatherState weatherState) {
        this.weatherState = weatherState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public Queue getCars() {
        return cars;
    }

    public void setCars(Queue cars) {
        this.cars = cars;
    }

    // check weather status and resource which want to refill , throw corresponded Exceptions
    public void refillSource(Location location) throws InterruptedException, BadWeatherConditionForWaterTurbines, BadWeatherConditionForSolarCharging, BadWeatherConditionForWindyTurbines {
        String energySourceType = location.getSlot().getEnergySource().getClass().getSimpleName();
        String weatherStatus = weatherState.getClass().getSimpleName();
        //should be complete
        if (energySourceType.equalsIgnoreCase("Water")) {
            if (weatherStatus.equals("RainyWeather")) {
                ReadAndWriteLog.writeLog("energy generating with rain ... ");
                showProcessBar();
                location.getSlot().getEnergySource().energyGenerating(2000);
            } else {
                ReadAndWriteLog.writeLog("we are in another weather condition ... can't fill this resource");
                throw new BadWeatherConditionForWaterTurbines();
            }
        }
        else if(energySourceType.equalsIgnoreCase("Solar")){
            if (weatherStatus.equals("SunnyWeather")) {
                ReadAndWriteLog.writeLog("solar energy generating ... ");
                showProcessBar();
                location.getSlot().getEnergySource().energyGenerating(2000);
            } else {
                ReadAndWriteLog.writeLog("we are in another weather condition ... can't fill this resource");
                throw new BadWeatherConditionForSolarCharging();
            }

        }
        else {
            if (weatherStatus.equals("WindyWeather")) {
                ReadAndWriteLog.writeLog("windy energy generating ... ");
                showProcessBar();
                location.getSlot().getEnergySource().energyGenerating(2000);
            } else {
                ReadAndWriteLog.writeLog("we are in another weather condition ... can't fill this resource");
                throw new BadWeatherConditionForWindyTurbines();
            }

        }

    }

    //the method which show a process bar to indicate progress and make sense  things for user

    public void showProcessBar() throws InterruptedException {
        int totalProgress = 40;
        int currentProgress = 0;

        System.out.print("Progress: [");

        while (currentProgress < totalProgress) {
            currentProgress++;

            int percentage = (currentProgress * 100) / totalProgress;
            int completedBlocks = (currentProgress * 20) / totalProgress;
            int remainingBlocks = 20 - completedBlocks;

            System.out.print("=".repeat(completedBlocks));
            System.out.print(" ".repeat(remainingBlocks));
            System.out.print("] " + percentage + "%");

            System.out.print("\r");

            Thread.sleep(100);
        }

        ReadAndWriteLog.writeLog("" +
                "Progress complete!");
        Thread.sleep(2000);
    }

    private List<Thread> threads = new ArrayList<>();

    // each car from queue is passed to some random location in the station concurrently , if all are occupied then send it to other stations
    public synchronized void startWorking() throws InterruptedException {
        Random random = new Random();

        while (!cars.isEmpty()) {
            Car car = this.cars.poll();
            Thread thread = new Thread(() -> {
                int locationIndex = random.nextInt(0, locations.size());
                Location location = locations.remove(locationIndex);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (!location.isOccupied()) {
                    ReadAndWriteLog.writeLog(" ");                    location.setCar(car);
                    ReadAndWriteLog.writeLog(" + + + + + + + + + + + + + + ++ ");
                    ReadAndWriteLog.writeLog("car " + car + " went  to location :  " + location.getName());
                    ReadAndWriteLog.writeLog(" + + + + + + + + + + + + + + ++ ");
                    try {
                        location.charge();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (EnergyExhaustedException e) {
                        throw new RuntimeException(e);
                    } catch (BadWeatherConditionForWaterTurbines e) {
                        throw new RuntimeException(e);
                    } catch (BadWeatherConditionForSolarCharging e) {
                        throw new RuntimeException(e);
                    } catch (BadWeatherConditionForWindyTurbines e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            //adding thread to our list of threads
            threads.add(thread);
        }
        //run threads
        threads.forEach(x -> x.start());
        //joining thread to wait for all to do their works
        threads.forEach(x-> {
            try {
                x.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });


    }

//checks if all locations are occupied or not
    public boolean isAllLocationOccupied() {
        for (Location e : locations
        ) {
            if (!e.isOccupied()) return false;

        }
        return true;
    }
}
