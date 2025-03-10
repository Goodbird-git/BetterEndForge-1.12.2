package mod.beethoven92.betterendforge.common.util.sdf;

import java.util.Map;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class PosInfo implements Comparable<PosInfo>
{
	private static final IBlockState AIR = Blocks.AIR.getDefaultState();
	private final Map<BlockPos, PosInfo> blocks;
	private final Map<BlockPos, PosInfo> add;
	private final BlockPos pos;
	private IBlockState state;

	public static PosInfo create(Map<BlockPos, PosInfo> blocks, Map<BlockPos, PosInfo> add, BlockPos pos) {
		return new PosInfo(blocks, add, pos);
	}

	private PosInfo(Map<BlockPos, PosInfo> blocks, Map<BlockPos, PosInfo> add, BlockPos pos) {
		this.blocks = blocks;
		this.add = add;
		this.pos = pos;
		blocks.put(pos, this);
	}
	
	public IBlockState getState()
	{
		return state;
	}

	public IBlockState getState(BlockPos pos) {
		PosInfo info = blocks.get(pos);
		if (info == null) {
			info = add.get(pos);
			return info == null ? AIR : info.getState();
		}
		return info.getState();
	}
	
	public void setState(IBlockState state) 
	{
		this.state = state;
	}

	public void setState(BlockPos pos, IBlockState state) {
		PosInfo info = blocks.get(pos);
		if (info != null) {
			info.setState(state);
		}
	}
	
	public IBlockState getState(EnumFacing dir) 
	{
		PosInfo info = blocks.get(pos.offset(dir));
		if (info == null) {
			info = add.get(pos.offset(dir));
			return info == null ? AIR : info.getState();
		}
		return info.getState();
	}

	public IBlockState getState(EnumFacing dir, int distance) {
		PosInfo info = blocks.get(pos.offset(dir, distance));
		if (info == null) {
			return AIR;
		}
		return info.getState();
	}
	
	public IBlockState getStateUp() 
	{
		return getState(EnumFacing.UP);
	}
	
	public IBlockState getStateDown() 
	{
		return getState(EnumFacing.DOWN);
	}
	
	@Override
	public int hashCode()
	{
		return pos.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		if (!(obj instanceof PosInfo)) 
		{
			return false;
		}
		return pos.equals(((PosInfo) obj).pos);
	}

	@Override
	public int compareTo(PosInfo info) 
	{
		return this.pos.getY() - info.pos.getY();
	}

	public BlockPos getPos() 
	{
		return pos;
	}
	
	public void setBlockPos(BlockPos pos, IBlockState state)
	{
		PosInfo info = new PosInfo(blocks, add, pos);
		info.state = state;
		add.put(pos, info);
	}
}
