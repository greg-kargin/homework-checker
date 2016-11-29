package ru.gregkargin.checker.compiler;

public class SupportedProgrammingLanguageException extends Exception {
    SupportedProgrammingLanguageException(String message) {
        super(message);
    }
    SupportedProgrammingLanguageException(String message, Throwable cause) {
        super(message, cause);
    }
}
