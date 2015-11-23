package iceandshadow2.render.fx;

import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.world.World;

public class EntityFxToxicMist extends EntityReddustFX {

	public EntityFxToxicMist(World w, double x, double y, double z) {
		super(w, x, y, z, 1.0F, 0.0F, 0.0F, 0.9F);
		this.setRBGColorF(0.0F, 0.8F + w.rand.nextFloat() * 0.2F, 0.0F);
	}

	@Override
	public float getBrightness(float p_70013_1_) {
		return 1.0F;
	}	
}
