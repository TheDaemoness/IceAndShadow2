package iceandshadow2.nyx;

import cpw.mods.fml.common.registry.EntityRegistry;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.nyx.entities.projectile.EntityIceArrow;

public class NyxEntities {
	public static void init(IceAndShadow2 owner) {

		// Set up Ice Arrows
		EntityRegistry.registerModEntity(EntityIceArrow.class,
				"IceAndShadow_Projectile_IceArrow", 3, owner, 120, 2, true);
	}
}
