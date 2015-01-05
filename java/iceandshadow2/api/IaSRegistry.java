package iceandshadow2.api;

import iceandshadow2.ias.IaSCreativeTabs;
import iceandshadow2.ias.items.tools.IaSTools;
import iceandshadow2.nyx.items.materials.NyxMaterialEchir;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public final class IaSRegistry {
	
	private static IaSToolMaterial defaultMaterial = new NyxMaterialEchir();
	private static HashMap<String,IaSToolMaterial> toolMaterials = new HashMap<String,IaSToolMaterial>();
	private static ArrayList<IIaSApiDistillable> handlersDistillable = new ArrayList<IIaSApiDistillable>();
	private static ArrayList<IIaSApiExaminable> handlersExaminable = new ArrayList<IIaSApiExaminable>();
	private static ArrayList<IIaSApiTransmutable> handlersTransmutable = new ArrayList<IIaSApiTransmutable>();
	private static ArrayList<IIaSApiSacrificeXp> handlersSacrificeXp = new ArrayList<IIaSApiSacrificeXp>();
	
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
	public static void addHandler(IIaSApiSacrificeXp handler) {
		handlersSacrificeXp.add(handler);
	}
	
	public static IIaSApiTransmutable getHandlerTransmutation(ItemStack target, ItemStack catalyst, EntityPlayer pl) {
		if(target == null || catalyst == null || pl == null)
			return null;
		Object obj;
		IIaSApiTransmutable trans;
		
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
	
	public static IIaSApiDistillable getHandlerDistillation(ItemStack target) {
		if(target == null)
			return null;
		Object obj;
		IIaSApiDistillable dist;
		
		obj = target.getItem();
		if(obj instanceof ItemBlock)
			obj = ((ItemBlock)obj).field_150939_a;
		if(obj instanceof IIaSApiDistillable) {
			dist = (IIaSApiDistillable)obj;
			if(dist.getBaseRate(target) > 0)
				return dist;
		}
		
		for(int i = 0; i < handlersDistillable.size(); ++i) {
			if(handlersDistillable.get(i).getBaseRate(target) > 0)
				return handlersDistillable.get(i);
		}
		return null;
	}
	
	public static int getSacrificeXpYield(ItemStack target) {
		if(target == null)
			return 0;
		Object obj;
		IIaSApiSacrificeXp sac;
		Random r = new Random();
		
		obj = target.getItem();
		if(obj instanceof ItemBlock)
			obj = ((ItemBlock)obj).field_150939_a;
		if(obj instanceof IIaSApiSacrificeXp) {
			sac = (IIaSApiSacrificeXp)obj;
			return Math.max(0,sac.getXpValue(target, r));
		}
		
		int sum = 0;
		for(IIaSApiSacrificeXp xp : handlersSacrificeXp) {
			sum = xp.getXpValue(target, r);
			if(sum > 0)
				return sum;
			else if (sum < 0)
				sum = 0;
		}
		return 0;
	}
}
