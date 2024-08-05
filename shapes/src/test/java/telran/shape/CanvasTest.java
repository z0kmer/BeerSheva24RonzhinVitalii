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

        Canvas canvas1 = new Canvas();
        canvas1.addShape(square1);
        canvas1.addShape(square2);
        canvas1.addShape(canvas);
        canvas1.addShape(rectangle);

        Canvas canvas2 = new Canvas();
        canvas2.addShape(square1);
        canvas2.addShape(canvas1);

        int expectedPerimeter = square1.perimiter() + square2.perimiter() + rectangle.perimiter();
        assertEquals(expectedPerimeter, canvas.perimiter());

        int expectedSquare = square1.square() + square2.square() + rectangle.square();
        assertEquals(expectedSquare, canvas.square());

        assertEquals(3, canvas.count());
        assertEquals(7, canvas1.count());
        assertEquals(9, canvas2.count());
    }
}
