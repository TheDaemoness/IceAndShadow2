package iceandshadow2.nyx.items.tools;

import cpw.mods.fml.common.registry.LanguageRegistry;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.IaSRegistry;
import iceandshadow2.api.IaSGrenadeLogic;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.IaSBaseItemMulti;
import iceandshadow2.ias.items.IaSBaseItemMultiTexturedGlow;
import iceandshadow2.ias.items.tools.IaSItemThrowingKnife;
import iceandshadow2.nyx.entities.projectile.EntityGrenade;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class NyxItemGrenade extends IaSBaseItemMulti {

	public NyxItemGrenade(String id) {
		super(EnumIaSModule.NYX, id, IaSRegistry.getGrenadeLogicCount());
		setMaxStackSize(4); //Three more than it should be for safety.
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack is) {
		final IaSGrenadeLogic explosiveReasoning = IaSRegistry.getGrenadeLogic(is.getItemDamage());
		final String original = LanguageRegistry.instance().getStringLocalization("item."+this.getModName()+".name");
		return original + " ("+LanguageRegistry.instance().getStringLocalization("ias2.grenade."+explosiveReasoning.getName())+")";
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer pl) {
		final IaSGrenadeLogic techno = IaSRegistry.getGrenadeLogic(is.getItemDamage());
		if(pl.isSneaking()) {
			pl.setItemInUse(is, getMaxItemUseDuration(is));
			if(!techno.fuseOnImpact) {
				techno.playFuseSound(pl);
			}
		}
		return is;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack is) {
		return EnumAction.bow;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack is) {
		final IaSGrenadeLogic logos = IaSRegistry.getGrenadeLogic(is.getItemDamage());
		return logos.fuseOnImpact?72000:logos.fuseLimit;
	}

	@Override
	public ItemStack onEaten(ItemStack is, World w, EntityPlayer pl) {
		onPlayerStoppedUsing(is, w, pl, 0);
		return is;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack is, World w, EntityPlayer pl, int time) {
		w.playSoundAtEntity(pl, "random.bow", 0.5F, 0.75F);
		time = getMaxItemUseDuration(is)-time;
		if(!w.isRemote) {
			EntityGrenade eg = new EntityGrenade(w, pl, IaSRegistry.getGrenadeLogic(is.getItemDamage()).getId(), time);
			w.spawnEntityInWorld(eg);
		}
		if (!pl.capabilities.isCreativeMode) {
			--is.stackSize;
			if(is.stackSize <= 0)
				pl.destroyCurrentEquippedItem();
		}
	}
}
