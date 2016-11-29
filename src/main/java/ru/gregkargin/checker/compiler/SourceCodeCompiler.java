package ru.gregkargin.checker.compiler;

import java.nio.file.Path;

/**
 * SourceCodeCompiler - объект позволяющий скомпилировать программу из предоставленных исходников,
 * написанных на одном из разрешенных языков программирования, определенных в перечислении
 * SupportedProgrammingLanguage.
 */
public interface SourceCodeCompiler {
    /**
     * Компилятор по заданному пути ищет файл с исходным кодом решения, написанному на одном из заданных языков
     * программирования, компилирует его и возвращает путь к скомпилированному исполняемому файлу.
     * @param pathToSourceCode путь до файла с исходным кодом
     * @param programmingLanguage язык программироания выбранный студентом
     * @return путь к скомпилированному исполняемому файлу
     * @throws CompilerException в таких ситуциях, когда
     *      1. Невозможно найти файл.
     *      2. Невозможно открыть файл
     *      3. Невозможно скомпилировать исходный код.
     *      4. Невозможно записать файл.
     */
    public Path compile(Path pathToSourceCode, SupportedProgrammingLanguage programmingLanguage) throws CompilerException;
}
