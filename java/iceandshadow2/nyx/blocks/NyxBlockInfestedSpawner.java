package iceandshadow2.nyx.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.ias.blocks.IaSBaseBlockMulti;
import iceandshadow2.ias.blocks.IaSBlockDirectional;
import iceandshadow2.ias.interfaces.IIaSNoInfest;
import iceandshadow2.ias.util.IaSBlockHelper;
import iceandshadow2.nyx.blocks.mixins.NyxBlockFunctionsInfested;
import iceandshadow2.nyx.entities.mobs.EntityNyxSpider;
import iceandshadow2.nyx.entities.mobs.EntityNyxWalker;
import iceandshadow2.nyx.entities.mobs.EntityNyxWightSanctified;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NyxBlockInfestedSpawner extends NyxBaseBlockSpawner {
	
	public NyxBlockInfestedSpawner(String par1) {
		super(par1, 3);
	}
	
	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.INFESTATION;
	}
	
	@Override
	protected boolean hasDifferentTopIcon() {
		return false;
	}

	@Override
	public Class<? extends EntityLiving> getSpawn(int metadata) {
		switch(metadata) {
			case 1: return EntityNyxSpider.class;
			case 2: return EntityNyxWightSanctified.class;
			default: return EntityNyxWalker.class;
		}
	}
	@Override
	public int getSpawnCount(int metadata) {
		switch(metadata) {
			case 1: return 3;
			case 2: return 2;
			default: return 1;
		}
	}

	@Override
	public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
		NyxBlockFunctionsInfested.onBlockClicked(par1World, par2, par3, par4, par5EntityPlayer);
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
		NyxBlockFunctionsInfested.onEntityCollidedWithBlock(par1World, par2, par3, par4, par5Entity);
	}
	
	@Override
	public void updateTick(World w, int x, int y, int z, Random r) {
		NyxBlockFunctionsInfested.updateTick(w, x, y, z, r);
		super.updateTick(w, x, y, z, r);
	}
}
