package iceandshadow2.nyx.blocks.utility;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.IIaSModName;
import iceandshadow2.IceAndShadow2;
import iceandshadow2.boilerplate.IntBits;
import iceandshadow2.ias.api.EnumIaSAspect;
import iceandshadow2.ias.api.IIaSAspect;
import iceandshadow2.ias.util.IaSBlockHelper;
import iceandshadow2.ias.util.IaSRegistration;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class NyxBlockRailPowered extends NyxBlockRail  {

	@Override
	public void onMinecartPass(World world, EntityMinecart cart, int x, int y, int z) {
		final int meta = world.getBlockMetadata(x, y, z);
		if(IntBits.areAllSet(meta, 8)) {
			final double speed = this.getRailMaxSpeed(world, cart, y, x, z);
			cart.motionY = Math.signum(cart.motionY)*speed;
			for(int i = 2; i <= 5; ++i) {
				final ForgeDirection dir = ForgeDirection.getOrientation(i);
				if(NyxBlockRail.connects(dir, world.getBlockMetadata(x, y, z), false)) {
					final Block bl = world.getBlock(x+dir.offsetX, y, z+dir.offsetZ);
					if(!IaSBlockHelper.isAir(bl) && !(bl instanceof BlockRailBase)) {
						cart.motionX = -dir.offsetX*speed;
						cart.motionZ = -dir.offsetZ*speed;
						return;
					}
				}
			}
			//If we're at this point, the cart was not accelerated away from a block.
			cart.motionX = Math.signum(cart.motionX)*speed;
			cart.motionZ = Math.signum(cart.motionZ)*speed;
		}
	}

	public NyxBlockRailPowered(String name) {
		super(name, true, false);
	}
}
