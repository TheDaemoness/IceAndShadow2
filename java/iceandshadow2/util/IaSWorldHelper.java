package iceandshadow2.util;

import net.minecraft.world.World;

public class IaSWorldHelper {

	public static int getDifficulty(World w) {
		return w.difficultySetting.getDifficultyId();
	}
}
