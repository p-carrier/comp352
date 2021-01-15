/*
 * Philippe Carrier 40153985
 * COMP352 Section AA
 * Assignment # 3
 * Sunday, June 14, 2020
 * */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * AVL Tree Map for the CVR
 */
public class AVLTreeMap {

    public AvlNode root = null;
    private int numNode;
    public AVLTreeMap() {}

    /**
     * Node for the AVL tree
     */
    public static class AvlNode {
        AvlNode leftChild, rightChild, parent;
        String key;
        Car value;
        int height;

        public AvlNode(AvlNode leftChild, AvlNode rightChild, AvlNode parent, String key, Car value, int height) {
            this.leftChild = leftChild;
            this.rightChild = rightChild;
            this.parent = parent;
            this.key = key;
            this.value = value;
            this.height = height;
        }

        /**
         * SEt the left child of the node
         * @param leftChild AVLNode - The left child of the node
         */
        public void setLeftChild(AvlNode leftChild) {
            this.leftChild = leftChild;
        }

        /**
         * Set the right child of the node
         * @param rightChild    AVLNode - The right child of the node
         */
        public void setRightChild(AvlNode rightChild) {
            this.rightChild = rightChild;
        }

        /**
         * Set the parent of the node
         * @param parent    AVLNode - The parent of the node
         */
        public void setParent(AvlNode parent) {
            this.parent = parent;
        }

        @Override
        public String toString() {
            return "AvlNode{" +
                    ", key='" + key + '\'' +
                    "leftChild=" + leftChild +
                    ", rightChild=" + rightChild +
                    '}';
        }
    }

    /**
     * Traverse the whole tree to extract all the value
     * @param node  AVLNode - the current node
     * @param sequence  Sequence - Where to store the value of the tree
     */
    public static void treeTraversal(AvlNode node, Sequence sequence) {
        if (node != null) {
            sequence.add(node.key, node.value);
            treeTraversal(node.leftChild, sequence);
            treeTraversal(node.rightChild, sequence);
        }
    }

    /**
     * The height of a specific node
     * @param node  AVLNode - The specific node
     * @return  int - the height of the node
     */
    private int height(AvlNode node) {
        if (node == null)
            return 0;
        return node.height;
    }

    /**
     * Whether the tree is balance or not
     * @param node  AVLNode - The specific node
     * @return  boolean
     */
    private boolean isBalanced(AvlNode node) {
        if (node == null)
            return true;
        return Math.abs(height(node.leftChild) - height(node.rightChild)) <= 1;
    }

    /**
     * Calculate the height of the node
     * @param node  AVLNode - the specific node
     */
    private void recomputeHeight(AvlNode node) {
        if (node != null)
            node.height = Math.max(height(node.leftChild), height(node.rightChild)) + 1;
    }

    /**
     * Get the child with the highest height
     * @param node  AVLNode - The specific node
     * @return  AVLNode - the child with the highest height
     */
    private AvlNode highestChild(AvlNode node) {
        if (height(node.leftChild) > height(node.rightChild))
            return node.leftChild;
        if (height(node.leftChild) < height(node.rightChild))
            return node.rightChild;
        if (root == node)
            return node.leftChild;
        if (node == node.parent.leftChild)
            return node.leftChild;
        return node.rightChild;
    }

    /**
     * Restructure the tree to fix the imbalance
     * @param x AVLNode - Node part of the imbalance
     * @return  AVLNode - The head of the new subtree
     */
    private AvlNode restructure(AvlNode x) {
        AvlNode y = x.parent;
        AvlNode z = y.parent;
        if (((x == y.rightChild) && (y == z.rightChild)) ||
                ((x == y.leftChild) && (y == z.leftChild))) {
            rotate(y);
            return y;
        } else {
            rotate(x);
            rotate(x);
            return x;
        }
    }

    /**
     * Rotation of the node to restructure the tree
     * @param node1 AVLNode - The pivot node
     */
    private void rotate(AvlNode node1) {
        AvlNode node2 = node1.parent;
        AvlNode node3 = node2.parent;

        if(node3 == null) {
            root = node1;
            node1.setParent(null);
        } else {
            changeOrder(node3, node1, node2 == node3.rightChild);
        }

        if (node1 == node2.rightChild) {
            changeOrder(node2, node1.leftChild, true);
            changeOrder(node1, node2, false);
        } else if(node1 == node2.leftChild) {
            changeOrder(node2, node1.rightChild, false);
            changeOrder(node1, node2, true);
        }
    }

    /**
     * Restructure the child parent relationship
     * @param parent    AVLNode - the new parent of the child node
     * @param child     AVLNode - the new child of the parent node
     * @param isRightChild  boolean - whether the child is on right or on the left
     */
    private void changeOrder(AvlNode parent, AvlNode child, boolean isRightChild) {
        if (child != null)
            child.setParent(parent);
        if (isRightChild)
            parent.setRightChild(child);
        else
            parent.setLeftChild(child);
    }

    /**
     * Find the position of a specific node
     * @param key   String - The VIN of the car in the node
     * @param isInsert  boolean - Whether it is for an insertion or not
     * @return  The node at a specific position
     */
    private AvlNode findPosition(String key, boolean isInsert) {
        AvlNode current = root;
        int diff;
        do {
            diff = current.key.compareTo(key);
            AvlNode temp = nextNode(diff, current);
            if (temp == null)
                break;
            current = temp;
        } while (diff != 0);
        if (isInsert)
            return current;
        else
            return current.key.equals(key) ? current : null;
    }

    /**
     * Where to continue the research for the specific node
     * @param diff  int - the difference between the search VIN and the node VIN
     * @param node  AVLNode - the current node
     * @return  AVLNode - The next node
     */
    private AvlNode nextNode(int diff, AvlNode node) {
        if (diff < 0) {
            return node.rightChild;
        } else {
            return node.leftChild;
        }
    }

    /**
     * Retrieve all the VIN of the cars in the data structure
     * @return  String[] - The VIN of the cars in CRV
     */
    public String[] allKeys() {
        ArrayList<String> keys = new ArrayList<>();
        treeTrav(root, keys);
        Object[] arr = keys.toArray();
        return Arrays.copyOf(arr, arr.length, String[].class);
    }

    /**
     * Traverse the tree to extract the VIN of all car
     * @param node  AVLNode - the current node
     * @param keys  ArrayList<String> - Where to store the keys
     */
    private void treeTrav(AvlNode node, ArrayList<String> keys) {
        if (node != null) {
            keys.add(node.key);
            treeTrav(node.leftChild, keys);
            treeTrav(node.rightChild, keys);
        }
    }

    /**
     * Add a car entry to CRV
     * @param key   String - The VIN of the car
     * @param value Car - the description of the car
     */
    public void add(String key, Car value) {
        AvlNode child = new AvlNode(null, null, null, key, value, 1);
        numNode++;

        if (root == null)
            root = child;
        else {
            AvlNode parent = findPosition(key, true);
            child.setParent(parent);
            if (parent.key.compareTo(key) < 0)
                parent.setRightChild(child);
            else
                parent.setLeftChild(child);

            do {
                if (!isBalanced(parent)) {
                    parent = restructure(highestChild(highestChild(parent)));
                    recomputeHeight(parent.leftChild);
                    recomputeHeight(parent.rightChild);
                }
                recomputeHeight(parent);
                parent = parent.parent;
            } while (parent != null);
        }
    }

    /**
     * Remove a car entry from CRV
     * @param key   String - The VIN of the car to remove
     * @return  CAR - The description of the car remove
     */
    public Car remove(String key) {
        if (root == null)
            return null;
        AvlNode current = findPosition(key, false);
        if (current == null)
            return null;
        AvlNode parent = current.parent;
        AvlNode leftChild = current.leftChild;
        AvlNode rightChild = current.rightChild;
        AvlNode lParent = null;
        if (leftChild == null && rightChild == null) {
            if (current == parent.leftChild)
                parent.leftChild = null;
            else
                parent.rightChild = null;
        } else if (leftChild == null) {
            if (current == parent.leftChild)
                parent.leftChild = current.rightChild;
            else
                parent.rightChild = current.rightChild;
        } else if (rightChild == null) {
            if (current == parent.rightChild)
                parent.leftChild = current.leftChild;
            else
                parent.rightChild = current.leftChild;
        } else {
            while (rightChild.leftChild != null) {
                rightChild = rightChild.leftChild;
            }
            lParent = rightChild.parent;
            AvlNode lRChild = rightChild.rightChild;
            rightChild.leftChild = current.leftChild;
            rightChild.rightChild = current.rightChild;
            if (current == parent.rightChild)
                parent.leftChild = rightChild;
            else
                parent.rightChild = rightChild;
            lParent.leftChild = lRChild;
        }

        do {
            if (!isBalanced(lParent == null ? parent : lParent)) {
                parent = restructure(highestChild(highestChild(parent)));
                recomputeHeight(parent.leftChild);
                recomputeHeight(parent.rightChild);
            }
            recomputeHeight(parent);
            parent = parent.parent;
        } while (parent != null);
        return current.value;
    }

    /**
     * Get the value of a specific car
     * @param key   String - The VIN of the car to search for
     * @return  Car - The description of the car
     */
    public Car getValue(String key) {
        AvlNode current = findPosition(key, false);
        if (current == null) {
            return null;
        }
        return current.value;
    }

    /**
     * Get the value of the VIN of car following the car of the inputted VIN
     * @param key   String - the VIN of the previous car
     * @return      String - The VIN of the next car
     */
    public String nextKey(String key) {
        AvlNode current = findPosition(key, false);
        if (current == null) {
            return null;
        }
        if (current.leftChild != null)
            return current.leftChild.key;
        if (current.rightChild != null)
            return current.rightChild.key;
        if (current.parent != null)
            return current.parent.key;
        return null;
    }

    /**
     * Get the value of the VIN of the car preceding the car of the inputted VIN
     * @param key   String - the VIN of the following car
     * @return      String - the VIN of the previous car
     */
    public String prevKey(String key) {
        AvlNode current = findPosition(key, false);
        if (current == null) {
            return null;
        }
        if (current.parent != null)
            return current.parent.key;
        return null;
    }

    /**
     * Get the list of accidents that a specific had.
     * @param key   String - the VIN of the car
     * @return  Stack<Accident> - The accidents that a specific car had
     */
    public Stack<Accident> prevAccids(String key) {
        Car car = getValue(key);
        return car.getAccidents();
    }

}
