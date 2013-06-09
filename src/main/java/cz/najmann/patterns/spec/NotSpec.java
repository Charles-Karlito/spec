package cz.najmann.patterns.spec;

/**
 * NOT decorator, used to create a new specification that is the inverse (NOT)
 * of the given spec.
 */
public final class NotSpec<T> extends BaseSpec<T> {

	private final Spec<T> spec1;

	/**
	 * Create a new NOT specification based on another spec.
	 * 
	 * @param spec1
	 *          Spec instance to not.
	 */
	public NotSpec(final Spec<T> spec1) {
		this.spec1 = spec1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSatisfiedBy(final T t) {
		return !spec1.isSatisfiedBy(t);
	}
}
