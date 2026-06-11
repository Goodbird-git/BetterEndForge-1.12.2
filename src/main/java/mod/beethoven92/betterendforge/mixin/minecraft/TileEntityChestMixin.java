package mod.beethoven92.betterendforge.mixin.minecraft;

import mod.beethoven92.betterendforge.common.tileentity.EChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TileEntityChest.class)
public abstract class TileEntityChestMixin extends TileEntity {

    @Inject(method = "isChestAt", at = @At(value = "RETURN", ordinal = 1), cancellable = true)
    private void betterendforge_isChestAt(BlockPos pos, CallbackInfoReturnable<Boolean> cir){
        TileEntityChest thisTile = (TileEntityChest)(Object)this;
        TileEntity thatTile = this.world.getTileEntity(pos);

        boolean thisIsBE = thisTile instanceof EChestTileEntity;
        boolean thatIsBE = thatTile instanceof EChestTileEntity;

        if(thisIsBE != thatIsBE) cir.setReturnValue(false); //one vanilla chest, one BE, don't connect
        else if(thisIsBE && thatIsBE)
            cir.setReturnValue(this.world.getBlockState(pos).getBlock() == this.getBlockType());
        //else vanilla behavior
    }
}
