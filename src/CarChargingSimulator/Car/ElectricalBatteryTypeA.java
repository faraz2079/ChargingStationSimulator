package CarChargingSimulator.Car;

public class ElectricalBatteryTypeA implements Battery{
    private final double capacity  = 100 ;
    private  double  remainingCharge  ;

    public double getRemainingCharge() {
        return remainingCharge;
    }

    public void setRemainingCharge(double remainingCharge) {
        this.remainingCharge = remainingCharge;
    }

    public ElectricalBatteryTypeA(double remainingCharge) {
        this.remainingCharge = remainingCharge;
    }

    @Override
    public double getCapacity() {
        return capacity;
    }

    @Override
    public void charge(double amount) {

        remainingCharge += amount;
    }

    @Override
    public double getRemaining() {
        return remainingCharge;
    }

    @Override
    public String toString() {
        return "ElectricalBatteryTypeA{" +
                "capacity=" + capacity +
                ", remainingCharge=" + remainingCharge +
                '}';
    }
}
