import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Implement the Trie data structure for storing strings in such a way to provide an effective lookup.
 */
public class Trie implements Serializable {

    /**
     * Add the string {@code element} to the trie. Works in O(|element|).
     *
     * @param element  an element to add
     * @return true if there were no such string before, false otherwise
     */
    boolean add(String element) {
        return false;
    }

    /**
     * Check whether the string {@code element} is present in the trie. Works in O(|element|).
     *
     * @param element  a string to look for in the trie
     * @return true if there is such an element, false otherwise
     */
    boolean contains(String element) {
        return false;
    }

    /**
     * Remove the string {@code element} from the trie. Works in O(|element|).
     *
     * @param element  a string to remove
     * @return true if there were such an element, false otherwise
     */
    boolean remove(String element) {
        return false;
    }

    /**
     * Get the size of the trie in the number of unique strings added to the trie and not removed from it.
     * Works in O(1).
     *
     * @return the number of strings in the trie
     */
    int size() {
        return 0;
    }

    /**
     * Count the number of strings in the trie that start exactly with the {@code prefix}
     * (i.e. those {@code s} for which s.startsWith(prefix) holds true). Works in O(|prefix|).
     *
     * @param prefix  a prefix string to look for
     * @return the number of string that start with {@code prefix}
     */
    int howManyStartsWithPrefix(String prefix) {
        return 0;
    }

    /**
     * Serialize the Trie object into the output stream {@code out}.
     *
     * @param out  an output stream to store the result of serialization
     * @throws IOException
     */
    void serialize(OutputStream out) throws IOException {

    }

    /**
     * Deserialize the True object from the input stream {@code in}.
     *
     * @param in  an input stream with the data for deserialization
     * @throws IOException
     */
    void deserialize(InputStream in) throws IOException {

    }

}
