package name.murfel.java.hw06;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Collections {

    /**
     * Apply the mapping function independently to each element of inputIterable.
     * It preserves the total number of elements.
     *
     * @param function      a function to apply to each element
     * @param inputIterable an iterable with elements to apply the function to
     * @param <Domain>      type of input elements
     * @param <Range>       type of output elements
     * @return an ArrayList of elements to which the mapping was applied
     */
    public static <Domain, Range> ArrayList<Range> map(Function1<Domain, Range> function, Iterable<Domain> inputIterable) {
        ArrayList<Range> output = new ArrayList<>();
        for (Domain element : inputIterable) {
            output.add(function.apply(element));
        }
        return output;
    }

    /**
     * Leave only those elements which conform to the given predicate.
     * Output list consists only of those elements which satisfy the predicate.
     * The elements themselves are left unchanged.
     *
     * @param predicate     a predicate returning true on elements which will be taken into output
     * @param inputIterable an iterable with input elements
     * @return an ArrayList of elements which satisfy the predicate
     */
    public static <T> ArrayList<T> filter(Predicate<T> predicate, Iterable<T> inputIterable) {
        ArrayList<T> output = new ArrayList<>();
        for (T element : inputIterable) {
            if (predicate.apply(element)) {
                output.add(element);
            }
        }
        return output;
    }

    /**
     * Take elements while they conform to the predicate. Once the first element which does not conform to predicate
     * is encountered, stop and return the constructed list without including that element.
     * All elements in the return list conform to the predicate.
     * Elements are added to the output list without any change.
     *
     * @param predicate     a predicate which outputs false on first element to be included
     * @param inputIterable an iterable with elements to take from
     * @return an ArrayList with all elements before the first element which does not conform to predicate
     */
    public static <T> ArrayList<T> takeWhile(Predicate<T> predicate, Iterable<T> inputIterable) {
        ArrayList<T> list = new ArrayList<>();
        for (T element : inputIterable) {
            if (!predicate.apply(element)) {
                break;
            }
            list.add(element);
        }
        return list;
    }

    /**
     * TakeWhile with inverted predicate.
     */
    public static <T> ArrayList<T> takeUnless(Predicate<T> predicate, Iterable<T> inputIterable) {
        return takeWhile(predicate.not(), inputIterable);
    }

    /**
     * Same as foldr(Function2<Input, Result, Result>, Result, Iterator<Input>)
     * but take a collection instead of Iterator<Input>.
     */
    public static <Input, Result> Result foldr(Function2<Input, Result, Result> function,
                                               Result initial, Collection<Input> collection) {
        return foldr(function, initial, collection.iterator());
    }

    /**
     * Folds the elements in input iterator by applying a function to every element to the result
     * of previous application and this element. The initial result value is specified by initial.
     * <p>
     * For example, an iterator with elements 1 2 3, initial value 0, and a summing function
     * will be right folded like (3 + (2 + (1 + 0))) = 6.
     * <p>
     * The concept of fold is taken from the functional programming paradigm.
     *
     * @param function a function to get next result out of a partial result and current element
     * @param initial  an initial value of result
     * @param iterator an iterator with elements to apply the function to
     * @return the result of last application of the function
     */
    public static <Input, Result> Result foldr(Function2<Input, Result, Result> function, Result initial,
                                               Iterator<Input> iterator) {
        if (!iterator.hasNext())
            return initial;
        return function.apply(iterator.next(), foldr(function, initial, iterator));
    }

    /**
     * Same as foldl(Function2<Result, Input, Result>, Result, Iterator<Input>)
     * but take a collection instead of Iterator<Input>.
     */
    public static <Input, Result> Result foldl(Function2<Result, Input, Result> function, Result initial,
                                               Collection<Input> collection) {
        return foldl(function, initial, collection.iterator());
    }

    /**
     * Same as foldr but applies the function in another direction (see example below).
     * <p>
     * For example, an iterator with elements 1 2 3, initial value 0, and a summing function
     * will be left folded like (((0 + 1) + 2) + 3) = 6.
     */
    public static <Input, Result> Result foldl(Function2<Result, Input, Result> function, Result initial,
                                               Iterator<Input> iterator) {
        if (!iterator.hasNext())
            return initial;
        return foldl(function, function.apply(initial, iterator.next()), iterator);
    }
}