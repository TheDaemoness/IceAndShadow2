package iceandshadow2.api;

import iceandshadow2.IceAndShadow2;
import iceandshadow2.ias.items.tools.IaSItemEchirArmorActive;
import iceandshadow2.ias.items.tools.IaSItemEchirKnifeActive;
import iceandshadow2.ias.items.tools.IaSItemEchirToolActive;
import iceandshadow2.ias.items.tools.IaSItemToolBroken;
import iceandshadow2.nyx.items.tools.NyxItemBow;
import iceandshadow2.nyx.items.tools.NyxItemFlask;
import iceandshadow2.nyx.items.tools.NyxItemSwordFrost;
import iceandshadow2.nyx.toolmats.NyxMaterialEchir;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.BlockObsidian;
import net.minecraft.block.BlockOre;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public final class IaSRegistry {

	private static IaSToolMaterial defaultMaterial = new NyxMaterialEchir();
	private static HashMap<String, IaSToolMaterial> toolMaterials = new HashMap<String, IaSToolMaterial>();
	private static ArrayList<IIaSApiExaminable> handlersExaminable = new ArrayList<IIaSApiExaminable>();
	private static ArrayList<IIaSApiTransmute> handlersTransmutable = new ArrayList<IIaSApiTransmute>();
	private static ArrayList<IIaSApiSacrificeXp> handlersSacrificeXp = new ArrayList<IIaSApiSacrificeXp>();
	private static HashSet<Class> transfusionTargetFirstClasses = new HashSet<Class>();

	public static void add(Object o) {
		if (IceAndShadow2.isRegistrationPublic())
			IaSRegistry.doAdd(o);
	}

	private static void addHandler(IIaSApiExaminable handler) {
		IaSRegistry.handlersExaminable.add(handler);
	}

	private static void addHandler(IIaSApiSacrificeXp handler) {
		IaSRegistry.handlersSacrificeXp.add(handler);
	}

	private static void addHandler(IIaSApiTransmute handler) {
		IaSRegistry.handlersTransmutable.add(handler);
	}

	protected static void addToolMaterial(IaSToolMaterial mat) {
		if (IaSRegistry.toolMaterials.containsKey(mat.getMaterialName()))
			throw new IllegalArgumentException("Material '" + mat.getMaterialName() + "' already exists!");
		IaSRegistry.toolMaterials.put(mat.getMaterialName(), mat);
	}

	private static void doAdd(Object o) {
		if (o instanceof IIaSApiExaminable)
			IaSRegistry.addHandler((IIaSApiExaminable) o);
		if (o instanceof IIaSApiSacrificeXp)
			IaSRegistry.addHandler((IIaSApiSacrificeXp) o);
		if (o instanceof IIaSApiTransmute)
			IaSRegistry.addHandler((IIaSApiTransmute) o);
		if (o instanceof IaSToolMaterial)
			IaSRegistry.addToolMaterial((IaSToolMaterial) o);
	}

	public static IaSToolMaterial getDefaultMaterial() {
		return IaSRegistry.defaultMaterial;
	}
	
	public static boolean isPrimarilyTransfusionTarget(ItemStack is) {
		if(is == null)
			return false;
		final Object item = is.getItem() instanceof ItemBlock?
				((ItemBlock)is.getItem()).field_150939_a:
					is.getItem();
		if(transfusionTargetFirstClasses.contains(item.getClass()))
			return true;
		for(Class c : transfusionTargetFirstClasses) {
			if(c.isInstance(item))
				return true;
		}
		return false;
	}
	
	public static void setPrimarilyTransfusionTarget(Class c) {
		transfusionTargetFirstClasses.add(c);
	}

	public static IIaSApiTransmute getHandlerTransmutation(ItemStack target, ItemStack catalyst) {
		if (target == null || catalyst == null)
			return null;
		Object obj;
		IIaSApiTransmute trans;

		obj = target.getItem();
		if (obj instanceof ItemBlock)
			obj = ((ItemBlock) obj).field_150939_a;
		if (obj instanceof IIaSApiTransmute) {
			trans = (IIaSApiTransmute) obj;
			if (trans.getTransmuteTime(target, catalyst) > 0)
				return trans;
		}

		obj = catalyst.getItem();
		if (obj instanceof ItemBlock)
			obj = ((ItemBlock) obj).field_150939_a;
		if (obj instanceof IIaSApiTransmute) {
			trans = (IIaSApiTransmute) obj;
			if (trans.getTransmuteTime(target, catalyst) > 0)
				return trans;
		}

		for (int i = 0; i < IaSRegistry.handlersTransmutable.size(); ++i)
			if (IaSRegistry.handlersTransmutable.get(i).getTransmuteTime(target, catalyst) > 0)
				return IaSRegistry.handlersTransmutable.get(i);
		return null;
	}

	public static float getSacrificeXpYield(ItemStack target) {
		if (target == null)
			return 0;
		Object obj;
		IIaSApiSacrificeXp sac;
		final Random r = new Random();

		obj = target.getItem();
		if (obj instanceof ItemBlock)
			obj = ((ItemBlock) obj).field_150939_a;
		if (obj instanceof IIaSApiSacrificeXp) {
			sac = (IIaSApiSacrificeXp) obj;
			return Math.max(0, sac.getXpValue(target, r));
		}

		float sum = 0;
		for (final IIaSApiSacrificeXp xp : IaSRegistry.handlersSacrificeXp) {
			sum = xp.getXpValue(target, r);
			if (sum > 0)
				return sum;
		}
		return 0;
	}

	public static IaSToolMaterial getToolMaterial(String key) {
		if (key == null)
			return IaSRegistry.getDefaultMaterial();
		if (IaSRegistry.toolMaterials.containsKey(key))
			return IaSRegistry.toolMaterials.get(key);
		return IaSRegistry.getDefaultMaterial();
	}

	public static Collection<IaSToolMaterial> getToolMaterials() {
		return IaSRegistry.toolMaterials.values();
	}

	public static IaSToolMaterial getTransmutationMaterial(ItemStack catalyst) {
		for (final IaSToolMaterial mat : IaSRegistry.toolMaterials.values())
			if (mat.getTransmutationCatalyst().isItemEqual(catalyst))
				return mat;
		return null;
	}

	public static Map<String, Integer> handleExamination(EntityPlayer checker, Map<String, Integer> knowledge) {
		return null;
	}

	public static Map<String, Integer> handleExaminationBook(EntityPlayer checker, int x, int y, int z,
			Map<String, Integer> knowledge) {
		return null;
	}

	public static void postInit() {
		for (final Object o : IceAndShadow2.getPostRegistrationHandlers())
			IaSRegistry.doAdd(o);
	}

	public static void preInit() {
		for (final Object o : IceAndShadow2.getPreRegistrationHandlers())
			IaSRegistry.doAdd(o);
		setPrimarilyTransfusionTarget(ItemArmor.class);
		setPrimarilyTransfusionTarget(ItemTool.class);
		setPrimarilyTransfusionTarget(ItemSword.class);
		setPrimarilyTransfusionTarget(ItemHoe.class);
		setPrimarilyTransfusionTarget(ItemShears.class);
		setPrimarilyTransfusionTarget(ItemFlintAndSteel.class);
		setPrimarilyTransfusionTarget(ItemBow.class);
		setPrimarilyTransfusionTarget(ItemFishingRod.class);
		setPrimarilyTransfusionTarget(IaSItemEchirArmorActive.class);
		setPrimarilyTransfusionTarget(IaSItemEchirKnifeActive.class);
		setPrimarilyTransfusionTarget(IaSItemEchirToolActive.class);
		setPrimarilyTransfusionTarget(IaSItemToolBroken.class);
		setPrimarilyTransfusionTarget(NyxItemBow.class);
		setPrimarilyTransfusionTarget(NyxItemFlask.class);
		setPrimarilyTransfusionTarget(NyxItemSwordFrost.class);
	}
}
