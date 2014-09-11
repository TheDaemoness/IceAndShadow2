package iceandshadow2.nyx.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.ias.blocks.IaSBaseBlockFluid;
import iceandshadow2.ias.blocks.IaSBaseBlockSingle;
import iceandshadow2.nyx.blocks.*;
import iceandshadow2.util.EnumIaSModule;

public class NyxBlockWater extends IaSBaseBlockFluid {

	public NyxBlockWater(String id, Fluid fluid) {
		super(EnumIaSModule.NYX, id, fluid);
		this.setStepSound(soundTypeSand);
		this.setLightOpacity(4);
		this.setResistance(300F);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x,
			int y, int z, Entity ent) {
		if(ent instanceof EntityPlayer) {
			EntityPlayer pl = (EntityPlayer)ent;
			pl.addPotionEffect(new PotionEffect(Potion.waterBreathing.id,5,0));
			if(!pl.isPotionActive(Potion.wither.id))
				pl.addPotionEffect(new PotionEffect(Potion.wither.id,39,1));
		}
		super.onEntityCollidedWithBlock(world, x, y,
				z, ent);
	}
	
}
