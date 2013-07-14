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

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract boolean isSatisfiedBy(final T t);

    /**
     * {@inheritDoc}
     */
    @Override
    public final Spec<T> and(final Spec<T> spec) {
        return new AndSpec<T>(this, spec);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Spec<T> or(final Spec<T> spec) {
        return new OrSpec<T>(this, spec);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Spec<T> not(final Spec<T> spec) {
        return new NotSpec<T>(spec);
    }
}
