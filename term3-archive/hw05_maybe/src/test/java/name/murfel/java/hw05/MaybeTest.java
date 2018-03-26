package name.murfel.java.hw05;

import org.junit.Test;

import static org.junit.Assert.*;

public class MaybeTest {
    @Test
    public void justGet() throws Exception {
        Maybe<Integer> maybe = Maybe.just(555);
        assertEquals(new Integer(555), maybe.get());
    }

    @Test
    public void nothing() throws Exception {
        Maybe<Integer> maybe = Maybe.nothing();
    }

    @Test
    public void isPresentNothing() throws Exception {
        Maybe<Integer> maybe = Maybe.nothing();
        assertFalse(maybe.isPresent());
    }

    @Test
    public void isPresentData() throws Exception {
        Maybe<Integer> maybe = Maybe.just(555);
        assertTrue(maybe.isPresent());
    }

    @Test
    public void mapToNothingDoesNotMutateMe() throws Exception {
        Maybe<Integer> maybe = Maybe.nothing();
        maybe.map(x -> 777);
        assertFalse(maybe.isPresent());
    }

    @Test
    public void mapToNothingDoesNewObjectRight() throws Exception {
        Maybe<Integer> maybe = Maybe.nothing();
        Maybe<Integer> newMaybe = maybe.map(x -> 777);
        assertFalse(newMaybe.isPresent());
    }

    @Test
    public void mapToDataDoesNotMutateMe() throws Exception {
        Maybe<Integer> maybe = Maybe.just(555);
        maybe.map(x -> x + 222);
        assertTrue(maybe.isPresent());
        assertEquals(new Integer(555), maybe.get());
    }

    @Test
    public void mapToDataDoesNewObjectRight() throws Exception {
        Maybe<Integer> maybe = Maybe.just(555);
        Maybe<Integer> newMaybe = maybe.map(x -> x + 222);
        assertTrue(newMaybe.isPresent());
        assertEquals(new Integer(777), newMaybe.get());
    }

}