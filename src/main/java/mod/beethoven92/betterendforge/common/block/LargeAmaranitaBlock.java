package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.block.BlockProperties.TripleShape;
import mod.beethoven92.betterendforge.common.block.template.PlantBlock;
import mod.beethoven92.betterendforge.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class LargeAmaranitaBlock extends PlantBlock {
	public static final PropertyEnum<TripleShape> SHAPE = PropertyEnum.create("shape", TripleShape.class);
	private static final AxisAlignedBB SHAPE_BOTTOM = new AxisAlignedBB(4D/16D, 0D/16D, 4D/16D, 12D/16D, 14D/16D, 12D/16D);
	private static final AxisAlignedBB SHAPE_TOP = new AxisAlignedBB(1D/16D, 3D/16D, 1D/16D, 15D/16D, 16D/16D, 15D/16D);

	public LargeAmaranitaBlock(Material materialIn) {
		super(materialIn);
		this.setDefaultState(this.blockState.getBaseState().withProperty(SHAPE, TripleShape.BOTTOM));
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return state.getValue(SHAPE) == TripleShape.TOP ? SHAPE_TOP : SHAPE_BOTTOM;
	}

	@Override
	protected boolean isTerrain(IBlockState state) {
		return state.getBlock() == ModBlocks.SANGNUM || state.getBlock() == ModBlocks.MOSSY_OBSIDIAN || state.getBlock() == ModBlocks.MOSSY_DRAGON_BONE;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, SHAPE);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);
		TripleShape shape = state.getValue(SHAPE);
		if (shape == TripleShape.BOTTOM) {
			return isTerrain(worldIn.getBlockState(pos.down())) && worldIn.getBlockState(pos.up()).getBlock() == this;
		} else if (shape == TripleShape.TOP) {
			return worldIn.getBlockState(pos.down()).getBlock() == this;
		} else {
			return worldIn.getBlockState(pos.down()).getBlock() == this && worldIn.getBlockState(pos.up()).getBlock() == this;
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!canPlaceBlockAt((World) worldIn, pos)) {
			worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
		} else {
			worldIn.setBlockState(pos, state);
		}
	}

	@Override
	public EnumOffsetType getOffsetType() {
		return EnumOffsetType.NONE;
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return false;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return false;
	}

	public int quantityDropped(Random random)
	{
		return 0;
	}

	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
	{
		if (!worldIn.isRemote && stack.getItem() == Items.SHEARS)
		{
			spawnAsEntity(worldIn, pos, new ItemStack(state.getBlock(), 1, 0));
		}
		else
		{
			super.harvestBlock(worldIn, player, pos, state, te, stack);
		}
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(SHAPE).ordinal();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(SHAPE, TripleShape.values()[meta]);
	}

	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState p_180646_1_, IBlockAccess p_180646_2_, BlockPos p_180646_3_) {
		if(p_180646_1_.getValue(SHAPE)==TripleShape.TOP){
			return SHAPE_TOP;
		}
		return SHAPE_BOTTOM;
	}
}
