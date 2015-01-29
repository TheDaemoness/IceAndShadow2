package iceandshadow2.nyx.items.lenses;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.EnumIaSLenses;
import iceandshadow2.api.IIaSApiDistillable;
import iceandshadow2.api.IIaSApiTransmute;
import iceandshadow2.api.IIaSApiTransmuteLens;
import iceandshadow2.api.IaSRegistry;
import iceandshadow2.ias.items.IaSBaseItemMulti;

public abstract class NyxItemLens extends IaSBaseItemMulti implements IIaSApiTransmuteLens {

	public NyxItemLens(String id) {
		super(EnumIaSModule.NYX, id, 1);
	}

	@Override
	public int getTransmuteRate(EnumIaSLenses lenstype, ItemStack lens,
			ItemStack target) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ItemStack> getTransmuteYield(ItemStack lens, ItemStack target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IIcon getAltarTopTexture(ItemStack lens) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean spawnParticles(ItemStack target, ItemStack catalyst,
			World world, Entity ent) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public abstract EnumIaSLenses getLensType();

}
