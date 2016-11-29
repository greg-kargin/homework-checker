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

    static final String NO_INPUT_NO_OUTPUT_SOURCE = new StringBuilder()
            .append("int main(){\n")
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

        Files.write(Paths.get("TesterTestDir/NO_INPUT_NO_OUTPUT.cpp"),
                NO_INPUT_NO_OUTPUT_SOURCE.getBytes());

        Files.write(Paths.get("TesterTestDir/NO_INPUT_OUTPUT.in"),
                NO_INPUT_OUTPUT_TESTFILE.getBytes());

        SourceCodeCompiler codeCompiler = new SimpleSourceCodeCompiler();
        executablePath = codeCompiler.compile(sourcePath, SupportedProgrammingLanguage.CPP);

        Tester tester = new SimpleTester();
        assertFalse(tester.test(executablePath, testPath));
    }
}
