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
	public static int internalId = 0;

	public static int getUniqueEntityId() {
		do {
			NyxEntities.startEntityId++;
		} while (EntityList.getStringFromID(NyxEntities.startEntityId) != null);
		return NyxEntities.startEntityId;
	}

	public static void registerModEntity(Class c, String name, int trackingRange, int updateFrequency, boolean sendVelocityUpdates) {
		EntityRegistry.registerModEntity(c, name, ++internalId, IceAndShadow2.instance, trackingRange, updateFrequency, sendVelocityUpdates);
	}

	public static void init(IceAndShadow2 owner) {
		NyxEntities.startEntityId = IaSFlags.entity_id_start;
		registerModEntity(EntityNyxSpider.class, "nyxMobSpiderWisp", 60, 1, true);
		NyxEntities.registerEntityEgg(EntityNyxSpider.class, 0x77ffdd, 0xff4444);

		registerModEntity(EntityNyxSkeleton.class, "nyxMobWinterSkeleton", 80, 1, true);
		NyxEntities.registerEntityEgg(EntityNyxSkeleton.class, 0x112222, 0xccffff);

		registerModEntity(EntityIceArrow.class, "nyxProjectileIceArrow", 120, 2, true);

		registerModEntity(EntityShadowBall.class, "nyxProjectileShadowBall", 80, 2, true);

		registerModEntity(EntityThrowingKnife.class, "nyxProjectileThrowingKnife", 80, 2, true);

		// Set up the technical EntityTransmutationCountdown entity.
		registerModEntity(EntityTransmutationCountdown.class, "nyxTechnicalTransmutationCountdown", 160, 1, false);

		registerModEntity(EntityNyxWightSanctified.class, "nyxMobWightSanctified", 60, 1, true);
		registerEntityEgg(EntityNyxWightSanctified.class, 0xccccdd, 0x220011);

		registerModEntity(EntityNyxNecromancer.class, "nyxMobWitheredNecromancer", 60, 1, true);
		registerEntityEgg(EntityNyxNecromancer.class, 0x331111, 0xffcccc);

		registerModEntity(EntityCosmeticShadowRiser.class, "nyxCosmeticShadowRiser", 40, 1, true);

		registerModEntity(EntityWightTeleport.class, "nyxProjectileWightTeleport", 80, 2, true);

		registerModEntity(EntityNyxWightToxic.class, "nyxMobWightToxic", 60, 1, true);
		NyxEntities.registerEntityEgg(EntityNyxWightToxic.class, 0x004400, 0xaaffaa);

		registerModEntity(EntityPoisonBall.class, "nyxProjectilePoisonBall", 40, 2, true);

		registerModEntity(EntityOrbNourishment.class, "nyxEntityOrbNourishment", 60, 1, true);

		registerModEntity(EntityNyxWalker.class, "nyxMobWalkerInfested", 60, 1, true);
		NyxEntities.registerEntityEgg(EntityNyxWalker.class, 0xccffdd, 0xccdddd);

		registerModEntity(EntityNyxSpiderBaby.class, "nyxMobSpiderWispBaby", 60, 1, true);
		NyxEntities.registerEntityEgg(EntityNyxSpiderBaby.class, 0x44ffdd, 0x774444);

		registerModEntity(EntityGrenade.class, "nyxProjectileGrenade", 80, 2, true);
	}

	public static void registerEntityEgg(Class<? extends Entity> entity, int primaryColor, int secondaryColor) {
		final int id = NyxEntities.getUniqueEntityId();
		EntityList.IDtoClassMapping.put(id, entity);
		EntityList.entityEggs.put(id, new EntityList.EntityEggInfo(id, primaryColor, secondaryColor));
	}
}
