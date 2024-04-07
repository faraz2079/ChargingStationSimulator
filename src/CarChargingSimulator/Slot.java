package CarChargingSimulator;

import CarChargingSimulator.Sources.EnergySource;
import Exceptions.EnergyExhaustedException;
import Exceptions.SlotExhaustedException;
import Logs.ReadAndWriteLog;

public abstract class Slot {
    private EnergySource energySource ;
    private final double capacity;
    private double currentAmount ;

    public EnergySource getEnergySource() {
        return energySource;
    }

    public void setEnergySource(EnergySource energySource) {
        this.energySource = energySource;
    }

    public double getCapacity() {
        return capacity;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public Slot(EnergySource energySource, double capacity) {
        this.energySource = energySource;
        this.capacity = capacity;
        this.currentAmount = capacity;
    }

    public double harvest(double amount  , String location , String station) throws SlotExhaustedException {
        if (currentAmount == 0 ) throw  new SlotExhaustedException(location , station);

        if (amount<=currentAmount){
            currentAmount -= amount;
            return amount;
        }

        else {
            double amount1 = currentAmount;
            currentAmount = 0 ;
            return amount1;

        }
    }
    public void  chargeSlot() throws RuntimeException, EnergyExhaustedException {
        if (currentAmount == capacity) return;
        try {

        if (Double.compare(currentAmount,0)==0  && currentAmount<capacity) {
            this.currentAmount += this.getEnergySource().energyHarvesting(capacity - currentAmount);
            chargeSlot();
        }

        }catch (EnergyExhaustedException e ){
            ReadAndWriteLog.writeLog("the source is over ");
            throw  new EnergyExhaustedException(this.energySource.getClass().getSimpleName());
        }
        catch (Exception e){
            throw  e;
        }
    }
}
