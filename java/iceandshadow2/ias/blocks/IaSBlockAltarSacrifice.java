package iceandshadow2.ias.blocks;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import iceandshadow2.ias.blocks.IaSBlockAltar;
import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.util.EnumIaSModule;

public class IaSBlockAltarSacrifice extends IaSBlockAltar {

	public IaSBlockAltarSacrifice(EnumIaSModule mod, String id) {
		super(mod, id);
		this.setLightLevel(0.5F);
		this.setResistance(9001.0F);
		this.setStepSound(Block.soundTypeStone);
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
