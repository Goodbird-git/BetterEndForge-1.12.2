package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.client.audio.BetterEndMusicNames;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.world.moderngen.decorator.Decoration;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;

public class LanternWoodsBiome extends BetterEndBiome {
	public LanternWoodsBiome() {
		super(new BiomeTemplate("lantern_woods")
				.setFogColor(189, 82, 70)
				.setFogDensity(1.1F)
				.setWaterFogColor(171, 234, 226)
				.setWaterColor(171, 234, 226)
				.setFoliageColor(254, 85, 57)
				.setSurface(ModBlocks.RUTISCUS)
				.setMusic(BetterEndMusicNames.MUSIC_FOREST)
				//.setParticles(ModParticleTypes.GLOWING_SPHERE, 0.001F)
				.addFeature(Decoration.LAKES, ModConfiguredFeatures.END_LAKE_NORMAL)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.FLAMAEA)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.LUCERNIA)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.LUCERNIA_BUSH)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.FILALUX)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.AERIDIUM)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.LAMELLARIUM)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.BOLUX_MUSHROOM)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.AURANT_POLYPORE)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.POND_ANEMONE)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_ORANGE)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_RED)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.RUSCUS)
				.addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.RUSCUS_WOOD)
				//.addStructure(StructureFeatures.END_CITY)
				.addMobSpawn(EnumCreatureType.MONSTER, EntityEnderman.class, 50, 1, 2));
	}
}
