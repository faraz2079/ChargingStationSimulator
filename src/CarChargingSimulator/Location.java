package CarChargingSimulator;

import CarChargingSimulator.Car.Car;
import Exceptions.*;
import Logs.ReadAndWriteLog;

import java.sql.SQLOutput;

public class  Location {

    private ChargingStation chargingStation;
    private String name;

    private Slot slot;


   volatile private Car car;

    public Location(String name, Slot slot, ChargingStation chargingStation) {
        this.name = name;
        this.slot = slot;
        this.chargingStation = chargingStation;
    }

    public boolean isOccupied() {
        return this.car != null;
    }

    //charge a car based on remaining amount in slot , if slot exhausted refill it by exception handling also if the energy resource  exhausted try to generate energy and so on ....
    public synchronized void charge() throws InterruptedException, EnergyExhaustedException, BadWeatherConditionForWaterTurbines, BadWeatherConditionForSolarCharging, BadWeatherConditionForWindyTurbines {
        Thread.sleep(2000);
        ReadAndWriteLog.writeLog(" ");//        ReadAndWriteLog.writeLog("*******************************************************");
        Thread.sleep(1000);
            ReadAndWriteLog.writeLog("remaining amount in slot for < " + this.getName() + " > in  "+this.chargingStation.getName()+" is " + slot.getCurrentAmount());
        Thread.sleep(1000);
//        ReadAndWriteLog.writeLog("*******************************************************");
        ReadAndWriteLog.writeLog(" ");        Thread.sleep(1000);
        ReadAndWriteLog.writeLog(" ");        ReadAndWriteLog.writeLog(" ");        Thread.sleep(2000);
//        ReadAndWriteLog.writeLog("----------------------------------------------------------------");
        Thread.sleep(1000);
        ReadAndWriteLog.writeLog("Car :" + car + "\n" + "with Battery status : " + car.getBattery());
        Thread.sleep(1000);
//        ReadAndWriteLog.writeLog("----------------------------------------------------------------");
        Thread.sleep(1000);
        ReadAndWriteLog.writeLog(" ");        Thread.sleep(2000);

        ReadAndWriteLog.writeLog(" the car " + car +" in "+ getName() +" in  "+ chargingStation.getName()+" is charging ... ");
        chargingStation.showProcessBar();
        double carBatteryRemaining = car.getBattery().getRemaining();
        double carBatteryCapacity = car.getBattery().getCapacity();
        if (carBatteryCapacity - carBatteryRemaining > 0) {
            Thread.sleep(1000);
            try {
                car.getBattery().charge(this.slot.harvest(carBatteryCapacity - carBatteryRemaining , getName() , chargingStation.getName()));
                Thread.sleep(1000);
            } catch (SlotExhaustedException e) {
        Thread.sleep(2000);
        try {
                reFillSlot();
        }catch (EnergyExhaustedException e1){

            this.chargingStation.refillSource(this);
        }
        catch (Exception e2){
            throw  e2;
        }

        Thread.sleep(2000);
            }

            finally {
                Thread.sleep(2000);
                ReadAndWriteLog.writeLog(" ");                ReadAndWriteLog.writeLog("##########################################################");
                Thread.sleep(1000);
                ReadAndWriteLog.writeLog(" in  :{ "+chargingStation.getName()+" }  the  :"+ car.getCarName() +" ----> battery status after this charge pump : " + car.getBattery());
                Thread.sleep(1000);
                ReadAndWriteLog.writeLog("remaining amount in slot " + slot.getCurrentAmount());
                Thread.sleep(1000);
                ReadAndWriteLog.writeLog("##########################################################");
                ReadAndWriteLog.writeLog(" ");                Thread.sleep(2000);
                charge();
            }

        } else {
            ReadAndWriteLog.writeLog("the  "+ car.getCarName()+"  is gone ");
            detach();
        }


    }

    //refilling slot with its capacity and log it
    private void reFillSlot() throws EnergyExhaustedException {
            ReadAndWriteLog.writeLog(" ");            ReadAndWriteLog.writeLog(" -------= IN  refill function =---------");
            ReadAndWriteLog.writeLog(" ");            ReadAndWriteLog.writeLog("the Charge Slot in  " + this.getName() + " is Empty  .... now we are refilling it");
            this.slot.chargeSlot();
            ReadAndWriteLog.writeLog("refilled the current amount slot in  " + this.getName() + " : " + slot.getCurrentAmount() + "\n----------= END OF REFILL =-------------\n");

    }

    public void detach() {
        this.car = null;
    }

    private void chargeSlot() throws EnergyExhaustedException {

        this.slot.getEnergySource().energyHarvesting(this.slot.getCapacity());

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Car getCar() {
        return car;
    }

    public synchronized void setCar(Car car) {
        this.car = car;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    @Override
    public String toString() {
        return "{" +
                ", name='" + name + '\'' +
                '}';
    }
}
