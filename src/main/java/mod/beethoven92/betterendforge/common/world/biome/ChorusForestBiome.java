package mod.beethoven92.betterendforge.common.world.biome;

import mod.beethoven92.betterendforge.client.audio.BetterEndMusicNames;
import mod.beethoven92.betterendforge.common.entity.EndSlimeEntity;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import mod.beethoven92.betterendforge.common.init.ModConfiguredFeatures;
import mod.beethoven92.betterendforge.common.init.ModSoundEvents;
import mod.beethoven92.betterendforge.common.world.moderngen.decorator.Decoration;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;

public class ChorusForestBiome extends BetterEndBiome
{

	public ChorusForestBiome() 
	{
		super(new BiomeTemplate("chorus_forest").
				setFogColor(87, 26, 87).
				setFogDensity(1.5F).
				setGrassColor(122, 45, 122).
				setFoliageColor(122, 45, 122).
				setWaterColor(73, 30, 73).
				setWaterFogColor(73, 30, 73).
				setSurface(ModBlocks.CHORUS_NYLIUM).
				//setParticles(ParticleTypes.PORTAL, 0.01F).
				setAmbientSound(ModSoundEvents.AMBIENT_CHORUS_FOREST).
				setMusic(BetterEndMusicNames.MUSIC_DARK).
				addFeature(Decoration.UNDERGROUND_ORES, ModConfiguredFeatures.VIOLECITE_LAYER).
				addFeature(Decoration.LAKES, ModConfiguredFeatures.END_LAKE_RARE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.PYTHADENDRON).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.PYTHADENDRON_BUSH).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHORUS_PLANT).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHORUS_MUSHROOM).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHORUS_GRASS).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.PURPLE_POLYPORE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.TAIL_MOSS).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.TAIL_MOSS_WOOD).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_PURPLE).
				addFeature(Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.CHARNIA_RED_RARE).
				//addStructure(StructureFeatures.END_CITY).
				addMobSpawn(EnumCreatureType.MONSTER, EndSlimeEntity.class, 10, 1, 2).
				addMobSpawn(EnumCreatureType.MONSTER, EntityEnderman.class, 50, 1, 4));
	}

}
