/*
 * Philippe Carrier 40153985
 * COMP352 Section AA
 * Assignment # 3
 * Sunday, June 14, 2020
 * */
import java.util.Random;
import java.util.Stack;

/**
 * Class that store the information of cars inside a data structure
 */
public class CVR {

    private int threshold;
    private final int totalNumVIN;
    private int keyLength;
    private int currentNumVin;
    private boolean isBelowThreshold;
    private Sequence sequence;
    private AVLTreeMap tree;

    private final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public CVR(int totalNumVIN) {
        this.totalNumVIN = totalNumVIN;
        this.currentNumVin = 0;
        isBelowThreshold = true;
        sequence = new Sequence();
    }

    /**
     * Set the threshold at which the way to store the value will change
     * @param threshold  int - the limit at which the method of storing value change
     */
    public void setThreshold(int threshold) {
        this.threshold = threshold;
        if (currentNumVin >= this.threshold) {
            isBelowThreshold = false;
        }
    }

    /**
     * Set the number of character that will comprise the VIN
     * @param keyLength     int - the length of the VIN
     */
    public void setKeyLength(int keyLength) {
        this.keyLength = keyLength;
    }

    private boolean verifyKeyLength(String key) {
        return key.length() == keyLength;
    }

    /**
     * Generate random VIN for a specify number of vehicle
     * @param n int - The number of VIN to generate
     * @return  String[] -  All the VIN generated
     */
    public String[] generate(int n) {
        String[] keys = new String[n];
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            StringBuilder key = new StringBuilder();
            for (int y = 0; y < keyLength; y++) {
                key.append(CHARACTERS.charAt(r.nextInt(CHARACTERS.length())));
            }
            keys[i] = key.toString();
        }
        return keys;
    }

    /**
     * Retrieve all the VIN of the cars in the data structure
     * @return  String[] - The VIN of the cars in CRV
     */
    public String[] allKeys() {
        String[] allKeys;
        if (isBelowThreshold) {
            allKeys = sequence.allKeys();
        } else {
            allKeys = tree.allKeys();
        }
        return allKeys;
    }

    /**
     * Add a car entry to CRV
     * @param key   String - The VIN of the car
     * @param value Car - the description of the car
     */
    public void add(String key, Car value) {
        if (verifyKeyLength(key)) {
            if (++currentNumVin >= threshold && isBelowThreshold) {
                isBelowThreshold = false;
                sequenceToTree();
            }

            if (isBelowThreshold) {
                sequence.add(key, value);
            } else {
                tree.add(key, value);
            }
        } else {
            System.out.println("Key length does not fit the appropriate length of " + keyLength + " characters.");
        }
    }

    /**
     * Remove a car entry from CRV
     * @param key   String - The VIN of the car to remove
     * @return  CAR - The description of the car remove
     */
    public Car remove(String key) {

        Car value = null;
        if (verifyKeyLength(key)) {
            if (--currentNumVin < threshold && !isBelowThreshold) {
                isBelowThreshold = true;
                treeToSequence();
            }
            if (isBelowThreshold) {
                value = sequence.remove(key);
            } else {
                value = tree.remove(key);
            }
        } else {
            System.out.println("Key length does not fit the appropriate length of " + keyLength + " characters.");
        }
        return value;
    }

    /**
     * Get the value of a specific car
     * @param key   String - The VIN of the car to search for
     * @return  Car - The description of the car
     */
    public Car getValue(String key) {
        if (verifyKeyLength(key)) {
            if (isBelowThreshold) {
                return sequence.getValue(key);
            }
            return tree.getValue(key);
        } else {
            System.out.println("Key length does not fit the appropriate length of " + keyLength + " characters.");
            return null;
        }
    }


    /**
     * Get the value of the VIN of car following the car of the inputted VIN
     * @param key   String - the VIN of the previous car
     * @return      String - The VIN of the next car
     */
    public String nextKey(String key) {
        if (verifyKeyLength(key)) {
            if (isBelowThreshold) {
                return sequence.nextKey(key);
            }
            return tree.nextKey(key);
        } else {
            System.out.println("Key length does not fit the appropriate length of " + keyLength + " characters.");
            return "";
        }
    }

    /**
     * Get the value of the VIN of the car preceding the car of the inputted VIN
     * @param key   String - the VIN of the following car
     * @return      String - the VIN of the previous car
     */
    public String prevKey(String key) {
        if (verifyKeyLength(key)) {
            if (isBelowThreshold) {
                return sequence.prevKey(key);
            }
            return tree.prevKey(key);
        } else {
            System.out.println("Key length does not fit the appropriate length of " + keyLength + " characters.");
            return "";
        }
    }

    /**
     * Get the list of accidents that a specific had.
     * @param key   String - the VIN of the car
     * @return  Stack<Accident> - The accidents that a specific car had
     */
    public Stack<Accident> prevAccids(String key) {
        if (verifyKeyLength(key)) {
            if (isBelowThreshold) {
                return sequence.prevAccids(key);
            }
            return tree.prevAccids(key);
        } else {
            System.out.println("Key length does not fit the appropriate length of " + keyLength + " characters.");
            return null;
        }
    }

    /**
     * Transform the sequence to a tree once the number of value pass the threshold
     */
    private void sequenceToTree() {
        Node current = sequence.head;
        tree = new AVLTreeMap();
        while (current != null) {
            tree.add(current.getKey(), current.getValue());
            current = current.getNext();
        }
    }

    /**
     * Transform the tree to a sequence once the number of value goes below
     * the threshold
     */
    private void treeToSequence() {
        sequence = new Sequence();
        AVLTreeMap.treeTraversal(tree.root, sequence);
    }
}
