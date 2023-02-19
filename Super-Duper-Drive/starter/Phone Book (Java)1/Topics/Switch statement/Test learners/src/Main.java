import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1 -> System.out.println("Yes!");
            case 2, 3, 4 -> System.out.println("No!");
            default -> System.out.println("Unknown number");
        }
    }
}