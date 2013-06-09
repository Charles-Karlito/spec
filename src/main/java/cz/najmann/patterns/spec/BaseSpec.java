package cz.najmann.patterns.spec;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Abstract base implementation of composite
 * {@link cz.najmann.patterns.spec.Spec} with default
 * implementations for {@code and}, {@code or} and {@code not}.
 */
public abstract class BaseSpec<T> implements Spec<T> {

    public static <T> boolean every(Iterable<T> iterable, Spec<T> spec) {
        for (T t : iterable) {
            if (!spec.isSatisfiedBy(t)) {
                return false;
            }
        }
        return true;
    }

    public static <T> boolean some(Iterable<T> iterable, Spec<T> spec) {
        for (T t : iterable) {
            if (spec.isSatisfiedBy(t)) {
                return true;
            }
        }
        return false;
    }

    public static <T> Iterable<T> filter(final Iterable<T> iterable, final Spec<T> spec) {
        // should return Iterable<T> to be able to target foreach statement
        // throw new UnsupportedOperationException();
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new IteratorIterator<T>(iterable.iterator(), spec);
            }
        };
    }

    public static <T> Iterable<T> filter(T[] array, Spec<T> spec) {
        return filter(Arrays.asList(array), spec);
    }

    /**
     * Applies iterator over iterator with condition defined by Spec interface
     *
     * @param <T>
     */
    static class IteratorIterator<T> implements Iterator<T> {

        private final Iterator<T> iterator;
        private final Spec<T> spec;

        private boolean queued;
        private T current;

        IteratorIterator(Iterator<T> iterator, Spec<T> spec) {
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


    /**
     * {@inheritDoc}
     */
    @Override
    public abstract boolean isSatisfiedBy(final T t);

    /**
     * {@inheritDoc}
     */
    @Override
    public Spec<T> and(final Spec<T> spec) {
        return new AndSpec<T>(this, spec);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Spec<T> or(final Spec<T> spec) {
        return new OrSpec<T>(this, spec);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Spec<T> not(final Spec<T> spec) {
        return new NotSpec<T>(spec);
    }
}
