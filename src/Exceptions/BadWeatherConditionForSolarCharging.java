package Exceptions;

public class BadWeatherConditionForSolarCharging extends RechargingMismatchException{
    public BadWeatherConditionForSolarCharging() {
        super("the current weather is not appropriate for solar panel recharging");
    }
}
