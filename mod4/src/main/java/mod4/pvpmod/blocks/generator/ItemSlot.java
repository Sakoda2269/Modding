package mod4.pvpmod.blocks.generator;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ItemSlot extends SlotItemHandler{
	
	private int tire;
	private int forbidden;

	public ItemSlot(IItemHandler handler, int index, int x, int y, int forbidden) {
		super(handler, index, x, y);
		this.forbidden = forbidden;
	}

	@Override
	public boolean mayPlace(ItemStack p_40231_) {
		if(tire == forbidden) {
			return false;
		}
		return true;
	}
	
	public void setTire(int tire) {
		this.tire = tire;
	}
	
	

}
