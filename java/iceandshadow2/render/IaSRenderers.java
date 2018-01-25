package iceandshadow2.render;

import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.tools.IaSItemArmor;
import iceandshadow2.ias.items.tools.IaSTools;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.entities.cosmetic.*;
import iceandshadow2.nyx.entities.mobs.*;
import iceandshadow2.nyx.entities.projectile.*;
import iceandshadow2.nyx.entities.util.*;
import iceandshadow2.nyx.tileentities.*;
import iceandshadow2.render.entity.mobs.*;
import iceandshadow2.render.entity.projectiles.*;
import iceandshadow2.render.item.*;
import iceandshadow2.render.tileentity.*;

import java.lang.reflect.Field;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class IaSRenderers {
	public static void init() {

		// Mobs.
		RenderingRegistry.registerEntityRenderingHandler(EntityNyxSkeleton.class, new RenderNyxSkeleton());
		RenderingRegistry.registerEntityRenderingHandler(EntityNyxSpider.class, new RenderNyxSpider());
		RenderingRegistry.registerEntityRenderingHandler(EntityNyxSpiderBaby.class, new RenderNyxSpiderBaby());
		RenderingRegistry.registerEntityRenderingHandler(EntityNyxWightSanctified.class,
				new RenderNyxWightSanctified());
		RenderingRegistry.registerEntityRenderingHandler(EntityNyxWightToxic.class, new RenderNyxWightToxic());
		RenderingRegistry.registerEntityRenderingHandler(EntityNyxWalker.class, new RenderNyxWalker());

		// Projectiles.
		RenderingRegistry.registerEntityRenderingHandler(EntityIceArrow.class, new RenderIceArrow());
		RenderingRegistry.registerEntityRenderingHandler(EntityShadowBall.class, new RenderNot());
		RenderingRegistry.registerEntityRenderingHandler(EntityThrowingKnife.class, new RenderThrowingKnife());
		RenderingRegistry.registerEntityRenderingHandler(EntityCosmeticShadowRiser.class, new RenderNot());
		RenderingRegistry.registerEntityRenderingHandler(EntityPoisonBall.class, new RenderNot());

		// Technical entities.
		RenderingRegistry.registerEntityRenderingHandler(EntityTransmutationCountdown.class, new RenderNot());
		RenderingRegistry.registerEntityRenderingHandler(EntityWightTeleport.class, new RenderNot());
		RenderingRegistry.registerEntityRenderingHandler(EntityOrbNourishment.class, new RenderOrbNourishment());

		// Items
		for (final Field f : NyxItems.class.getFields()) {
			try {
				final Object o = f.get(null);
				if (o instanceof IIaSGlowing && o instanceof Item)
					if (((IIaSGlowing) o).usesDefaultGlowRenderer()) {
						MinecraftForgeClient.registerItemRenderer((Item) o, new RenderItemVanillaGlowing());
					}
			} catch (final Exception e) {
			}
		}
		MinecraftForgeClient.registerItemRenderer(NyxItems.frostBowShort, new RenderItemBow(false));
		MinecraftForgeClient.registerItemRenderer(NyxItems.frostBowLong, new RenderItemBow(true));

		for (final Item item : IaSTools.tools) {
			MinecraftForgeClient.registerItemRenderer(item, new RenderItemVanillaGlowing());
		}
		for (final Item item : IaSTools.toolsActiveEchir) {
			MinecraftForgeClient.registerItemRenderer(item, new RenderItemVanillaGlowing());
		}
		for (final Item item : IaSTools.weapons) {
			MinecraftForgeClient.registerItemRenderer(item, new RenderItemVanillaGlowing());
		}
		for (final Item item : IaSTools.swordsActiveEchir) {
			MinecraftForgeClient.registerItemRenderer(item, new RenderItemVanillaGlowing());
		}
		for (final Item item : IaSTools.armorActiveEchir) {
			MinecraftForgeClient.registerItemRenderer(item, new RenderItemVanillaGlowing());
		}
		for (final IaSItemArmor[] armors : IaSTools.armor) {
			for(final Item armor : armors) {
				MinecraftForgeClient.registerItemRenderer(armor, new RenderItemVanillaGlowing());
			}
		}


		ClientRegistry.bindTileEntitySpecialRenderer(NyxTeTransmutationAltar.class,
				new RenderNyxTeTransmutationAltar());
	}

}
