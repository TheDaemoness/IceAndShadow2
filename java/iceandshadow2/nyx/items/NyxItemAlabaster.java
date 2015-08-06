package iceandshadow2.nyx.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.items.IaSBaseItemMultiGlow;
import iceandshadow2.ias.items.IaSBaseItemSingleGlow;
import iceandshadow2.nyx.entities.ai.senses.IIaSSensate;
import iceandshadow2.nyx.entities.mobs.EntityNyxSpider;
import iceandshadow2.nyx.entities.mobs.IIaSMobGetters;

public class NyxItemAlabaster extends IaSBaseItemMultiGlow {

	@SideOnly(Side.CLIENT)
	protected IIcon burned;
	
	public NyxItemAlabaster(String texName) {
		super(EnumIaSModule.NYX, texName, 2);
		this.setMaxStackSize(16);
	}

	@Override
	public void addInformation(ItemStack s, EntityPlayer p, List l, boolean b) {
		if (s.getItemDamage() == 0) {
			l.add(EnumChatFormatting.DARK_RED.toString()
					+ EnumChatFormatting.ITALIC.toString()
					+ "Run.");
		}
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player,
			Entity e) {
		if(player.worldObj.isRemote)
			return true;
		if(stack.getItemDamage() == 0 && e instanceof EntityMagmaCube) {
			((EntityMagmaCube)e).setDead();
			player.worldObj.createExplosion(player, e.posX, e.posY, e.posZ, 0.5f, true);
			stack.setItemDamage(1);
			return true;
		}
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int dmg) {
		if (dmg == 1)
			return burned;
		return this.itemIcon;
	}

	@Override
	public void registerIcons(IIconRegister ir) {
		this.itemIcon = ir.registerIcon(this.getTexName()+'0');
		burned = ir.registerIcon(this.getTexName()+'1');
	}

	@Override
	public void onUpdate(ItemStack is, World w,
			Entity e, int i, boolean isHeld) {
		if(w.isRemote || is.getItemDamage() > 0)
			return;
		if(e instanceof EntityLivingBase) {
			EntityLivingBase sucker = (EntityLivingBase)e;
			if((sucker.getAge() & 127) == 0) {
				if(sucker.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD) {
					List li = w.getEntitiesWithinAABBExcludingEntity(sucker, 
							AxisAlignedBB.getBoundingBox(
									sucker.posX-32, sucker.posY-8, sucker.posZ-32, 
									sucker.posX+32, sucker.posY+24, sucker.posZ+32));
					for(Object ent : li) {
						if(ent instanceof EntityMob) {
							EntityMob joker = (EntityMob)ent;
							if(joker.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
								if(ent instanceof IIaSMobGetters)
									((IIaSMobGetters)ent).setSearchTarget(sucker);
								else if(!joker.isInvisible() && joker.getAttackTarget() == null)
									joker.setTarget(sucker);
							}
						}
					}
				}
			}
		}
		super.onUpdate(is, w, e, i, isHeld);
	}
	
	

}
