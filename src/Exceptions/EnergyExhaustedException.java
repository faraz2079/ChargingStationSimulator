package Exceptions;

public class EnergyExhaustedException extends ExhaustedResourceException {
    public EnergyExhaustedException(String type) {
        super("We can't supply enough energy for this type : " + type);
    }
}
