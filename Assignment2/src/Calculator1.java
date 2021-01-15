/*
 * Philippe Carrier 40153985
 * COMP352 Section AA
 * Assignment # 2
 * Sunday, May 31, 2020
 * */

import java.util.HashMap;
import java.util.Stack;

/**
 * Program that calculate arithmetic expression and returns the answer of the expression
 * with the help of two stacks.
 *
 * @author Philippe Carrier
 */
public class Calculator1 {

    private final static HashMap<String, Integer> SYMBOLS = createMap();

    /**
     * Populate the symbol hash map that give the level of importance of
     * a given operator
     * @return  HashMap, the level of importance of all the operators.
     */
    private static HashMap<String, Integer> createMap() {
        HashMap<String,Integer> map = new HashMap<>();
        map.put("(", 1000);
        map.put(")", 1000);
        map.put("!", 2);
        map.put("^", 4);
        map.put("*", 5);
        map.put("/", 5);
        map.put("+", 6);
        map.put("-", 6);
        map.put(">", 7);
        map.put(">=", 7);
        map.put("<", 7);
        map.put("<=", 7);
        map.put("==", 8);
        map.put("!=", 8);
        map.put("=", 9);
        return map;
    }

    public static void main(String[] args) {
        System.out.println(calculator("5 == 4"));
        System.out.println(calculator("5 + 9 >= 12 / 4"));
        System.out.println(calculator("(30 - 4) / 2 != 13 + 0"));
        System.out.println(calculator("5+4"));
        System.out.println(calculator("(5+4)"));
        System.out.println(calculator("(5+4*3)/2"));
        System.out.println(calculator("(5!+4*3)/2"));
        System.out.println(calculator("(-2+4*5)/(10+1)-9/3"));
        System.out.println(calculator("(4+20/5)^3-50"));
        System.out.println(calculator("(-2+4*5)+(10+1)/(9/3)"));
        System.out.println(calculator("(-2+4*5)/(10+1)-9/3"));
        System.out.println(calculator("((5!/4-10)*2+3)*5"));
        System.out.println(calculator("-52-98/10*(-94)"));
        System.out.println(calculator("-15/-71+46-(-57*47)--93/(-21--26)+-20"));
        System.out.println(calculator("25/(2-43)+-31/(88+74-8+-46/(79*-22))"));
        System.out.println(calculator("-44 / 9! / 7 - (71 / 52 + 3) * 31 / 65 - 89 + -1 / -54"));
        System.out.println(calculator("(((8+2*3)!-7 + 3/-2) + 40)/(115 - 20)"));
        System.out.println(calculator("((8+-2*3!+21+ 14/-3) + 92)/(5! - 6*7)"));
        System.out.println(calculator("93/5*(4 + 18 - 3^3)"));
        System.out.println(calculator("8^8 / 10! + 52 / (14! - 8)"));
        System.out.println(calculator("5^9/40 - 75*4^8"));
        System.out.println(calculator("52/5*12*(7 - 24*-5)/32"));
        System.out.println(calculator("581/(5! - 72 /4) + 908 / (80 -3^3)"));
    }

    /**
     * Calculate the result of a given equation
     * @param equation  String - the arithmetic expression
     * @return          String - The result of the arithmetic expression
     */
    private static String calculator(String equation) {
        System.out.println(equation);
        equation = equation.replaceAll(" ", "");
        Stack<String> symbols = new Stack<>();
        Stack<String> numbers = new Stack<>();

        for (int i = 0; i < equation.length(); i++) {
            String character = Character.toString(equation.charAt(i));
            if (SYMBOLS.containsKey(character)) {
                switch(character) {
                    case ">":
                    case "<":
                    case "=":
                        char after = equation.charAt(i + 1);
                        if (after == '=') {
                            character += "=";
                            i++;
                        }
                    case "(":
                        symbols.push(character);
                        break;
                    case ")":
                        while (!symbols.peek().equals("(")) {
                            String y = numbers.pop();
                            String x = numbers.pop();
                            String equ = symbols.pop();
                            numbers.push(Double.toString(
                                    basicCalculator(
                                            Double.parseDouble(x),
                                            Double.parseDouble(y),
                                            equ
                                    )));
                        }
                        symbols.pop();
                        break;
                    case "!":
                        after = equation.charAt(i + 1);
                        if (after == '=') {
                            character += "=";
                            i++;
                            while (!symbols.isEmpty() && SYMBOLS.get(symbols.peek()) <= SYMBOLS.get(character)) {
                                String y = numbers.pop();
                                String x = numbers.pop();
                                String equ = symbols.pop();
                                numbers.push(Double.toString(
                                        basicCalculator(
                                                Double.parseDouble(x),
                                                Double.parseDouble(y),
                                                equ
                                        )));
                            }
                            symbols.push(character);
                        } else {
                            String x = numbers.pop();
                            double result = factorial(Double.parseDouble(x));
                            numbers.push(Double.toString(result));
                        }
                        break;
                    case "^":
                        double pow = Double.parseDouble(Character.toString(equation.charAt(i + 1)));
                        i++;
                        String x = numbers.pop();
                        double result = power(Double.parseDouble(x), pow);
                        numbers.push(String.valueOf(result));
                        break;
                    case "-":
                        char before;
                        if (i == 0 ||
                                (((before = equation.charAt(i - 1)) < '0' || before > '9')
                                        && before != ')' && before != '!')) {
                            int current = i;
                            int space = 1;
                            char next;
                            while (i < equation.length() - 1
                                    && (next = equation.charAt(current + space)) >= '0'
                                    && next <= '9') {
                                character += next;
                                space++;
                                i++;
                            }
                            numbers.push(character);
                            break;
                        }
                    default:
                        int importance = SYMBOLS.get(character);
                        while (!symbols.isEmpty() && SYMBOLS.get(symbols.peek()) <= importance) {
                            String y = numbers.pop();
                            x = numbers.pop();
                            String equ = symbols.pop();
                            numbers.push(Double.toString(
                                    basicCalculator(
                                            Double.parseDouble(x),
                                            Double.parseDouble(y),
                                            equ
                                    )));
                        }
                        symbols.push(character);
                }
            } else {
                int space = 1;
                char next;
                int current = i;
                while (i < equation.length() - 1
                        && (next = equation.charAt(current + space)) >= '0'
                        && next <= '9') {
                    character += next;
                    space++;
                    i++;
                }
                numbers.push(character);
            }
        }
        while (!symbols.isEmpty()) {
            String y = numbers.pop();
            String x = numbers.pop();
            String equ = symbols.pop();
            switch (equ) {
                case ">":
                    return Boolean.toString(Double.parseDouble(x) > Double.parseDouble(y));
                case ">=":
                    return Boolean.toString(Double.parseDouble(x) >= Double.parseDouble(y));
                case "<":
                    return Boolean.toString(Double.parseDouble(x) < Double.parseDouble(y));
                case "<=":
                    return Boolean.toString(Double.parseDouble(x) <= Double.parseDouble(y));
                case "==":
                    return Boolean.toString(Double.parseDouble(x) == Double.parseDouble(y));
                case "!=":
                    return Boolean.toString(Double.parseDouble(x) != Double.parseDouble(y));
                default:
                    numbers.push(Double.toString(
                            basicCalculator(
                                    Double.parseDouble(x),
                                    Double.parseDouble(y),
                                    equ
                            )));
            }
        }
        return numbers.pop();
    }

    /**
     * Calculate the factorial of a given number
     * @param num   double - the number
     * @return      double - the result of the factorial
     */
    private static double factorial(double num) {
        if (num == 1)
            return 1;
        return num * factorial(num - 1);
    }

    /**
     * Calculate the n power of a given number
     * @param num   double - the number
     * @param pow double - the power of the number
     * @return      double - the result of the number to the power
     */
    private static double power(double num, double pow) {
        if (pow == 1)
            return num;
        return num * power(num, pow - 1);
    }

    /**
     * Basic arithmetic operation
     * @param x         double - first variable
     * @param y         double - second variable
     * @param equ       char - the basic arithmetic operator
     * @return          double - result of the basic arithmetic operation
     */
    public static double basicCalculator(double x, double y, String equ) {
        switch (equ) {
            case "*":
                return x * y;
            case "/":
                return x / y;
            case "-":
                return x - y;
            case "+":
                return x + y;
            default:
                return 0;
        }
    }
}
