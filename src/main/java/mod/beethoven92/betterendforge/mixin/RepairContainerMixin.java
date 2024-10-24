package mod.beethoven92.betterendforge.mixin;

import mod.beethoven92.betterendforge.common.interfaces.ExtendedRepairContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerRepair;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ContainerRepair.class)
public abstract class RepairContainerMixin extends Container implements ExtendedRepairContainer
{
//	private final World world = this.player.world; TODO ANVIL
//	private final RecipeManager recipeManager = this.world.getRecipeManager();
//
//	private List<AnvilSmithingRecipe> be_recipes = Collections.emptyList();
//	private AnvilSmithingRecipe be_currentRecipe;
//	private IntReferenceHolder anvilLevel;
//
//
//	@Inject(method = "<init>*", at = @At("TAIL"))
//	public void be_initAnvilLevel(InventoryPlayer playerInventory, final World worldIn, final BlockPos blockPosIn, EntityPlayer player) {
//		int anvLevel = 1;
//		Block anvilBlock = world.getBlockState(blockPos).getBlock();
//		if (anvilBlock instanceof EndAnvilBlock) {
//			anvLevel = ((EndAnvilBlock) anvilBlock).getCraftingLevel();
//		}
//		IntReferenceHolder anvilLevel = IntReferenceHolder.single();
//		anvilLevel.set(anvLevel);
//		this.anvilLevel = trackInt(anvilLevel);
//	}
//
//	@Shadow
//	public abstract void updateRepairOutput();
//
//	@Shadow @Final private EntityPlayer player;
//
//	@Inject(method = "func_230303_b_", at = @At("HEAD"), cancellable = true)
//	protected void be_canTakeOutput(PlayerEntity player, boolean present, CallbackInfoReturnable<Boolean> info)
//	{
//		if (be_currentRecipe != null)
//		{
//			info.setReturnValue(be_currentRecipe.checkHammerDurability(this.field_234643_d_, player));
//		}
//	}
//
//	@Inject(method = "func_230301_a_", at = @At("HEAD"), cancellable = true)
//	protected void be_onTakeOutput(PlayerEntity player, ItemStack stack, CallbackInfoReturnable<ItemStack> info)
//	{
//		if (be_currentRecipe != null)
//		{
//			this.field_234643_d_.getStackInSlot(1).shrink(be_currentRecipe.inputCount);
//
//			stack = be_currentRecipe.craft(this.field_234643_d_, player);
//
//			this.onCraftMatrixChanged(field_234643_d_);
//
//			this.field_234644_e_.consume((world, blockPos) -> {
//				BlockState anvilState = world.getBlockState(blockPos);
//				if (!player.abilities.isCreativeMode && anvilState.isIn(BlockTags.ANVIL) && player.getRNG().nextFloat() < 0.12F)
//				{
//					BlockState landingState = AnvilBlock.damage(anvilState);
//					if (landingState == null)
//					{
//						world.removeBlock(blockPos, false);
//						world.playEvent(1029, blockPos, 0);
//					}
//					else
//					{
//						world.setBlockState(blockPos, landingState, 2);
//						world.playEvent(1030, blockPos, 0);
//					}
//				}
//				else
//				{
//					world.playEvent(1030, blockPos, 0);
//				}
//
//			});
//			info.setReturnValue(stack);
//		}
//	}
//
//	@Inject(method = "updateRepairOutput", at = @At("HEAD"), cancellable = true)
//	public void updateRepairOutput(CallbackInfo info)
//	{
//		be_recipes = this.recipeManager.getRecipes(AnvilSmithingRecipe.TYPE, this.field_234643_d_, world);
//
//		if (be_recipes.size() > 0)
//		{
//			int anvilLevel = this.anvilLevel.get();
//			be_recipes = be_recipes.stream().filter(recipe ->
//					anvilLevel >= recipe.anvilLevel).collect(Collectors.toList());
//			if (be_recipes.size() > 0)
//			{
//				if (be_currentRecipe == null || !be_recipes.contains(be_currentRecipe))
//				{
//					be_currentRecipe = be_recipes.get(0);
//				}
//				be_updateResult();
//				info.cancel();
//			}
//			else
//			{
//				be_currentRecipe = null;
//			}
//		}
//	}
//
//	@Inject(method = "updateItemName", at = @At("HEAD"), cancellable = true)
//	public void updateItemName(String string, CallbackInfo info)
//	{
//		if (be_currentRecipe != null)
//		{
//			info.cancel();
//		}
//	}
//
//	@Override
//	public boolean enchantItem(PlayerEntity playerIn, int id)
//	{
//		if (id == 0)
//		{
//			this.be_previousRecipe();
//			return true;
//		}
//		else if (id == 1)
//		{
//			this.be_nextRecipe();
//			return true;
//		}
//		return super.enchantItem(playerIn, id);
//	}
//
//	private void be_updateResult()
//	{
//		if (be_currentRecipe == null) return;
//		this.field_234642_c_.setInventorySlotContents(0, be_currentRecipe.getCraftingResult(this.field_234643_d_));
//		this.detectAndSendChanges();
//	}
//
//	@Override
//	public void be_updateCurrentRecipe(AnvilSmithingRecipe recipe)
//	{
//		this.be_currentRecipe = recipe;
//		this.be_updateResult();
//	}
//
//	@Override
//	public AnvilSmithingRecipe be_getCurrentRecipe()
//	{
//		return this.be_currentRecipe;
//	}
//
//	@Override
//	public List<AnvilSmithingRecipe> be_getRecipes()
//	{
//		return this.be_recipes;
//	}
}