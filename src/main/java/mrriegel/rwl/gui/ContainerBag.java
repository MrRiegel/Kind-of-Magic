package mrriegel.rwl.gui;

import mrriegel.rwl.init.ModItems;
import mrriegel.rwl.inventory.InventoryBag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBag extends Container {

	InventoryBag inv;

	public ContainerBag(EntityPlayer player, InventoryPlayer invPlayer,
			InventoryBag inv) {
		this.inv = inv;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 5; j++) {
				addSlotToContainer(new CryBagSlot(inv, j + i * 5, 44 + j * 18,
						19 + i * 18));
			}
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9,
						8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			if (i == invPlayer.currentItem)
				addSlotToContainer(new EvilSlot(invPlayer, i, 8 + i * 18, 142));
			addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return inv.isUseableByPlayer(player);
	}

	@Override
	public void onContainerClosed(EntityPlayer p_75134_1_) {
		inv.updateNBT();
		super.onContainerClosed(p_75134_1_);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);

		// null checks and checks if the item can be stacked (maxStackSize > 1)
		if (slotObject != null && slotObject.getHasStack()) {
			ItemStack stackInSlot = slotObject.getStack();
			if (!stackInSlot.getItem().equals(ModItems.cry))
				return null;
			stack = stackInSlot.copy();

			// merges the item into player inventory since its in the tileEntity
			if (slot < inv.getSizeInventory()) {
				if (!this.mergeItemStack(stackInSlot, inv.getSizeInventory(),
						36 + inv.getSizeInventory() + 1, true)) {
					return null;
				}
			}
			// places it into the tileEntity is possible since its in the player
			// inventory
			else if (!this.mergeItemStack(stackInSlot, 0,
					inv.getSizeInventory(), false)) {
				return null;
			}

			if (stackInSlot.stackSize == 0) {
				slotObject.putStack(null);
			} else {
				slotObject.onSlotChanged();
			}

			if (stackInSlot.stackSize == stack.stackSize) {
				return null;
			}
			slotObject.onPickupFromSlot(player, stackInSlot);
		}
		return stack;
	}

}
