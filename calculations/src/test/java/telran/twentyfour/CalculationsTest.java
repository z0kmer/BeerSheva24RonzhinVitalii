package telran.twentyfour;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import static telran.twentyfour.Calculations.divde;
import static telran.twentyfour.Calculations.isDividedOn;
import static telran.twentyfour.Calculations.maxDigits;
import static telran.twentyfour.Calculations.multiply;
import static telran.twentyfour.Calculations.subtract;
import static telran.twentyfour.Calculations.sum;
import static telran.twentyfour.Calculations.sumOfDigits;

public class CalculationsTest {
    @Test
    void sumTest() {
        assertEquals(4, sum(2, 2));
    }
    
    void multiplyTest() {
        assertEquals(6, multiply(2, 3));
    }
    
    void divdeTest() {
        assertEquals(2, divde(6, 3));
    }

    void subtractTest() {
        assertEquals(2, subtract(5, 3));
    }

    void sumOfDigitsTest() {
        assertEquals(50, sumOfDigits(1234556789));
    }

    void maxDigitsTest() {
        assertEquals(6, maxDigits(1256324210));
    }

    void isDividedOnTest() {
        assertEquals(true, isDividedOn(711, 3));
    }

}
