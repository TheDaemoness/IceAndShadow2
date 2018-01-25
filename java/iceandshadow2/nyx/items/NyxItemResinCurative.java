package iceandshadow2.nyx.items;

import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.IaSItemFood;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;

public class NyxItemResinCurative extends IaSItemFood implements IIaSGlowing {

	public NyxItemResinCurative(String texName) {
		super(EnumIaSModule.NYX, texName, -20, 0.0F, false);
		setAlwaysEdible();
		setMaxStackSize(16);
	}

	@Override
	public EnumIaSAspect getAspect() {
		return EnumIaSAspect.POISONWOOD;
	}

	@Override
	public int getFirstGlowPass(ItemStack is) {
		return 0;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
		return EnumAction.block;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 16;
	}

	@Override
	public int getRenderPasses(int metadata) {
		return 1;
	}

	@Override
	public ItemStack onEaten(ItemStack is, World wld, EntityPlayer pl) {
		pl.removePotionEffect(Potion.poison.id);
		is.stackSize -= 1;
		return is;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack heap, World order, EntityPlayer pwai) {
		if (pwai.isPotionActive(Potion.poison)) {
			pwai.setItemInUse(heap, getMaxItemUseDuration(heap));
		}
		return heap;
	}

	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public boolean usesDefaultGlowRenderer() {
		return true;
	}
}
