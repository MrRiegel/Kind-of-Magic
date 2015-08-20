package mrriegel.rwl.tile;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class GrowerTile extends TileEntity {

	@Override
	public void updateEntity() {
		if (worldObj.isRemote)
			return;
		if (advanced())
			for (int x = xCoord - 3; x < xCoord + 3; x++) {
				for (int y = yCoord - 1; y < yCoord + 4; y++) {
					for (int z = zCoord - 3; z < zCoord + 3; z++) {
						if (x == xCoord && y == yCoord && z == zCoord)
							continue;
						Block block = worldObj.getBlock(x, y, z);
						if (block instanceof IPlantable
								|| block instanceof IGrowable) {
							if (worldObj.rand.nextInt(70) == 0) {
								block.updateTick(worldObj, x, y, z,
										worldObj.rand);
								if (block instanceof IPlantable) {
									IPlantable ip = (IPlantable) block;
									System.out.println("ip :"
											+ ip.getPlantMetadata(worldObj, x,
													y, z));
								} else if (block instanceof IGrowable) {
									IGrowable ig = (IGrowable) block;
								}
							}
						}
					}
				}
			}
		else
			for (int x = xCoord - 1; x < xCoord + 1; x++) {
				for (int y = yCoord - 1; y < yCoord + 4; y++) {
					for (int z = zCoord - 1; z < zCoord + 1; z++) {
						if (x == xCoord && y == yCoord && z == zCoord)
							continue;
						Block block = worldObj.getBlock(x, y, z);
						if (block instanceof IPlantable
								|| block instanceof IGrowable) {
							if (worldObj.rand.nextInt(130) == 0) {
								block.updateTick(worldObj, x, y, z,
										worldObj.rand);
								particle(worldObj, x - 0.5D, y + 0.5D, z - 0.5D);
							}
						}
					}
				}
			}
	}

	private void particle(World world, double x, double y, double z) {
		Random ran = new Random();
		for (int i = 0; i < 20; i++) {
			world.spawnParticle("happyVillager", x + ran.nextDouble(), y, z
					+ ran.nextDouble(), 0, 0, 0);
		}
	}

	private boolean advanced() {
		boolean hot = worldObj.getBlock(xCoord, yCoord - 1, zCoord).equals(
				Blocks.fire)
				|| worldObj.getBlock(xCoord, yCoord - 1, zCoord).equals(
						Blocks.lava);
		int count = 0;
		if (worldObj.getBlock(xCoord, yCoord + 1, zCoord + 1).getMaterial()
				.equals(Material.water)
				&& worldObj.getBlockMetadata(xCoord, yCoord + 1, zCoord + 1) == 0)
			count++;
		if (worldObj.getBlock(xCoord, yCoord + 1, zCoord - 1).getMaterial()
				.equals(Material.water)
				&& worldObj.getBlockMetadata(xCoord, yCoord + 1, zCoord - 1) == 0)
			count++;
		if (worldObj.getBlock(xCoord + 1, yCoord + 1, zCoord).getMaterial()
				.equals(Material.water)
				&& worldObj.getBlockMetadata(xCoord + 1, yCoord + 1, zCoord) == 0)
			count++;
		if (worldObj.getBlock(xCoord - 1, yCoord + 1, zCoord).getMaterial()
				.equals(Material.water)
				&& worldObj.getBlockMetadata(xCoord - 1, yCoord + 1, zCoord) == 0)
			count++;

		boolean water = worldObj.getBlock(xCoord, yCoord + 1, zCoord).equals(
				Blocks.water)
				&& count > 1;
		return water && hot;
	}

}
