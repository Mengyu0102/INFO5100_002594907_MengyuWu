public class Square extends Rectangle {

    public static final String SHAPE_NAME = "Square";

    public Square(double side) {
        super(side, side);
    }

    @Override
    public void displayInfo() {
        System.out.println("--- Square Information ---");
        System.out.println("This is a " + this.getClass().getSimpleName());
        System.out.println("Default Color (Static Field): " + DEFAULT_COLOR);
        System.out.println("Specific Name (Static Field): " + SHAPE_NAME);
        System.out.println("Side Length: " + calculatePerimeter() / 4);
        System.out.println("Area: " + calculateArea());
        System.out.println("Perimeter: " + calculatePerimeter());
    }
}