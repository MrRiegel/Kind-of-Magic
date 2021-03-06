package mrriegel.rwl.render;

import mrriegel.rwl.handler.ConfigurationHandler;
import mrriegel.rwl.inventory.InventoryNevTool;
import mrriegel.rwl.item.NevAxe;
import mrriegel.rwl.item.NevPick;
import mrriegel.rwl.item.NevShovel;
import mrriegel.rwl.item.NevSword;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

public class NevToolRenderer implements IItemRenderer {
	private static RenderItem renderItem = new RenderItem();

	@Override
	public boolean handleRenderType(ItemStack itemStack, ItemRenderType type) {
		return type == ItemRenderType.INVENTORY;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack itemStack,
			Object... data) {
		RenderHelper.enableGUIStandardItemLighting();

		renderItem.renderItemIntoGUI(Minecraft.getMinecraft().fontRenderer,
				Minecraft.getMinecraft().renderEngine, itemStack, 0, 0);

		if (itemStack.stackTagCompound == null)
			return;

		ItemStack target = ItemStack.loadItemStackFromNBT(itemStack
				.getTagCompound().getCompoundTag(InventoryNevTool.tagName));

		if (target == null)
			return;
		if (!ConfigurationHandler.renderCrystal)
			return;

		GL11.glPushMatrix();
		GL11.glScalef(0.5f, 0.5f, 0.5f);
		if (itemStack.getItem() instanceof NevPick
				|| itemStack.getItem() instanceof NevSword) {
			GL11.glTranslatef(-8f, -8f, 0.0f);
			renderItem.renderItemIntoGUI(Minecraft.getMinecraft().fontRenderer,
					Minecraft.getMinecraft().renderEngine, target, 14, 17);
		} else if (itemStack.getItem() instanceof NevAxe) {
			GL11.glTranslatef(-8f, -8f, 0.0f);
			renderItem.renderItemIntoGUI(Minecraft.getMinecraft().fontRenderer,
					Minecraft.getMinecraft().renderEngine, target, 16, 18);
		} else if (itemStack.getItem() instanceof NevShovel) {
			GL11.glTranslatef(32f, -16f, 0.0f);
			GL11.glRotatef(90, 0, 0, 1);
			renderItem.renderItemIntoGUI(Minecraft.getMinecraft().fontRenderer,
					Minecraft.getMinecraft().renderEngine, target, 26, 5);
		}

		GL11.glPopMatrix();
	}
}
