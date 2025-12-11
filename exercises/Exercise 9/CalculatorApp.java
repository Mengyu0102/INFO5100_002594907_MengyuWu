import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CalculatorApp extends Application {

    // --- UI Components ---
    private TextField display; // Text field to show input and result

    // --- State Variables for Calculation ---
    private String currentNumber = "";
    private double firstOperand = 0;
    private String operator = "";
    private boolean start = true; // Flag to indicate start of a new operation/number

    // --- Entry Point for JavaFX Application ---
    @Override
    public void start(Stage primaryStage) {

        // 1. Setup Display Area (VBox)
        display = new TextField();
        display.setEditable(false); // Display should not be editable by keyboard
        display.setAlignment(Pos.CENTER_RIGHT);
        display.setText("0"); // Initial display value

        VBox displayBox = new VBox(10, display);
        displayBox.setPadding(new Insets(10));

        // 2. Setup Button Grid (GridPane)
        GridPane grid = createButtonGrid();

        // 3. Main Layout (VBox to stack display and grid)
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(displayBox, grid);

        // 4. Scene and Stage Setup
        Scene scene = new Scene(root);
        primaryStage.setTitle("Four Function Calculator");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false); // Simple layout, fixed size is better
        primaryStage.show();
    }

    /**
     * Creates the GridPane containing all number and operator buttons.
     * @return The configured GridPane.
     */
    private GridPane createButtonGrid() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        // Define button labels for the grid (4 rows, 4 columns)
        String[][] buttonLabels = {
                {"7", "8", "9", "/"},
                {"4", "5", "6", "*"},
                {"1", "2", "3", "-"},
                {"0", ".", "=", "+"},
                {"C", "<-"} // Clear and Backspace row (optional, simplified to C)
        };

        // Loop to create and place buttons
        for (int i = 0; i < buttonLabels.length; i++) {
            for (int j = 0; j < buttonLabels[i].length; j++) {
                String label = buttonLabels[i][j];
                Button button = new Button(label);
                button.setPrefSize(50, 50); // Set a fixed size for simple, consistent look

                // Set the event handler based on button type
                if (label.matches("[0-9.]")) { // Numbers and Decimal
                    button.setOnAction(e -> handleNumber(label));
                } else if (label.matches("[-+*/]")) { // Operators
                    button.setOnAction(e -> handleOperator(label));
                } else if (label.equals("=")) { // Equals
                    button.setOnAction(e -> handleEquals());
                } else if (label.equals("C")) { // Clear
                    button.setOnAction(e -> handleClear());
                } else if (label.equals("<-")) { // Backspace (Simplification: used 'C' instead)
                    button.setOnAction(e -> handleClear());
                }

                // Add the button to the grid
                if (label.equals("C")) {
                    GridPane.setColumnSpan(button, 2); // Make 'C' span two columns
                    grid.add(button, 0, 4);
                } else if (label.equals("<-")) {
                    // Skip backspace for simplicity or integrate it differently
                } else {
                    // Standard placement
                    grid.add(button, j, i);
                }
            }
        }

        // Add a dedicated Clear button 'C' at the bottom
        Button clearButton = new Button("C");
        clearButton.setPrefSize(110, 50);
        clearButton.setOnAction(e -> handleClear());
        GridPane.setColumnSpan(clearButton, 2); // Span over two columns
        grid.add(clearButton, 0, 4);

        // Add the Backspace button
        Button backButton = new Button("<-");
        backButton.setPrefSize(50, 50);
        backButton.setOnAction(e -> handleBackspace());
        grid.add(backButton, 2, 4);

        return grid;
    }

    // --- Event Handlers ---

    /** Handles number and decimal point button presses. */
    private void handleNumber(String value) {
        if (start) {
            currentNumber = "";
            start = false;
        }

        // Prevent multiple decimals
        if (value.equals(".") && currentNumber.contains(".")) {
            return;
        }

        // Avoid leading zero unless followed by decimal
        if (currentNumber.equals("0") && !value.equals(".")) {
            currentNumber = value;
        } else {
            currentNumber += value;
        }

        display.setText(currentNumber);
    }

    /** Handles operator button presses (+, -, *, /). */
    private void handleOperator(String value) {
        if (!operator.isEmpty() && !start) {
            // If we already have an operator and a number, treat it as an implicit '='
            handleEquals();
        }

        try {
            firstOperand = Double.parseDouble(currentNumber);
            operator = value;
            start = true; // Next input starts a new number
        } catch (NumberFormatException e) {
            display.setText("Error");
            handleClear(); // Reset state
        }
    }

    /** Handles the equals button press (=). */
    private void handleEquals() {
        if (operator.isEmpty() || start) {
            return; // Nothing to calculate or waiting for first number
        }

        double secondOperand;
        try {
            secondOperand = Double.parseDouble(currentNumber);
        } catch (NumberFormatException e) {
            display.setText("Error");
            return;
        }

        double result = calculate(firstOperand, secondOperand, operator);

        // Display result and reset state for the next operation
        display.setText(String.valueOf(result));
        currentNumber = String.valueOf(result);
        operator = ""; // Clear the operator
        firstOperand = result; // Result becomes the first operand for chained operations
        start = true;
    }

    /** Handles the clear button press (C). */
    private void handleClear() {
        currentNumber = "0";
        firstOperand = 0;
        operator = "";
        start = true;
        display.setText("0");
    }

    /** Handles the backspace button press (<-). */
    private void handleBackspace() {
        if (currentNumber.length() > 1) {
            currentNumber = currentNumber.substring(0, currentNumber.length() - 1);
        } else {
            currentNumber = "0";
            start = true;
        }
        display.setText(currentNumber);
    }

    /** Performs the actual calculation based on the operator. */
    private double calculate(double num1, double num2, String op) {
        switch (op) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 == 0) {
                    display.setText("Error (Div/0)");
                    handleClear();
                    return 0;
                }
                return num1 / num2;
            default:
                return 0;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}