
import java.util.*;

public class Main {

    public static String letters = "qwertyuiopasdfghjklzxcvbnm";
    public static String symbols = "-+*/^";
    public static String s = "";

    public static int inpPriority (char c){
        if (letters.contains(String.valueOf(c))) {
            return 7;
        }
        return switch (c){
            case '+', '-' -> 1;
            case '*', '/' -> 3;
            case '^' -> 6;
            case '(' -> 9;
            case ')' -> 0;
            default -> 10;
        };
    }

    public static int stackPriority (String s){
        if (letters.contains(s)) {
            return 8;
        }
        return switch (s){
            case "+", "-" -> 2;
            case "*", "/" -> 4;
            case "^" -> 5;
            case "(" -> 0;
            default -> 10;
        };
    }

    public static String getRewPolNotation(String input, String result){
        Stack<String> stack = new Stack<String>();
        int i = 0;
        while (i < input.length()) {
            if (stack.empty() || inpPriority(input.charAt(i)) > stackPriority(stack.peek())){
                if (input.charAt(i) != ')'){
                    stack.push(String.valueOf(input.charAt(i)));
                }
                i++;
            }
            else {
                s = stack.pop();
                if (!(s.equals("("))){
                    result += s;
                }
            }
        }
        while (!(stack.empty())) {
            s  = stack.pop();
            if (!(s.equals("("))) {
                result += s;
            }
        }
        return result;
    }

    public static int getRank(String result) {
        int rank = 0;

        for (int i = 0; i < result.length(); i++){
            if (letters.contains(String.valueOf(result.charAt(i)))){
                rank++;
            }
            else if (symbols.contains(String.valueOf(result.charAt(i)))) {
                rank--;
            }
        }
        return rank;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter expression:");
        String input = scanner.nextLine();
        String result = "";
        System.out.println("Expression in reversed polish notation:");
        result = getRewPolNotation(input, result);
        System.out.println(result);
        System.out.println("Rank of new expression: " + getRank(result));
    }
}
