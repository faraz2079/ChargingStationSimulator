package Test;


import CarChargingSimulator.Car.Car;
import CarChargingSimulator.Car.ElectricalBatteryTypeA;
import CarChargingSimulator.Car.ElectricalBatteryTypeB;
import CarChargingSimulator.ChargingStation;
import CarChargingSimulator.Location;
import CarChargingSimulator.SolarPowerSlot;
import CarChargingSimulator.WaterPowerSlot;
import Exceptions.*;
import Logs.ReadAndWriteLog;
import Weather.RainyWeather;
import Weather.WeatherState;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.converter.ConvertWith;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.*;

public class junitTests {
    static WeatherState weatherState;
    static ChargingStation chargingStationA;
    static Location location;
    static Car carA;
    static Car carB;
    static Car carC;
    static Location location2;

    @BeforeClass
    public static void init() throws TimeLimitForCarException, StationQueueIsFullException, InterruptedException {
        weatherState = new RainyWeather();
        chargingStationA = new ChargingStation("StationA", weatherState);
        carA = new Car("123", "Car A", new ElectricalBatteryTypeA(0));
        carB = new Car("456", "Car B", new ElectricalBatteryTypeB(80));
        carC = new Car("789", "Car C", new ElectricalBatteryTypeA(20));
        location = new Location("Location 1", new SolarPowerSlot(), chargingStationA);
        location2 = new Location("Location 2", new SolarPowerSlot(), chargingStationA);
        chargingStationA.addLocation(location);
        chargingStationA.addLocation(location2);
        chargingStationA.getLocations().get(0).setCar(carA);

    }

    //this unit test  is asserting if a car with empty battery is correctly charging

    @Test
    public void chargeTest() throws EnergyExhaustedException, BadWeatherConditionForWaterTurbines, BadWeatherConditionForSolarCharging, BadWeatherConditionForWindyTurbines, InterruptedException {
        location.charge();
        assertEquals(carA.getBattery().getCapacity(), carA.getBattery().getRemaining(), 0);
    }

    //this unit test checks whether an empty slot is refilling correctly or not

    @Before
    public void setSlotRemainingZero() {

        location.getSlot().setCurrentAmount(0);
    }

    @Test(expected = SlotExhaustedException.class)
    public void slotRefill() throws EnergyExhaustedException, BadWeatherConditionForWaterTurbines, BadWeatherConditionForSolarCharging, BadWeatherConditionForWindyTurbines, InterruptedException, SlotExhaustedException {
        location.getSlot().harvest(150, location.getName(), chargingStationA.getName());
    }

    @Before
    public void addCarToStation() throws TimeLimitForCarException, StationQueueIsFullException, InterruptedException {
        chargingStationA.addCar(carA);
        chargingStationA.addCar(carB);
    }

    @Test(expected = TimeLimitForCarException.class)
    public void sendCarToACrowdStation() throws TimeLimitForCarException, StationQueueIsFullException, InterruptedException {
        chargingStationA.addCar(carC);

    }

    @Test(expected = BadWeatherConditionForSolarCharging.class)
    public void rechargeSources() throws BadWeatherConditionForWaterTurbines, BadWeatherConditionForSolarCharging, BadWeatherConditionForWindyTurbines, InterruptedException {
        location.getSlot().getEnergySource().setTotalAvailableAmount(0);
        chargingStationA.refillSource(location);
    }

    @Test
    public void deleteLogTesting() throws IOException {
        ReadAndWriteLog.deleteLog();
        FileReader fileReader = new FileReader(".\\src\\Logs\\logs.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();
        assertEquals("this method isn't works correctly", " ", line);
    }

    @Test
    public void detachCarAFromLocation() {
        location.detach();
        assertNull(location.getCar());
    }

    @Test
    public void locationToStringTest() {
        assertEquals("{" +
                ", name='" + location.getName() + '\'' +
                '}', location.toString());
    }

    @Before
    public void initializeSource() {
        location.getSlot().getEnergySource().energyGenerating(1000);
    }

    @Test
    public void sourceEnergyGenerating() {
        location.getSlot().getEnergySource().energyGenerating(2000);
        assertEquals(4000, location.getSlot().getEnergySource().getTotalAvailableAmount(), 0);
    }

    @Test
    public void energyHarvesting() throws EnergyExhaustedException {
        location.getSlot().getEnergySource().energyHarvesting(1000);
        assertEquals(1000, location.getSlot().getEnergySource().getTotalAvailableAmount(), 0);
    }


    @Test
    public void logWritingTest() throws IOException {
        ReadAndWriteLog.deleteLog();
        ReadAndWriteLog.writeLog("test");
        FileReader fileReader = new FileReader(".\\src\\Logs\\logs.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();
                 line = bufferedReader.readLine();
        assertEquals( "{  test}"  ,line.substring(line.indexOf('[')+1 , line.indexOf(']')).strip());


    }



}
