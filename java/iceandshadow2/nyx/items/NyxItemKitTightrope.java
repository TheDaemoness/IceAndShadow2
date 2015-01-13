package iceandshadow2.nyx.items;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.items.IaSBaseItemSingle;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.render.fx.IaSFxManager;
import iceandshadow2.util.IaSPlayerHelper;

public class NyxItemKitTightrope extends IaSBaseItemSingle {

	public static final int LENGTH_MAX = 48;
	
	public NyxItemKitTightrope(String texName) {
		super(EnumIaSModule.NYX, texName);
		this.setMaxStackSize(4);
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer pl,
			World w, int x, int y, int z,
			int side, float lx, float ly,
			float lz) {
		if(side == 0 || side == 1)
			return false;
		ForgeDirection dir = ForgeDirection.getOrientation(side);
		if(!w.isSideSolid(x, y, z, dir))
			return false;
		int ilen = 1;
		int xc = x, yc = y, zc = z;
		for(int i = 1; i < LENGTH_MAX; ++i) {
			if(dir == ForgeDirection.EAST)
				++xc;
			if(dir == ForgeDirection.WEST)
				--xc;
			if(dir == ForgeDirection.SOUTH)
				++zc;
			if(dir == ForgeDirection.NORTH)
				--zc;
			Block bl = w.getBlock(xc, yc, zc);
			if(bl.getMaterial() == Material.air)
				++ilen;
			else if(bl.isReplaceable(w, xc, yc, zc))
				++ilen;
			else if(w.isSideSolid(xc, yc, zc, dir.getOpposite()))
				break;
			else {
				IaSPlayerHelper.messagePlayer(pl, "There's something in the way of the rope.");
				return false;
			}
		}
		if(ilen == 1)
			return false;
		if(ilen < 6) {
			IaSPlayerHelper.messagePlayer(pl, "That distance would be a waste of a tightrope.");
			return false;
		}
		if(ilen == LENGTH_MAX) {
			IaSPlayerHelper.messagePlayer(pl, "The tightrope won't reach that far.");
			return false;
		}
		w.playSoundAtEntity(pl, "random.bow", 1.0F,
				1.0F / (itemRand.nextFloat() * 0.4F + 1.2F));
		if(dir == ForgeDirection.EAST)
			w.setBlock(x+1, y, z, NyxBlocks.hookTightropeX);
		if(dir == ForgeDirection.WEST)
			w.setBlock(x-1, y, z, NyxBlocks.hookTightropeX);
		if(dir == ForgeDirection.SOUTH)
			w.setBlock(x, y, z+1, NyxBlocks.hookTightropeZ);
		if(dir == ForgeDirection.NORTH)
			w.setBlock(x, y, z-1, NyxBlocks.hookTightropeZ);
		for(int i = 2; i < ilen; ++i) {
			if(dir == ForgeDirection.EAST)
				w.setBlock(x+i, y, z, NyxBlocks.ropeX);
			if(dir == ForgeDirection.WEST)
				w.setBlock(x-i, y, z, NyxBlocks.ropeX);
			if(dir == ForgeDirection.SOUTH)
				w.setBlock(x, y, z+i, NyxBlocks.ropeZ);
			if(dir == ForgeDirection.NORTH)
				w.setBlock(x, y, z-i, NyxBlocks.ropeZ);
		}
		if(dir == ForgeDirection.EAST) {
			w.setBlock(x+ilen-1, y, z, NyxBlocks.hookTightropeX);
			w.playSoundEffect(x+ilen-1+0.5, y+0.5, z+0.5, "dig.stone", 1.0F,
					1.0F / (itemRand.nextFloat() * 0.4F + 1.2F));
		}
		if(dir == ForgeDirection.WEST) {
			w.setBlock(x-ilen+1, y, z, NyxBlocks.hookTightropeX);
			w.playSoundEffect(x-ilen+1+0.5, y+0.5, z+0.5, "dig.stone", 1.0F,
					1.0F / (itemRand.nextFloat() * 0.4F + 1.2F));
		}
		if(dir == ForgeDirection.SOUTH) {
			w.setBlock(x, y, z+ilen-1, NyxBlocks.hookTightropeZ);
			w.playSoundEffect(x+0.5, y+0.5, z+ilen-1+0.5, "dig.stone", 1.0F,
					1.0F / (itemRand.nextFloat() * 0.4F + 1.2F));
		}
		if(dir == ForgeDirection.NORTH) {
			w.setBlock(x, y, z-ilen+1, NyxBlocks.hookTightropeZ);
			w.playSoundEffect(x+0.5, y+0.5, z-ilen+1+0.5, "dig.stone", 1.0F,
					1.0F / (itemRand.nextFloat() * 0.4F + 1.2F));
		}
		is.stackSize -= 1;
		return true;
	}

	@Override
	public void onUpdate(ItemStack is, World w,
			Entity ent, int wat, boolean onhand) {
		super.onUpdate(is, w, ent, wat, onhand);
		if(!w.isRemote)
			return;
		if(ent instanceof EntityPlayer) {
			EntityPlayer el = (EntityPlayer)ent;
			if(el.getEquipmentInSlot(0) == null)
				return;
			if(el.getEquipmentInSlot(0).getItem() != this)
				return;
			ForgeDirection dir = null;
			MovingObjectPosition mop = null;
			if(el.isSneaking()) {
				Vec3 v = el.getLookVec();
				if(Math.abs(v.xCoord) > Math.abs(v.zCoord)) {
					if(v.xCoord > 0)
						dir = ForgeDirection.EAST;
					else
						dir = ForgeDirection.WEST;
				} else {
					if(v.zCoord > 0)
						dir = ForgeDirection.SOUTH;
					else
						dir = ForgeDirection.NORTH;
				}
				int modX = 0, modZ = 0;
				if(el.posX < 0)
					modX = -1;
				if(el.posZ < 0)
					modZ = -1;
				mop = new MovingObjectPosition(modX+(int)el.posX, ((int)(el.posY)-2), modZ+(int)el.posZ, dir.ordinal(), v);
			} else {
				mop = this.getMovingObjectPositionFromPlayer(w, el, true);
				if(mop == null)
					return;
				if(mop.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK)
					return;
				if(mop.sideHit == 0 || mop.sideHit == 1)
					return;
				dir = ForgeDirection.getOrientation(mop.sideHit);
			}
			int ilen = 1, xc = mop.blockX, yc = mop.blockY, zc = mop.blockZ;
			if(!w.isSideSolid(xc, yc, zc, dir))
				return;
			for(int i = 0; i < LENGTH_MAX; ++i) {
				if(dir == ForgeDirection.EAST)
					++xc;
				if(dir == ForgeDirection.WEST)
					--xc;
				if(dir == ForgeDirection.SOUTH)
					++zc;
				if(dir == ForgeDirection.NORTH)
					--zc;
				if(i == 0)
					continue;
				Block bl = w.getBlock(xc, yc, zc);
				if(bl.getMaterial() == Material.air)
					++ilen;
				else if(bl.isReplaceable(w, xc, yc, zc))
					++ilen;
				else if(w.isSideSolid(xc, yc, zc, dir.getOpposite()))
					break;
				else
					return;
			}
			if(ilen == 1)
				return;
			if(ilen < 5)
				return;
			if(ilen == LENGTH_MAX)
				return;
			for(int i = 1; i < ilen; ++i) {
				if(dir == ForgeDirection.EAST)
					spawnParticlesX(w, mop.blockX+i, mop.blockY, mop.blockZ);
				if(dir == ForgeDirection.WEST)
					spawnParticlesX(w, mop.blockX-i, mop.blockY, mop.blockZ);
				if(dir == ForgeDirection.SOUTH)
					spawnParticlesZ(w, mop.blockX, mop.blockY, mop.blockZ+i);
				if(dir == ForgeDirection.NORTH)
					spawnParticlesZ(w, mop.blockX, mop.blockY, mop.blockZ-i);
			}
		}
		
	}

	private void spawnParticlesX(World w, int x, int y, int z) {
		IaSFxManager.spawnParticle(w, "cloudSmall", x+w.rand.nextDouble(), 0.5+y, 0.5+z, 0.0F, 0.0F, 0.0F, true, false);
	}
	
	private void spawnParticlesZ(World w, int x, int y, int z) {
		IaSFxManager.spawnParticle(w, "cloudSmall", 0.5+x, 0.5+y, z+w.rand.nextDouble(), 0.0F, 0.0F, 0.0F, true, false);
	}
	
}
