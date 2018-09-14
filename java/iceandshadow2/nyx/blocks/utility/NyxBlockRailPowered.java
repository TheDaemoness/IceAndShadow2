package iceandshadow2.nyx.blocks.utility;

import iceandshadow2.boilerplate.IntBits;
import iceandshadow2.ias.util.IaSBlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class NyxBlockRailPowered extends NyxBlockRail {

	public NyxBlockRailPowered(String name) {
		super(name, true, false);
	}

	@Override
	public void onMinecartPass(World world, EntityMinecart cart, int x, int y, int z) {
		final int meta = world.getBlockMetadata(x, y, z);
		if (IntBits.areAllSet(meta, 8)) {
			final double speed = getRailMaxSpeed(world, cart, y, x, z);
			cart.motionY = Math.signum(cart.motionY) * speed;
			for (int i = 2; i <= 5; ++i) {
				final ForgeDirection dir = ForgeDirection.getOrientation(i);
				if (NyxBlockRail.connects(dir, world.getBlockMetadata(x, y, z), false)) {
					final Block bl = world.getBlock(x + dir.offsetX, y, z + dir.offsetZ);
					if (!IaSBlockHelper.isAir(bl) && !(bl instanceof BlockRailBase)) {
						cart.motionX = -dir.offsetX * speed;
						cart.motionZ = -dir.offsetZ * speed;
						return;
					}
				}
			}
			// If we're at this point, the cart was not accelerated away from a block.
			cart.motionX = Math.signum(cart.motionX) * speed;
			cart.motionZ = Math.signum(cart.motionZ) * speed;
		}
	}
}
