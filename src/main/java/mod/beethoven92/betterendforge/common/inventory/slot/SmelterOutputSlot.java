package mod.beethoven92.betterendforge.common.inventory.slot;

import mod.beethoven92.betterendforge.common.tileentity.EndStoneSmelterTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SmelterOutputSlot extends Slot
{
	private EntityPlayer player;
	private int amount;

	public SmelterOutputSlot(EntityPlayer player, IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition);
		this.player = player;
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return false;
	}

	@Override
	public ItemStack decrStackSize(int amount)
	{
		if (this.getHasStack())
		{
			this.amount += Math.min(amount, this.getStack().getCount());
		}

		return super.decrStackSize(amount);
	}

	@Override
	public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack)
	{
		this.onCrafting(stack);
		super.onTake(thePlayer, stack);
		return stack;
	}

	@Override
	protected void onCrafting(ItemStack stack, int amount)
	{
		this.amount += amount;
		this.onCrafting(stack);
	}

	@Override
	protected void onCrafting(ItemStack stack)
	{
		stack.onCrafting(this.player.world, this.player, this.amount);
		if (!this.player.world.isRemote && this.inventory instanceof EndStoneSmelterTileEntity)
		{
			((EndStoneSmelterTileEntity) this.inventory).dropExperience(player);
		}
		this.amount = 0;
	}
}
