package iceandshadow2.nyx.blocks;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.IaSDamageSources;
import iceandshadow2.ias.blocks.IaSBaseBlockAirlike;
import iceandshadow2.ias.util.IaSPlayerHelper;
import iceandshadow2.ias.util.IaSWorldHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

//NOTE: This is air used for underground and very high areas in Nyx.
//It's used to suffocate players and other such entities.
public class NyxBlockAir extends IaSBaseBlockAirlike {

	public static final short ATMOS_HEIGHT = 192;

	public NyxBlockAir(String id) {
		super(EnumIaSModule.NYX, id);
		setLightOpacity(1);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity ent) {
		final int hardness = IaSWorldHelper.getDifficulty(world);
		if (ent instanceof EntityPlayer) {
			IaSPlayerHelper.drainXP((EntityPlayer) ent, hardness, "Your life begins to bleed into your surroundings.",
					false);
		} else if (ent instanceof EntityAgeable) {
			ent.attackEntityFrom(IaSDamageSources.dmgDrain, 1 + hardness);
		}
		super.onEntityCollidedWithBlock(world, x, y, z, ent);
	}

	@Override
	public void onNeighborBlockChange(World w, int x, int y, int z, Block bl) {
		for (final ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			if (dir == ForgeDirection.DOWN && y <= ATMOS_HEIGHT) {
				continue;
			}
			if (w.isAirBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)) {
				w.setBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, this);
			}
		}
	}
}
