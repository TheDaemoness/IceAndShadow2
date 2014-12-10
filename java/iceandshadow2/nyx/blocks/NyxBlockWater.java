package iceandshadow2.nyx.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.blocks.IaSBaseBlockFluid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class NyxBlockWater extends IaSBaseBlockFluid {

	public NyxBlockWater(String id, Fluid fluid) {
		super(EnumIaSModule.NYX, id, fluid);
		this.setLightOpacity(4);
		this.setResistance(300F);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x,
			int y, int z, Entity ent) {
		if(ent instanceof EntityLivingBase) {
			EntityLivingBase el = (EntityLivingBase)ent;
			if(el.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD) {
				if(!el.isPotionActive(Potion.wither.id))
					el.addPotionEffect(new PotionEffect(Potion.wither.id,39,1));
			}
		}
		super.onEntityCollidedWithBlock(world, x, y,
				z, ent);
	}
	
}
