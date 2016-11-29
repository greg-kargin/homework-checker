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

    @Test
    public void noInputNoOutputTest() throws IOException, CompilerException, TesterException {
        Path sourcePath = Paths.get("TesterTestDir/NO_INPUT_NO_OUTPUT.cpp");
        Path testPath = Paths.get("TesterTestDir/NO_INPUT_NO_OUTPUT.in");

        if (!Files.exists(sourcePath.getParent())) {
            Files.createDirectories(sourcePath.getParent());
        }

        Files.write(Paths.get("TesterTestDir/NO_INPUT_NO_OUTPUT.cpp"),
                NO_INPUT_NO_OUTPUT_SOURCE.getBytes());

        Files.write(Paths.get("TesterTestDir/NO_INPUT_NO_OUTPUT.in"),
                NO_INPUT_NO_OUTPUT_TESTFILE.getBytes());

        SourceCodeCompiler codeCompiler = new SimpleSourceCodeCompiler();
        Path executablePath = codeCompiler.compile(sourcePath, SupportedProgrammingLanguage.CPP);

        Tester tester = new SimpleTester();
        assertTrue(tester.test(executablePath, testPath));
    }

    public void noInputOutputTest() throws IOException, CompilerException, TesterException {
        Path sourcePath = Paths.get("TesterTestDir/NO_INPUT_NO_OUTPUT.cpp");
        Path testPath = Paths.get("TesterTestDir/NO_INPUT_OUTPUT.in");

        if (!Files.exists(sourcePath.getParent())) {
            Files.createDirectories(sourcePath.getParent());
        }

        Files.write(Paths.get("TesterTestDir/NO_INPUT_NO_OUTPUT.cpp"),
                NO_INPUT_NO_OUTPUT_SOURCE.getBytes());

        Files.write(Paths.get("TesterTestDir/NO_INPUT_OUTPUT.in"),
                NO_INPUT_OUTPUT_TESTFILE.getBytes());

        SourceCodeCompiler codeCompiler = new SimpleSourceCodeCompiler();
        Path executablePath = codeCompiler.compile(sourcePath, SupportedProgrammingLanguage.CPP);

        Tester tester = new SimpleTester();
        assertFalse(tester.test(executablePath, testPath));
    }
}
