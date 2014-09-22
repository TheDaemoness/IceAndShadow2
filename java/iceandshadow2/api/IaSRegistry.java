package iceandshadow2.api;

import java.util.HashMap;

public final class IaSRegistry {
	private static HashMap<String,IaSToolMaterial> toolMaterials = new HashMap<String,IaSToolMaterial>();
	
	public static void registerToolMaterial(String key, IaSToolMaterial mat) {
		if(toolMaterials.containsKey(key))
			throw new IllegalArgumentException("Material " + key + " already exists!");
		toolMaterials.put(key, mat);
	}
}
