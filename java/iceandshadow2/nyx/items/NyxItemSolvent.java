package iceandshadow2.nyx.items;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSApiTransmutable;
import iceandshadow2.ias.items.IaSBaseItemMulti;

public class NyxItemSolvent extends IaSBaseItemMulti implements IIaSApiTransmutable {

	public NyxItemSolvent(String id) {
		super(EnumIaSModule.NYX, id, 1);
	}

	@Override
	public int getTransmutationTime(ItemStack target, ItemStack catalyst) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ItemStack> getTransmutationYield(ItemStack target,
			ItemStack catalyst, World world) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean spawnParticles(ItemStack target, ItemStack catalyst,
			World world, Entity ent) {
		return false;
	}

}
