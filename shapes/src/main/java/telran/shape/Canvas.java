package telran.shape;

public class Canvas implements Shape{
    Shape[] shapes;
    public Canvas() {
    shapes = new Shape[0];
    }

    public void addShape(Shape shape) {
        Shape[] newShapes = new Shape[shapes.length + 1];
        System.arraycopy(shapes, 0, newShapes, 0, shapes.length);
        newShapes[shapes.length] = shape;
        shapes = newShapes;
        countOfShapes();
    }

    public int countOfShapes() {
        //sum of all shapes on canvas
        return counter++;
    }

    @Override
    public int perimiter() {
        //sum of all included shape perimiters 
        int totalPerimeter = 0;
        for (Shape shape : shapes) {
            totalPerimeter += shape.perimiter();
        }
        return totalPerimeter;
    }

    @Override
    public int square() {
        //sum of all included shape squares
        int totalSquare = 0;
        for (Shape shape : shapes) {
            totalSquare += shape.square();
        }
        return totalSquare;
    }

}
