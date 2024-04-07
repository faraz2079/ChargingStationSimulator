package CarChargingSimulator.Car;

import java.time.LocalDateTime;
import java.util.Date;

public class Car implements Comparable{
    private LocalDateTime arriveTime;
    private String carId;
    private String carName ;
    private Battery battery;

    public Car(String carId, String carName, Battery battery) {
        this.carId = carId;
        this.carName = carName;
        this.battery = battery;
    }

    public LocalDateTime getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(LocalDateTime arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public Battery getBattery() {
        return battery;
    }

    public void setBattery(Battery battery) {
        this.battery = battery;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId='" + carId + '\'' +
                ", carName='" + carName + '\'' +
                '}';
    }

    //this method used to allocating cars into the station's  queue
    @Override
    public int compareTo(Object o) {
        return this.arriveTime.compareTo( ((Car) o ).arriveTime   );
    }
}
