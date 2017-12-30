import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Implement the Trie data structure for storing (adding and removing) unique strings in a way allowing effective lookup
 * and an interface to count how many strings start with the given prefix.
 */
public class Trie implements Serializable {
    /**
     * Add the string {@code element} to the trie. If such a string is already present in the trie, do nothing, as
     * copies are not allowed.
     *<p>
     * Works in O(|element|).
     *
     * @param element  an element to add
     * @return true if there were no such string before, false otherwise
     */
    boolean add(String element) {
        if (contains(element)) {
            return false;
        }
        Node node = root;
        for (char c : element.toCharArray()) {
            node.terminatingChildrenCount++;
            if (!node.children.containsKey(c)) {
                node.children.put(c, new Node());
            }
            node = node.children.get(c);
        }
        node.isTerminating = true;
        return true;
    }

    /**
     * Check whether the string {@code element} is present in the trie. Works in O(|element|).
     *
     * @param element  a string to look for in the trie
     * @return true if there is such an element, false otherwise
     */
    boolean contains(String element) {
        Node node = root;
        for (char c : element.toCharArray()) {
            if (!node.children.containsKey(c)) {
                return false;
            }
            node = node.children.get(c);
        }
        return node.isTerminating;
    }

    /**
     * Remove the string {@code element} from the trie. Also, clean up unneeded nodes with chars to save memory.
     * Works in O(|element|).
     *
     * @param element  a string to remove
     * @return true if there were such an element, false otherwise
     */
    boolean remove(String element) {
        if (!contains(element)) {
            return false;
        }

        Node node = root;
        for (int i = 0; i < element.length(); i++) {
            node.terminatingChildrenCount--;
            char chr = element.charAt(i);
            node = node.children.get(chr);
        }
        node.isTerminating = false;

        char curChar;
        Node prevNode;
        node = root;
        for (int i = 0; i < element.length(); i++) {
            curChar = element.charAt(i);
            prevNode = node;
            node = node.children.get(curChar);
            if (node.terminatingChildrenCount == 0 && !node.isTerminating) {
                prevNode.children.remove(curChar);
                break;
            }
        }
        return true;
    }

    /**
     * Get the size of the trie in the number of unique strings added to the trie and not removed from it.
     * Works in O(1).
     *
     * @return the number of strings in the trie
     */
    int size() {
        return root.terminatingChildrenCount + (root.isTerminating ? 1 : 0);
    }

    /**
     * Count the number of strings in the trie that start exactly with the {@code prefix}
     * (i.e. those {@code s} for which s.startsWith(prefix) holds true). Works in O(|prefix|).
     *
     * @param prefix  a prefix string to look for
     * @return the number of string that start with {@code prefix}
     */
    int howManyStartsWithPrefix(String prefix) {
        Node node = root;
        for (char c : prefix.toCharArray()) {
            if (!node.children.containsKey(c)) {
                return 0;
            }
            node = node.children.get(c);
        }
        return node.terminatingChildrenCount + (node.isTerminating ? 1 : 0);
    }

    private class Node {
        private boolean isTerminating;
        private int terminatingChildrenCount;  // not including yourself
        private HashMap<Character, Node> children = new HashMap<Character, Node>();
    }

    private Node root = new Node();
}
