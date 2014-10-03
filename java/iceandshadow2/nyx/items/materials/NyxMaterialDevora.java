package iceandshadow2.nyx.items.materials;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import iceandshadow2.api.EnumIaSToolClass;
import iceandshadow2.api.IIaSThrowingKnife;
import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.ias.items.tools.IaSItemThrowingKnife;
import iceandshadow2.ias.items.tools.IaSItemTool;
import iceandshadow2.util.IaSBlockHelper;
import iceandshadow2.util.entity.IaSEntityHelper;

public class NyxMaterialDevora extends IaSToolMaterial {

	@Override
	public int getXpValue(World world, ItemStack is) {
		return 0;
	}
	
	@Override
	public boolean getBrokenTool(ItemStack is, EntityLivingBase user) {
		return false;
	}

	@Override
	public boolean rejectWhenZero() {
		return false;
	}

	@Override
	public String getMaterialName() {
		return "Devora";
	}
	
	@Override
	public boolean onSwing(ItemStack is, EntityLivingBase user) {
		if(is.getItem() instanceof IaSItemThrowingKnife)
			return false;
		if(!user.worldObj.isRemote) {
			MovingObjectPosition m = IaSEntityHelper.getObjectPosition(user.worldObj, user, false);
			if(m == null)
				return false;
			if(m.typeOfHit == m.typeOfHit.BLOCK) {
				Vec3 v = IaSBlockHelper.getBlockSideCoords(m.blockX, m.blockY, m.blockZ, ForgeDirection.getOrientation(m.sideHit), user.worldObj.rand, 0.75F);
				if(((IaSItemTool)is.getItem()).getIaSToolClass() == EnumIaSToolClass.SPADE)
					user.worldObj.createExplosion(user, v.xCoord, v.yCoord, v.zCoord, 0.3F, true);
				else
					user.worldObj.createExplosion(user, v.xCoord, v.yCoord, v.zCoord, 0.1F, false);
			} else if (m.typeOfHit == m.typeOfHit.ENTITY) {
				user.worldObj.createExplosion(user, m.entityHit.posX, m.entityHit.posY, m.entityHit.posZ, 0.1F, true);
			} else
				return false;
			is.damageItem(1, user);
		}
		return false;
	}

	@Override
	public float getBaseSpeed() {
		return 64;
	}

	@Override
	public int getBaseLevel() {
		return 0;
	}

	@Override
	public int getDurability(ItemStack is) {
		return 384;
	}

}
