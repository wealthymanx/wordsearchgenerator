// Programmer: Marcel Rodriguez
// Date: 7/24/2023
// Class: CS &145
// Assignment 2: Word Search Generator
// Purpose: Generate a word search puzzle for the user.

import java.util.*;

public class WordSearchGenerator {
    private static char[][] wordSearch;
    private static char[][] solution;
    private static int size;
    
    // Handles user interface and menu options
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        printIntro();

        char choice;
        do {
            System.out.println("\nMenu:");
            System.out.println("g - Generate new word search");
            System.out.println("p - Print word search");
            System.out.println("s - Show solution");
            System.out.println("q - Quit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextLine().toLowerCase().charAt(0);

            switch (choice) {
                case 'g':
                    generate(scanner);
                    break;
                case 'p':
                    print();
                    break;
                case 's':
                    showSolution();
                    break;
                case 'q':
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 'q');

        scanner.close();
    }
    // Prints a welcome message and program introduction
    public static void printIntro() {
        System.out.println("Welcome to the Word Search Generator!");
        System.out.println("This program allows you to generate and solve word search puzzles.");
    }
    // Generates a new word search puzzle
    public static void generate(Scanner scanner) {
        System.out.print("Enter the size of the word search grid: ");
        size = scanner.nextInt();
        scanner.nextLine(); 

        // Initialize the word search and solution grids
        wordSearch = new char[size][size];
        solution = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                wordSearch[i][j] = ' ';
                solution[i][j] = ' ';
            }
        }

        System.out.print("Enter the number of words to include in the word search: ");
        int numWords = scanner.nextInt();
        scanner.nextLine(); 

        for (int i = 1; i <= numWords; i++) {
            System.out.print("Enter word " + i + ": ");
            String word = scanner.nextLine().toUpperCase();

            if (!placeWord(word)) {
                System.out.println("Failed to place word: " + word + " in the word search.");
            }
        }

        fillEmptyCells();
        System.out.println("Word search generated!");
    }
    // Place a word in the grid randomly
    private static boolean placeWord(String word) {
        Random random = new Random();
        int direction = random.nextInt(8); // 8 possible directions: horizontal, vertical, and diagonal
        int row, col, rowDir, colDir;

        int tryCount = 0;
        do {
            tryCount++;
            if (tryCount > 100) {
                return false; // Unable to place the word after 100 tries
            }

            row = random.nextInt(size);
            col = random.nextInt(size);

            switch (direction) {
                case 0:
                    rowDir = 1;
                    colDir = 0;
                    break;
                case 1:
                    rowDir = 0;
                    colDir = 1;
                    break;
                case 2:
                    rowDir = 1;
                    colDir = 1;
                    break;
                case 3:
                    rowDir = -1;
                    colDir = 0;
                    break;
                case 4:
                    rowDir = 0;
                    colDir = -1;
                    break;
                case 5:
                    rowDir = -1;
                    colDir = -1;
                    break;
                case 6:
                    rowDir = -1;
                    colDir = 1;
                    break;
                case 7:
                    rowDir = 1;
                    colDir = -1;
                    break;
                default:
                    rowDir = 0;
                    colDir = 0;
            }
        } while (!isSafe(word, row, col, rowDir, colDir));

        // Place the word in the word search and solution grids
        for (int i = 0; i < word.length(); i++) {
            wordSearch[row][col] = word.charAt(i);
            solution[row][col] = word.charAt(i);
            row += rowDir;
            col += colDir;
        }

        return true;
    }
    // Check if it's safe to place a word at a given position
    private static boolean isSafe(String word, int row, int col, int rowDir, int colDir) {
        if (row + (word.length() - 1) * rowDir >= size || row + (word.length() - 1) * rowDir < 0) {
            return false;
        }

        if (col + (word.length() - 1) * colDir >= size || col + (word.length() - 1) * colDir < 0) {
            return false;
        }

        for (int i = 0; i < word.length(); i++) {
            if (wordSearch[row][col] != ' ' && wordSearch[row][col] != word.charAt(i)) {
                return false;
            }
            row += rowDir;
            col += colDir;
        }

        return true;
    }
    // Fills empty cells with random letters
    private static void fillEmptyCells() {
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (wordSearch[i][j] == ' ') {
                    wordSearch[i][j] = (char) (random.nextInt(26) + 'A'); // Fill with random uppercase letters
                }
            }
        }
    }
    // Prints the current word search grid
    public static void print() {
        if (wordSearch == null) {
            System.out.println("Please generate a word search first.");
            return;
        }

        System.out.println("Word Search:");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(wordSearch[i][j] + " ");
            }
            System.out.println();
        }
    }
    // Prints the solution grid
    public static void showSolution() {
        if (solution == null) {
            System.out.println("Please generate a word search first.");
            return;
        }

        System.out.println("Solution:");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(solution[i][j] + " ");
            }
            System.out.println();
        }
    }
}