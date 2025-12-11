public abstract class Shape {
    public static final String DEFAULT_COLOR = "White";

    public abstract double calculateArea();

    public abstract double calculatePerimeter();

    public void displayInfo() {
        System.out.println("--- Shape Information ---");
        System.out.println("This is a " + this.getClass().getSimpleName());
        System.out.println("Default Color (Static Field): " + DEFAULT_COLOR);
    }
}