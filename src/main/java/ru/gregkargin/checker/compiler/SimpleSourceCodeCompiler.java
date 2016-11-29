package ru.gregkargin.checker.compiler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SimpleSourceCodeCompiler implements SourceCodeCompiler {

    private String assembleExecCommand(Path pathToSourceCode, SupportedProgrammingLanguage programmingLanguage)
            throws SupportedProgrammingLanguageException {
        switch (programmingLanguage) {
            case CPP: case CPP11:
            return new StringBuilder()
                    .append(programmingLanguage.getCompilerCall())
                    .append(" ")
                    .append(pathToSourceCode.toString())
                    .append(" -o ")
                    .append(pathToSourceCode.getParent() == null ? "" : pathToSourceCode.getParent() + "/")
                    .append("a.out")
                    .toString();

//            case JAVA:
//                return new StringBuilder()
//                        .append(programmingLanguage.getCompilerCall())
//                        .append(" ")
//                        .append(pathToSourceCode.toString())
//                        .append(" -d ")
//                        .append(pathToSourceCode.getParent() == null ? "" : pathToSourceCode.getParent())
//                        .toString();
            default:
                throw new SupportedProgrammingLanguageException("Unknown programming language");
        }
    }

    @Override
    public Path compile(Path pathToSourceCode, SupportedProgrammingLanguage programmingLanguage) throws CompilerException {
        Runtime compilerRuntime = Runtime.getRuntime();
        Process compilationProcess = null;
        try {
            compilationProcess = compilerRuntime.exec(assembleExecCommand(pathToSourceCode, programmingLanguage));
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
