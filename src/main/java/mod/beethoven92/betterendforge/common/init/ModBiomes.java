package mod.beethoven92.betterendforge.common.init;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import mod.beethoven92.betterendforge.common.util.JsonFactory;
import mod.beethoven92.betterendforge.common.world.biome.*;
import mod.beethoven92.betterendforge.common.world.biome.cave.*;
import mod.beethoven92.betterendforge.common.world.generator.BiomePicker;
import mod.beethoven92.betterendforge.common.world.generator.EndBiomeType;
import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import mod.beethoven92.betterendforge.config.Configs;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.io.InputStream;
import java.util.*;

public class ModBiomes 
{
	private static final HashMap<ResourceLocation, BetterEndBiome> ID_MAP = Maps.newHashMap();
	private static final HashMap<Biome, BetterEndBiome> CLIENT = Maps.newHashMap();
	private static final Set<ResourceLocation> SUBBIOMES_UNMUTABLES = Sets.newHashSet();
	
	public static final BiomePicker LAND_BIOMES = new BiomePicker();
	public static final BiomePicker VOID_BIOMES = new BiomePicker();
	public static final BiomePicker CAVE_BIOMES = new BiomePicker();
	public static final List<BetterEndBiome> SUBBIOMES = Lists.newArrayList();
	private static final JsonObject EMPTY_JSON = new JsonObject();
	
	private static IForgeRegistry<Biome> biomeRegistry;
	
	// Vanilla Land
	public static final BetterEndBiome END_LAND = registerBiome(Biomes.SKY, EndBiomeType.LAND, 1F);
	public static final BetterEndBiome END_VOID = registerBiome(Biomes.SKY, EndBiomeType.VOID, 1F);
	
	// Better End Land biomes
	public static final BetterEndBiome MEGALAKE = registerBiome(new MegalakeBiome(), EndBiomeType.LAND);
	public static final BetterEndBiome CRYSTAL_MOUNTAINS = registerBiome(new CrystalMountainsBiome(), EndBiomeType.LAND);
	public static final BetterEndBiome FOGGY_MUSHROOMLAND = registerBiome(new FoggyMushroomlandBiome(), EndBiomeType.LAND);
	public static final BetterEndBiome MEGALAKE_GROVE = registerSubBiome(new MegalakeGroveBiome(), MEGALAKE);
	public static final BetterEndBiome DUST_WASTELANDS = registerBiome(new DustWastelandsBiome(), EndBiomeType.LAND);
	public static final BetterEndBiome PAINTED_MOUNTAINS = registerSubBiome(new PaintedMountainsBiome(), DUST_WASTELANDS);
	public static final BetterEndBiome CHORUS_FOREST = registerBiome(new ChorusForestBiome(), EndBiomeType.LAND);
	public static final BetterEndBiome SHADOW_FOREST = registerBiome(new ShadowForestBiome(), EndBiomeType.LAND);
	public static final BetterEndBiome BLOSSOMING_SPIRES = registerBiome(new BlossomingSpiresBiome(), EndBiomeType.LAND);
	public static final BetterEndBiome SULPHUR_SPRINGS = registerBiome(new SulphurSpringsBiome(), EndBiomeType.LAND);
	public static final BetterEndBiome AMBER_LAND = registerBiome(new AmberLandBiome(), EndBiomeType.LAND);
	public static final BetterEndBiome UMBRELLA_JUNGLE = registerBiome(new UmbrellaJungleBiome(), EndBiomeType.LAND);
	public static final BetterEndBiome GLOWING_GRASSLANDS = registerBiome(new GlowingGrasslandsBiome(), EndBiomeType.LAND);
	public static final BetterEndBiome DRAGON_GRAVEYARDS = registerBiome(new DragonGraveyardsBiome(), EndBiomeType.LAND);
	public static final BetterEndBiome LANTERN_WOODS = registerBiome(new LanternWoodsBiome(), EndBiomeType.LAND);
	public static final BetterEndBiome DRY_SHRUBLAND = registerBiome(new DryShrublandBiome(), EndBiomeType.LAND);
	public static final BetterEndBiome NEON_OASIS = registerSubBiome(new NeonOasisBiome(), DUST_WASTELANDS);
	public static final BetterEndBiome UMBRA_VALLEY = registerBiome(new UmbraValleyBiome(), EndBiomeType.LAND);

	// Better End void biomes
	public static final BetterEndBiome ICE_STARFIELD = registerBiome(new IceStarfieldBiome(), EndBiomeType.VOID);
	
	// Better End Cave biomes
	public static final BetterEndCaveBiome EMPTY_END_CAVE = registerCaveBiome(new EmptyEndCaveBiome());
	public static final BetterEndCaveBiome EMPTY_SMARAGDANT_CAVE = registerCaveBiome(new EmptySmaragdantCaveBiome());
	public static final BetterEndCaveBiome LUSH_SMARAGDANT_CAVE = registerCaveBiome(new LushSmaragdantCaveBiome());
	public static final BetterEndCaveBiome EMPTY_AURORA_CAVE = registerCaveBiome(new EmptyAuroraCaveBiome());
	public static final BetterEndCaveBiome LUSH_AURORA_CAVE = registerCaveBiome(new LushAuroraCaveBiome());
	public static final BetterEndCaveBiome END_CAVE = registerCaveBiome(new EndCaveBiome());
	public static final BetterEndCaveBiome JADE_CAVE = registerCaveBiome(new JadeCaveBiome());

	public static void register() {}

	public static void onWorldLoad(IForgeRegistry<Biome> registry) {
		CAVE_BIOMES.getBiomes().forEach(biome -> biome.updateActualBiomes(registry));
		CAVE_BIOMES.rebuild();
	}
	
	public static void mutateRegistry(IForgeRegistry<Biome> biomeRegistry)
	{
		ModBiomes.biomeRegistry = biomeRegistry;
		
		LAND_BIOMES.clearMutables();
		VOID_BIOMES.clearMutables();
		CAVE_BIOMES.clearMutables();
		
		Map<String, JsonObject> configs = Maps.newHashMap();
		
		biomeRegistry.forEach((biome) -> {
			if (biome == Biomes.SKY)
			{
				ResourceLocation id = biomeRegistry.getKey(biome);

				Configs.BIOME_CONFIG.getBoolean(id, "fogEnabled", true);
				if (Configs.BIOME_CONFIG.getBoolean(id, "enabled", true))
				{
					if (!LAND_BIOMES.containsImmutable(id) && !VOID_BIOMES.containsImmutable(id) && !SUBBIOMES_UNMUTABLES.contains(id)) 
					{
						JsonObject config = configs.get(id.getNamespace());
						if (config == null) 
						{
							config = loadJsonConfig(id.getNamespace());
							configs.put(id.getNamespace(), config);
						}
						
						float fog = 1F;
						float chance = 1F;
					    boolean isVoid = false;
					    boolean hasCaves = true;
					    JsonElement element = config.get(id.getPath());
					    if (element != null && element.isJsonObject()) 
					    {
					    	fog = JsonFactory.getFloat(element.getAsJsonObject(), "fog_density", 1);
						    chance = JsonFactory.getFloat(element.getAsJsonObject(), "generation_chance", 1);
						    isVoid = JsonFactory.getString(element.getAsJsonObject(), "type", "land").equals("void");
						    hasCaves = JsonFactory.getBoolean(element.getAsJsonObject(), "has_caves", true);
					    }
					    
					    BetterEndBiome endBiome = new BetterEndBiome(id, biome, fog, chance, hasCaves);
					    if (isVoid) 
					    {
					    	VOID_BIOMES.addBiomeMutable(endBiome);
					    }
					    else 
					    {
					     	LAND_BIOMES.addBiomeMutable(endBiome);
					    }
					    ID_MAP.put(id, endBiome);
				    }
				}
			}
		});
		Biomes.SKY.topBlock = Blocks.END_STONE.getDefaultState();

		Configs.BIOME_CONFIG.saveChanges();
		
		rebuildPicker(LAND_BIOMES, biomeRegistry);
		rebuildPicker(VOID_BIOMES, biomeRegistry);
		rebuildPicker(CAVE_BIOMES, biomeRegistry);
		
		SUBBIOMES.forEach((endBiome) -> {
			endBiome.updateActualBiomes(biomeRegistry);
		});
		
		CLIENT.clear();
	}
	
	private static void rebuildPicker(BiomePicker picker, IForgeRegistry<Biome> biomeRegistry)
	{
		picker.rebuild();
		picker.getBiomes().forEach((endBiome) -> {
			endBiome.updateActualBiomes(biomeRegistry);
		});
	}
	
	private static JsonObject loadJsonConfig(String namespace) 
	{
		InputStream inputstream = ModBiomes.class.getResourceAsStream("/data/" + namespace + "/end_biome_properties.json");
		if (inputstream != null) 
		{
			return JsonFactory.getJsonObject(inputstream);
		}
		else 
		{
			return EMPTY_JSON;
		}
	}
	
	public static void initRegistry(MinecraftServer server) 
	{
		if (biomeRegistry == null) 
		{
			biomeRegistry = ForgeRegistries.BIOMES;
		}
	}
	
	// REGISTER A BETTER END BIOME FROM AN EXISTING BIOME
	public static BetterEndBiome registerBiome(Biome biome, EndBiomeType type, float genChance) 
	{
		return registerBiome(biome, type, 1, genChance);
	}
	
	public static BetterEndBiome registerBiome(Biome biome, EndBiomeType type, float fogDensity, float genChance) 
	{
		BetterEndBiome endBiome = new BetterEndBiome(ForgeRegistries.BIOMES.getKey(biome), biome, fogDensity, genChance, true);
		if (Configs.BIOME_CONFIG.getBoolean(endBiome.getID(), "enabled", true))
		{
			addToPicker(endBiome, type);
		}
		return endBiome;
	}
	
	// REGISTER A BETTER END BIOME FROM AN EXISTING BIOME AND SET IT AS SUB BIOME FOR THE SELECTED PARENT
	public static BetterEndBiome registerSubBiome(Biome biome, BetterEndBiome parent, float genChance, boolean hasCaves) 
	{
		return registerSubBiome(biome, parent, 1, genChance, hasCaves);
	}
	
	public static BetterEndBiome registerSubBiome(Biome biome, BetterEndBiome parent, float fogDensity, float genChance, boolean hasCaves) 
	{
		BetterEndBiome endBiome = new BetterEndBiome(ForgeRegistries.BIOMES.getKey(biome), biome, fogDensity, genChance, hasCaves);
		if (Configs.BIOME_CONFIG.getBoolean(endBiome.getID(), "enabled", true))
		{
			parent.addSubBiome(endBiome);
		    SUBBIOMES.add(endBiome);
		    SUBBIOMES_UNMUTABLES.add(endBiome.getID());
		    ID_MAP.put(endBiome.getID(), endBiome);
		}
		return endBiome;
	}

	// REGISTER A NEW BETTER END BIOME
	public static BetterEndBiome registerBiome(BetterEndBiome biome, EndBiomeType type) 
	{
		registerBiomeDirect(biome);
		if (Configs.BIOME_CONFIG.getBoolean(biome.getID(), "enabled", true))
		{
			addToPicker(biome, type);
			ID_MAP.put(biome.getID(), biome);
		}
		return biome;
	}
	
	public static BetterEndBiome registerSubBiome(BetterEndBiome biome, BetterEndBiome parent) 
	{
		registerBiomeDirect(biome);
		if (Configs.BIOME_CONFIG.getBoolean(biome.getID(), "enabled", true))
		{
			parent.addSubBiome(biome);
			SUBBIOMES.add(biome);
			SUBBIOMES_UNMUTABLES.add(biome.getID());
			ID_MAP.put(biome.getID(), biome);
		}
		return biome;
	}
	
	private static BetterEndBiome registerBiome(ResourceLocation key, EndBiomeType type, float genChance)
	{
		return registerBiome(ForgeRegistries.BIOMES.getValue(key), type, genChance);
	}
	
	private static BetterEndBiome registerSubBiome(ResourceLocation key, BetterEndBiome parent, float genChance)
	{
		return registerSubBiome(ForgeRegistries.BIOMES.getValue(key), parent, genChance, true);
	}
	
	private static void addToPicker(BetterEndBiome biome, EndBiomeType type) 
	{
		if (type == EndBiomeType.LAND)
		{
			LAND_BIOMES.addBiome(biome);
		}
		else
		{
			VOID_BIOMES.addBiome(biome);
		}
	}
	
	private static void registerBiomeDirect(BetterEndBiome biome) 
	{
		if (Configs.BIOME_CONFIG.getBoolean(biome.getID(), "enabled", true))
		{
			biome.getBiome().setRegistryName(biome.getID()); 
		}
	}
	
	public static BetterEndBiome getFromBiome(Biome biome) 
	{
		return ID_MAP.getOrDefault(biomeRegistry.getKey(biome), END_VOID);
	}
	
	public static ResourceLocation getBiomeID(Biome biome) 
	{
		ResourceLocation id = biomeRegistry.getKey(biome);
		return id == null ? END_VOID.getID() : id;
	}

	public static BetterEndBiome getBiome(ResourceLocation biomeID) 
	{		
		return ID_MAP.getOrDefault(biomeID, END_VOID);
	}
	
	@SideOnly(Side.CLIENT)
	public static BetterEndBiome getRenderBiome(Biome biome) 
	{
		BetterEndBiome endBiome = CLIENT.get(biome);
		if (endBiome == null) 
		{
			ResourceLocation id = ForgeRegistries.BIOMES.getKey(biome);
			endBiome = id == null ? END_VOID : ID_MAP.getOrDefault(id, END_VOID);
			CLIENT.put(biome, endBiome);
		}
		return endBiome;
	}
	
	public static List<BetterEndBiome> getModBiomes() 
	{
		List<BetterEndBiome> result = Lists.newArrayList();
		result.addAll(LAND_BIOMES.getBiomes());
		result.addAll(VOID_BIOMES.getBiomes());
		result.addAll(CAVE_BIOMES.getBiomes());
		result.addAll(SUBBIOMES);
		return result;
	}
	
	public static BetterEndCaveBiome registerCaveBiome(BetterEndCaveBiome biome) 
	{
		registerBiomeDirect(biome);
		if (Configs.BIOME_CONFIG.getBoolean(biome.getID(), "enabled", true))
		{
			CAVE_BIOMES.addBiome(biome);
			ID_MAP.put(biome.getID(), biome);
		}
		return biome;
	}

	public static BetterEndCaveBiome getCaveBiome(Random random)
	{
		if (!CAVE_BIOMES.isRebuilt())
			mutateRegistry(biomeRegistry);
		return (BetterEndCaveBiome)CAVE_BIOMES.getBiome(random);
	}

	public static boolean hasBiome(ResourceLocation biomeID) 
	{
		return ID_MAP.containsKey(biomeID);
	}
}
