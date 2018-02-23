package iceandshadow2.nyx.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.ias.api.EnumIaSAspect;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.IaSBaseItemSingle;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.entities.projectile.EntityShadowBall;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NyxItemBoneCursed extends NyxBaseItemBone {

	public NyxItemBoneCursed(String texName) {
		super(texName);
		setMaxDamage(180);
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.NYX;
	}

	@Override
	public void onBoneDone(ItemStack is) {
		is.setItemDamage(0);
		is.func_150996_a(NyxItems.cursedPowder);
	}

	@Override
	public void doEffect(Entity ent) {
		if(ent instanceof EntityLivingBase) {
			EntityLivingBase elb = (EntityLivingBase)ent;
			elb.addPotionEffect(new PotionEffect(Potion.blindness.id, 50, 0));
			elb.addPotionEffect(new PotionEffect(Potion.invisibility.id, 30, 0));
			if(elb instanceof EntityPlayer) {
				EntityPlayer ep = (EntityPlayer)elb;
				ep.getFoodStats().addExhaustion(0.25f);
			}
		}
	}
}
