package iceandshadow2.nyx.blocks;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.ias.blocks.IaSBaseBlockLeaves;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.util.EnumIaSModule;

public class NyxBlockPoisonLeaves extends IaSBaseBlockLeaves {

	public NyxBlockPoisonLeaves(String texName) {
		super(EnumIaSModule.NYX, texName);
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
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
    	if(par5Entity instanceof EntityLivingBase  && !(par5Entity instanceof EntityMob)) {
			((EntityLivingBase)par5Entity).attackEntityFrom(IaSDamageSources.dmgPoisonwood, 1);
    		if(!(((EntityLivingBase)par5Entity).isPotionActive(Potion.poison)))
    			((EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(Potion.poison.id, 35*(par1World.difficultySetting.getDifficultyId()+1), 0));
    	}
    }

	@Override
    public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
    	if(par5EntityPlayer.getCurrentEquippedItem() == null)
    		par5EntityPlayer.addPotionEffect(new PotionEffect(Potion.poison.id, 20*(par1World.difficultySetting.getDifficultyId()+1), 0));
    }

}
