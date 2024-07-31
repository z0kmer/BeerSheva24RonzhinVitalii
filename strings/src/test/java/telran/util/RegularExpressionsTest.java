package telran.util;

import static org.junit.jupiter.api.Assertions.*;
import static telran.util.RegularExpressions.*;

import org.junit.jupiter.api.Test;


public class RegularExpressionsTest {
    
    @Test
    void TestFirstNames() {
        String result = RegularExpressions.firstName();
        String[] arraysTrue = {"Alice", "Bobby", "Charlie"};
        String[] arraysFalse = {"eve", "123", "John", "Сашуня"};
    for(int i = 0; i < arraysTrue.length; i++) {
        assertTrue(arraysTrue[i].matches(result));
    }
    for(int i = 0; i < arraysFalse.length; i++) {
        assertFalse(arraysFalse[i].matches(result));
    }
    }

    @Test
    void testValidJavaVariable() {
        String result = RegularExpressions.javaVariable();
        String[] arraysTrue = {"myVar", "m", "_myVar", "$myyVar", "_1","V", "$"};
        String[] arraysFalse = {"123Var", "my Var", "myVar!", "_", "cашуня", "", "0"};
    for(int i = 0; i < arraysTrue.length; i++) {
        assertTrue(arraysTrue[i].matches(result));
    }
    for(int i = 0; i < arraysFalse.length; i++) {
        assertFalse(arraysFalse[i].matches(result));
    }
    }


    @Test
    public void testisArithmeticExpression() {
        assertTrue(isArithmeticExpression("2 * (3 + 2 / (7 - 2) * (4 - 0) / 2)"));
        assertTrue(isArithmeticExpression("abrgal * (3 + 2 / (7 - 2) * (4 - 0) / 2)"));
        assertTrue(isArithmeticExpression("2 * (qwex + 2 / (7 - 2) * (4 - 0) / 2)"));
        assertTrue(isArithmeticExpression("2 * (__ + 2 / (7 - $) * (4 - 0) / 2)"));
    
        assertFalse(isArithmeticExpression("("));
        assertFalse(isArithmeticExpression(")"));
        assertFalse(isArithmeticExpression("((())"));
        assertFalse(isArithmeticExpression("2 (3 + b/ ( 7-2)* (4-0) / 2 )"));
        assertFalse(isArithmeticExpression("2 (3 + b)"));
        assertFalse(isArithmeticExpression("(3 + b)2"));
        assertFalse(isArithmeticExpression("(3 + b) 2"));
        assertFalse(isArithmeticExpression("2*(+3 + b)"));
        assertFalse(isArithmeticExpression("2*( +3 + b)"));
        assertFalse(isArithmeticExpression("2*( +3 + abstract)"));
        assertFalse(isArithmeticExpression("(3 + b+)*2"));
        assertFalse(isArithmeticExpression("(3 + b+ ) *2"));
        assertFalse(isArithmeticExpression("(3 + b+ ) *_"));
        assertFalse(isArithmeticExpression("2 * (3 + b/ 2( 7-2) (4-0) / 2 )"));
    }

    @Test
    public void testisValidbrackets() {
        assertTrue(isBracketsBalanced("()"));
        assertTrue(isBracketsBalanced("2*(3+b/(7-2)*(4-0)/2)"));
    
        assertFalse(isBracketsBalanced("("));

        assertFalse(isBracketsBalanced(")"));
        assertFalse(isBracketsBalanced("((())"));
    }

    @Test
    public void testValidateExpression() {
        assertTrue(isBracketsBalanced("()"));
        assertTrue(validateExpression("(6)"));
        assertTrue(validateExpression("2*(3+b/(7-2)*(4-0)/2)"));
    
        assertFalse(validateExpression(")("));

        assertFalse(validateExpression("2(3+b/(7-2)*(4-0)/2)"));
        assertFalse(validateExpression("2(3+b)"));
        assertFalse(validateExpression("2*(+3+b)"));
        assertFalse(validateExpression("2*(3+b/2(7-2)(4-0)/2)"));

        assertFalse(isArithmeticExpression("(3+b)2"));
        assertFalse(isArithmeticExpression("(3+b+)*2"));
    }

    @Test
    public void testIsValidElements() {
        assertTrue(isValidElements("(6)"));
        assertTrue(isValidElements("2*(3+b/(7-2)*(4-0)/2)"));

        assertTrue(isValidElements("2(3+bbb/(7-2)*(4-0)/2)"));
        assertTrue(isValidElements("2(3+b)"));
        assertTrue(isValidElements("2*(+3+_b)"));
        assertTrue(isValidElements("2*(3+b/2(7-2)(4-0)/2)"));

        assertTrue(isValidElements("(3+b)2"));
    }
    
}
