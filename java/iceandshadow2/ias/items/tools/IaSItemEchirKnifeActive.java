package iceandshadow2.ias.items.tools;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSApiTransmute;
import iceandshadow2.api.IaSRegistry;
import iceandshadow2.ias.items.IaSBaseItemSingleGlow;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class IaSItemEchirKnifeActive extends IaSBaseItemSingleGlow implements IIaSApiTransmute {

	public IaSItemEchirKnifeActive(String texName, int tab) {
		super(EnumIaSModule.IAS, texName);
		setMaxStackSize(32);
		setFull3D();
	}

	@Override
	public void addInformation(ItemStack s, EntityPlayer p, List l, boolean b) {
		l.add(EnumChatFormatting.GRAY.toString()
				+ EnumChatFormatting.ITALIC.toString()
				+ "Sneak and Use Item to finalize.");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1, World w,
			EntityPlayer pirate) {
		if (pirate.isSneaking()) // Pirates can have subtlety too!
			par1 = new ItemStack(IaSTools.knife, par1.stackSize);
		return par1;
	}

	@Override
	public int getTransmuteTime(ItemStack target, ItemStack catalyst) {
		if (target.getItem() != this)
			return 0;
		if (IaSRegistry.getTransmutationMaterial(catalyst) != null)
			return 300;
		return 0;
	}

	@Override
	public List<ItemStack> getTransmuteYield(ItemStack target,
			ItemStack catalyst, World world) {
		final int mut = Math.min(target.stackSize,4);
		target.stackSize -= mut;
		catalyst.stackSize -= 1;
		final ItemStack res = IaSTools.setToolMaterial(IaSTools.knife, IaSRegistry.getTransmutationMaterial(catalyst).getMaterialName());
		res.stackSize = mut;
		final ArrayList<ItemStack> st = new ArrayList<ItemStack>(1);
		st.add(res);
		return st;
	}

	@Override
	public boolean spawnTransmuteParticles(ItemStack target, ItemStack catalyst,
			World world, Entity ent) {
		return false;
	}

}
