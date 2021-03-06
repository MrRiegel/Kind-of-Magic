package mrriegel.rwl.gui;

import mrriegel.rwl.init.ModItems;
import mrriegel.rwl.inventory.InventoryNevTool;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerNevTool extends Container implements IContainer {

	InventoryNevTool inv;
	ItemStack con;
	InventoryPlayer playerInventory;

	public ContainerNevTool(EntityPlayer player, InventoryPlayer invPlayer,
			InventoryNevTool inv) {
		this.inv = inv;

		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 1; j++) {
				addSlotToContainer(new CrySlot(this, inv, j + i * 1,
						80 + j * 18, 48 + i * 18));
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
		con = invPlayer.getCurrentItem();
		playerInventory = invPlayer;
		if (con != null && con.stackTagCompound != null) {
			ItemStack stack = ItemStack
					.loadItemStackFromNBT(con.stackTagCompound
							.getCompoundTag(inv.tagName));
			inv.setInventorySlotContents(0, stack);
		}

	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return inv.isUseableByPlayer(player);
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

	public void onSlotChanged() {
		ItemStack stack = playerInventory.mainInventory[playerInventory.currentItem];

		setTarget(con, inv.getStackInSlot(0));

		playerInventory.mainInventory[playerInventory.currentItem] = con;
	}

	private void setTarget(ItemStack con2, ItemStack stackInSlot) {
		if (!con.hasTagCompound())
			con.setTagCompound(new NBTTagCompound());
		NBTTagCompound tag = new NBTTagCompound();
		if (stackInSlot != null)
			stackInSlot.writeToNBT(tag);
		con2.getTagCompound().setTag(inv.tagName, tag);

	}

}
