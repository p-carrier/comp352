/*
 * Philippe Carrier 40153985
 * COMP352 Section AA
 * Assignment # 1
 * Friday, May 15, 2020
 * */

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * Program that find all the permutation of a short string of random character inside a longer string
 * of random character. It then outputs the results to one file for the string along with the place it
 * was found inside the longer string. It also outputs the time it takes to run the program in nano seconds
 * for each iterations to another file.
 *
 * @author Philippe Carrier
 */
public class Version2 {

    public static void main(String[] args) {
        int max = 200;
        long[] time = new long[max - 4];
        try(PrintWriter outputStream = new PrintWriter(new FileOutputStream("version2_result.txt"))) {
            long start = System.nanoTime();
//            to make sure that there is at least one element that is found in the string
            permuNew("abcde", "adsfabcdeasdfjlbcdeabadsf", outputStream);
            long end = System.nanoTime();
            time[0] = end - start;
            for (int i = 6; i <= max; i += 1) {
                start = System.nanoTime();
                permuNew(Version1.randomLetter(i), Version1.randomLetter(i * 2), outputStream);
                end = System.nanoTime();
                time[i - 5] = end - start;
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try(PrintWriter timeOutputStream = new PrintWriter(new FileOutputStream("version2_time.txt"))) {
            for (int i = 0; i < time.length; i++) {
                int index = i + 5;
                timeOutputStream.print(index);
                timeOutputStream.print(": ");
                timeOutputStream.println(time[i]);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Find if one or more permutation of shortStr are contain in longStr
     * @param shortStr      String - the smaller string
     * @param longStr       String - the longer string
     * @param outputStream  PrintWriter - to output the values to the file
     */
    public static void permuNew(String shortStr, String longStr, PrintWriter outputStream) {
        int len = shortStr.length();
        int limit = longStr.length() - len;
        outputStream.print(shortStr);
        outputStream.println(": ");
        for (int i = 0; i <= limit; i++) {
            boolean isPresent = true;
            StringBuilder perms = new StringBuilder(shortStr);
            for (int j = i; j < i + len; j++) {
                char letter = longStr.charAt(j);
                int index = perms.indexOf(Character.toString(letter));
                if (index < 0) {
                    isPresent = false;
                    break;
                }
                perms.deleteCharAt(index);
            }
            if (isPresent) {
                String ans = "Found one match: " +
                        longStr.substring(i, i + len) +
                        " is in " +
                        longStr +
                        " at location " +
                        i;
                outputStream.println(ans);
            }
        }
        outputStream.println();

    }

}
