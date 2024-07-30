package telran.util;

public class RegularExpressions {
    public static String firstName() {
        //regex for strings starting with capital letter and rest as lowercase letters
        //minimal length is 5 letters
        return "[A-Z][a-z]{4,}";
    }

    public static String javaVariable() {
        //regular expression for testing syntax of Java variable names
        //only ACII symbols are allowed
        return "^[a-zA-Z$][a-zA-Z_$0-9]*|^[_][a-zA-Z_$0-9]{1,}";
    }

}
