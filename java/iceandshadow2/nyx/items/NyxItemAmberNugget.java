package iceandshadow2.nyx.items;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.IaSRegistry;
import iceandshadow2.ias.api.IIaSApiTransmute;
import iceandshadow2.ias.items.IaSBaseItemSingleGlow;
import iceandshadow2.render.fx.IaSFxManager;

public class NyxItemAmberNugget extends IaSBaseItemSingleGlow implements IIaSApiTransmute {

	public NyxItemAmberNugget(String texName) {
		super(EnumIaSModule.NYX, texName);
		setHasSubtypes(true);
		setMaxStackSize(16);
		IaSRegistry.blacklistUncraft(this);
	}

	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {
		return EnumRarity.uncommon;
	}

	@Override
	public int getTransmuteTime(ItemStack target, ItemStack catalyst) {
		return 0;
	}

	@Override
	public List<ItemStack> getTransmuteYield(ItemStack target, ItemStack catalyst, World world) {
		return null;
	}

	@Override
	public boolean spawnTransmuteParticles(ItemStack target, ItemStack catalyst, World world, Entity pos) {
		if (world.rand.nextBoolean())
			IaSFxManager.spawnParticle(world, "vanilla_spell", pos.posX - 0.1 + world.rand.nextDouble() / 5,
					pos.posY - 0.2 - world.rand.nextDouble() / 3, pos.posZ - 0.1 + world.rand.nextDouble() / 5,
					-0.025 + world.rand.nextDouble() / 20, -0.05F, -0.025 + world.rand.nextDouble() / 20, false, false);
		return false;
	}

}
