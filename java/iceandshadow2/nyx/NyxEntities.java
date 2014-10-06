package iceandshadow2.nyx;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import cpw.mods.fml.common.registry.EntityRegistry;
import iceandshadow2.IaSFlags;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.nyx.entities.mobs.EntityNyxSkeleton;
import iceandshadow2.nyx.entities.projectile.EntityIceArrow;
import iceandshadow2.nyx.entities.projectile.EntityShadowBall;

public class NyxEntities {
	
	public static int startEntityId;
	
	public static void init(IceAndShadow2 owner) {
		startEntityId = IaSFlags.entity_id_start;
		// Set up Spider Wisps.
		/*
		EntityRegistry.registerModEntity(EntityNyxSpider.class,
				"IceAndShadow_SpiderWisp", 1, owner, 60, 1, true); 
		LanguageRegistry.instance().addStringLocalization(
				(new EntityNyxSpider(null)).getEntityName(),
				"Spider Wisp");
		registerEntityEgg(EntityNyxSpider.class, 0x77ffdd, 0xff4444);
		*/

		// Set up Winter Skeletons.
		EntityRegistry.registerModEntity(EntityNyxSkeleton.class,
				"IceAndShadow_WinterSkeleton", 2, owner, 80, 1, true);
		registerEntityEgg(EntityNyxSkeleton.class, 0x112222, 0xccffff);

		// Set up Ice Arrows
		EntityRegistry.registerModEntity(EntityIceArrow.class,
				"IceAndShadow_Projectile_IceArrow", 3, owner, 120, 2, true);
		
		//Set up Shadow Balls
		EntityRegistry.registerModEntity(EntityShadowBall.class,
				"IceAndShadow_Projectile_ShadowBall", 14, owner, 80, 2, true);
		
		
	}
	
	public static int getUniqueEntityId() {
		do {
			startEntityId++;
		} while (EntityList.getStringFromID(startEntityId) != null);
		return startEntityId;
	}

	@SuppressWarnings("unchecked")
	public static void registerEntityEgg(Class<? extends Entity> entity,
			int primaryColor, int secondaryColor) {
		int id = getUniqueEntityId();
		EntityList.IDtoClassMapping.put(id, entity);
		EntityList.entityEggs.put(id, new EntityList.EntityEggInfo(id, primaryColor,
				secondaryColor));
	}
}