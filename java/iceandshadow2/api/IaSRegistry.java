package iceandshadow2.api;

import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.ias.items.tools.IaSTools;
import iceandshadow2.nyx.items.materials.NyxMaterialEchir;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

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
	private static ArrayList<IIaSApiDistillable> handlersDistillable;
	private static ArrayList<IIaSApiExaminable> handlersExaminable;
	private static ArrayList<IIaSApiTransmutable> handlersTransmutable;
	private static Item iconery;
	
	public static void init() {
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
		handlersDistillable = new ArrayList<IIaSApiDistillable>();
		handlersExaminable = new ArrayList<IIaSApiExaminable>();
		handlersTransmutable = new ArrayList<IIaSApiTransmutable>();
	}
	
	public static void addToolMaterial(IaSToolMaterial mat) {
		if(toolMaterials.containsKey(mat.getMaterialName()))
			throw new IllegalArgumentException("Material '" + mat.getMaterialName() + "' already exists!");
		toolMaterials.put(mat.getMaterialName(), mat);
	}
	
	public static IaSToolMaterial getToolMaterial(String key) {
		if(key == null)
			return getDefaultMaterial();
		if(toolMaterials.containsKey(key))
			return toolMaterials.get(key);
		return getDefaultMaterial();
	}
	
	public static Collection<IaSToolMaterial> getToolMaterials() {
		return toolMaterials.values();
	}

	public static IaSToolMaterial getDefaultMaterial() {
		return defaultMaterial;
	}
	
	public static void addHandler(IIaSApiDistillable handler) {
		handlersDistillable.add(handler);
	}
	public static void addHandler(IIaSApiExaminable handler) {
		handlersExaminable.add(handler);
	}
	public static void addHandler(IIaSApiTransmutable handler) {
		handlersTransmutable.add(handler);
	}
	
	public static IIaSApiTransmutable getHandlerTransmutation(ItemStack target, ItemStack catalyst, EntityPlayer pl) {
		Object obj;
		IIaSApiTransmutable trans;
		if(target == null || catalyst == null || pl == null)
			return null;
		
		obj = target.getItem();
		if(obj instanceof ItemBlock)
			obj = ((ItemBlock)obj).field_150939_a;
		if(obj instanceof IIaSApiTransmutable) {
			trans = (IIaSApiTransmutable)obj;
			if(trans.canDoTransmutation(target, catalyst, pl))
				return trans;
		}
		
		obj = catalyst.getItem();
		if(obj instanceof ItemBlock)
			obj = ((ItemBlock)obj).field_150939_a;
		if(obj instanceof IIaSApiTransmutable) {
			trans = (IIaSApiTransmutable)obj;
			if(trans.canDoTransmutation(target, catalyst, pl))
				return trans;
		}
		
		for(int i = 0; i < handlersTransmutable.size(); ++i) {
			if(handlersTransmutable.get(i).canDoTransmutation(target, catalyst, pl))
				return handlersTransmutable.get(i);
		}
		return null;
	}
}
