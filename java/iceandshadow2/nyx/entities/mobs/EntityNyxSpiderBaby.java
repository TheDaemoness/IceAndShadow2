package iceandshadow2.nyx.entities.mobs;

import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.api.IIaSAspect;
import iceandshadow2.ias.util.IaSEntityHelper;
import iceandshadow2.ias.util.IaSWorldHelper;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.entities.util.EntityOrbNourishment;
import iceandshadow2.nyx.world.NyxBiomes;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityNyxSpiderBaby extends EntityNyxSpider {

	public EntityNyxSpiderBaby(World par1World) {
		super(par1World);
		setSize(0.3F, 0.2F);
		experienceValue = 1;
	}

	protected void doUncloakSound() {
		worldObj.playSoundAtEntity(this, "IceAndShadow2:mob_nyxwisp_materialize",
				0.5F - IaSWorldHelper.getDifficulty(worldObj) * 0.10F, rand.nextFloat() * 0.2F + 1.9F);
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(IaSWorldHelper.getDifficulty(worldObj));
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(2.25D);
	}
	
	@Override
	protected void dropFewItems(boolean par1, int par2) {
		if (!par1)
			return;

		final int diff = IaSWorldHelper.getDifficulty(worldObj);
		final int baite = rand.nextInt(Math.max(1, 8 - diff) + par2) - par2;
		if (baite <= 0)
			dropItem(NyxItems.resin, rand.nextInt(5) < par2 - 1 ? 2 : 1);
		
		dropItem(Items.string, 1);

		worldObj.spawnEntityInWorld(new EntityOrbNourishment(worldObj, posX, posY, posZ, 1));
	}
	
	@Override
	protected void dropRareDrop(int par1) {
		dropItem(NyxItems.toughGossamer, 1);
	}
	
	public float getAttackStrength(Entity par1Entity) {
		return 3.0F;
	}
	
	@Override
	public boolean attackEntityAsMob(Entity ent) {
		if(super.attackEntityAsMob(ent)) {
			if(ent instanceof EntityPlayer) {
				((EntityPlayer)ent).getFoodStats().addStats(
					-rand.nextInt(2)-IaSWorldHelper.getDifficulty(worldObj)*2,
					-IaSWorldHelper.getDifficulty(worldObj)*3);
			}
			final int
				x = (int)(ent.posX<0?ent.posX-1:ent.posX),
				y = (int)(ent.posY)+1,
				z = (int)(ent.posZ<0?ent.posZ-1:ent.posZ);
			worldObj.setBlock(x, y, z, Blocks.web);
			worldObj.playSoundAtEntity(this, "mob.spider.death",
					0.5F, rand.nextFloat() * 0.2F + 1.9F);
			this.setDead();
			return true;
		}
		return false;
	}
}
