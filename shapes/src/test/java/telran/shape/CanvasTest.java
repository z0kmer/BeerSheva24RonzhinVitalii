package telran.shape;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CanvasTest {

    @Test
    void testPerimeterAndSquare() {
        Shape square1 = new Square(5);
        Shape square2 = new Square(3);
        Shape rectangle = new Rectangle(4, 6);

        Canvas canvas = new Canvas();
        canvas.addShape(square1);
        canvas.addShape(square2);
        canvas.addShape(rectangle);

        int expectedPerimeter = square1.perimiter() + square2.perimiter() + rectangle.perimiter();
        assertEquals(expectedPerimeter, canvas.perimiter());

        int expectedSquare = square1.square() + square2.square() + rectangle.square();
        assertEquals(expectedSquare, canvas.square());
    }
}