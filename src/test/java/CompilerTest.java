import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ru.gregkargin.checker.compiler.CompilerException;
import ru.gregkargin.checker.compiler.SimpleSourceCodeCompiler;
import ru.gregkargin.checker.compiler.SourceCodeCompiler;
import ru.gregkargin.checker.compiler.SupportedProgrammingLanguage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SuppressWarnings("Duplicates")
public class CompilerTest {
    static final String CPP_SOURCE = new StringBuilder()
            .append("#include <iostream>\n")
            .append("using namespace std;\n\n")
            .append("int main(){\n")
            .append("\treturn 0;\n")
            .append("}")
            .toString();

    static final String CPP11_SOURCE = new StringBuilder()
            .append("#include <iostream>\n")
            .append("using namespace std;\n\n")
            .append("int main(){\n")
            .append("\tauto s = string(0);\n")
            .append("\treturn 0;\n")
            .append("}")
            .toString();

    static final String JAVA_SOURCE = new StringBuilder()
            .append("public class TestClass {\n")
            .append("\tpublic static void main(String[] args) {\n")
            .append("\tint x = 1;\n")
            .append("\t}\n")
            .append("}")
            .toString();

    private Path sourcePath, executablePath;

    @Before
    public void initPath() {
        sourcePath = null;
        executablePath = null;
    }

    @After
    public void deleteFiles() throws IOException {
        Files.deleteIfExists(sourcePath);
        Files.deleteIfExists(executablePath);
        Files.deleteIfExists(executablePath);
        for (Path parent = sourcePath.getParent(); parent != null; parent = parent.getParent()) {
            Files.deleteIfExists(parent);
        }
    }

    @Test
    public void testDefaultPathAndCPP() throws IOException, CompilerException{
        sourcePath = Paths.get("default.cpp");
        executablePath = Paths.get("a.out");
        Files.write(sourcePath, CPP_SOURCE.getBytes());
        SourceCodeCompiler compiler = new SimpleSourceCodeCompiler();
        compiler.compile(sourcePath, SupportedProgrammingLanguage.CPP);
        if (!Files.exists(executablePath)) {
            throw new CompilerException("a.out was not created");
        }
    }

    @Test
    public void testSimplePathAndCPP11() throws IOException, CompilerException {
        sourcePath = Paths.get("username/users_attempt.cpp");
        executablePath = Paths.get("username/a.out");
        if (!Files.exists(sourcePath.getParent())) {
            Files.createDirectories(sourcePath.getParent());
        }
        Files.write(sourcePath, CPP11_SOURCE.getBytes());
        SourceCodeCompiler compiler = new SimpleSourceCodeCompiler();
        compiler.compile(sourcePath, SupportedProgrammingLanguage.CPP11);
        if (!Files.exists(executablePath)) {
            throw new CompilerException("a.out was not created");
        }
    }

    @Test
    public void testLongPath() throws IOException, CompilerException {
        sourcePath = Paths.get("a/b/c/d/e/f/users_attempt.cpp");
        executablePath = Paths.get("a/b/c/d/e/f/a.out");
        if (!Files.exists(sourcePath.getParent())) {
            Files.createDirectories(sourcePath.getParent());
        }
        Files.write(sourcePath, CPP11_SOURCE.getBytes());
        SourceCodeCompiler compiler = new SimpleSourceCodeCompiler();
        compiler.compile(sourcePath, SupportedProgrammingLanguage.CPP11);
        if (!Files.exists(executablePath)) {
            throw new CompilerException("a.out was not created");
        }
    }

    /*
    Добавить тест на проверку исключения возвращаемого в случае некорректного исходника.
     */

}

