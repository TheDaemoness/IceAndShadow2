package iceandshadow2.nyx.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.api.IIaSApiTransmute;
import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.IaSItemFood;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.util.IaSEntityHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NyxItemResinCurative extends IaSItemFood implements IIaSGlowing {

	public NyxItemResinCurative(String texName) {
		super(EnumIaSModule.NYX, texName, -20, 0.0F, false);
		setAlwaysEdible();
		setMaxStackSize(16);
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
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public boolean usesDefaultGlowRenderer() {
		return true;
	}
	
	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.POISONWOOD;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
		return EnumAction.block;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 16;
	}
	
	@Override
	public ItemStack onEaten(ItemStack is, World wld, EntityPlayer pl) {
		pl.removePotionEffect(Potion.poison.id);
		is.stackSize -= 1;
		return is;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack heap, World order, EntityPlayer pwai) {
		if (pwai.isPotionActive(Potion.poison))
			pwai.setItemInUse(heap, getMaxItemUseDuration(heap));
		return heap;
	}
}
