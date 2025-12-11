import java.io.Serializable;

public class Rectangle extends Shape implements Serializable {
    private static final long serialVersionUID = 1L; // Recommended practice

    private double width;
    private double height;

    public static final String SHAPE_NAME = "Rectangle";

    // ... (rest of the class remains the same)

    @Override
    public double calculateArea() {
        return width * height;
    }
    // ...
}