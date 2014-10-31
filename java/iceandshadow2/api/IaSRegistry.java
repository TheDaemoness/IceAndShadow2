package iceandshadow2.api;

import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.ias.IaSFakeItem;
import iceandshadow2.ias.items.tools.IaSTools;
import iceandshadow2.nyx.items.materials.NyxMaterialEchir;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

class IaSMaterialIconGetter extends Item {
	@Override
	public void registerIcons(IIconRegister i) {
		for(IaSToolMaterial m : IaSRegistry.getToolMaterials())
			m.registerIcons(i);
		IaSRegistry.getDefaultMaterial().registerIcons(i);
	}
	
	@Override
	public boolean getHasSubtypes() {
		return true;
	}
}

public final class IaSRegistry {
	
	private static IaSToolMaterial defaultMaterial;
	private static HashMap<String,IaSToolMaterial> toolMaterials = new HashMap<String,IaSToolMaterial>();
	private static Item iconery;
	
	public static void postInit() {
		if(iconery != null)
			return;
		defaultMaterial = new NyxMaterialEchir();
		iconery = new IaSMaterialIconGetter();
		GameRegistry.registerItem(iconery, "thisItemDoesNotExist");
		IaSTools.axe.setCreativeTab(IaSCreativeTabs.tools);
		IaSTools.pickaxe.setCreativeTab(IaSCreativeTabs.tools);
		IaSTools.spade.setCreativeTab(IaSCreativeTabs.tools);
		IaSTools.sword.setCreativeTab(IaSCreativeTabs.combat);
		IaSTools.knife.setCreativeTab(IaSCreativeTabs.combat);
	}
	
	public static void addToolMaterial(IaSToolMaterial mat) {
		if(toolMaterials.containsKey(mat.getMaterialName()))
			throw new IllegalArgumentException("Material '" + mat.getMaterialName() + "' already exists!");
		toolMaterials.put(mat.getMaterialName(), mat);
	}
	
	public static IaSToolMaterial getToolMaterial(String key) {
		if(toolMaterials.containsKey(key))
			return toolMaterials.get(key);
		return null; //In case I decide to switch to TreeMap later for some reason.
	}
	
	public static Collection<IaSToolMaterial> getToolMaterials() {
		return toolMaterials.values();
	}

	public static IaSToolMaterial getDefaultMaterial() {
		return defaultMaterial;
	}
}
