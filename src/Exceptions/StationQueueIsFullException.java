package Exceptions;

public class StationQueueIsFullException extends RedirectCarException{
    public StationQueueIsFullException(String message) {
        super("the queue in " + message + " is full ");
    }
}
