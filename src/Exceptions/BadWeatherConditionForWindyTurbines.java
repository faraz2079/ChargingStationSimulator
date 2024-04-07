package Exceptions;

public class BadWeatherConditionForWindyTurbines extends RechargingMismatchException{
    public BadWeatherConditionForWindyTurbines() {
        super("the current weather is not appropriate for windy turbines");
    }
}
