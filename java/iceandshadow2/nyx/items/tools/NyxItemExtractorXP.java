package iceandshadow2.nyx.items.tools;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.IaSFlags;
import iceandshadow2.api.IIaSApiTransmute;
import iceandshadow2.ias.items.IaSBaseItemSingle;
import iceandshadow2.ias.items.IaSItemFood;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.world.NyxTeleporter;
import iceandshadow2.util.IaSPlayerHelper;

public class NyxItemExtractorXP extends IaSBaseItemSingle {

	@SideOnly(Side.CLIENT)
	protected IIcon fillIcons[];
	
	public NyxItemExtractorXP(String texName) {
		super(EnumIaSModule.NYX, texName);
		this.setMaxStackSize(1);
		this.setMaxDamage(14);
	}

	@Override
	public void registerIcons(IIconRegister r) {
		this.itemIcon = r.registerIcon(this.getTexName());
		fillIcons = new IIcon[7];
		for(int i = 0; i <= 7; ++i)
			fillIcons[i] = r.registerIcon(this.getTexName()+i);
	}
	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
		return EnumAction.block;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 32;
	}
	
	@Override
	public ItemStack onEaten(ItemStack is, World wld, EntityPlayer pl) {
		pl.addExperienceLevel(-5);
		is.setItemDamage(is.getItemDamage()+1);
		return is;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack heap, World order,
			EntityPlayer pwai) {
		if(pwai.experienceLevel >= 5 && heap.getItemDamage() < this.getMaxDamage()-1)
			pwai.setItemInUse(heap, this.getMaxItemUseDuration(heap));
		return heap;
	}
}
