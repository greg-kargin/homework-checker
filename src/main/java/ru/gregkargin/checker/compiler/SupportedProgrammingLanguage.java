package ru.gregkargin.checker.compiler;

/**
 * Перечилсение содержащие поддерживаемые компилятором языки программирования
 */
public enum SupportedProgrammingLanguage {
    CPP("clang++"),
    CPP11("clang++ -std=c++11 -stdlib=libc++");
    //JAVA("javac");

    private String compilerCall;
    SupportedProgrammingLanguage(String compilerCall) {
        this.compilerCall = compilerCall;
    }

    public String getCompilerCall() {
        return compilerCall;
    }

    static SupportedProgrammingLanguage fromString(String languageName) throws SupportedProgrammingLanguageException {
        switch(languageName) {
            case "c++" :
                return SupportedProgrammingLanguage.CPP;
            case "c++11" :
                return SupportedProgrammingLanguage.CPP11;
            //case "java" :
            //    return SupportedProgrammingLanguage.JAVA;
            default :
                throw new SupportedProgrammingLanguageException("UnsupportedLanguage");
        }
    }

}
