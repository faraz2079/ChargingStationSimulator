package CarChargingSimulator.Sources;

import Exceptions.EnergyExhaustedException;

public abstract class EnergySource {
    public EnergySource(int minutesToFull50Unit, double totalAvailableAmount) {
        this.minutesToFull50Unit = minutesToFull50Unit;
        TotalAvailableAmount = totalAvailableAmount;
    }

    public int getMinutesToFull50Unit() {
        return minutesToFull50Unit;
    }

    public void setMinutesToFull50Unit(int minutesToFull50Unit) {
        this.minutesToFull50Unit = minutesToFull50Unit;
    }

    public double getTotalAvailableAmount() {
        return TotalAvailableAmount;
    }

    public void setTotalAvailableAmount(double totalAvailableAmount) {
        TotalAvailableAmount = totalAvailableAmount;
    }

    private  int minutesToFull50Unit ; // the amount of time to charge 50 unit of a battery ( no matter type A or type B )

    private double TotalAvailableAmount; // total available amount of this resource

    public abstract double energyHarvesting(double amount) throws EnergyExhaustedException;
     public   void energyGenerating(double amount){

         this.TotalAvailableAmount += amount;
     }




}
