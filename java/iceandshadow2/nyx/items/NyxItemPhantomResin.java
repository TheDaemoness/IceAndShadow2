package iceandshadow2.nyx.items;

import java.util.ArrayList;
import java.util.List;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSApiTransmute;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.IaSBaseItemSingle;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class NyxItemPhantomResin extends IaSBaseItemSingle implements IIaSGlowing, IIaSApiTransmute {

	public NyxItemPhantomResin(String texName) {
		super(EnumIaSModule.NYX, texName);
	}
	
	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public int getRenderPasses(int metadata) {
		return 1;
	}

	@Override
	public int getFirstGlowPass(ItemStack is) {
		return 0;
	}

	@Override
	public boolean usesDefaultGlowRenderer() {
		return true;
	}

	@Override
	public int getTransmuteTime(ItemStack target, ItemStack catalyst) {
		if(target.getItem() == this && catalyst.getItem() == NyxItems.boneCursed)
			return target.stackSize>=9?420:0;
		return 0;
	}

	@Override
	public List<ItemStack> getTransmuteYield(ItemStack target,
			ItemStack catalyst, World world) {
		int ss = 0;
		while(target.stackSize >= 9) {
			target.stackSize -= 9;
			++ss;
		}
		catalyst.stackSize -= 1;
		List<ItemStack> retval = new ArrayList<ItemStack>(1);
		retval.add(new ItemStack(NyxBlocks.hardShadow, ss));
		return retval;
	}

	@Override
	public boolean spawnTransmuteParticles(ItemStack target,
			ItemStack catalyst, World world, Entity ent) {
		return false;
	}
}
