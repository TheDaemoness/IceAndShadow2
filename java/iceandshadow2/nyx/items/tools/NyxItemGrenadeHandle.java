package iceandshadow2.nyx.items.tools;

import cpw.mods.fml.common.registry.LanguageRegistry;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.IaSRegistry;
import iceandshadow2.api.IaSGrenadeLogic;
import iceandshadow2.boilerplate.IntBits;
import iceandshadow2.ias.interfaces.IIaSGlowing;
import iceandshadow2.ias.items.IaSBaseItemMulti;
import iceandshadow2.ias.items.IaSBaseItemMultiTexturedGlow;
import iceandshadow2.ias.items.tools.IaSItemThrowingKnife;
import iceandshadow2.nyx.entities.projectile.EntityGrenade;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class NyxItemGrenadeHandle extends IaSBaseItemMultiTexturedGlow {

	public NyxItemGrenadeHandle(String id) {
		super(EnumIaSModule.NYX, id, 2);
		setMaxStackSize(32);
	}
}
