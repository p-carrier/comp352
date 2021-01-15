/*
 * Philippe Carrier 40153985
 * COMP352 Section AA
 * Assignment # 1
 * Friday, May 15, 2020
 * */

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Program that create all the permutation of a short string of random character and then try to find
 * if the permutation is present inside a longer string of random character. It then outputs the results
 * to one file for the string along with the first occurrence where it was found inside the longer string.
 * It also outputs the time it takes to run the program in milliseconds for each iterations to another file.
 *
 * @author Philippe Carrier
 */
public class Version1 {

    public static void main(String[] args) {
        int max = 12;
        long[] time = new long[max - 4];
        try(PrintWriter outputStream = new PrintWriter(new FileOutputStream("out.txt"))) {
            // to make sure that at least some values are matched inside the strings
            long start = System.currentTimeMillis();
            permu("abcde", "dfhkjabcdedfdceabadsf", "", outputStream);
            long end = System.currentTimeMillis();
            time[0] = end - start;
            for (int i = 6; i <= max; i += 1) {
                start = System.currentTimeMillis();
                permu(randomLetter(i), randomLetter(i * 2), "", outputStream);
                end = System.currentTimeMillis();
                time[i - 5] = end - start;
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try(PrintWriter timeOutputStream = new PrintWriter(new FileOutputStream("time.txt"))) {
            for (int i = 0; i < time.length; i++) {
                int len = 5 + i;
                timeOutputStream.print(len);
                timeOutputStream.print(": ");
                timeOutputStream.println(time[i]);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Given a string, it creates all the possible way the letters inside the string can be position
     * then output the value and check if present in a bigger string.
     * @param shortStr      String - the smaller string
     * @param longStr       String - the longer string
     * @param permutation   String - a valid way to assemble shortStr letters
     * @param outputStream  PrintWriter - to output the values to the file
     */
    private static void permu(String shortStr, String longStr, String permutation, PrintWriter outputStream) {
        if (shortStr.isEmpty()) {
//            to limit the size of the out.txt file I limit the amount output by limiting the amount that goes out
            if (permutation.length() <= 9)
                outputStream.println(permutation);
            findOccurrences(permutation, longStr, outputStream);
            return;
        }

        for (int i = 0; i < shortStr.length(); i++) {
            permu(shortStr.substring(0, i) + shortStr.substring(i + 1), longStr, permutation + shortStr.charAt(i), outputStream);
        }
    }

    /**
     * Creates a string with specified length with random letters
     * @param len   int - the size of the string
     * @return  String - the string of length len with random letters
     */
    public static String randomLetter(int len) {
        StringBuilder str = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < len; i++) {
            str.append(Character.toChars(r.nextInt(26) + 97));
        }
        return str.toString();
    }

    /**
     * Find within a string if another string is present
     * @param permutation   String - the shorter string segment
     * @param longStr       String - the longer string segment
     * @param outputStream  PrintWriter - to output the result to the file
     */
    public static void findOccurrences(String permutation, String longStr, PrintWriter outputStream) {
        int len = permutation.length();
        int limit = longStr.length() - len;
        for (int i = 0; i <= limit; i++) {
            if (permutation.equals(longStr.substring(i, i + len))) {
                String ans = "Found one match: " +
                        permutation +
                        " is in " +
                        longStr +
                        " at location " +
                        i;
//                to limit the size of the out.txt file I limit the amount output by limiting the amount that goes out
                if (permutation.length() <= 9)
                    outputStream.println(ans);
                break;
            }
        }
    }

}
