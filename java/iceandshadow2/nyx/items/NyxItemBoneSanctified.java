package iceandshadow2.nyx.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.IaSFlags;
import iceandshadow2.ias.items.IaSBaseItemSingleGlow;
import iceandshadow2.util.IaSPlayerHelper;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NyxItemBoneSanctified extends IaSBaseItemSingleGlow {

	public NyxItemBoneSanctified(String texName) {
		super(EnumIaSModule.NYX, texName);
		this.setMaxStackSize(1);
		this.setMaxDamage(300);
		this.setNoRepair();
		this.setFull3D();
	}

	@Override
	public boolean usesDefaultGlowRenderer() {
		return true;
	}
	
	@Override
	public void addInformation(ItemStack s, EntityPlayer p,
			List l, boolean b) {
		l.add(EnumChatFormatting.GRAY.toString()+
					EnumChatFormatting.ITALIC.toString()+
					"It should ease death if broken in time.");
	}

	@Override
	public void onUpdate(ItemStack stack, World par2World, Entity par3Entity, int par4, boolean par5) {
		super.onUpdate(stack, par2World, par3Entity, par4, par5);
		if(!(par3Entity instanceof EntityPlayer)) {
			stack.stackSize = 0;
			return;
		}
		if(stack.isItemDamaged()) {
			if(((EntityPlayer)par3Entity).capabilities.isCreativeMode)
				stack.setItemDamage(0);
			else if(stack.attemptDamageItem(1, par2World.rand)) {
				stack.setItemDamage(0);
				((EntityPlayer)par3Entity).inventory.consumeInventoryItem(this);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack par1ItemStack) {
		return par1ItemStack.getItemDamage() > 0;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack isk, World par2World, EntityPlayer plai)
	{	
		if(plai.capabilities.isCreativeMode) {
			IaSPlayerHelper.messagePlayer(plai, "The bone becomes thicker than before, as if resentful of your use of creative mode.");
			return isk;
		}
		if(!isk.isItemDamaged()) {
			if(plai.dimension != IaSFlags.dim_nyx_id) {
				IaSPlayerHelper.messagePlayer(plai, "The bone refuses to break. Perhaps it relies on some power in Nyx that is absent here.");
				return isk;
			}
			plai.attackEntityFrom(DamageSource.magic, 0.5F);
			isk.setItemDamage(1);
		}
		return isk;
	}
}
