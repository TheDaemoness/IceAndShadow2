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
import iceandshadow2.ias.items.IaSBaseItemSingleGlow;
import iceandshadow2.nyx.NyxItems;

public class NyxItemNifelhiumPowder extends IaSBaseItemMultiTexturedGlow implements IIaSApiTransmute {

	public NyxItemNifelhiumPowder(String texName) {
		super(EnumIaSModule.NYX, texName, 2);
		setMaxStackSize(16);
		GameRegistry.addShapelessRecipe(new ItemStack(this, 4, 1)
				, new ItemStack(this, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(this, 1, 0)
				, new ItemStack(this, 1, 1), new ItemStack(this, 1, 1)
				, new ItemStack(this, 1, 1), new ItemStack(this, 1, 1));
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.NYX;
	}

	@Override
	public int getTransmuteTime(ItemStack target, ItemStack catalyst) {
		if (catalyst.getItem() != this)
			return 0;
		if (target.getItem() == NyxItems.icicle && catalyst.getItemDamage() == 1)
			return 160;
		return 0;
	}

	@Override
	public List<ItemStack> getTransmuteYield(ItemStack target, ItemStack catalyst, World world) {
		--catalyst.stackSize;
		--target.stackSize;
		if (target.getItem() == NyxItems.icicle) {
			final List<ItemStack> li = new ArrayList<ItemStack>();
			li.add(new ItemStack(NyxItems.icicle, 32));
			return li;
		}
		return null;
	}

	@Override
	public boolean spawnTransmuteParticles(ItemStack target, ItemStack catalyst, World world, Entity ent) {
		return false;
	}
}
