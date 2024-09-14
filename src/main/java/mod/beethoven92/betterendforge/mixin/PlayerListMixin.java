package mod.beethoven92.betterendforge.mixin;

import net.minecraft.server.management.PlayerList;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerList.class)
public class PlayerListMixin 
{
//	@Final
//	@Shadow
//	private static Logger LOGGER;
//
//	@Final
//	@Shadow
//	private MinecraftServer server;
//
//	@Final
//	@Shadow
//	private List<ServerPlayerEntity> players;
//
//	@Final
//	@Shadow
//	private Map<UUID, ServerPlayerEntity> uuidToPlayerMap;
//
//	@Final
//	@Shadow
//	private DynamicRegistries.Impl field_232639_s_;;
//
//	@Shadow
//	private int viewDistance;
//
//	@Shadow
//	public CompoundNBT readPlayerDataFromFile(ServerPlayerEntity player)
//	{
//		return null;
//	}
//
//	@Shadow
//	public int getMaxPlayers()
//	{
//		return 0;
//	}
//
//	@Shadow
//	private void setPlayerGameTypeBasedOnOther(ServerPlayerEntity player, @Nullable ServerPlayerEntity oldPlayer, ServerWorld world) {}
//
//	@Shadow
//	public MinecraftServer getServer()
//	{
//		return null;
//	}
//	@Shadow
//	public void updatePermissionLevel(ServerPlayerEntity player) {}
//
//	@Shadow
//	protected void sendScoreboard(ServerScoreboard scoreboardIn, ServerPlayerEntity playerIn) {}
//
//	@Shadow
//	public void func_232641_a_(ITextComponent p_232641_1_, ChatType p_232641_2_, UUID p_232641_3_) {}
//
//	@Shadow
//	public void sendPacketToAllPlayers(IPacket<?> packetIn) {}
//
//	@Shadow
//	public void sendWorldInfo(ServerPlayerEntity playerIn, ServerWorld worldIn) {}
//
//	@Inject(method = "initializeConnectionToPlayer", at = @At(value = "HEAD"), cancellable = true)
//	public void be_initializeConnectionToPlayer(NetworkManager netManager, ServerPlayerEntity playerIn, CallbackInfo info)
//	{
////		if (CommonConfig.swapOverworldWithEnd())
////		{
////			GameProfile gameprofile = playerIn.getGameProfile();
////		    PlayerProfileCache playerprofilecache = this.server.getPlayerProfileCache();
////		    GameProfile gameprofile1 = playerprofilecache.getProfileByUUID(gameprofile.getId());
////		    String s = gameprofile1 == null ? gameprofile.getName() : gameprofile1.getName();
////		    playerprofilecache.addEntry(gameprofile);
////		    CompoundNBT compoundnbt = this.readPlayerDataFromFile(playerIn);
////		    RegistryKey<World> registrykey = compoundnbt != null ? DimensionType.decodeWorldKey(new Dynamic<>(NBTDynamicOps.INSTANCE, compoundnbt.get("Dimension"))).resultOrPartial(LOGGER::error).orElse(World.THE_END) : World.THE_END;
////		    ServerWorld serverworld = this.server.getWorld(registrykey);
////		    ServerWorld serverworld1;
////		    if (serverworld == null)
////		    {
////		    	LOGGER.warn("Unknown respawn dimension {}, defaulting to overworld", (Object)registrykey);
////		        serverworld1 = this.server.func_241755_D_();
////		    }
////		    else
////		    {
////		    	serverworld1 = serverworld;
////		    }
////
////		    playerIn.setWorld(serverworld1);
////		    playerIn.interactionManager.setWorld((ServerWorld)playerIn.world);
////		    String s1 = "local";
////		    if (netManager.getRemoteAddress() != null)
////		    {
////		    	s1 = netManager.getRemoteAddress().toString();
////		    }
////
////		    LOGGER.info("{}[{}] logged in with entity id {} at ({}, {}, {})", playerIn.getName().getString(), s1, playerIn.getEntityId(), playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ());
////		    IWorldInfo iworldinfo = serverworld1.getWorldInfo();
////		    this.setPlayerGameTypeBasedOnOther(playerIn, (ServerPlayerEntity)null, serverworld1);
////		    ServerPlayNetHandler serverplaynethandler = new ServerPlayNetHandler(this.server, netManager, playerIn);
////		    net.minecraftforge.fml.network.NetworkHooks.sendMCRegistryPackets(netManager, "PLAY_TO_CLIENT");
////		    GameRules gamerules = serverworld1.getGameRules();
////		    boolean flag = gamerules.getBoolean(GameRules.DO_IMMEDIATE_RESPAWN);
////		    boolean flag1 = gamerules.getBoolean(GameRules.REDUCED_DEBUG_INFO);
////		    serverplaynethandler.sendPacket(new SJoinGamePacket(playerIn.getEntityId(), playerIn.interactionManager.getGameType(), playerIn.interactionManager.func_241815_c_(), BiomeManager.getHashedSeed(serverworld1.getSeed()), iworldinfo.isHardcore(), this.server.func_240770_D_(), this.field_232639_s_, serverworld1.getDimensionType(), serverworld1.getDimensionKey(), this.getMaxPlayers(), this.viewDistance, flag1, !flag, serverworld1.isDebug(), serverworld1.func_241109_A_()));
////		    serverplaynethandler.sendPacket(new SCustomPayloadPlayPacket(SCustomPayloadPlayPacket.BRAND, (new PacketBuffer(Unpooled.buffer())).writeString(this.getServer().getServerModName())));
////		    serverplaynethandler.sendPacket(new SServerDifficultyPacket(iworldinfo.getDifficulty(), iworldinfo.isDifficultyLocked()));
////		    serverplaynethandler.sendPacket(new SPlayerAbilitiesPacket(playerIn.abilities));
////		    serverplaynethandler.sendPacket(new SHeldItemChangePacket(playerIn.inventory.currentItem));
////		    serverplaynethandler.sendPacket(new SUpdateRecipesPacket(this.server.getRecipeManager().getRecipes()));
////		    serverplaynethandler.sendPacket(new STagsListPacket(this.server.func_244266_aF()));
////		    net.minecraftforge.fml.network.NetworkHooks.syncCustomTagTypes(playerIn, this.server.func_244266_aF());
////		    this.updatePermissionLevel(playerIn);
////		    playerIn.getStats().markAllDirty();
////		    playerIn.getRecipeBook().init(playerIn);
////		    this.sendScoreboard(serverworld1.getScoreboard(), playerIn);
////		    this.server.refreshStatusNextTick();
////		    IFormattableTextComponent iformattabletextcomponent;
////		    if (playerIn.getGameProfile().getName().equalsIgnoreCase(s))
////		    {
////		    	iformattabletextcomponent = new TranslationTextComponent("multiplayer.player.joined", playerIn.getDisplayName());
////		    }
////		    else
////		    {
////		    	iformattabletextcomponent = new TranslationTextComponent("multiplayer.player.joined.renamed", playerIn.getDisplayName(), s);
////		    }
////
////		    this.func_232641_a_(iformattabletextcomponent.mergeStyle(TextFormatting.YELLOW), ChatType.SYSTEM, Util.DUMMY_UUID);
////		    serverplaynethandler.setPlayerLocation(playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), playerIn.rotationYaw, playerIn.rotationPitch);
////		    this.players.add(playerIn);
////		    this.uuidToPlayerMap.put(playerIn.getUniqueID(), playerIn);
////		    this.sendPacketToAllPlayers(new SPlayerListItemPacket(SPlayerListItemPacket.Action.ADD_PLAYER, playerIn));
////
////		    for(int i = 0; i < this.players.size(); ++i)
////		    {
////		    	playerIn.connection.sendPacket(new SPlayerListItemPacket(SPlayerListItemPacket.Action.ADD_PLAYER, this.players.get(i)));
////		    }
////
////		    serverworld1.addNewPlayer(playerIn);
////		    this.server.getCustomBossEvents().onPlayerLogin(playerIn);
////		    this.sendWorldInfo(playerIn, serverworld1);
////		    if (!this.server.getResourcePackUrl().isEmpty())
////		    {
////		    	playerIn.loadResourcePack(this.server.getResourcePackUrl(), this.server.getResourcePackHash());
////		    }
////
////		    for(EffectInstance effectinstance : playerIn.getActivePotionEffects())
////		    {
////		    	serverplaynethandler.sendPacket(new SPlayEntityEffectPacket(playerIn.getEntityId(), effectinstance));
////		    }
////
////		    if (compoundnbt != null && compoundnbt.contains("RootVehicle", 10))
////		    {
////		    	CompoundNBT compoundnbt1 = compoundnbt.getCompound("RootVehicle");
////		        Entity entity1 = EntityType.loadEntityAndExecute(compoundnbt1.getCompound("Entity"), serverworld1, (p_217885_1_) -> {
////		        	return !serverworld1.summonEntity(p_217885_1_) ? null : p_217885_1_;
////		        });
////		        if (entity1 != null)
////		        {
////		        	UUID uuid;
////		            if (compoundnbt1.hasUniqueId("Attach"))
////		            {
////		            	uuid = compoundnbt1.getUniqueId("Attach");
////		            }
////		            else
////		            {
////		            	uuid = null;
////		            }
////
////		            if (entity1.getUniqueID().equals(uuid))
////		            {
////		            	playerIn.startRiding(entity1, true);
////		            }
////		            else
////		            {
////		            	for(Entity entity : entity1.getRecursivePassengers())
////		            	{
////		            		if (entity.getUniqueID().equals(uuid))
////		            		{
////		            			playerIn.startRiding(entity, true);
////		                        break;
////		                    }
////		                }
////		            }
////
////		            if (!playerIn.isPassenger())
////		            {
////		            	LOGGER.warn("Couldn't reattach entity to player");
////		                serverworld1.removeEntity(entity1);
////
////		                for(Entity entity2 : entity1.getRecursivePassengers())
////		                {
////		                   serverworld1.removeEntity(entity2);
////		                }
////		            }
////		        }
////		    }
////
////		    playerIn.addSelfToInternalCraftingInventory();
////		    net.minecraftforge.fml.hooks.BasicEventHooks.firePlayerLoggedIn( playerIn );
////		    info.cancel();
////		}
//	}
}
