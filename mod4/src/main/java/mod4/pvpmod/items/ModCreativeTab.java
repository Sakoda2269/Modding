package mod4.pvpmod.items;

import mod4.pvpmod.PVPmod;
import mod4.pvpmod.init.ItemInit;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTab extends CreativeModeTab{
	
	public static final ModCreativeTab instance = new ModCreativeTab(CreativeModeTab.TABS.length, PVPmod.MOD_ID);

	public ModCreativeTab(int index_, String label) {
		super(index_, label);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(ItemInit.SMILE.get());
	}

}
