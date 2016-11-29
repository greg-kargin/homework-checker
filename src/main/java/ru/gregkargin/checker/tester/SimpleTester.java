package ru.gregkargin.checker.tester;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SimpleTester implements Tester {
    public static final String TEST_SEPARATOR = "input_ends";

    private boolean checkTest(BufferedReader stdInput, BufferedReader testInput) throws IOException {
        String testLine = "";
        while (!testLine.equals(TEST_SEPARATOR) && testInput != null) {
            testLine = testInput.readLine();
        }

        testLine = testInput.readLine();
        String inputLine = stdInput.readLine();
        while (testLine != null) {
            if (inputLine == null) {
                return false;
            }
            if (!testLine.trim().equals(inputLine.trim())) {
                return false;
            }
            testLine = testInput.readLine();
            inputLine = stdInput.readLine();
        }
        return true;
    }

    @Override
    public boolean test(Path pathToExecutable, Path pathToTest) throws TesterException {
        Process testingProcess;
        try {
            testingProcess = new ProcessBuilder()
                    .command(pathToExecutable.toString())
                    .redirectInput(pathToTest.toFile())
                    .start();
        } catch (IOException e) {
            throw new TesterException("Could not run executable", e);
        }

        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(testingProcess.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(testingProcess.getErrorStream()));

        try {
            String error;
            if (stdError.readLine() != null && !(error = stdError.readLine()).isEmpty()) {
                throw new TesterException(error);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader testInput;
        try {
            testInput = new BufferedReader(Files.newBufferedReader(pathToTest));
        } catch (IOException e) {
            throw new TesterException("Could not read test file", e);
        }

        try {
            return checkTest(stdInput, testInput);
        } catch (IOException e) {
            throw new TesterException("IOerror during checking", e);
        }
    }
}
