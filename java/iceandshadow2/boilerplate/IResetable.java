package iceandshadow2.boilerplate;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.Callable;

import net.minecraft.world.World;

/**
 * Simplistic internal-use interface for resetable objects.
 * Mainly used for iterators/suppliers and the like to avoid having to repeatedly reconstruct the same objects.
 */
public interface IResetable {
	public boolean reset();
}
