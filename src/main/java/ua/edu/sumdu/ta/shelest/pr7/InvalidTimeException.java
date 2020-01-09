package ua.edu.sumdu.ta.shelest.pr7;

public class InvalidTimeException extends RuntimeException {

    static final String message = "Given time parameters are invalid";

    public InvalidTimeException() {
        super(message);
    }
}
