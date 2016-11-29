package ru.gregkargin.checker.compiler;

public class CompilerException extends Exception {
    public CompilerException(final String message) {
        super(message);
    }
    public CompilerException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
