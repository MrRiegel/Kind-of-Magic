package mrriegel.rwl.item;

import mrriegel.rwl.creative.CreativeTab;
import mrriegel.rwl.reference.Reference;
import net.minecraft.item.Item;

public class MazeDust extends Item {

	public MazeDust() {
		super();
		this.setCreativeTab(CreativeTab.tab1);
		this.setUnlocalizedName(Reference.MOD_ID + ":" + "mdust");
		this.setTextureName(Reference.MOD_ID + ":" + "mdust");
	}

}
