package cz.najmann.patterns.spec;


/**
 * OR specification, used to create a new specification that is the OR of two
 * other specifications.
 */
public final class OrSpec<T> extends BaseSpec<T> {

	private final Spec<T> spec1;
	private final Spec<T> spec2;

	/**
	 * Create a new OR specification based on two other spec.
	 * 
	 * @param spec1
	 *          Spec one.
	 * @param spec2
	 *          Spec two.
	 */
	public OrSpec(final Spec<T> spec1, final Spec<T> spec2) {
		this.spec1 = spec1;
		this.spec2 = spec2;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSatisfiedBy(final T t) {
		return spec1.isSatisfiedBy(t) || spec2.isSatisfiedBy(t);
	}
}
