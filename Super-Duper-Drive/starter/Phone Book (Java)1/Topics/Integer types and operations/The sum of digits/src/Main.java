import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] num = scanner.next().split("");
        int sum = 0;
        for (String s : num) {
            sum = sum + Integer.parseInt(s);
        }
        System.out.println(sum);
    }
}