package mrriegel.rwl.block;

import java.util.Random;

import mrriegel.rwl.creative.CreativeTab;
import mrriegel.rwl.init.ModItems;
import mrriegel.rwl.reference.Reference;
import net.minecraft.block.BlockLeaves;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PanLeaves extends BlockLeaves {

	public IIcon FancyLeaves;
	public IIcon FastLeaves;

	public PanLeaves() {
		super();
		this.setCreativeTab(CreativeTab.tab1);
		this.setBlockName(Reference.MOD_ID + ":" + "panleaves");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register) {
		FancyLeaves = register.registerIcon(Reference.MOD_ID + ":"
				+ "panleaves");
		FastLeaves = register.registerIcon(Reference.MOD_ID + ":"
				+ "panleaves_fast");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockColor() {
		return 0x00bfff;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int p_149741_1_) {
		return 0x00bfff;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_,
			int p_149720_3_, int p_149720_4_) {
		return 0x00bfff;
	}

	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3) {
		return ModItems.resin;
	}

	@Override
	public boolean isOpaqueCube() {
		return !super.isOpaqueCube();
	}

	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess acces, int x, int y,
			int z, int meta) {
		return true;
	}

	@Override
	public void dropBlockAsItemWithChance(World world, int x, int y, int z,
			int metadata, float chance, int fortune) {
		if (world.isRemote) {
			return;
		}

		if (world.rand.nextInt(59) == 0) {
			Item item = this.getItemDropped(metadata, world.rand, fortune);
			this.dropBlockAsItem(world, x, y, z, new ItemStack(item));

		}
		if (world.rand.nextInt(59) == 0) {
			Item item = ModItems.drop;
			this.dropBlockAsItem(world, x, y, z, new ItemStack(item));

		}
		if (world.rand.nextInt(79) == 0) {
			Item item = ModItems.panstick;
			this.dropBlockAsItem(world, x, y, z, new ItemStack(item));

		}
	}

	@Override
	public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
		return Minecraft.getMinecraft().gameSettings.fancyGraphics ? this.FancyLeaves
				: this.FastLeaves;
	}

	@Override
	public String[] func_150125_e() {
		return null;
	}

}
