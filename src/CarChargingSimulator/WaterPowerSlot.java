package CarChargingSimulator;

import CarChargingSimulator.Sources.EnergySource;
import CarChargingSimulator.Sources.Water;

public class WaterPowerSlot extends Slot{
    public WaterPowerSlot( ) {
        super(new Water(), 80);
    }
}
