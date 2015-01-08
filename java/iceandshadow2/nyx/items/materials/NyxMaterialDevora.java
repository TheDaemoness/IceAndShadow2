package iceandshadow2.nyx.items.materials;

import iceandshadow2.api.EnumIaSToolClass;
import iceandshadow2.api.IIaSTool;
import iceandshadow2.api.IaSEntityKnifeBase;
import iceandshadow2.api.IaSToolMaterial;
import iceandshadow2.ias.items.tools.IaSItemThrowingKnife;
import iceandshadow2.util.IaSBlockHelper;
import iceandshadow2.util.IaSEntityHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

public class NyxMaterialDevora extends IaSToolMaterial {

	private static ResourceLocation knife_tex = new ResourceLocation("iceandshadow2:textures/entity/nyxknife_devora.png");
	
	@Override
	public boolean getBrokenTool(ItemStack is, EntityLivingBase user) {
		return false;
	}
	
	@Override
	public boolean glows(EnumIaSToolClass mat) {
		return true;
	}

	@Override
	public String getMaterialName() {
		return "Devora";
	}
	
	@Override
	public int onAttack(ItemStack is, EntityLivingBase user, Entity target) {
		user.worldObj.createExplosion(user, target.posX, target.posY+target.getEyeHeight()/2, target.posZ, 0.1F, true);
		return super.onAttack(is, user, target);
	}

	@Override
	public boolean onSwing(ItemStack is, EntityLivingBase user) {
		if(is.getItem() instanceof IaSItemThrowingKnife)
			return false;
		if(!user.worldObj.isRemote) {
			MovingObjectPosition m = IaSEntityHelper.getObjectPosition(user.worldObj, user, false);
			if(m == null)
				return false;
			if(m.typeOfHit == MovingObjectType.BLOCK) {
				Vec3 v = IaSBlockHelper.getBlockSideCoords(m.blockX, m.blockY, m.blockZ, ForgeDirection.getOrientation(m.sideHit), user.worldObj.rand, 0.75F);
				EnumIaSToolClass tc = ((IIaSTool)is.getItem()).getIaSToolClass();
				if(tc == EnumIaSToolClass.SPADE)
					user.worldObj.createExplosion(user, v.xCoord, v.yCoord, v.zCoord, 0.3F, true);
				else
					user.worldObj.createExplosion(user, v.xCoord, v.yCoord, v.zCoord, 0.1F, false);
			} else if (m.typeOfHit == MovingObjectType.ENTITY) {
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

	@Override
	public boolean onKnifeHit(EntityLivingBase user, IaSEntityKnifeBase knife,
			Entity target) {
		if(!knife.worldObj.isRemote)
			knife.worldObj.createExplosion(user, knife.posX, knife.posY, knife.posZ, 0.3F, true);
		return false;
	}

	@Override
	public boolean onKnifeHit(EntityLivingBase user, IaSEntityKnifeBase knife,
			ChunkCoordinates block) {
		if(!knife.worldObj.isRemote)
			knife.worldObj.createExplosion(user, knife.posX, knife.posY, knife.posZ, 0.3F, true);
		return false;
	}

	@Override
	public ResourceLocation getKnifeTexture(IaSEntityKnifeBase knife) {
		return knife_tex;
	}
}
