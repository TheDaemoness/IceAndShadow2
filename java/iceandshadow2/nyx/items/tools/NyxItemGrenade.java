package iceandshadow2.nyx.items.tools;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.IaSRegistry;
import iceandshadow2.api.IaSGrenadeLogic;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.IaSBaseItemMulti;
import iceandshadow2.ias.items.IaSBaseItemMultiGlow;
import iceandshadow2.ias.items.IaSBaseItemMultiTexturedGlow;
import iceandshadow2.ias.items.tools.IaSItemThrowingKnife;
import iceandshadow2.ias.util.IntBits;
import iceandshadow2.nyx.NyxItems;
import iceandshadow2.nyx.entities.projectile.EntityGrenade;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class NyxItemGrenade extends IaSBaseItemMultiGlow {
	
	@SideOnly(Side.CLIENT)
	protected IIcon payloadIcons[];
	
	public static IaSGrenadeLogic getGrenadeLogic(ItemStack is) {
		return IaSRegistry.getGrenadeLogic(is.getItemDamage() >>> 1);
	}
	public static boolean isRemoteDetonated(ItemStack is) {
		return IntBits.areAllSet(is.getItemDamage(), 1);
	}

	public NyxItemGrenade(String id) {
		super(EnumIaSModule.NYX, id, IaSRegistry.getGrenadeLogicCount()*2);
		setMaxStackSize(4); //Three more than it should be for safety.
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack is) {
		final IaSGrenadeLogic explosiveReasoning = getGrenadeLogic(is);
		final String suffix = isRemoteDetonated(is)?"Remote.name":".name";
		final String original = LanguageRegistry.instance().getStringLocalization("item."+this.getModName()+suffix);
		return original + " ("+LanguageRegistry.instance().getStringLocalization("ias2.grenade."+explosiveReasoning.getName())+")";
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer pl) {
		final IaSGrenadeLogic techno = IaSRegistry.getGrenadeLogic(is.getItemDamage());
		if(pl.isSneaking()) {
			pl.setItemInUse(is, getMaxItemUseDuration(is));
			if(!IntBits.areAllSet(is.getItemDamage(), 1)) {
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
		return isRemoteDetonated(is)?72000:getGrenadeLogic(is).fuseLimit;
	}

	@Override
	public ItemStack onEaten(ItemStack is, World w, EntityPlayer pl) {
		onPlayerStoppedUsing(is, w, pl, 0);
		return is;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack is, World w, EntityPlayer pl, int time) {
		w.playSoundAtEntity(pl, "random.bow", 0.5F, 0.75F);
		if(isRemoteDetonated(is)) {
			time = 0;
		} else {
			time = Math.max(1, getMaxItemUseDuration(is)-time);
		}
		if(!w.isRemote) {
			EntityGrenade eg = new EntityGrenade(w, pl, getGrenadeLogic(is).getId(), time);
			w.spawnEntityInWorld(eg);
		}
		if (!pl.capabilities.isCreativeMode) {
			--is.stackSize;
			if(is.stackSize <= 0)
				pl.destroyCurrentEquippedItem();
		}
	}
	
	@Override
	public String getUnlocalizedHint(EntityPlayer entityPlayer, ItemStack itemStack) {
		return isRemoteDetonated(itemStack)?"grenadeRemote":"grenade";
	}

	@Override
	public String getLocalizedHintArgument(EntityPlayer entityPlayer, ItemStack itemStack) {
		if(isRemoteDetonated(itemStack))
			return null;
		final int fusetime = IaSRegistry.getGrenadeLogic(itemStack.getItemDamage()).fuseLimit;
		final String fuse = String.format("%.2f", fusetime/20f);
		final String hint = LanguageRegistry.instance().getStringLocalization(Math.abs(fusetime)==20?"ias2.unit.second":"ias2.unit.seconds");
		return fuse+" "+hint;
	}
	
	@Override
	public int getFirstGlowPass(ItemStack is) {
		return 2;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderPasses(int metadata) {
		return 3;
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		if(pass > 0)
			return NyxItems.grenadeHandle.getIconFromDamage(isRemoteDetonated(stack)?1:0);
		return payloadIcons[getGrenadeLogic(stack).getId()];
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister reg) {
		payloadIcons = new IIcon[IaSRegistry.getGrenadeLogicCount()];
		for (int i = 0; i < IaSRegistry.getGrenadeLogicCount(); ++i) {
			payloadIcons[i] = reg.registerIcon(getTextureName() + IaSRegistry.getGrenadeLogic(i).getName());
		}
	}
}
