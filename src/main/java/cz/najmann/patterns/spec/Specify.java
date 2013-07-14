package cz.najmann.patterns.spec;

import java.util.*;

/**
 * User: littleli
 * Date: 15.7.13
 * Time: 0:04
 */
public class Specify {

    private Specify() {
        // not meant for instantiation
    }

    public static <T> boolean every(Iterable<T> iterable, Spec<T> spec) {
        for (T t : iterable)
            if (!spec.isSatisfiedBy(t))
                return false;
        return true;
    }

    public static <T> boolean some(Iterable<T> iterable, Spec<T> spec) {
        for (T t : iterable)
            if (spec.isSatisfiedBy(t))
                return true;
        return false;
    }

    public static <T> Iterable<T> filter(final Iterable<T> iterable, final Spec<T> spec) {
        // should return Iterable<T> to be able to participate in foreach statements
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new SpecIterator<T>(iterable.iterator(), spec);
            }
        };
    }

    public static <T> Iterable<T> filter(final T[] arry, Spec<T> spec) {
        return filter(Arrays.asList(arry), spec);
    }

    public static <T> List<T> filterToList(final Iterable<T> iterable, final Spec<T> spec) {
        LinkedList<T> targetList = new LinkedList<T>();
        for (T t : iterable)
            if (spec.isSatisfiedBy(t))
                targetList.add(t);
        return targetList;
    }

    public static <T> List<T> filterToList(final T[] arry, final Spec<T> spec) {
        return filterToList(Arrays.asList(arry), spec);
    }

    /**
     * Applies iterator over iterator with condition defined by Spec interface
     *
     * @param <T>
     */
    static class SpecIterator<T> implements Iterator<T> {

        private final Iterator<T> iterator;
        private final Spec<T> spec;

        private boolean queued;
        private T current;

        SpecIterator(Iterator<T> iterator, Spec<T> spec) {
            this.iterator = iterator;
            this.spec = spec;
            this.queued = false;
        }

        @Override
        public boolean hasNext() {
            if (queued) return true;
            while (iterator.hasNext()) {
                T t = iterator.next();
                if (spec.isSatisfiedBy(t)) {
                    current = t;
                    return queued = true;
                }
            }
            return queued = false;
        }

        @Override
        public T next() {
            if (hasNext()) {
                queued = false;
                return current;
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
