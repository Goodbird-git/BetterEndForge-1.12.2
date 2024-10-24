package mod.beethoven92.betterendforge.common.capability;

import com.google.common.collect.ImmutableList;
import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModBiomes;
import mod.beethoven92.betterendforge.common.world.generator.GeneratorOptions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

public class EndData implements INBTSerializable<NBTTagCompound> {
	@CapabilityInject(EndData.class)
	public static final Capability<EndData> CAPABILITY = null;

	private Set<UUID> players;
	private BlockPos spawn;

	public EndData() {
		players = new HashSet<>();
	}

	private void login(EntityPlayerMP player) {
		if (players.contains(player.getUniqueID()))
			return;
		players.add(player.getUniqueID());

		teleportToSpawn(player);
	}

	private void teleportToSpawn(EntityPlayerMP player) {
		// If custom spawn point is set or config not set, get out of here
		if (player.getBedLocation() != null || !GeneratorOptions.swapOverworldToEnd())
			return;

		World world = player.getServerWorld();
		World end = player.getServer().getWorld(1);
		if (end == null)
			return;

		if (spawn == null)
			spawn = findSpawn(end, player);
		if (spawn == null)
			return;

		if (world == end) {
			player.setPositionAndUpdate(spawn.getX(), spawn.getY(), spawn.getZ());
		} else {
			player.changeDimension(end.provider.getDimension(), (world1, entity, yaw) -> entity.moveToBlockPosAndAngles(spawn, yaw, entity.rotationPitch));
		}
	}

	private BlockPos findSpawn(World end, EntityPlayer player) {
		ImmutableList<Biome> biomes = ImmutableList.of(ModBiomes.AMBER_LAND.getActualBiome(),
				ModBiomes.BLOSSOMING_SPIRES.getActualBiome(), ModBiomes.CHORUS_FOREST.getActualBiome(),
				ModBiomes.CRYSTAL_MOUNTAINS.getActualBiome(), ModBiomes.DRY_SHRUBLAND.getActualBiome(),
				ModBiomes.DUST_WASTELANDS.getActualBiome(), ModBiomes.FOGGY_MUSHROOMLAND.getActualBiome(),
				ModBiomes.GLOWING_GRASSLANDS.getActualBiome(), ModBiomes.LANTERN_WOODS.getActualBiome(),
				ModBiomes.MEGALAKE.getActualBiome(), ModBiomes.SULPHUR_SPRINGS.getActualBiome(),
				ModBiomes.UMBRELLA_JUNGLE.getActualBiome());
		for (Biome biome : biomes) {
			BlockPos pos = end.getBiomeProvider().findBiomePosition(0, 40, 6400, biomes, end.rand);
			if (pos == null)
				continue;

			for (int i = 0; i < 40; i++) {
				BlockPos p = pos.add(end.rand.nextInt(40) - 20, 0, end.rand.nextInt(40) - 20);
				for (int k = 0; k < 150; k++) {
					if (!end.isAirBlock(p.add(0, k, 0)) && end.isAirBlock(p.add(0, k + 1, 0))
							&& end.isAirBlock(p.add(0, k + 2, 0)))
						return p.add(0, k + 1, 0);
				}
			}
		}
		return null;
	}

	public static void playerLogin(EntityPlayerMP player) {
		World end = player.getServer().getWorld(1);
		if (end == null)
			return;
		end.getCapability(CAPABILITY, null).login(player);
	}

	public static void playerRespawn(EntityPlayerMP player) {
		World end = player.getServer().getWorld(1);
		if (end == null)
			return;
		end.getCapability(CAPABILITY, null).teleportToSpawn(player);
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		if (spawn != null)
			nbt.setTag("spawn", NBTUtil.createPosTag(spawn));
		NBTTagList list = new NBTTagList();
		for (UUID id : players)
			list.appendTag(NBTUtil.createUUIDTag(id));
		nbt.setTag("players", list);
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("spawn"))
			spawn = NBTUtil.getPosFromTag(nbt.getCompoundTag("spawn"));

		NBTTagList list = nbt.getTagList("players", Constants.NBT.TAG_COMPOUND);
		for (int i = 0; i < list.tagCount(); i++)
			players.add(NBTUtil.getUUIDFromTag(list.getCompoundTagAt(i)));
	}

	@EventBusSubscriber(modid = BetterEnd.MOD_ID)
	public static class Provider implements ICapabilitySerializable<NBTTagCompound> {

		private final EndData instance = CAPABILITY.getDefaultInstance();

		@Override
		public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
			return getCapability(capability, facing)!=null;
		}

		@Override
		public <T> T getCapability(Capability<T> cap, EnumFacing side) {
			return CAPABILITY.cast(instance);
		}

		@Override
		public NBTTagCompound serializeNBT() {
			return (NBTTagCompound) CAPABILITY.getStorage().writeNBT(CAPABILITY, instance, null);
		}

		@Override
		public void deserializeNBT(NBTTagCompound nbt) {
			CAPABILITY.getStorage().readNBT(CAPABILITY, instance, null, nbt);
		}

		private static final ResourceLocation LOCATION = new ResourceLocation(BetterEnd.MOD_ID, "enddata");

		@SubscribeEvent
		public static void attachCapability(AttachCapabilitiesEvent<World> event) {
			if (event.getObject().provider.getDimension() == 1)
				event.addCapability(LOCATION, new Provider());
		}
	}

	public static class Storage implements IStorage<EndData> {

		@Override
		public NBTBase writeNBT(Capability<EndData> capability, EndData instance, EnumFacing side) {
			return instance.serializeNBT();
		}

		@Override
		public void readNBT(Capability<EndData> capability, EndData instance, EnumFacing side, NBTBase nbt) {
			instance.deserializeNBT((NBTTagCompound) nbt);
		}
	}
}