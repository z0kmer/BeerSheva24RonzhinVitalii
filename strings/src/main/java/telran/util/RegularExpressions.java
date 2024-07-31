package telran.util;

import java.util.Arrays;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.lang.model.SourceVersion;

public class RegularExpressions {
    static final String keyWords[] = { "abstract", "assert", "boolean",
                    "break", "byte", "case", "catch", "char", "class", "const",
                    "continue", "default", "do", "double", "else", "enum", "extends", "false",
                    "final", "finally", "float", "for", "goto", "if", "implements",
                    "import", "instanceof", "int", "interface", "long", "native",
                    "new", "null", "package", "private", "protected", "public",
                    "return", "short", "static", "strictfp", "super", "switch",
                    "synchronized", "this", "throw", "throws", "transient", "true",
                    "try", "void", "volatile", "while" };
    
    public static String firstName() {
            // regex for strings startin with
            // capital letter and rest as a
            // lower case letters
            // minimal length is 5 letters
        return "[A-Z][a-z]{4,}";
    }
    
    public static String javaVariable() {
            // regular expression for testing synthax
            // of Java variable names
        return "((?!_$)[a-zA-Z$_][\\w$]*)";
    }

    public static String number0_300() {
        return "[1-9]\\d?|[1-2]\\d\\d|300|0";
    }

    public static String ipV4Octet(){
            
        return "([0-1]?\\d{1,2}|2([0-4]\\d|5[0-5]))";
    }

    public static String ipV4Address(){
        String octetExpr = ipV4Octet();
        return String.format("%s(\\.%s){3}", octetExpr, octetExpr);
    }
    public static String stringWithJavaNames(String names) {
        String [] tokens = names.split("\\s+");

        int i = getJavaNameIndex(tokens, -1);
        String res = "";
        if (i >= 0) {
            res = tokens[i];
        }
        while((i = getJavaNameIndex(tokens, i)) > 0) {
            res += " " + tokens[i];
        }
        return res;
    }
    
    private static int getJavaNameIndex(String[] tokens, int i) {
        i++;
        while(i < tokens.length && !isJavaName(tokens[i])) {
            i++;
        }
        return i < tokens.length ? i : -1;
    }
    
    private static boolean isJavaName(String string) {
        
        return string.matches(javaVariable()) && Arrays.binarySearch(keyWords, string) < 0;
    }

    public static boolean isArithmeticExpression(String expr) {
            //1. brackets
            //right position of open / close bracket is matter of regex
            //matching between open and close bracket is matter of the method you are supposed to write
            //based on a counter. If counter is negative - no matching;
            // if at ending up going through a string the counter doesn't equal 0 - no matching
            //matching may be only in one case: at the ending up of going the counter will be 0
            // Operator - regular expression for one out of 4 arithemetic operators [*/+-]
            //Operand may be either Java variable name or number (better any)

        boolean containsKeyword = SourceVersion.isKeyword(expr);

        String exprWithoutSpaces = expr.replace(" ", "");

        return !containsKeyword && isValidElements(exprWithoutSpaces) && isBracketsBalanced(exprWithoutSpaces) && validateExpression(exprWithoutSpaces);
    }
    public static boolean isBracketsBalanced(String expression) {
        Stack<Character> stack = new Stack<>();
        int brackets = 0;

        int i = 0;
        while (i < expression.length() && brackets >= 0) {
            char ch = expression.charAt(i);
            if (ch == '(') {
                brackets++;
                stack.push(ch);
            } else if (ch == ')') {
                brackets--;
                if (stack.isEmpty() || stack.pop() != '(') {
                    brackets = -1;
                }
            }
            i++;
        }
            
        return brackets == 0 && stack.isEmpty();
    }
    public static boolean validateExpression(String expression) {
        String regex = ".*[a-zA-Z0-9)]\\(.*";
        boolean openingBracketsValidFrom = !Pattern.compile(regex).matcher(expression).matches();
        regex = ".*\\([+\\-*/].*";
        boolean openingBracketsValidTo = !Pattern.compile(regex).matcher(expression).matches();
        String regex1 = ".*[+\\-*/(]\\).*";
        boolean closingBracketsValidFrom = !Pattern.compile(regex1).matcher(expression).matches();
        regex1 = ".*\\)[a-zA-Z0-9(].*";
        boolean closingBracketsValidTo = !Pattern.compile(regex1).matcher(expression).matches();

        return openingBracketsValidFrom && openingBracketsValidTo && closingBracketsValidFrom && closingBracketsValidTo;
    }

    public static boolean isValidElements(String expression) {
        
        String variablePattern = "[a-zA-Z_$][a-zA-Z_$0-9]*";

        Pattern pattern = Pattern.compile(variablePattern);
        Matcher matcher = pattern.matcher(expression);
            boolean res = true;
        while (matcher.find() && res == true) {
            String variable = matcher.group();
            if (!isJavaIdentifier(variable)) {
                res = false;
            }
        }
        return res;
    }
    private static boolean isJavaIdentifier(String s) {
        return s.matches("[a-zA-Z_$][a-zA-Z_$0-9]*");
    }
}
