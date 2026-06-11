package mod.beethoven92.betterendforge.client.renderer;

import java.util.HashMap;

import com.google.common.collect.Maps;

import mod.beethoven92.betterendforge.BetterEnd;
import mod.beethoven92.betterendforge.common.init.ModItems;
import mod.beethoven92.betterendforge.common.tileentity.EChestTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelLargeChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EndChestTileEntityRenderer extends TileEntitySpecialRenderer<EChestTileEntity> {
	private static final HashMap<Block, ResourceLocation[]> TEXTURES = Maps.newHashMap();
	private static ResourceLocation[] defaultTextures;

	private final ModelChest simpleChest = new ModelChest();
	private final ModelChest largeChest = new ModelLargeChest();

	static {
		defaultTextures = new ResourceLocation[] {
				new ResourceLocation("textures/entity/chest/normal.png"),
				new ResourceLocation("textures/entity/chest/normal_double.png"),
		};

		ModItems.ITEMS.forEach((item) -> {
			if (item instanceof ItemBlock) {
				Block block = ((ItemBlock) item).getBlock();
				if (block instanceof BlockChest) {
					String name = block.getRegistryName().getPath();
					TEXTURES.put(block, new ResourceLocation[] {
							new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/chest/" + name + "_.png"),
							new ResourceLocation(BetterEnd.MOD_ID, "textures/entity/chest/" + name + "_double.png"),
					});
					item.setTileEntityItemStackRenderer(new ChestItemTileEntityRenderer());
				}
			}
		});
	}

	@Override
	public void render(EChestTileEntity entity, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		World world = entity.getWorld();
		boolean hasWorld = world != null;
		IBlockState state = hasWorld ? entity.getWorld().getBlockState(entity.getPos()) : Blocks.CHEST.getDefaultState().withProperty(BlockChest.FACING, EnumFacing.SOUTH);
		Block block = entity.getBlockType() == null?entity.getChest():entity.getBlockType();

		if (block instanceof BlockChest) {
			BlockChest chest = (BlockChest) block;
			EnumFacing isDouble = isDoubleChest(world, entity.getPos());
			ModelChest model = isDouble!=null ? this.largeChest : this.simpleChest;

			GlStateManager.pushMatrix();
			GlStateManager.enableRescaleNormal();
			GlStateManager.translate(x, y + 1.0F, z + 1.0F);
			GlStateManager.scale(1.0F, -1.0F, -1.0F);
			GlStateManager.translate(0.5F, 0.5F, 0.5F);
			GlStateManager.rotate(state.getValue(BlockChest.FACING).getHorizontalAngle(), 0.0F, 1.0F, 0.0F);
			GlStateManager.translate(-0.5F, -0.5F, -0.5F);

			float lidAngle = entity.prevLidAngle + (entity.lidAngle - entity.prevLidAngle) * partialTicks;
			lidAngle = 1.0F - lidAngle;
			lidAngle = 1.0F - lidAngle * lidAngle * lidAngle;
			model.chestLid.rotateAngleX = -(lidAngle * ((float) Math.PI / 2F));

			ResourceLocation[] textures = TEXTURES.getOrDefault(block, defaultTextures);
			if(isDouble==null) {
				this.bindTexture(textures[0]);
				model.renderAll();
			}else{
				this.bindTexture(textures[1]);
				if(state.getValue(BlockChest.FACING)==EnumFacing.NORTH){
					if(isDouble==EnumFacing.WEST){
						model.renderAll();
					}
				}
				if(state.getValue(BlockChest.FACING)==EnumFacing.SOUTH){
					if(isDouble==EnumFacing.EAST){
						model.renderAll();
					}
				}

				if(state.getValue(BlockChest.FACING)==EnumFacing.EAST){
					if(isDouble==EnumFacing.NORTH){
						model.renderAll();
					}
				}
				if(state.getValue(BlockChest.FACING)==EnumFacing.WEST){
					if(isDouble==EnumFacing.SOUTH){
						model.renderAll();
					}
				}
				//model.renderAll();
			}



			GlStateManager.disableRescaleNormal();
			GlStateManager.popMatrix();
		}
	}

	private EnumFacing isDoubleChest(World worldIn, BlockPos pos)
	{
        if (worldIn!=null && worldIn.getBlockState(pos).getBlock() instanceof BlockChest) {
            for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
                if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock() == worldIn.getBlockState(pos).getBlock()) {
                    return enumfacing;
                }
            }

        }
        return null;
    }
}
