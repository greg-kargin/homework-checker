import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.gregkargin.checker.compiler.CompilerException;
import ru.gregkargin.checker.compiler.SimpleSourceCodeCompiler;
import ru.gregkargin.checker.compiler.SourceCodeCompiler;
import ru.gregkargin.checker.compiler.SupportedProgrammingLanguage;
import ru.gregkargin.checker.tester.SimpleTester;
import ru.gregkargin.checker.tester.Tester;
import ru.gregkargin.checker.tester.TesterException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

@SuppressWarnings("Duplicates")
public class TesterTest {
    static final String NO_INPUT_NO_OUTPUT_TESTFILE = new StringBuilder()
            .append(SimpleTester.TEST_SEPARATOR)
            .toString();

    static final String NO_INPUT_OUTPUT_TESTFILE = new StringBuilder()
            .append(SimpleTester.TEST_SEPARATOR)
            .append("\nabcd")
            .toString();

    static final String INPUT_OUTPUT_SOURCE = new StringBuilder()
            .append("#include <iostream> \n")
            .append("int main(){\n")
            .append("\t std::cout << \"abcd\";")
            .append("}")
            .toString();

    static final String INPUT_OUTPUT_TESTFILE = new StringBuilder()
            .append(SimpleTester.TEST_SEPARATOR)
            .append("\nabcd")
            .toString();

    static final String NO_INPUT_NO_OUTPUT_SOURCE = new StringBuilder()
            .append("int main(){\n")
            .append("}")
            .toString();

    static final String COMPUTE_OUTPUT_SOURCE = new StringBuilder()
            .append("#include<iostream> \n")
            .append("int32_t fib(int32_t i) {\n")
            .append("\tif(i == 0 || i == 1)\n")
            .append("\t\treturn 1;\n")
            .append("\telse\n")
            .append("\t\treturn fib(i-1) + fib(i-2);\n")
            .append("}\n")
            .append("int main() {\n")
            .append("\tint32_t i;\n")
            .append("\tstd::cin >> i;\n")
            .append("\tstd::cout << fib(i);\n")
            .append("}\n")
            .toString();

    static final String COMPUTE_OUTPUT_TESTFILE = new StringBuilder()
            .append("10\n")
            .append(SimpleTester.TEST_SEPARATOR)
            .append("\n")
            .append("89")
            .toString();

    static final String INPUT_TESTFILE = new StringBuilder()
            .append("10\n")
            .append(SimpleTester.TEST_SEPARATOR)
            .toString();

    static final String INPUT_SOURCE = new StringBuilder()
            .append("#include <iostream>\n")
            .append("int main() {\n")
            .append("\tint i; \n")
            .append("\tstd::cin >> i;\n")
            .append("}")
            .toString();

    private Path sourcePath, testPath, executablePath;

    @Before
    public void initPaths() {
        sourcePath = null;
        testPath = null;
        executablePath = null;
    }

    @After
    public void deleteFiles() throws IOException {
        Files.deleteIfExists(sourcePath);
        Files.deleteIfExists(testPath);
        Files.delete(executablePath);
        for (Path parent = sourcePath.getParent(); parent != null; parent = parent.getParent()) {
            Files.deleteIfExists(parent);
        }
    }

    @Test
    public void noInputNoOutputTest() throws IOException, CompilerException, TesterException {
        sourcePath = Paths.get("TesterTestDir/NO_INPUT_NO_OUTPUT.cpp");
        testPath = Paths.get("TesterTestDir/NO_INPUT_NO_OUTPUT.in");

        if (!Files.exists(sourcePath.getParent())) {
            Files.createDirectories(sourcePath.getParent());
        }

        Files.write(sourcePath, NO_INPUT_NO_OUTPUT_SOURCE.getBytes());
        Files.write(testPath, NO_INPUT_NO_OUTPUT_TESTFILE.getBytes());


        SourceCodeCompiler codeCompiler = new SimpleSourceCodeCompiler();
        executablePath = codeCompiler.compile(sourcePath, SupportedProgrammingLanguage.CPP);

        Tester tester = new SimpleTester();
        assertTrue(tester.test(executablePath, testPath));
    }

    @Test
    public void noInputOutputTest() throws IOException, CompilerException, TesterException {
        sourcePath = Paths.get("TesterTestDir/NO_INPUT_NO_OUTPUT.cpp");
        testPath = Paths.get("TesterTestDir/NO_INPUT_OUTPUT.in");

        if (!Files.exists(sourcePath.getParent())) {
            Files.createDirectories(sourcePath.getParent());
        }

        Files.write(sourcePath, NO_INPUT_NO_OUTPUT_SOURCE.getBytes());
        Files.write(testPath, NO_INPUT_OUTPUT_TESTFILE.getBytes());

        SourceCodeCompiler codeCompiler = new SimpleSourceCodeCompiler();
        executablePath = codeCompiler.compile(sourcePath, SupportedProgrammingLanguage.CPP);

        Tester tester = new SimpleTester();
        assertFalse(tester.test(executablePath, testPath));
    }

    @Test
    public void InputOutputTest() throws IOException, CompilerException, TesterException {
        sourcePath = Paths.get("TesterTestDir/INPUT_OUTPUT.cpp");
        testPath = Paths.get("TesterTestDir/INPUT_OUTPUT.in");

        if (!Files.exists(sourcePath.getParent())) {
            Files.createDirectories(sourcePath.getParent());
        }

        Files.write(sourcePath, INPUT_OUTPUT_SOURCE.getBytes());
        Files.write(testPath, INPUT_OUTPUT_TESTFILE.getBytes());

        SourceCodeCompiler codeCompiler = new SimpleSourceCodeCompiler();
        executablePath = codeCompiler.compile(sourcePath, SupportedProgrammingLanguage.CPP);

        Tester tester = new SimpleTester();
        assertTrue(tester.test(executablePath, testPath));
    }

    @Test
    public void computeOutput() throws IOException, CompilerException, TesterException {
        sourcePath = Paths.get("TesterTestDir/COMPUTE_OUTPUT.cpp");
        testPath = Paths.get("TesterTestDir/COMPUTE_OUTPUT.in");

        if (!Files.exists(sourcePath.getParent())) {
            Files.createDirectories(sourcePath.getParent());
        }

        Files.write(sourcePath, COMPUTE_OUTPUT_SOURCE.getBytes());
        Files.write(testPath, COMPUTE_OUTPUT_TESTFILE.getBytes());

        SourceCodeCompiler codeCompiler = new SimpleSourceCodeCompiler();
        executablePath = codeCompiler.compile(sourcePath, SupportedProgrammingLanguage.CPP);

        Tester tester = new SimpleTester();
        assertTrue(tester.test(executablePath, testPath));
    }

    @Test
    public void InputTest() throws IOException, CompilerException, TesterException {
        sourcePath = Paths.get("TesterTestDir/INPUT.cpp");
        testPath = Paths.get("TesterTestDir/INPUT.in");

        if (!Files.exists(sourcePath.getParent())) {
            Files.createDirectories(sourcePath.getParent());
        }

        Files.write(sourcePath, INPUT_SOURCE.getBytes());
        Files.write(testPath, INPUT_TESTFILE.getBytes());

        SourceCodeCompiler codeCompiler = new SimpleSourceCodeCompiler();
        executablePath = codeCompiler.compile(sourcePath, SupportedProgrammingLanguage.CPP);

        Tester tester = new SimpleTester();
        assertTrue(tester.test(executablePath, testPath));
    }
}
