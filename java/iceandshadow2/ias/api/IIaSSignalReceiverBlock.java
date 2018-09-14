package iceandshadow2.ias.api;

import net.minecraft.world.World;

/**
 * An interface for blocks that can receive signals from remotes and the like.
 * These functions are called once each time a signal begins, and once each time
 * a signal ends. The data object may be null, and it may differ between the
 * start and stop call.
 */
public interface IIaSSignalReceiverBlock {

	public void onSignalStart(World w, int x, int y, int z, Object data);

	public void onSignalStop(World w, int x, int y, int z, Object data);
}
