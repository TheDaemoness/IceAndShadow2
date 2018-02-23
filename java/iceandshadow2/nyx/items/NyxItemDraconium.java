package iceandshadow2.nyx.items;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.api.EnumIaSAspect;
import iceandshadow2.ias.api.IIaSApiTransmute;
import iceandshadow2.ias.items.IaSBaseItemMultiTexturedGlow;
import iceandshadow2.nyx.NyxItems;

public class NyxItemDraconium extends IaSBaseItemMultiTexturedGlow implements IIaSApiTransmute {

	public NyxItemDraconium(String texName) {
		super(EnumIaSModule.NYX, texName, 2);
		GameRegistry.addShapelessRecipe(new ItemStack(this, 4, 1), new ItemStack(this, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(this, 1, 0), new ItemStack(this, 1, 1), new ItemStack(this, 1, 1),
				new ItemStack(this, 1, 1), new ItemStack(this, 1, 1));
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.STYX;
	}

	@Override
	public int getTransmuteTime(ItemStack target, ItemStack catalyst) {
		if (target.getItem() != NyxItems.echirIngot || target.getItemDamage() != 1 || catalyst.getItem() != this
				|| catalyst.getItemDamage() != 1)
			return 0;
		return 120;
	}

	@Override
	public List<ItemStack> getTransmuteYield(ItemStack target, ItemStack catalyst, World world) {
		final List<ItemStack> it = new ArrayList<ItemStack>();
		catalyst.stackSize -= 1;
		target.stackSize -= 1;
		it.add(new ItemStack(NyxItems.draconiumIngot, 1, 1));
		return it;
	}

	@Override
	public boolean spawnTransmuteParticles(ItemStack target, ItemStack catalyst, World world, Entity ent) {
		return false;
	}

}
