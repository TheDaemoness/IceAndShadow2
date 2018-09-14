package iceandshadow2.ias.items.tools;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IaSRegistry;
import iceandshadow2.ias.api.IIaSApiTransmute;
import iceandshadow2.ias.api.IaSToolMaterial;
import iceandshadow2.ias.items.IaSBaseItemSingleGlow;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class IaSItemEchirToolActive extends IaSBaseItemSingleGlow implements IIaSApiTransmute {

	protected int slot;
	protected boolean wep;

	public IaSItemEchirToolActive(String texName, int tab, boolean isWeapon) {
		super(EnumIaSModule.IAS, texName);
		slot = tab; // FtM sex change, wot?
		wep = isWeapon;
		setMaxStackSize(1);
		setFull3D();
	}

	@Override
	public void addInformation(ItemStack s, EntityPlayer p, List l, boolean b) {
		l.add(EnumChatFormatting.GRAY.toString() + EnumChatFormatting.ITALIC.toString()
				+ "Sneak and Use Item to finalize.");
	}

	@Override
	public int getMaxDamage() {
		return IaSRegistry.getDefaultMaterial().getDurability(new ItemStack(IaSTools.tools[slot]));
	}

	@Override
	public int getTransmuteTime(ItemStack target, ItemStack catalyst) {
		if (target.getItem() != this || target.isItemDamaged())
			return 0;
		if (IaSRegistry.getTransmutationMaterial(catalyst) != null && catalyst.stackSize >= 3)
			return 300;
		return 0;
	}

	@Override
	public List<ItemStack> getTransmuteYield(ItemStack target, ItemStack catalyst, World world) {
		final IaSToolMaterial mat = IaSRegistry.getTransmutationMaterial(catalyst);
		catalyst.stackSize -= 3;
		if (wep)
			target.func_150996_a(IaSTools.weapons[slot]);
		else
			target.func_150996_a(IaSTools.tools[slot]);
		if (target.stackTagCompound == null)
			target.setTagCompound(new NBTTagCompound());
		final NBTTagCompound nbt = target.stackTagCompound;
		nbt.setString(IaSTools.NBT_ID, mat.getMaterialName());
		return null;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1, World w, EntityPlayer ninja) {
		if (ninja.isSneaking())
			// actually isn't redundant.
			if (wep)
				par1 = new ItemStack(IaSTools.weapons[slot], 1, par1.getItemDamage());
			else
				par1 = new ItemStack(IaSTools.tools[slot], 1, par1.getItemDamage());
		return par1;
	}

	@Override
	public boolean spawnTransmuteParticles(ItemStack target, ItemStack catalyst, World world, Entity ent) {
		return false;
	}

}
