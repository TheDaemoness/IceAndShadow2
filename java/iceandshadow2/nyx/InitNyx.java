package iceandshadow2.nyx;

import iceandshadow2.IaSFlags;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.nyx.tileentities.NyxTeSingleItemStorage;
import iceandshadow2.nyx.world.NyxBiomes;
import iceandshadow2.nyx.world.NyxWorldProvider;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.registry.GameRegistry;

public class InitNyx {
	public static void init(IceAndShadow2 owner) {
		NyxBlocks.init();
		NyxBiomes.init();
		NyxItems.init();
		NyxRecipes.init();
		NyxEntities.init(owner);

		GameRegistry.registerTileEntity(NyxTeSingleItemStorage.class, "NyxTeSingleItemStorage");

		DimensionManager.registerProviderType(IaSFlags.dim_nyx_id, NyxWorldProvider.class, false);
		DimensionManager.registerDimension(IaSFlags.dim_nyx_id, IaSFlags.dim_nyx_id);
	}

	public static void lateInit(IceAndShadow2 iceAndShadow2) {
		NyxItems.lateInit();
	}
}
