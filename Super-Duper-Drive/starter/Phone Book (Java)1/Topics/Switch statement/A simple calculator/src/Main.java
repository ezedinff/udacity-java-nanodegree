import java.util.Scanner;

class Main {
    private static int maxOperands = 3;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String input = scanner.nextLine();
            String[] operands = input.split(" ");

            if (operands.length != maxOperands) {
                System.out.println("Invalid input");
                return;
            }

            long num1;
            long num2;
            try {
                num1 = Long.parseLong(operands[0].trim());
                num2 = Long.parseLong(operands[2].trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input");
                return;
            }

            switch (operands[1].trim()) {
                case "+" -> System.out.println(num1 + num2);
                case "-" -> System.out.println(num1 - num2);
                case "*" -> System.out.println(num1 * num2);
                case "/" -> {
                    if (num2 == 0) {
                        System.out.println("Division by 0!");
                        return;
                    }
                    System.out.println(num1 / num2);
                }
                default -> System.out.println("Unknown operator");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
