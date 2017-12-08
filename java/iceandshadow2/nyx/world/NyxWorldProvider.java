package iceandshadow2.nyx.world;

import iceandshadow2.IaSFlags;
import iceandshadow2.nyx.world.gen.ruins.GenRuinsCentral;
import iceandshadow2.util.IaSBlockHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraftforge.client.IRenderHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NyxWorldProvider extends WorldProvider {

	public static final Vec3 NYX_COLOR = Vec3.createVectorHelper(0.0, 0.02F, 0.05F);

	public NyxWorldProvider() {
		this.isHellWorld = false;
		this.hasNoSky = true;
		registerWorldChunkManager();
	}

	@Override
	public float calculateCelestialAngle(long par1, float par3) {
		return 0.5F;
	}

	@Override
	public void calculateInitialWeather() {
		this.worldObj.getWorldInfo().setRaining(false);
		this.worldObj.getWorldInfo().setThundering(false);
	}

	@Override
	public boolean canBlockFreeze(int x, int y, int z, boolean byWater) {
		return true;
	}

	@Override
	public boolean canDoLightning(Chunk chunk) {
		return true;
	}

	@Override
	public boolean canDoRainSnowIce(Chunk chunk) {
		return false;
	}

	@Override
	public boolean canRespawnHere() {
		return true;
	}

	@Override
	public IChunkProvider createChunkGenerator() {
		return new NyxChunkProvider(this.worldObj, this.worldObj.getSeed(), true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean doesXZShowFog(int par1, int par2) {
		return true;
	}

	@Override
	protected void generateLightBrightnessTable() {
		final float f = 0.005F;
		for (int i = 0; i <= 15; ++i) {
			final float var3 = 1.0F - i / 15.0F;
			this.lightBrightnessTable[i] = (1.0F - var3) / (var3 * 2.0F + 2.0F) * (1.0F - f) + f;
		}
	}

	@Override
	public BiomeGenBase getBiomeGenForCoords(int x, int z) {
		return this.worldChunkMgr.getBiomeGenAt(x, z);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getCloudHeight() {
		return 192;
	}

	@Override
	public String getDepartMessage() {
		return "Escaping Nyx...";
	}

	@Override
	public String getDimensionName() {
		return "Nyx";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Vec3 getFogColor(float par1, float par2) {
		return NyxWorldProvider.NYX_COLOR;
	}

	@Override
	public int getMoonPhase(long par1) {
		return 4;
	}

	@Override
	public double getMovementFactor() {
		return 3.0;
	}

	@Override
	public String getSaveFolder() {
		return "DIM-IaS-nyx";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Vec3 getSkyColor(Entity cameraEntity, float partialTicks) {
		return NyxWorldProvider.NYX_COLOR;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IRenderHandler getSkyRenderer() {
		return super.getSkyRenderer();
	}

	@Override
	public ChunkCoordinates getSpawnPoint() {
		return new ChunkCoordinates(0, GenRuinsCentral.getGenHeight(this.worldObj, 0, 0), 0);
	}

	@Override
	public String getWelcomeMessage() {
		return "Entering Nyx...";
	}

	@Override
	public boolean isDaytime() {
		return false;
	}

	@Override
	public boolean isSurfaceWorld() {
		return true;
	}

	@Override
	public void registerWorldChunkManager() {
		final BiomeGenBase[] nyxBiomes = { NyxBiomes.nyxHighMountains, NyxBiomes.nyxLowMountains, NyxBiomes.nyxMesas,
				NyxBiomes.nyxHills, NyxBiomes.nyxMesaForest, NyxBiomes.nyxHillForest, NyxBiomes.nyxRugged,
				NyxBiomes.nyxInfested };
		GenLayer biomesGenLayer = new GenLayerNyxRandomBiomes(nyxBiomes, 200L);
		biomesGenLayer = GenLayerZoom.magnify(1000L, biomesGenLayer, 2);
		GenLayer biomesIndexLayer = new GenLayerVoronoiZoom(10L, biomesGenLayer);
		biomesIndexLayer = GenLayerZoom.magnify(1000L, biomesIndexLayer, 1);

		this.worldChunkMgr = new NyxChunkManager(nyxBiomes, biomesGenLayer, biomesIndexLayer, this.worldObj);
		this.dimensionId = IaSFlags.dim_nyx_id;
		this.hasNoSky = true;
	}

	@Override
	public void updateWeather() {
	}
}
