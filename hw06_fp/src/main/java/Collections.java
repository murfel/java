import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Collections {

    public static <A, B> ArrayList<B> map(Function1<A, B> f, Iterable<A> a) {
        ArrayList<B> b = new ArrayList<>();
        for (A ai : a) {
            b.add(f.apply(ai));
        }
        return b;
    }

    public static <A> ArrayList<A> filter(Predicate<A> p, Iterable<A> a) {
        ArrayList<A> list = new ArrayList<>();
        for (A ai : a) {
            if (p.apply(ai)) {
                list.add(ai);
            }
        }
        return list;
    }

    public static <A> ArrayList<A> takeWhile(Predicate<A> p, Iterable<A> a) {
        ArrayList<A> list = new ArrayList<>();
        for (A ai : a) {
            if (p.apply(ai)) {
                list.add(ai);
            } else {
                break;
            }
        }
        return list;
    }

    public static <A> ArrayList<A> takeUnless(Predicate<A> p, Iterable<A> a) {
        return takeWhile(p.not(), a);
    }

    public static <A, B> B foldr(Function2<A, B, B> f, B b, Collection<A> col) {
        return foldr(f, b, col.iterator());
    }

    private static <A, B> B foldr(Function2<A, B, B> f, B b, Iterator<A> it) {
        if (!it.hasNext())
            return b;
        return f.apply(it.next(), foldr(f, b, it));
    }

    public static <A, B> B foldl(Function2<B, A, B> f, B b, Collection<A> col) {
        return foldl(f, b, col.iterator());
    }

    private static <A, B> B foldl(Function2<B, A, B> f, B b, Iterator<A> it) {
        if (!it.hasNext())
            return b;
        return foldl(f, f.apply(b, it.next()), it);
    }
}
