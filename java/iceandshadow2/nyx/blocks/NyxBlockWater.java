package iceandshadow2.nyx.blocks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.blocks.IaSBaseBlockFluid;

public class NyxBlockWater extends IaSBaseBlockFluid {

	public NyxBlockWater(String id, Fluid fluid) {
		super(EnumIaSModule.NYX, id, fluid);
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
