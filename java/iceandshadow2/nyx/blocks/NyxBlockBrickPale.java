package iceandshadow2.nyx.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.nyx.NyxBlocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NyxBlockBrickPale extends IaSBaseBlockSingle {
	
	public NyxBlockBrickPale(String id) {
		super(EnumIaSModule.NYX, id, Material.rock);
		this.setResistance(10.0F);
        this.setHardness(2.0F);
        this.setHarvestLevel("pickaxe", 0);
        this.setLuminescence(0.3F);
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world,
			int x, int y, int z) {
		return false;
	}
	
	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        float var5 = 0.0125F;
        return AxisAlignedBB.getBoundingBox((double)((float)par2 + var5), (double)((float)par3 + var5), (double)((float)par4 + var5), (double)((float)(par2 + 1) - var5), (double)((float)(par3 + 1) - var5), (double)((float)(par4 + 1) - var5));
    }
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x,
			int y, int z, Entity ent) {
		if(ent instanceof EntityLivingBase) {
			EntityLivingBase lb = (EntityLivingBase)ent;
			if(lb.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD)
				return;
			if(lb.getEquipmentInSlot(1) != null)
				return;
			if(!lb.isPotionActive(Potion.wither.id))
				lb.addPotionEffect(new PotionEffect(Potion.wither.id,39,0));
		}
		super.onEntityCollidedWithBlock(world, x, y, z, ent);
	}

	@Override
	public void onBlockExploded(World world, int x, int y, int z,
			Explosion explosion) {
		world.setBlock(x, y, z, NyxBlocks.brickPaleCracked);
	}
	
}
