package mod4.pvpmod.networking.packet;


import java.util.function.Supplier;

import mod4.pvpmod.blocks.generator.GeneratorMenu;
import mod4.pvpmod.blocks.generator.GeneratorTile;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class InsertItemPacketC2S {
	
	int tire;
	
	public InsertItemPacketC2S(int tire) {
		this.tire = tire;
	}
	
	public InsertItemPacketC2S(FriendlyByteBuf buf) {
		tire = buf.readInt();
	}
	
	public void toBytes(FriendlyByteBuf buf) {
		buf.writeInt(tire);
	}
	
	public boolean handle(Supplier<NetworkEvent.Context > supplier) {
		NetworkEvent.Context context = supplier.get();
		context.enqueueWork(() ->{
			//サーバーサイドで実行される
			ServerPlayer player = context.getSender();
			//ServerLevel level = player.getLevel();
			GeneratorMenu menu = (GeneratorMenu)player.containerMenu;
			System.out.println("----------------------load" + tire);
			menu.slot1.setTire(tire);
			menu.slot2.setTire(tire);
			menu.slot3.setTire(tire);
			menu.slot4.setTire(tire);
			if(tire != 0) {
//				menu.getSlot(GeneratorMenu.SLOT1).set(new ItemStack(Item.byId(menu.data.get(15 + (tire - 1) * 3)), menu.data.get(30 + (tire - 1) * 3)));
//				menu.getSlot(GeneratorMenu.SLOT2).set(new ItemStack(Item.byId(menu.data.get(16 + (tire - 1) * 3)), menu.data.get(31 + (tire - 1) * 3)));
//				menu.getSlot(GeneratorMenu.SLOT3).set(new ItemStack(Item.byId(menu.data.get(17 + (tire - 1) * 3)), menu.data.get(32 + (tire - 1)  * 3)));
				GeneratorTile.insertItem(menu.be, GeneratorMenu.SLOT1, new ItemStack(Item.byId(menu.data.get(15 + (tire - 1) * 3)), menu.data.get(30 + (tire - 1) * 3)));
				GeneratorTile.insertItem(menu.be, GeneratorMenu.SLOT2, new ItemStack(Item.byId(menu.data.get(16 + (tire - 1) * 3)), menu.data.get(31 + (tire - 1) * 3)));
				GeneratorTile.insertItem(menu.be, GeneratorMenu.SLOT3, new ItemStack(Item.byId(menu.data.get(17 + (tire - 1) * 3)), menu.data.get(32 + (tire - 1)  * 3)));
			}
			if(tire != 5) {
//				menu.getSlot(GeneratorMenu.SLOT4).set(new ItemStack(Item.byId(menu.data.get(45 + tire)), menu.data.get(50 + tire)));
				GeneratorTile.insertItem(menu.be, GeneratorMenu.SLOT4, new ItemStack(Item.byId(menu.data.get(45 + tire)), menu.data.get(50 + tire)));
			}
			
		});
		return true;
	}

}
