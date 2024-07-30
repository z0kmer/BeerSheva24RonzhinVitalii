package telran.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
}
