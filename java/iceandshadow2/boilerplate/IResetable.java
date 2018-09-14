package iceandshadow2.boilerplate;

/**
 * Simplistic internal-use interface for resetable objects. Mainly used for
 * iterators/suppliers and the like to avoid having to repeatedly reconstruct
 * the same objects.
 */
public interface IResetable {
	public boolean reset();
}
