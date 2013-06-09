package cz.najmann.patterns.spec;

/**
 * Spec interface.
 * <p/>
 * Use {@link cz.najmann.patterns.spec.Spec} as base for creating specifications, and
 * only the method {@link #isSatisfiedBy(T)} must be implemented.
 */
public interface Spec<T> {

    /**
     * Check if {@code t} is satisfied by the specification.
     *
     * @param t Object to test.
     * @return {@code true} if {@code t} satisfies the specification.
     */
    boolean isSatisfiedBy(T t);

    /**
     * Create a new spec that is the AND operation of {@code this} spec and another spec.
     *
     * @param spec Spec to AND.
     * @return A new spec.
     */
    Spec<T> and(Spec<T> spec);

    /**
     * Create a new spec that is the OR operation of {@code this} spec and another spec.
     *
     * @param spec Spec to OR.
     * @return A new spec.
     */
    Spec<T> or(Spec<T> spec);

    /**
     * Create a new spec that is the NOT operation of {@code this} spec.
     *
     * @param spec Spec to NOT.
     * @return A new spec.
     */
    Spec<T> not(Spec<T> spec);
}
