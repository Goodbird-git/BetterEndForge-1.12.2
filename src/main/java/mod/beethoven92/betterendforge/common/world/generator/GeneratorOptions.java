package mod.beethoven92.betterendforge.common.world.generator;

import mod.beethoven92.betterendforge.config.Configs;
import mod.beethoven92.betterendforge.config.jsons.JsonConfigs;
//import net.minecraft.core.BlockPos;
//import net.minecraft.util.Mth;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
//import ru.betterend.config.Configs;

public class GeneratorOptions {
	public static boolean vanillaEndIntegration;
	private static int biomeSizeLand;
	private static int biomeSizeVoid;
	private static int biomeSizeCaves;
	private static boolean hasPortal;
	private static boolean hasPillars = true;
	private static boolean hasDragonFights = true;
	private static boolean swapOverworldToEnd = false;
	private static boolean changeChorusPlant = true;
	private static boolean removeChorusFromVanillaBiomes = false;
	private static boolean newGenerator = true;
	private static boolean noRingVoid = false;
	private static boolean generateCentralIsland = true;
	private static boolean generateObsidianPlatform = true;
	private static int endCityFailChance = 5;
	public static LayerOptions bigOptions;
	public static LayerOptions mediumOptions;
	public static LayerOptions smallOptions;
	private static boolean changeSpawn;
	private static BlockPos spawn;
	private static BlockPos portal = BlockPos.ORIGIN;
	private static boolean replacePortal;
	private static boolean replacePillars;
	private static long islandDistBlock;
	private static int islandDistChunk;
	private static boolean directSpikeHeight;
	private static boolean generateOuterLeaves = true;

	public static void init() {

//		biomeSizeLand = Configs.GENERATOR_CONFIG.getInt("biomeMap", "biomeSizeLand", 256);
//		biomeSizeVoid = Configs.GENERATOR_CONFIG.getInt("biomeMap", "biomeSizeVoid", 256);
//		biomeSizeCaves = Configs.GENERATOR_CONFIG.getInt("biomeMap", "biomeSizeCaves", 32);
//		hasPortal = Configs.GENERATOR_CONFIG.getBoolean("portal", "hasPortal", true);
//		hasPillars = Configs.GENERATOR_CONFIG.getBoolean("spikes", "hasSpikes", true);
//		hasDragonFights = Configs.GENERATOR_CONFIG.getBooleanRoot("hasDragonFights", true);
//		swapOverworldToEnd = Configs.GENERATOR_CONFIG.getBooleanRoot("swapOverworldToEnd", false	);
//		changeChorusPlant = Configs.GENERATOR_CONFIG.getBoolean("chorusPlant", "changeChorusPlant", true);
//		removeChorusFromVanillaBiomes = Configs.GENERATOR_CONFIG.getBoolean(
//			"chorusPlant",
//			"removeChorusFromVanillaBiomes",
//			false
//		);
//		vanillaEndIntegration = Configs.GENERATOR_CONFIG.getBoolean("customGenerator", "vanillaEndIntegration", true);
//		newGenerator = Configs.GENERATOR_CONFIG.getBoolean("customGenerator", "useNewGenerator", true);
//		noRingVoid = Configs.GENERATOR_CONFIG.getBoolean("customGenerator", "noRingVoid", false);
//		generateCentralIsland = Configs.GENERATOR_CONFIG.getBoolean("customGenerator", "generateCentralIsland", true);
//		endCityFailChance = Configs.GENERATOR_CONFIG.getInt("customGenerator", "endCityFailChance", 5);
//		generateObsidianPlatform = Configs.GENERATOR_CONFIG.getBooleanRoot("generateObsidianPlatform", true);
//		bigOptions = new LayerOptions(
//			"customGenerator.layers.bigIslands",
//				Configs.GENERATOR_CONFIG,
//			300,
//			200,
//			70,
//			10,
//			false
//		);
//		mediumOptions = new LayerOptions(
//			"customGenerator.layers.mediumIslands",
//				Configs.GENERATOR_CONFIG,
//			150,
//			100,
//			70,
//			20,
//			true
//		);
//		smallOptions = new LayerOptions(
//			"customGenerator.layers.smallIslands",
//				Configs.GENERATOR_CONFIG,
//			60,
//			50,
//			70,
//			30,
//			false
//		);
//		changeSpawn = Configs.GENERATOR_CONFIG.getBoolean("spawn", "changeSpawn", false);
//		spawn = new BlockPos(
//			Configs.GENERATOR_CONFIG.getInt("spawn.point", "x", 20),
//			Configs.GENERATOR_CONFIG.getInt("spawn.point", "y", 65),
//			Configs.GENERATOR_CONFIG.getInt("spawn.point", "z", 0)
//		);
//		replacePortal = Configs.GENERATOR_CONFIG.getBoolean("portal", "customEndPortal", true);
		replacePillars = true;//Configs.GENERATOR_CONFIG.getBoolean("spikes", "customObsidianSpikes", true);
		int circleRadius = 1000;//Configs.GENERATOR_CONFIG.getInt("customGenerator", "voidRingSize", 1000);
		islandDistBlock = (long) circleRadius * (long) circleRadius;
		islandDistChunk = (circleRadius >> 3); // Twice bigger than normal
		generateOuterLeaves = Configs.GENERATOR_CONFIG.getBoolean("customGenerator", "generateOuterLeaves", true);
	}

//	public static int getBiomeSizeLand() {
//		return MathHelper.clamp(biomeSizeLand, 1, 8192);
//	}
//
//	public static int getBiomeSizeVoid() {
//		return MathHelper.clamp(biomeSizeVoid, 1, 8192);
//	}
//
//	public static int getBiomeSizeCaves() {
//		return MathHelper.clamp(biomeSizeCaves, 1, 8192);
//	}
//
//	public static boolean hasPortal() {
//		return hasPortal;
//	}

	public static boolean hasPillars() {
		return hasPillars;
	}

	public static boolean hasDragonFights() {
		return hasDragonFights;
	}

	public static boolean swapOverworldToEnd() {
		return swapOverworldToEnd;
	}

	public static boolean changeChorusPlant() {
		return changeChorusPlant;
	}
//
//	public static boolean removeChorusFromVanillaBiomes() {
//		return removeChorusFromVanillaBiomes;
//	}

	public static boolean noRingVoid() {
		return noRingVoid;
	}

	public static boolean useNewGenerator() {
		return true;
	}

	public static boolean hasCentralIsland() {
		return true;
	}

	public static boolean generateObsidianPlatform() {
		return generateObsidianPlatform;
	}

	public static int getEndCityFailChance() {
		return endCityFailChance;
	}

	public static boolean changeSpawn() {
		return changeSpawn;
	}

	public static BlockPos getSpawn() {
		return spawn;
	}

	public static BlockPos getPortalPos() {
		return portal;
	}

	public static void setPortalPos(BlockPos portal) {
		GeneratorOptions.portal = portal;
	}

	public static boolean replacePortal() {
		return replacePortal;
	}

	public static boolean replacePillars() {
		return replacePillars;
	}

	public static long getIslandDistBlock() {
		return islandDistBlock;
	}

	public static int getIslandDistChunk() {
		return islandDistChunk;
	}

	public static void setDirectSpikeHeight() {
		directSpikeHeight = true;
	}

	public static boolean isVanillaEndIntegrationEnabled()
	{
		return vanillaEndIntegration;
	}

	public static boolean isDirectSpikeHeight() {
		boolean height = directSpikeHeight;
		directSpikeHeight = false;
		return height;
	}

	public static boolean getGenerateOuterLeaves(){
		return generateOuterLeaves;
	}
}
