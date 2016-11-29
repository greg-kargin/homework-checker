package ru.gregkargin.checker.tester;

public class TesterException extends Exception {
    TesterException(String message) {
        super(message);
    }
    TesterException(String message, Throwable cause) {
        super(message, cause);
    }
}
