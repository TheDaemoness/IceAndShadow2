package iceandshadow2.nyx;

import iceandshadow2.IaSFlags;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.nyx.entities.cosmetic.*;
import iceandshadow2.nyx.entities.mobs.*;
import iceandshadow2.nyx.entities.projectile.*;
import iceandshadow2.nyx.entities.util.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import cpw.mods.fml.common.registry.EntityRegistry;

public class NyxEntities {

	public static int startEntityId;

	public static int getUniqueEntityId() {
		do {
			NyxEntities.startEntityId++;
		} while (EntityList.getStringFromID(NyxEntities.startEntityId) != null);
		return NyxEntities.startEntityId;
	}

	public static void init(IceAndShadow2 owner) {
		NyxEntities.startEntityId = IaSFlags.entity_id_start;
		// Set up Spider Wisps.
		EntityRegistry.registerModEntity(EntityNyxSpider.class,
				"nyxMobSpiderWisp", 1, owner, 60, 1, true);
		NyxEntities.registerEntityEgg(EntityNyxSpider.class, 0x77ffdd, 0xff4444);

		// Set up Winter Skeletons.
		EntityRegistry.registerModEntity(EntityNyxSkeleton.class,
				"nyxMobWinterSkeleton", 2, owner, 80, 1, true);
		NyxEntities.registerEntityEgg(EntityNyxSkeleton.class, 0x112222, 0xccffff);

		// Set up Ice Arrows
		EntityRegistry.registerModEntity(EntityIceArrow.class,
				"nyxProjectileIceArrow", 3, owner, 120, 2, true);

		// Set up Shadow Balls
		EntityRegistry.registerModEntity(EntityShadowBall.class,
				"nyxProjectileShadowBall", 4, owner, 80, 2, true);

		// Set up Throwing Knives
		EntityRegistry.registerModEntity(EntityThrowingKnife.class,
				"nyxProjectileThrowingKnife", 5, owner, 80, 2, true);

		// Set up the technical EntityTransmutationCountdown entity.
		EntityRegistry.registerModEntity(EntityTransmutationCountdown.class,
				"nyxTechnicalTransmutationCountdown", 6, owner, 160, 1, false);

		// Set up White Ghouls
		EntityRegistry.registerModEntity(EntityNyxGhoul.class,
				"nyxMobWhiteGhoul", 7, owner, 60, 1, true);
		NyxEntities.registerEntityEgg(EntityNyxGhoul.class, 0xccccdd, 0x220011);

		// Set up Withered Necromancers
		EntityRegistry.registerModEntity(EntityNyxNecromancer.class,
				"nyxMobWitheredNecromancer", 8, owner, 60, 1, true);
		NyxEntities.registerEntityEgg(EntityNyxNecromancer.class, 0x331111, 0xffcccc);

		EntityRegistry.registerModEntity(EntityCosmeticShadowRiser.class,
				"nyxCosmeticShadowRiser", 9, owner, 40, 1, true);

		EntityRegistry.registerModEntity(EntityWightTeleport.class,
				"nyxProjectileWightTeleport", 10, owner, 80, 2, true);

		EntityRegistry.registerModEntity(EntityNyxWightToxic.class,
				"nyxMobWightToxic", 11, owner, 60, 1, true);
		NyxEntities.registerEntityEgg(EntityNyxWightToxic.class, 0x004400, 0xaaffaa);

		EntityRegistry.registerModEntity(EntityPoisonBall.class,
				"nyxProjectilePoisonBall", 12, owner, 40, 2, true);
		
		EntityRegistry.registerModEntity(EntityOrbNourishment.class,
				"nyxEntityOrbNourishment", 13, owner, 60, 1, true);
	}

	@SuppressWarnings("unchecked")
	public static void registerEntityEgg(Class<? extends Entity> entity,
			int primaryColor, int secondaryColor) {
		final int id = NyxEntities.getUniqueEntityId();
		EntityList.IDtoClassMapping.put(id, entity);
		EntityList.entityEggs.put(id, new EntityList.EntityEggInfo(id,
				primaryColor, secondaryColor));
	}
}
