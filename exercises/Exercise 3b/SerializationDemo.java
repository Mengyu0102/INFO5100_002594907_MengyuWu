import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializationDemo {
    private static final String FILENAME = "shapes_data.ser";

    public static void main(String[] args) {
        // 1. 创建待序列化的对象列表 (Polymorphism)
        List<Shape> originalShapes = createShapes();

        System.out.println("=== 1. Original Objects Before Serialization ===");
        for (Shape shape : originalShapes) {
            System.out.println("Original: " + shape.getClass().getSimpleName() +
                    ", Area: " + shape.calculateArea());
        }
        System.out.println("----------------------------------------------\n");

        // 2. 序列化 (Serialization: Saving the objects)
        serializeObjects(originalShapes);

        // 3. 反序列化 (Deserialization: Restoring the objects)
        List<Shape> deserializedShapes = deserializeObjects();

        // 4. 验证反序列化后的对象
        System.out.println("=== 3. Deserialized Objects After Reading from File ===");
        if (deserializedShapes != null) {
            for (Shape shape : deserializedShapes) {
                // 多态性在这里依然有效
                System.out.println("Deserialized: " + shape.getClass().getSimpleName() +
                        ", Area: " + shape.calculateArea());
                shape.displayInfo(); // 验证对象状态是否被成功恢复
                System.out.println("--");
            }
        }
    }

    private static List<Shape> createShapes() {
        List<Shape> shapes = new ArrayList<>();
        shapes.add(new Rectangle(12.0, 6.0));
        shapes.add(new Circle(5.0));
        shapes.add(new Square(3.0));
        shapes.add(new Triangle(3.0, 4.0, 5.0, 3.0, 4.0));
        return shapes;
    }

    private static void serializeObjects(List<Shape> shapes) {
        try (FileOutputStream fileOut = new FileOutputStream(FILENAME);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            objectOut.writeObject(shapes);
            System.out.println("✅ Objects successfully serialized to " + FILENAME);

        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    private static List<Shape> deserializeObjects() {
        List<Shape> shapes = null;
        try (FileInputStream fileIn = new FileInputStream(FILENAME);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {

            shapes = (List<Shape>) objectIn.readObject();
            System.out.println("\n✅ Objects successfully deserialized from " + FILENAME);

        } catch (IOException i) {
            System.out.println("File IO error during deserialization.");
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Shape class not found during deserialization.");
            c.printStackTrace();
        }
        return shapes;
    }
}