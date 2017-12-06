package iceandshadow2.nyx.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.api.IIaSApiTransmute;
import iceandshadow2.ias.items.IaSBaseItemSingleGlow;
import iceandshadow2.nyx.NyxItems;

public class NyxItemNifelhiumPowder extends IaSBaseItemSingleGlow implements IIaSApiTransmute {

	public NyxItemNifelhiumPowder(String texName) {
		super(EnumIaSModule.NYX, texName);
		setMaxStackSize(16);
	}

	@Override
	public int getTransmuteTime(ItemStack target, ItemStack catalyst) {
		if (catalyst.getItem() != this)
			return 0;
		if (target.getItem() == NyxItems.icicle)
			return 160;
		return 0;
	}

	@Override
	public List<ItemStack> getTransmuteYield(ItemStack target, ItemStack catalyst, World world) {
		--catalyst.stackSize;
		--target.stackSize;
		if (target.getItem() == NyxItems.icicle) {
			final List<ItemStack> li = new ArrayList<ItemStack>();
			li.add(new ItemStack(NyxItems.icicle, 64));
			li.add(new ItemStack(NyxItems.icicle, 64));
			return li;
		}
		return null;
	}

	@Override
	public boolean spawnTransmuteParticles(ItemStack target, ItemStack catalyst, World world, Entity ent) {
		return false;
	}
	
	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.NYX;
	}
}
