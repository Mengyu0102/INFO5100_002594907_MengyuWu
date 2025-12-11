import java.util.Scanner;
import java.util.Random;

public class NumberGuessingGame {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Random rand = new Random();

        int choice;

        do {
            System.out.println("=== Number Guessing Game ===");
            System.out.println("1. Start Game");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    int answer = rand.nextInt(10) + 1;
                    boolean correct = false;


                    for (int i = 1; i <= 5; i++) {
                        System.out.print("You have 5 attempts to guess a number. Please enter a number between 1 and 10: ");
                        int guess = input.nextInt();

                        if (guess < answer) {
                            System.out.println("Too low!");
                        } else if (guess > answer) {
                            System.out.println("Too high!");
                        } else {
                            System.out.println("Correct!");
                            correct = true;
                            break;
                        }
                    }
                    if (!correct) {
                        System.out.println("Out of attempts! The correct number was: " + answer);
                    }
                    break;
                case 2:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice! Please enter 1 or 2");
            }
        } while (choice != 2);
        input.close();
    }
}