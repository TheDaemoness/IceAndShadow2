package iceandshadow2.ias.items.tools;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.IIaSApiTransmutable;
import iceandshadow2.api.IaSRegistry;
import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.ias.items.IaSBaseItemSingleGlow;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class IaSItemEchirToolActive extends IaSBaseItemSingleGlow implements IIaSApiTransmutable {

	protected int slot;
	protected boolean wep;
	
	public IaSItemEchirToolActive(String texName, int tab, boolean isWeapon) {
		super(EnumIaSModule.IAS, texName);
		slot = tab; //FtM sex change, wot?
		wep = isWeapon;
		this.setMaxStackSize(1);
		this.setFull3D();
	}

	@Override
	public int getMaxDamage() {
		return IaSRegistry.getDefaultMaterial().getDurability(new ItemStack(IaSTools.tools[slot]));
	}
	
	@Override
	public void addInformation(ItemStack s, EntityPlayer p,
			List l, boolean b) {
		l.add(	EnumChatFormatting.GRAY.toString()+
				EnumChatFormatting.ITALIC.toString()+
				"Sneak and Use Item to finalize.");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1, World w,
			EntityPlayer ninja) {
		if(ninja.isSneaking()) { //Do NOT remove this if statement, it actually isn't redundant.
			if(wep)
				par1 = new ItemStack(IaSTools.weapons[slot],1,par1.getItemDamage());
			else
				par1 = new ItemStack(IaSTools.tools[slot],1,par1.getItemDamage());
		}
		return par1;
	}

	@Override
	public int getTransmutationTime(ItemStack target, ItemStack catalyst) {
		if(target.getItem() != this)
			return 0;
		if(IaSRegistry.getTransmutationMaterial(catalyst) != null && catalyst.stackSize >= 3)
			return 300;
		return 0;
	}

	@Override
	public List<ItemStack> getTransmutationYield(ItemStack target,
			ItemStack catalyst, World world) {
		IaSToolMaterial mat = IaSRegistry.getTransmutationMaterial(catalyst);
		catalyst.stackSize -= 3;
		ArrayList<ItemStack> ar = new ArrayList<ItemStack>();
		if(wep)
			ar.add(IaSTools.setToolMaterial(IaSTools.weapons[slot], mat.getMaterialName()));
		else
			ar.add(IaSTools.setToolMaterial(IaSTools.tools[slot], mat.getMaterialName()));
		target.stackSize -= 1;
		return null;
	}

	@Override
	public boolean spawnParticles(ItemStack target, ItemStack catalyst,
			World world, Entity ent) {
		return false;
	}

}
