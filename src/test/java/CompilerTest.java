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

    @Test
    public void testDefaultPathAndCPP() throws IOException, CompilerException{
        Path defaultPath = Paths.get("default.cpp");
        Path expectedOutputPath = Paths.get("a.out");
        Files.write(defaultPath, CPP_SOURCE.getBytes());
        SourceCodeCompiler compiler = new SimpleSourceCodeCompiler();
        compiler.compile(defaultPath, SupportedProgrammingLanguage.CPP);
        try {
            if (!Files.exists(expectedOutputPath)) {
                throw new Exception("a.out was not created");
            }
        } catch (Exception e) {
            Files.delete(defaultPath);
            throw new CompilerException("", e);
        }
        Files.delete(defaultPath);
        Files.delete(expectedOutputPath);
    }

    @Test
    public void testSimplePathAndCPP11() throws IOException, CompilerException {
        Path userPath = Paths.get("username/users_attempt.cpp");
        Path expectedOutputPath = Paths.get("username/a.out");
        if (!Files.exists(userPath.getParent())) {
            Files.createDirectories(userPath.getParent());
        }
        Files.write(userPath, CPP11_SOURCE.getBytes());
        SourceCodeCompiler compiler = new SimpleSourceCodeCompiler();
        compiler.compile(userPath, SupportedProgrammingLanguage.CPP11);
        try {
            if (!Files.exists(expectedOutputPath)) {
                throw new Exception("a.out was not created");
            }
        } catch (Exception e) {
            Files.delete(userPath);
            throw new CompilerException("", e);
        }
        Files.delete(userPath);
        Files.delete(expectedOutputPath);
    }

    @Test
    public void testLongPath() throws IOException, CompilerException {
        Path userPath = Paths.get("a/b/c/d/e/f/users_attempt.cpp");
        Path expectedOutputPath = Paths.get("a/b/c/d/e/f/a.out");
        if (!Files.exists(userPath.getParent())) {
            Files.createDirectories(userPath.getParent());
        }
        Files.write(userPath, CPP11_SOURCE.getBytes());
        SourceCodeCompiler compiler = new SimpleSourceCodeCompiler();
        compiler.compile(userPath, SupportedProgrammingLanguage.CPP11);
        try {
            if (!Files.exists(expectedOutputPath)) {
                throw new Exception("a.out was not created");
            }
        } catch (Exception e) {
            Files.delete(userPath);
            throw new CompilerException("", e);
        }
        Files.delete(userPath);
        Files.delete(expectedOutputPath);
    }

    /*
    Добавить тест на проверку исключения возвращаемого в случае некорректного исходника.
     */

}

