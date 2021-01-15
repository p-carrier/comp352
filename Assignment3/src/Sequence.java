/*
 * Philippe Carrier 40153985
 * COMP352 Section AA
 * Assignment # 3
 * Sunday, June 14, 2020
 * */
import java.util.Stack;

/**
 * Sequence data structure for the CVR class
 */
public class Sequence {

    Node head;
    Node tail;
    private int size;

    public Sequence() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Retrieve all the VIN of the cars in sequence
     * @return  String[] - The VIN of the cars in CRV
     */
    public String[] allKeys() {
        Node current = head;
        System.out.println(size);
        String[] keys = new String[size];
        int pointer = 0;
        while (current != null) {
            keys[pointer] = current.getKey();
            current = current.getNext();
            pointer++;
        }
        return keys;
    }

    /**
     * Add a car entry to sequence
     * @param key   String - The VIN of the car
     * @param value Car - the description of the car
     */
    public void add(String key, Car value) {
        Node car = new Node(key, null, null, value);
        size++;
        if (head == null) {
            head = tail = car;
        } else {
            tail.setNext(car);
            car.setPrev(tail);
            tail = car;
        }
    }

    /**
     * Get the node of the specific car
     * @param key   String - the VIN of the car
     * @return  The node of the specific car
     */
    private Node getCurrentNode(String key) {
        Node current = head;
        while (!current.getKey().equals(key)) {
            current = current.getNext();
        }
        return current;
    }

    /**
     * Remove a car entry from the sequence
     * @param key   String - The VIN of the car to remove
     * @return  CAR - The description of the car remove
     */
    public Car remove(String key) {
        Node current = getCurrentNode(key);
        if (current == null)
            return null;
        size--;
        if (current.getNext() == null && current.getPrev() == null) {
            head = tail = null;
        } else if (current.getNext() == null) {
            current.getPrev().setNext(null);
            tail = current.getPrev();
        } else if (current.getPrev() == null) {
            head = current.getNext();
            current.getNext().setPrev(null);
        } else {
            current.getPrev().setNext(current.getNext());
            current.getNext().setPrev(current.getPrev());
        }

        return current.getValue();
    }

    /**
     * Get the value of a specific car
     * @param key   String - The VIN of the car to search for
     * @return  Car - The description of the car
     */
    public Car getValue(String key) {
        Node current = getCurrentNode(key);
        return current.getValue();
    }

    /**
     * Get the value of the VIN of car following the car of the inputted VIN
     * @param key   String - the VIN of the previous car
     * @return      String - The VIN of the next car
     */
    public String nextKey(String key) {
        Node current = getCurrentNode(key);
        return current.getNext().getKey();
    }

    /**
     * Get the value of the VIN of the car preceding the car of the inputted VIN
     * @param key   String - the VIN of the following car
     * @return      String - the VIN of the previous car
     */
    public String prevKey(String key) {
        Node current = getCurrentNode(key);
        return current.getPrev().getKey();
    }

    /**
     * Get the list of accidents that a specific had.
     * @param key   String - the VIN of the car
     * @return  Stack<Accident> - The accidents that a specific car had
     */
    public Stack<Accident> prevAccids(String key) {
        Node current = getCurrentNode(key);
        return current.getValue().getAccidents();
    }
}
