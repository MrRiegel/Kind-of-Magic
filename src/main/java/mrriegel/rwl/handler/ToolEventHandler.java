package mrriegel.rwl.handler;

import java.util.ArrayList;

import mrriegel.rwl.init.ModItems;
import mrriegel.rwl.inventory.InventoryNevTool;
import mrriegel.rwl.item.INev;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ToolEventHandler {

	@SubscribeEvent
	public void death(LivingDropsEvent event) {
		if (!(event.entity instanceof EntityLiving))
			return;
		EntityLivingBase e = (EntityLiving) event.entity;
		DamageSource source = event.source;
		if (!e.worldObj.isRemote
				&& source.getSourceOfDamage() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) source.getSourceOfDamage();
			if (player.getHeldItem() == null) {
				return;
			}
			ItemStack stack = player.getHeldItem();
			if (stack.getItem().equals(ModItems.nevsword)
					&& stack.getTagCompound() != null
					&& stack.getTagCompound()
							.getCompoundTag(InventoryNevTool.tagName)
							.getShort("Damage") == 4) {
				if (e.getClass().toString().contains("entity.boss")
						|| e instanceof EntityPlayer)
					return;

				ArrayList<EntityItem> l = new ArrayList<EntityItem>();
				for (EntityItem ei : event.drops) {
					l.add(new EntityItem(ei.worldObj, ei.posX, ei.posY,
							ei.posZ, ei.getEntityItem()));
				}
				for (EntityItem ei : l) {
					int c = e.worldObj.rand.nextInt(1) + 1;
					for (int i = 0; i < c; i++) {
						event.drops.add(new EntityItem(ei.worldObj, ei.posX,
								ei.posY, ei.posZ, ei.getEntityItem()));
					}
				}
			} else if (player.getHeldItem().getItem().equals(ModItems.nevsword)
					&& stack.getTagCompound() != null
					&& stack.getTagCompound()
							.getCompoundTag(InventoryNevTool.tagName)
							.getShort("Damage") == 13) {
				for (int i = 0; i < e.getMaxHealth() / 3; i++) {
					player.worldObj.spawnEntityInWorld(new EntityXPOrb(
							player.worldObj, e.posX + 0.5d, e.posY,
							e.posZ + 0.5d, EntityXPOrb.getXPSplit(1)));
				}

			}

		}
	}

	@SubscribeEvent
	public void xp(BreakEvent event) {
		EntityPlayer player = event.getPlayer();
		if (!player.worldObj.isRemote) {

			if (player.getHeldItem() == null) {
				return;
			}

			ItemStack stack = player.getHeldItem();
			if (player.getHeldItem().getItem().equals(ModItems.nevpick)
					&& stack.getTagCompound() != null
					&& stack.getTagCompound()
							.getCompoundTag(InventoryNevTool.tagName)
							.getShort("Damage") == 13) {
				int xp = event.block.getExpDrop(player.worldObj,
						player.worldObj.getBlockMetadata(event.x, event.y,
								event.z), 0);
				if (xp > 0) {
					event.setExpToDrop((int) (event.getExpToDrop() * 1.8));
				}
			}

		}
	}

	@SubscribeEvent
	public void destroyed(PlayerDestroyItemEvent event) {
		ItemStack stack = event.original;
		if (stack.getItem() instanceof INev
				&& stack.getTagCompound() != null
				&& !stack.getTagCompound()
						.getCompoundTag(InventoryNevTool.tagName).toString()
						.equals("{}")) {
			event.entityPlayer.inventory.setInventorySlotContents(
					event.entityPlayer.inventory.currentItem, ItemStack
							.loadItemStackFromNBT(stack.getTagCompound()
									.getCompoundTag(InventoryNevTool.tagName)));
		}
	}
}
