package ru.gregkargin.checker.compiler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SimpleSourceCodeCompiler implements SourceCodeCompiler {

    private String[] getCommand(Path pathToSourceCode, SupportedProgrammingLanguage programmingLanguage)
            throws SupportedProgrammingLanguageException {
        switch (programmingLanguage) {
            case CPP:
                return new String[] {
                     "clang++", pathToSourceCode.toString(), "-o",
                     pathToSourceCode.getParent() == null ? "a.out" : pathToSourceCode.getParent() + "/a.out"
                };

            case CPP11:
                return new String[] {
                        "clang++", "-std=c++11", "-lib=libc++", pathToSourceCode.toString(), "-o",
                        pathToSourceCode.getParent() == null ? "a.out" : pathToSourceCode.getParent() + "/a.out"
                };

            default:
                throw new SupportedProgrammingLanguageException("Unknown programming language");
        }
    }

    @Override
    public Path compile(Path pathToSourceCode, SupportedProgrammingLanguage programmingLanguage) throws CompilerException {
        Process compilationProcess;
        try {
            compilationProcess = new ProcessBuilder()
                    .command(getCommand(pathToSourceCode, programmingLanguage))
                    .start();
        } catch (Exception e) {
            throw new CompilerException("Could not execute:", e);
        }

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(compilationProcess.getErrorStream()));

        try {
            String error;
            if (stdError.readLine() != null && !(error = stdError.readLine()).isEmpty()) {
                throw new CompilerException(error);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathToSourceCode.getParent() == null ? Paths.get("a.out") :
                Paths.get(pathToSourceCode.getParent().toString() + "/a.out");
    }
}
