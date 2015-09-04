package mrriegel.rwl.block;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import mrriegel.rwl.creative.CreativeTab;
import mrriegel.rwl.init.LandRecipe;
import mrriegel.rwl.init.LandRecipes;
import mrriegel.rwl.init.ModItems;
import mrriegel.rwl.init.RitualRecipe;
import mrriegel.rwl.init.RitualRecipes;
import mrriegel.rwl.reference.Reference;
import mrriegel.rwl.tile.MazerTile;
import mrriegel.rwl.utility.RWLUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Keep extends Block {

	@SideOnly(Side.CLIENT)
	private IIcon[] icons = new IIcon[6];

	public Keep() {
		super(Material.rock);
		this.setHardness(1.5f);
		this.setCreativeTab(CreativeTab.tab1);
		this.setBlockName(Reference.MOD_ID + ":" + "keep");
		setBlockBounds(0.25F, 0.25F, 0.25F, 0.75F, 0.75F, 0.75F);
		setLightLevel(1.0F);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		for (int i = 0; i < 6; i++) {
			this.icons[i] = reg.registerIcon(Reference.MOD_ID + ":" + "keep");

		}

	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return this.icons[side];

	}

	@Override
	public boolean isOpaqueCube() {
		return !super.isOpaqueCube();
	}

	@Override
	public boolean renderAsNormalBlock() {
		return !super.renderAsNormalBlock();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int p_149727_6_, float p_149727_7_,
			float p_149727_8_, float p_149727_9_) {
		
		if(world.getTileEntity(x, y - 2, z)==null)
			return false;
		MazerTile tile = (MazerTile) world.getTileEntity(x, y - 2, z);
		System.out.println("sneak:" +player.isSneaking());
		System.out.println("item:" +player.getHeldItem());
		if (!player.isSneaking() && player.getHeldItem() != null
				&& player.getHeldItem().getItem().equals(ModItems.catalyst)
				&& tile != null && tile.isActive()
				&& MazerB.isConstruct(world, x, y - 2, z)) {
			
			ItemStack stack = player.getHeldItem();
			for (RitualRecipe r : RitualRecipes.lis) {
				System.out.println("holly1");
				if (!tile.isProcessing()) {
					System.out.println("holly2");
					if (r.matches(tile.getInv(), world, player,
							stack.getItemDamage())) {
						System.out.println("holly3");
						tile.clear();
						tile.setProcessing(true);
						tile.setCooldown(75);
						tile.setStack(r.getOutput());
						tile.setPlayer(player);
						tile.setName(player.getDisplayName());

						player.experienceLevel = player.experienceLevel
								- r.getXp();

						return true;

					}

				}
			}
			for (LandRecipe r : LandRecipes.lis) {
				if (!tile.isProcessing()) {
					if (r.matches(tile.getInv(), world, player,
							stack.getItemDamage())) {
						tile.clear();
						player.experienceLevel = player.experienceLevel
								- r.getXp();
						if (r.getOutput() instanceof String) {
							if (((String) r.getOutput()).equals("Day")) {
								long ku = world.getWorldTime() % 24000;
								world.setWorldTime(world.getWorldTime() - ku);
								world.setWorldTime(world.getWorldTime() + 24000);
							} else if (((String) r.getOutput()).equals("Night")) {
								long ku = world.getWorldTime() % 24000;
								world.setWorldTime(world.getWorldTime() - ku);
								world.setWorldTime(world.getWorldTime() + 36000);
							}
						} else if (r.getOutput() instanceof Class
								&& !world.isRemote) {
							System.out.println("cls: "+r.getOutput());
							System.out.println("num: "+r.getNumber());
							for (int i = 0; i < r.getNumber(); i++) {
								System.out.println("cls: jappp");
								Class<?> cl = (Class) r.getOutput();
								if (cl.getName().contains("boss"))
									return false;
								Constructor<?> ct = null;
								try {
									ct = cl.getConstructor(World.class);
								} catch (NoSuchMethodException e1) {
									e1.printStackTrace();
								} catch (SecurityException e1) {
									e1.printStackTrace();
								}
								EntityLivingBase e = null;
								try {
									e = (EntityLivingBase) ct
											.newInstance(new Object[] { world });
								} catch (InstantiationException e1) {
									e1.printStackTrace();
								} catch (IllegalAccessException e1) {
									e1.printStackTrace();
								} catch (IllegalArgumentException e1) {
									e1.printStackTrace();
								} catch (InvocationTargetException e1) {
									e1.printStackTrace();
								}
								double xtmp = world.rand.nextBoolean() ? -1.0d
										: 1.0d;
								double ztmp = world.rand.nextBoolean() ? -1.0d
										: 1.0d;
								e.posX = x + world.rand.nextDouble() * 1.5D
										* xtmp;
								e.posY = y + 0.5D;
								e.posZ = z + world.rand.nextDouble() * 1.5D
										* ztmp;
								System.out.println(String.format("%f %f %f",
										e.posX, e.posY, e.posZ));
								if ((RWLUtils.double2int(e.posX) == x && RWLUtils
										.double2int(e.posZ) == z)
										|| (RWLUtils.double2int(e.posX) == x + 2 && RWLUtils
												.double2int(e.posZ) == z + 2)
										|| (RWLUtils.double2int(e.posX) == x - 2 && RWLUtils
												.double2int(e.posZ) == z + 2)
										|| (RWLUtils.double2int(e.posX) == x + 2 && RWLUtils
												.double2int(e.posZ) == z - 2)
										|| (RWLUtils.double2int(e.posX) == x - 2 && RWLUtils
												.double2int(e.posZ) == z - 2)) {
									i--;
									continue;
								}
								world.spawnEntityInWorld(e);
								e.setPositionAndUpdate(e.posX, e.posY, e.posZ);
								System.out.println("gespawn: "+e);
							}
						}
					}
				}
			}
		}
		return false;

	}
}
