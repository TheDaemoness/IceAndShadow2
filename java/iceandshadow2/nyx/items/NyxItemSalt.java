package iceandshadow2.nyx.items;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import iceandshadow2.EnumIaSModule;
import iceandshadow2.api.EnumIaSAspect;
import iceandshadow2.ias.items.IaSBaseItemMultiTextured;
import iceandshadow2.ias.items.IaSItemFood;
import iceandshadow2.ias.util.IaSPlayerHelper;
import iceandshadow2.nyx.NyxBlocks;
import iceandshadow2.nyx.world.NyxBiomes;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class NyxItemSalt extends IaSBaseItemMultiTextured {
	
	public NyxItemSalt(String id) {
		super(EnumIaSModule.NYX, id, 2);
		GameRegistry.addShapelessRecipe(new ItemStack(this, 1, 1), new ItemStack(this, 1, 0));
	}
}
