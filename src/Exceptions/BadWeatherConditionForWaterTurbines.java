package Exceptions;

public class BadWeatherConditionForWaterTurbines extends RechargingMismatchException{
    public BadWeatherConditionForWaterTurbines() {
        super("the current weather is not appropriate for windy recharging");
    }
}
