package iceandshadow2.nyx;

import iceandshadow2.IaSFlags;
import iceandshadow2.nyx.world.NyxBiomes;
import iceandshadow2.nyx.world.NyxWorldProvider;
import net.minecraftforge.common.DimensionManager;

public class InitNyx {
	public static void init() {
		NyxBlocks.init();
		NyxItems.init();
		
		NyxBiomes.init();
		
		DimensionManager.registerProviderType(IaSFlags.dim_nyx_id, NyxWorldProvider.class,
				false);
		DimensionManager.registerDimension(IaSFlags.dim_nyx_id, IaSFlags.dim_nyx_id);
	}
}
