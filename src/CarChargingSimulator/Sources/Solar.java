package CarChargingSimulator.Sources;

import Exceptions.EnergyExhaustedException;
//solar resource with default 1000 units energy
public class Solar  extends EnergySource{
    public Solar() {
        super(5, 1000);
    }

    //harvesting fixed amount of energy from this resource
    @Override
    public double energyHarvesting(double amount1) throws EnergyExhaustedException {
        if (getTotalAvailableAmount() == 0) throw new EnergyExhaustedException(this.getClass().getSimpleName());
        if (this.getTotalAvailableAmount() - amount1 >= 0) {
            double amount = this.getTotalAvailableAmount() - amount1;
            this.setTotalAvailableAmount(amount);
            return amount1 ;
        }
        else throw  new EnergyExhaustedException(this.getClass().getSimpleName());
    }
}
