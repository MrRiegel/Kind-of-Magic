package mrriegel.rwl.item;

import mrriegel.rwl.reference.Reference;
import mrriegel.rwl.utility.NBTHelper;
import mrriegel.rwl.utility.RWLUtils;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Up extends ItemEdelstein {
	public Up() {
		super();
		cooldown = 200;
		this.setUnlocalizedName(Reference.MOD_ID + ":" + "up");
		this.setTextureName(Reference.MOD_ID + ":" + "up");

	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world,
			EntityPlayer player) {
		if (world.isRemote || NBTHelper.getInt(stack, "cooldown") != 0)
			return stack;
		for (double i = player.posY + 2.0D; i < 255; i = i + 1.0D) {
			if (world
					.getBlock(RWLUtils.double2int(player.posX),
							RWLUtils.double2int(i),
							RWLUtils.double2int(player.posZ)).getMaterial()
					.isSolid()) {
				if (world.getBlock(RWLUtils.double2int(player.posX),
						RWLUtils.double2int(i + 1),
						RWLUtils.double2int(player.posZ)).getMaterial() == Material.air
						&& world.getBlock(RWLUtils.double2int(player.posX),
								RWLUtils.double2int(i + 2),
								RWLUtils.double2int(player.posZ)).getMaterial() == Material.air) {
					if (i >= 127 && world.provider.dimensionId == -1)
						break;
					player.setPositionAndUpdate(player.posX, i + 1.05D,
							player.posZ);
					NBTHelper.setInteger(stack, "cooldown", cooldown);
					break;

				}
			}
		}
		return stack;

	}

	@Override
	public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_,
			World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_,
			int p_77648_7_, float p_77648_8_, float p_77648_9_,
			float p_77648_10_) {
		return false;
	}
}