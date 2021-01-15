public class Node {

    private final String key;
    private Node next;
    private Node prev;
    private Car value;

    public Node(String key, Node next, Node prev, Car value) {
        this.key = key;
        this.next = next;
        this.prev = prev;
        this.value = value;
    }

    /**
     * Get the VIN of the car
     * @return  The VIN of the car
     */
    public String getKey() {
        return key;
    }

    /**
     * Get the node of the following car
     * @return  Node - The node of the car
     */
    public Node getNext() {
        return next;
    }

    /**
     * Set the node of the following car
     * @param next  Node - The node of the following car
     */
    public void setNext(Node next) {
        this.next = next;
    }

    /**
     * Get the node of the previous car
     * @return  Node - the node of the previous car
     */
    public Node getPrev() {
        return prev;
    }

    /**
     * Set the node of the previous car
     * @param prev  Node - The node of the previous car
     */
    public void setPrev(Node prev) {
        this.prev = prev;
    }

    /**
     * Get the value of the car
     * @return  Car - the value of the car
     */
    public Car getValue() {
        return value;
    }

    /**
     * View the information of the node
     * @return  String - The information of the car.
     */
    @Override
    public String toString() {
        return "Node{" +
                "key='" + key + '\'' +
//                ", next=" + next +
                ", prev=" + prev +
//                ", value=" + value +
                '}';
    }
}
