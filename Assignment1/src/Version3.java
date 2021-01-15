public class Version3 {

    public static void main(String[] args) {
        int max = 10;
        long[] time = new long[max - 4];
        for (int i = 5; i <= max; i++) {
            long start = System.currentTimeMillis();
//            permu(Version1.randomLetter(i).toCharArray(), Version1.randomLetter(i*2), 0);
            permu2(Version1.randomLetter(i).toCharArray(), i);
            long end = System.currentTimeMillis();
            time[i - 5] = end - start;
        }

        for (long t : time) {
            System.out.println(t);
        }

    }

    private static void permu2(char[] shortStr, int index) {
        if (index == 1) {
            System.out.println(shortStr);
            return;
        }
        // Generate permutations with kth unaltered
        // Initially k == length(A)
        permu2(shortStr, index - 1);

        // Generate permutations for kth swapped with each k-1 initial
        for (int i = 0; i < index - 1; i ++) {
            if (index % 2 == 0) {
                movePlace(shortStr, i, index-1);
            } else {
                movePlace(shortStr, 0, index-1);
            }
            permu2(shortStr, index - 1);
        }

    }

    private static void permu(char[] shortStr, String longStr, int index) {
        if (index >= shortStr.length) {
            System.out.println(shortStr);
//            Version1.findOccurence(perm, longStr);
            return;
        }

        for (int i = index; i < shortStr.length; i++) {
            movePlace(shortStr, index, i);
            permu(shortStr, longStr, index + 1);
            movePlace(shortStr, index, i);
        }
    }

    private static void movePlace(char[] shortStr, int index, int moveIndex) {
        char temp = shortStr[index];
        shortStr[index] = shortStr[moveIndex];
        shortStr[moveIndex] = temp;
    }
}
