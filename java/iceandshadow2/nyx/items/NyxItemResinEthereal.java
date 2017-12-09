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

public class NyxItemResinEthereal extends IaSBaseItemSingle implements IIaSGlowing, IIaSApiTransmute {

	public NyxItemResinEthereal(String texName) {
		super(EnumIaSModule.NYX, texName);
	}

	@Override
	public int getFirstGlowPass(ItemStack is) {
		return 0;
	}

	@Override
	public int getRenderPasses(int metadata) {
		return 1;
	}

	@Override
	public int getTransmuteTime(ItemStack target, ItemStack catalyst) {
		if (catalyst.getItem() == this && target.getItem() == NyxItems.boneCursed)
			return catalyst.stackSize >= 9 ? 420 : 0; // Blaze it.
		if (catalyst.getItem() == this && target.getItem() == NyxItems.leaf)
			return 42; // The answer.
		return 0;
	}

	@Override
	public List<ItemStack> getTransmuteYield(ItemStack target, ItemStack catalyst, World world) {
		final List<ItemStack> retval = new ArrayList<ItemStack>(1);
		target.stackSize -= 1;
		if (target.getItem() == NyxItems.boneCursed) {
			int ss = 0;
			while (catalyst.stackSize >= 9) {
				catalyst.stackSize -= 9;
				++ss;
			}
			retval.add(new ItemStack(NyxBlocks.hardShadow, ss));
		} else {
			catalyst.stackSize -= 1;
			retval.add(new ItemStack(NyxItems.resinCurative, 1));
		}
		return retval;
	}

	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public boolean spawnTransmuteParticles(ItemStack target, ItemStack catalyst, World world, Entity ent) {
		return false;
	}

	@Override
	public boolean usesDefaultGlowRenderer() {
		return true;
	}
}
