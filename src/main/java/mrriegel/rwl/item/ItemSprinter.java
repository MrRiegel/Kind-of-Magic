package mrriegel.rwl.item;

import mrriegel.rwl.reference.Reference;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;

public class ItemSprinter extends ItemTalisman {
	public ItemSprinter() {
		super();
		this.setUnlocalizedName(Reference.MOD_ID + ":" + "sprinter");
		this.setTextureName(Reference.MOD_ID + ":" + "sprinter");
	}

	@Override
	public void perform(EntityPlayer player) {
		if (player.isSneaking())
			return;
		if (player.onGround && player.moveForward > 0F
				&& !player.isInsideOfMaterial(Material.water)
				&& !player.isSprinting())
			player.moveFlying(0F, 1F, 0.06F);
		else if (player.onGround && player.moveForward > 0F
				&& !player.isInsideOfMaterial(Material.water)
				&& player.isSprinting())
			player.moveFlying(0F, 1F, 0.13F);
		else if (player.capabilities.isFlying && player.moveForward > 0F
				&& !player.isInsideOfMaterial(Material.water)
				&& player.isSprinting())
			player.moveFlying(0F, 1F, 0.1F);

	}

}
