package mod4.pvpmod.items;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class JumpBoots extends ArmorItem{

	public JumpBoots(ArmorMaterial material, EquipmentSlot slot, Properties propaties) {
		super(material, slot, propaties);
	}

	@Override
	public void onArmorTick(ItemStack stack, Level world, Player player) {
		if(player.isOnGround()) {
			//player.jumpFromGround();
		}
//		/player.getOnPos()
		double x = player.getX();
		double y = player.getY();
		double z = player.getZ();
		if(world.getBlockState(new BlockPos(x, y-0.5, z)).getBlock().getDescriptionId().equals("block.minecraft.water")) {
			//player.setDeltaMovement(new Vec3(0, 0.5f, 0));
			
			player.jumpFromGround();
			//player.jumpInFluid(null);
		}
		if(!world.isClientSide()) {
		}
	}
	
	

}
