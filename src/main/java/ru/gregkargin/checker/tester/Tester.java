package ru.gregkargin.checker.tester;

import java.nio.file.Path;

/**
 * Tester – объект позволяющий проверить работу скомпилированной программы с помощью заранее сформированного теста.
 */
public interface Tester {
    /**
     * Проверить работу программы на заданном тесте
     * @param pathToExecutable путь к исполняемому файлу.
     * @param pathToTest путь к тесту, тест это файл, который содержит входные данные до строки input_ends,
     *                   затем следует корректный ответ на тест.
     * @return true, если тест пройден, иначе false.
     */
    public boolean test(Path pathToExecutable, Path pathToTest) throws TesterException;
}
