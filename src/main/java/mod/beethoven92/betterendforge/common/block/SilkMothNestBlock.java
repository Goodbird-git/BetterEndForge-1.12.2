package mod.beethoven92.betterendforge.common.block;

import java.util.Random;

import mod.beethoven92.betterendforge.common.entity.SilkMothEntity;
import mod.beethoven92.betterendforge.common.init.ModEntityTypes;
import mod.beethoven92.betterendforge.common.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SilkMothNestBlock extends Block {
	public static final PropertyBool ACTIVE = PropertyBool.create("active");
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyInteger FULLNESS = PropertyInteger.create("fullness", 0, 3);
	private static final AxisAlignedBB TOP = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D);
	private static final AxisAlignedBB BOTTOM = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);

	public SilkMothNestBlock() {
		super(Material.CLOTH);
		setSoundType(SoundType.CLOTH);
		this.setDefaultState(this.blockState.getBaseState().withProperty(ACTIVE, true).withProperty(FACING, EnumFacing.NORTH).withProperty(FULLNESS, 0));
		this.setTickRandomly(true);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, ACTIVE, FACING, FULLNESS);
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
	}

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		if (!state.getValue(ACTIVE) && player.capabilities.isCreativeMode) {
			BlockHelper.setWithUpdate(worldIn, pos.down(), Blocks.AIR.getDefaultState());
		}
		IBlockState up = worldIn.getBlockState(pos.up());
		if (up.getBlock() == this && !up.getValue(ACTIVE)) {
			BlockHelper.setWithUpdate(worldIn, pos.up(), Blocks.AIR.getDefaultState());
		}
		super.onBlockHarvested(worldIn, pos, state, player);
	}

	@Override
	public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
		if (!state.getValue(ACTIVE)) {
			return;
		}
		if (random.nextBoolean()) {
			return;
		}
		EnumFacing dir = state.getValue(FACING);
		BlockPos spawn = pos.offset(dir);
		if (!worldIn.getBlockState(spawn).getMaterial().isReplaceable()) {
			return;
		}
		int count = worldIn.getEntitiesWithinAABB(SilkMothEntity.class, new AxisAlignedBB(pos).grow(16)).size();
		if (count > 8) {
			return;
		}
		SilkMothEntity moth = new SilkMothEntity(worldIn);
		moth.setLocationAndAngles(spawn.getX() + 0.5, spawn.getY() + 0.5, spawn.getZ() + 0.5, dir.getHorizontalAngle(), 0);
		moth.motionX = dir.getXOffset() * 0.4;
		moth.motionY = 0;
		moth.motionZ = dir.getZOffset() * 0.4;
		moth.setHive(worldIn, pos);
		worldIn.spawnEntity(moth);
		worldIn.playSound(null, pos, SoundEvents.BLOCK_LAVA_POP, SoundCategory.BLOCKS, 1.0F, 1.0F);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(ACTIVE) ?1:0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(ACTIVE, meta==1);
	}


	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
}
