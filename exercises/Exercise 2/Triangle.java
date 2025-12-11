public class Triangle extends Shape {
    private double side1;
    private double side2;
    private double side3;
    private double base;
    private double height;

    public Triangle(double side1, double side2, double side3, double base, double height) {
        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;
        this.base = base;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return 0.5 * base * height;
    }

    @Override
    public double calculatePerimeter() {
        return side1 + side2 + side3;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Sides: " + side1 + ", " + side2 + ", " + side3);
        System.out.println("Area (Base/Height): " + calculateArea());
        System.out.println("Perimeter: " + calculatePerimeter());
    }
}