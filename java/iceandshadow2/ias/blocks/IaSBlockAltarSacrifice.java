package iceandshadow2.ias.blocks;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.api.IIaSAltarDistillable;
import iceandshadow2.ias.blocks.IaSBlockAltar;
import iceandshadow2.ias.items.IaSBaseItem;
import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.IaSFlags;
import iceandshadow2.util.EnumIaSModule;

public class IaSBlockAltarSacrifice extends IaSBlockAltar {

	public IaSBlockAltarSacrifice(EnumIaSModule mod, String id) {
		super(mod, id);
		this.setLightLevel(0.5F);
		this.setResistance(9001.0F);
		this.setStepSound(this.soundTypeStone);
		this.setTickRandomly(true);
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, int x, int y, int z,
			Entity theEnt) {
		if (theEnt instanceof EntityItem && !par1World.isRemote) {
			ItemStack staque = ((EntityItem) theEnt).getEntityItem();
			burnItem(par1World, x, y, z, staque);
			theEnt.setDead();
		} else
			theEnt.attackEntityFrom(IaSDamageSources.dmgXpAltar, 1);
	}
	
	@Override
	public boolean onBlockActivated(World par1World, int x, int y,
			int z, EntityPlayer par5EntityPlayer, int par6, float par7,
			float par8, float par9) {
		boolean f = burnItem(par1World, x, y, z, par5EntityPlayer.getCurrentEquippedItem());
		if(f)
			par5EntityPlayer.setCurrentItemOrArmor(0, null);
		return f;
	}

	public boolean burnItem(World wd, int x, int y, int z, ItemStack is) {
		return true;
	}
}
