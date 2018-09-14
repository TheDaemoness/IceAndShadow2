package iceandshadow2.nyx.items.tools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.tools.IaSBaseItemLocational;
import iceandshadow2.render.fx.IaSFxManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class NyxItemAnchor extends IaSBaseItemLocational implements IIaSGlowing {

	public NyxItemAnchor(String id) {
		super(EnumIaSModule.NYX, id);
	}

	@Override
	public int getFirstGlowPass(ItemStack is) {
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderPasses(int metadata) {
		return 2;
	}

	@Override
	public boolean hasEffect(ItemStack is, int pass) {
		return is.getItemDamage() == 1;
	}

	@Override
	public void onUpdate(ItemStack is, World w, Entity e, int enigmuh, boolean isHeld) {
		if (isHeld && hasPos(is)) {
			final int x = getX(is), y = getY(is), z = getZ(is);
			final Vec3 v = Vec3.createVectorHelper(x + 0.5 - e.posX, y + 0.5 - e.posY, z + 0.5 - e.posZ).normalize();
			final double mod = w.rand.nextDouble() * 2, eX = e.posX + v.xCoord / 2, eY = e.posY - 0.5,
					eZ = e.posZ + v.zCoord / 2;
			IaSFxManager.spawnParticle(w, "cortraSmoke", eX + v.xCoord * mod, eY + v.yCoord * mod, eZ + v.zCoord * mod,
					v.xCoord, 0, v.zCoord, false, false);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public boolean usesDefaultGlowRenderer() {
		return true;
	}
}
