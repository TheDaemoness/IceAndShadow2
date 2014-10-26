package iceandshadow2.nyx.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.ias.blocks.IaSBaseBlockLeaves;
import iceandshadow2.ias.interfaces.IIaSNoInfest;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.blocks.mixins.NyxBlockFunctionsPoisonwood;

public class NyxBlockPoisonLeaves extends IaSBaseBlockLeaves implements IIaSNoInfest {

	private static Random r = new Random();
	
	public NyxBlockPoisonLeaves(String texName) {
		super(EnumIaSModule.NYX, texName);
	}
	
	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world,
			int x, int y, int z, int fortune) {
		return super.onSheared(item, world, x, y, z, fortune);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z,
			int meta, int fortune) {
		ArrayList<ItemStack> is = new ArrayList<ItemStack>();
		if (!world.isRemote) {
            if (world.rand.nextInt(20) == 0)
                is.add(new ItemStack(NyxItems.poisonFruit));
        }
		return is;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
		NyxBlockFunctionsPoisonwood.onEntityCollidedWithBlock(par1World, par2, par3, par4, par5Entity);
    }

	@Override
    public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
    	NyxBlockFunctionsPoisonwood.onBlockClicked(par1World, par2, par3, par4, par5EntityPlayer);
    }

}
