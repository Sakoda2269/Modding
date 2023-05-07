package mod4.pvpmod.blocks.generator;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ItemSlot extends Slot{
	
	private int tire;
	private int forbidden;

	public ItemSlot(Container container, int index, int x, int y, int forbidden) {
		super(container, index, x, y);
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
