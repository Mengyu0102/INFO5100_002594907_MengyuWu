import java.util.ArrayList;
import java.util.List;

public class PolymorphismDemo {
    public static void main(String[] args) {
        System.out.println("=== Static Field Demonstration ===");
        System.out.println("Shape Default Color: " + Shape.DEFAULT_COLOR);
        System.out.println("Rectangle Specific Name: " + Rectangle.SHAPE_NAME);
        System.out.println("----------------------------------\n");

        System.out.println("=== Polymorphism Demonstration ===");

        List<Shape> shapes = new ArrayList<>();
        shapes.add(new Rectangle(10.0, 5.0));
        shapes.add(new Circle(7.0));
        shapes.add(new Square(4.0));
        shapes.add(new Triangle(3.0, 4.0, 5.0, 3.0, 4.0));

        for (Shape shape : shapes) {
            shape.displayInfo();
            System.out.println("Calculated Area (from runtime object): " + shape.calculateArea());
            System.out.println("----------------------------------");
        }

        Rectangle rect = new Rectangle(1, 1);
        System.out.println("\nAccessing static field via instance (Rectangle): " + rect.SHAPE_NAME);
    }
}