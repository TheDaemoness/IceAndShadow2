package iceandshadow2.nyx.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.api.EnumIaSAspect;
import iceandshadow2.ias.items.IaSBaseItemMultiTexturedGlow;
import iceandshadow2.ias.util.IaSPlayerHelper;
import iceandshadow2.nyx.entities.projectile.EntityPoisonBall;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;

public class NyxItemToxicCore extends IaSBaseItemMultiTexturedGlow {

	public static void doToxicWave(World w, Entity caster) {
		final List<Entity> l = w.getEntitiesWithinAABBExcludingEntity(caster,
				AxisAlignedBB.getBoundingBox(caster.posX - 16, caster.posY - 16, caster.posZ - 16, caster.posX + 16,
						caster.posY + 24, caster.posZ + 16));
		for (final Entity ent : l)
			if (ent instanceof EntityMob && EnumIaSAspect.getAspect(ent) != EnumIaSAspect.POISONWOOD) {
				EntityPoisonBall pb;
				if (caster instanceof EntityLivingBase) {
					pb = new EntityPoisonBall(w, (EntityLivingBase) caster);
				} else {
					pb = new EntityPoisonBall(w);
				}
				pb.setThrowableHeading(ent.posX - caster.posX,
						ent.posY - caster.posY + ((EntityMob) ent).getEyeHeight() * 1.25 - caster.getEyeHeight(),
						ent.posZ - caster.posZ, 1.0F, 1.0F);
				w.spawnEntityInWorld(pb);
			}
	}

	public NyxItemToxicCore(String texName) {
		super(EnumIaSModule.NYX, texName, 2);
		setMaxStackSize(4);
		setFull3D();
		GameRegistry.addShapelessRecipe(new ItemStack(this, 4, 1), new ItemStack(this, 1, 0));
		GameRegistry.addShapelessRecipe(new ItemStack(this, 1, 0), new ItemStack(this, 1, 1), new ItemStack(this, 1, 1),
				new ItemStack(this, 1, 1), new ItemStack(this, 1, 1));
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.POISONWOOD;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1Stack, World par2World, EntityPlayer player) {
		if (!par2World.isRemote) {
			par2World.spawnEntityInWorld(new EntityPoisonBall(par2World, player));
			if (par1Stack.getItemDamage() == 0) {
				doToxicWave(par2World, player);
				IaSPlayerHelper.giveItem(player, new ItemStack(this, 1, 1));
			}
			par1Stack.stackSize -= 1;
		}
		return par1Stack;
	}
}
