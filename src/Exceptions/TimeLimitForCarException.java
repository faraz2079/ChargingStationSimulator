package Exceptions;

public class TimeLimitForCarException extends RedirectCarException{
    public TimeLimitForCarException(String message) {
        super("the cars after  " +  message + " can't wait in this line because this station takes time more than 15 minutes");
    }
}
