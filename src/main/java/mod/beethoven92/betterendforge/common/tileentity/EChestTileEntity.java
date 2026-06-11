package mod.beethoven92.betterendforge.common.tileentity;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;

public class EChestTileEntity extends TileEntityChest {
	private Block chest = Blocks.AIR;

	public void setChest(Block chest) {
		this.chest = chest;
	}
	
	public Block getChest() {
		return chest;
	}
	
	public boolean hasChest() {
		return chest.getDefaultState().getBlock()!=Blocks.AIR;
	}
}
