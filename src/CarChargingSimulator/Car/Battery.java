package CarChargingSimulator.Car;
//battery interface to cars
public interface Battery {
    double getCapacity();
    void charge(double amount);

    double getRemaining();


}
