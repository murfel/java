import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class TrieTest {
    private List<String> smallSample = Arrays.asList("meow", "purr", "meown", "me");
    private List<String> mediumSample = Arrays.asList("A", "to", "tea", "ted", "ten", "i", "in", "inn");

    private void randomShuffleAddRepeat(List<String> inputData, int repeat) throws Exception {
        for (int i = 0; i < repeat; i++) {
            Trie trie = new Trie();
            Collections.shuffle(inputData);
            for (String s : inputData) {
                trie.add(s);
            }
            assertEquals(inputData.size(), trie.size());
        }
    }

    @org.junit.Test
    public void addOneJust() throws Exception {
        Trie trie = new Trie();
        trie.add("kotiki");
    }

    @org.junit.Test
    public void addTwoContains() throws Exception {
        Trie trie = new Trie();
        trie.add("kotiki");
        assertTrue(trie.contains("kotiki"));
        trie.add("meow");
        assertTrue(trie.contains("meow"));
    }

    @org.junit.Test
    public void containsEmpty() throws Exception {
        Trie trie = new Trie();
        assertFalse(trie.contains("meow"));
    }

    @org.junit.Test
    public void containsNoEmptyStringEmpty() throws Exception {
        Trie trie = new Trie();
        assertFalse(trie.contains(""));
    }

    @org.junit.Test
    public void addOneContains() throws Exception {
        Trie trie = new Trie();
        trie.add("kotiki");
        assertTrue(trie.contains("kotiki"));
    }

    @org.junit.Test
    public void containsOneNegative() throws Exception {
        Trie trie = new Trie();
        trie.add("kotiki");
        assertFalse(trie.contains("korovki"));
    }

    @org.junit.Test
    public void containsAfterRemove() throws Exception {
        Trie trie = new Trie();
        trie.add("kotiki");
        trie.add("korovki");
        trie.remove("kotiki");
        assertTrue(trie.contains("korovki"));
    }

    @org.junit.Test
    public void containsAfterRemoveNegative() throws Exception {
        Trie trie = new Trie();
        trie.add("kotiki");
        trie.add("korovki");
        trie.remove("kotiki");
        assertFalse(trie.contains("kotiki"));
    }

    @org.junit.Test
    public void removeFromEmpty() throws Exception {
        Trie trie = new Trie();
        assertFalse(trie.remove("kotiki"));
        assertEquals(0, trie.size());
    }

    @org.junit.Test
    public void removeEmptyStringFromEmpty() throws Exception {
        Trie trie = new Trie();
        assertFalse(trie.remove(""));
        assertEquals(0, trie.size());
    }

    @org.junit.Test
    public void addRemove() throws Exception {
        Trie trie = new Trie();
        trie.add("kotiki");
        assertTrue(trie.remove("kotiki"));
        assertEquals(0, trie.size());
    }

    @org.junit.Test
    public void addRemoveNegative() throws Exception {
        Trie trie = new Trie();
        trie.add("kotiki");
        assertFalse(trie.remove("korovki"));
        assertEquals(1, trie.size());
    }

    @org.junit.Test
    public void addTwoRemoveOne() throws Exception {
        Trie trie = new Trie();
        trie.add("kotiki");
        trie.add("korovki");
        assertTrue(trie.remove("kotiki"));
    }

    @org.junit.Test
    public void addRemoveSmallSample() throws Exception {
        Trie trie = new Trie();
        for (String s : smallSample) {
            trie.add(s);
        }
        for (String s : smallSample) {
            assertTrue(trie.remove(s));
        }
        assertEquals(0, trie.size());
    }

    @org.junit.Test
    public void sizeEmpty() throws Exception {
        Trie trie = new Trie();
        assertEquals(0, trie.size());
    }

    @org.junit.Test
    public void sizeOne() throws Exception {
        Trie trie = new Trie();
        trie.add("kotiki");
        assertEquals(1, trie.size());
    }

    @org.junit.Test
    public void sizeOneAddedTwice() throws Exception {
        Trie trie = new Trie();
        trie.add("kotiki");
        trie.add("kotiki");
        assertEquals(1, trie.size());
    }

    @org.junit.Test
    public void sizeTwoOneAddedTwice() throws Exception {
        Trie trie = new Trie();
        trie.add("kotiki");
        trie.add("murmurmur");
        trie.add("kotiki");
        assertEquals(2, trie.size());
    }

    @org.junit.Test
    public void sizeSmallSample() throws Exception {
        Trie trie = new Trie();
        for (String s : smallSample) {
            trie.add(s);
        }
        assertEquals(smallSample.size(), trie.size());
    }

    @org.junit.Test
    public void sizeSmallSampleRandomShuffled() throws Exception {
        randomShuffleAddRepeat(smallSample, 100);
    }

    @org.junit.Test
    public void sizeMediumSample() throws Exception {
        Trie trie = new Trie();
        for (String s : mediumSample) {
            trie.add(s);
        }
        assertEquals(mediumSample.size(), trie.size());
    }

    @org.junit.Test
    public void sizeMediumSampleRandomShuffled() throws Exception {
        randomShuffleAddRepeat(mediumSample, 100);
    }

    @org.junit.Test
    public void howManyStartsWithPrefixEmpty() throws Exception {
        Trie trie = new Trie();
        assertEquals(0, trie.howManyStartsWithPrefix("meow"));
    }

    @org.junit.Test
    public void howManyStartsWithPrefixOneElement() throws Exception {
        Trie trie = new Trie();
        trie.add("meow");
        assertEquals(1, trie.howManyStartsWithPrefix(""));
        assertEquals(1, trie.howManyStartsWithPrefix("m"));
        assertEquals(1, trie.howManyStartsWithPrefix("me"));
        assertEquals(1, trie.howManyStartsWithPrefix("meo"));
        assertEquals(1, trie.howManyStartsWithPrefix("meow"));
        assertEquals(0, trie.howManyStartsWithPrefix("meown"));
    }

    @org.junit.Test
    public void howManyStartsWithPrefixMultipleElements() throws Exception {
        Trie trie = new Trie();
        // Arrays.asList("A", "to", "tea", "ted", "ten", "i", "in", "inn");
        for (String s : mediumSample) {
            trie.add(s);
        }
        assertEquals(mediumSample.size(), trie.howManyStartsWithPrefix(""));
        assertEquals(0, trie.howManyStartsWithPrefix("too"));
        assertEquals(0, trie.howManyStartsWithPrefix("a"));
        assertEquals(4, trie.howManyStartsWithPrefix("t"));
        assertEquals(3, trie.howManyStartsWithPrefix("te"));
        assertEquals(3, trie.howManyStartsWithPrefix("i"));
        assertEquals(2, trie.howManyStartsWithPrefix("in"));
    }
}