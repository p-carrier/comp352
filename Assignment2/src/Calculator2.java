/*
 * Philippe Carrier 40153985
 * COMP352 Section AA
 * Assignment # 2
 * Sunday, May 31, 2020
 * */

import java.util.HashMap;

/**
 * Program that calculate recursively arithmetic expression and returns the answer of the expression.
 *
 * @author Philippe Carrier
 */
public class Calculator2 {

    private final static HashMap<String, Integer> SYMBOLS = createMap();

    /**
     * Populate the symbol hash map that give the level of importance of
     * a given operator
     * @return  HashMap, the level of importance of all the operators.
     */
    private static HashMap<String, Integer> createMap() {
        HashMap<String,Integer> myMap = new HashMap<>();
        myMap.put("(", 1);
        myMap.put(")", 500);
        myMap.put("!", 2);
        myMap.put("^", 4);
        myMap.put("*", 5);
        myMap.put("/", 5);
        myMap.put("+", 6);
        myMap.put("-", 6);
        myMap.put(">", 7);
        myMap.put(">=", 7);
        myMap.put("<", 7);
        myMap.put("<=", 7);
        myMap.put("==", 8);
        myMap.put("!=", 8);
        return myMap;
    }

    public static void main(String[] args) {
        System.out.println("5==4");
        System.out.println(calculator("5==4", new int[] {0, 1000}));
        System.out.println("5+9>=12/4");
        System.out.println(calculator("5+9>=12/4", new int[] {0, 1000}));
        System.out.println("(30-4)/2!=13+0");
        System.out.println(calculator("(30-4)/2!=13+0", new int[] {0, 1000}));
        System.out.println("5+4");
        System.out.println(calculator("5+4", new int[] {0, 1000}));
        System.out.println("(5+4)");
        System.out.println(calculator("(5+4)", new int[] {0, 1000}));
        System.out.println("(5+4*3)/2");
        System.out.println(calculator("(5+4*3)/2", new int[]{0, 1000}));
        System.out.println("(5!+4*3)/2");
        System.out.println(calculator("(5!+4*3)/2", new int[]{0, 1000}));
        System.out.println("(-2+4*5)/(10+1)-9/3");
        System.out.println(calculator("(-2+4*5)/(10+1)-9/3", new int[]{0, 1000}));
        System.out.println("(4+20/5)^3-50");
        System.out.println(calculator("(4+20/5)^3-50", new int[]{0, 1000}));
        System.out.println("(-2+4*5)+(10+1)/(9/3)");
        System.out.println(calculator("(-2+4*5)+(10+1)/(9/3)", new int[]{0, 1000}));
        System.out.println("((5!/4-10)*2+3)*5");
        System.out.println(calculator("((5!/4-10)*2+3)*5", new int[]{0, 1000}));
        System.out.println("-52-98/10*(-94)");
        System.out.println(calculator("-52-98/10*(-94)", new int[]{0, 1000}));
        System.out.println("-15/(-71)+46-(-57*47)-(-93)/(-21-(-26))+(-20)");
        System.out.println(calculator("-15/(-71)+46-(-57*47)-(-93)/(-21-(-26))+(-20)", new int[]{0, 1000}));
        System.out.println("25/(2-43)+(-31)/(88+74-8+(-46)/(79*(-22)))");
        System.out.println(calculator("25/(2-43)+(-31)/(88+74-8+(-46)/(79*(-22)))", new int[]{0, 1000}));
        System.out.println("-44/9!/7-(71/52+3)*31/65-89+(-1)/(-54)");
        System.out.println(calculator("-44/9!/7-(71/52+3)*31/65-89+(-1)/(-54)", new int[]{0, 1000}));
        System.out.println("(((8+2*3)!-7+3/(-2))+40)/(115-20)");
        System.out.println(calculator("(((8+2*3)!-7+3/(-2))+40)/(115-20)", new int[]{0, 1000}));
        System.out.println("((8+(-2)*3!+21+14/(-3))+92)/(5!-6*7)");
        System.out.println(calculator("((8+(-2)*3!+21+14/(-3))+92)/(5!-6*7)", new int[]{0, 1000}));
        System.out.println("93/5*(4+18-3^3)");
        System.out.println(calculator("93/5*(4+18-3^3)", new int[]{0, 1000}));
        System.out.println("8^8/10!+52/(14!-8)");
        System.out.println(calculator("8^8/10!+52/(14!-8)", new int[]{0, 1000}));
        System.out.println("5^9/40-75*4^8");
        System.out.println(calculator("5^9/40-75*4^8", new int[]{0, 1000}));
        System.out.println("52/5*12*(7-24*(-5))/32");
        System.out.println(calculator("52/5*12*(7-24*(-5))/32", new int[]{0, 1000}));
        System.out.println("581/(5!-72/4)+908/(80-3^3)");
        System.out.println(calculator("581/(5!-72/4)+908/(80-3^3)", new int[]{0, 1000}));
    }

    /**
     * Recursive method that calculate the result of arithmetic expression
     * @param equation  String - the arithmetic expression
     * @param index     int[] - gives the position in the initial arithmetic expression and the
     *                  importance of the previous operator
     * @return          String - The result of the arithmetic expression
     */
    private static String calculator(String equation, int[] index) {
        double result = 0;
        char[] characters = equation.toCharArray();
        boolean isLeftNumberPresent = false;
        for (int i = 0; i < characters.length; i++ ) {
            switch (characters[i]) {
                case '(':
                    int[] movement = {i + 1, 1000};
                    result = Double.parseDouble(calculator(equation.substring(i + 1), movement));
                    i = movement[0];
                    isLeftNumberPresent = true;
                    break;
                case ')':
                    index[0] += i;
                    return Double.toString(result);
                case '!':
                    if (characters[i + 1] != '=') {
                        result = factorial(result);
                        isLeftNumberPresent = true;
                    } else {
                        return Boolean.toString(result != Double.parseDouble(calculator(equation.substring(i + 2), new int[]{0, 1000})));
                    }
                    break;
                case '^':
                    String num = findNumber(characters, i + 1);
                    i += num.length();
                    result = power(result, Double.parseDouble(num));
                    isLeftNumberPresent = true;
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    num = findNumber(characters, i);
                    i += num.length() - 1;
                    result = Integer.parseInt(num);
                    isLeftNumberPresent = true;
                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                    if (SYMBOLS.get(Character.toString(characters[i])) >= index[1]) {
                        index[0] += i - 1;
                        return Double.toString(result);
                    }
                    if (isLeftNumberPresent) {
                        int nextOperation = findNextOperation(characters, i + 1);
                        String secondOp = "";
                        if (nextOperation < equation.length() - 1 && characters[nextOperation + 1] == '=') {
                            secondOp = "=";
                        }
                        if (nextOperation >= 0
                                && SYMBOLS.get(Character.toString(characters[i]))
                                > SYMBOLS.get(characters[nextOperation] + secondOp)) {
                            char operation = characters[i];
                            int [] move = {i + 1, SYMBOLS.get(Character.toString(operation))};
                            result = operation(result, Double.parseDouble(calculator(equation.substring(i + 1), move)), operation);
                            i = move[0];
                        } else {
                            int importance;
                            if (nextOperation >= 0) {
                                importance = SYMBOLS.get(Character.toString(characters[nextOperation]));
                            } else {
                                importance = 1000;
                                nextOperation = equation.length();
                            }
                            result = operation(result,
                                    Integer.parseInt(equation.substring(
                                            i + 1,
                                            nextOperation
                                    )),
                                    characters[i]);
                            if (index[1] < importance) {
                                index[0] += nextOperation - 1;
                                return Double.toString(result);
                            } else {
                                i = nextOperation - 1;
                            }
                        }
                    } else {
                        num = findNumber(characters, i + 1);
                        result = Integer.parseInt(  characters[i] + num);
                        i += num.length();
                        isLeftNumberPresent = true;
                    }
                    break;
                case '>':
                    if (characters[i + 1] != '=') {
                        return Boolean.toString(result > Double.parseDouble(calculator(equation.substring(i + 1), new int[]{0, 1000})));
                    } else {
                        return Boolean.toString(result >= Double.parseDouble(calculator(equation.substring(i + 2), new int[]{0, 1000})));
                    }
                case '<':
                    if (characters[i + 1] != '=') {
                        return Boolean.toString(result < Double.parseDouble(calculator(equation.substring(i + 1), new int[]{0, 1000})));
                    } else {
                        return Boolean.toString(result <= Double.parseDouble(calculator(equation.substring(i + 2), new int[]{0, 1000})));
                    }
                case '=':
                    if (characters[i + 1] == '=')
                        return Boolean.toString(result == Double.parseDouble(calculator(equation.substring(i + 2), new int[]{0, 1000})));
            }
        }
        index[0] += equation.length();
        return Double.toString(result);
    }

    /**
     * Find the complete number given by the expression
     * @param characters    char[] - list of all the character in the expression
     * @param index         position in the characters list where to start searching
     * @return              the complete number
     */
    private static String findNumber(char[] characters, int index) {
        StringBuilder temp = new StringBuilder();
        int position = index;
        char next;
        while (position < characters.length
                && ((next = characters[position++]) >= '0' && next <= '9')) {
            temp.append(next);
        }
        return temp.toString();
    }

    /**
     * Find the operator following the current operator
     * @param characters    char[] - list of all the character in the expression
     * @param index         position in the characters list where to start searching
     * @return              int - position of the next operator
     */
    private static int findNextOperation(char[] characters, int index) {
        char current;
        int position = index;
        while (position <= characters.length - 1
                && (current = characters[position]) >= '0'
                && current <= '9') {
            position++;
        }
        return position == characters.length ? -1 : position;
    }

    /**
     * Basic arithmetic operation
     * @param x         double - first variable
     * @param y         double - second variable
     * @param equation  char - the basic arithmetic operator
     * @return          double - result of the basic arithmetic operation
     */
    private static double operation(double x, double y, char equation) {
        switch (equation) {
            case '+':
                return x + y;
            case '-':
                return x - y;
            case '/':
                return x / y;
            case '*':
                return x * y;
            default:
                return 0;
        }
    }

    /**
     * Calculate the factorial of a given number
     * @param num   double - the number
     * @return      double - the result of the factorial
     */
    private static double factorial(double num) {
        if (num == 1)
            return num;
        return num * factorial(num - 1);
    }

    /**
     * Calculate the n power of a given number
     * @param num   double - the number
     * @param power double - the power of the number
     * @return      double - the result of the number to the power
     */
    private static double power(double num, double power) {
        if (power == 1)
            return num;
        return num * power(num, power - 1);
    }
}
