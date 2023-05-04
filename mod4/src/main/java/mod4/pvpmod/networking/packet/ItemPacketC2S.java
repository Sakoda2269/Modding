package mod4.pvpmod.networking.packet;


import java.util.function.Supplier;

import mod4.pvpmod.blocks.generator.GeneratorMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

public class ItemPacketC2S {
	
	int tire;
	ItemStack item1;
	ItemStack item2;
	ItemStack item3;
	ItemStack update;
	
	public ItemPacketC2S(int tire, ItemStack item1, ItemStack item2, ItemStack item3, ItemStack update) {
		this.tire = tire;
		this.item1 = item1;
		this.item2 = item2;
		this.item3 = item3;
		this.update = update;
		
	}
	
	public ItemPacketC2S(FriendlyByteBuf buf) {
		tire = buf.readInt();
		item1 = buf.readItem();
		item2 = buf.readItem();
		item3 = buf.readItem();
		update= buf.readItem();
	}
	
	public void toBytes(FriendlyByteBuf buf) {
		buf.writeInt(tire);
		buf.writeItem(item1);
		buf.writeItem(item2);
		buf.writeItem(item3);
		buf.writeItem(update);
	}
	
	public boolean handle(Supplier<NetworkEvent.Context > supplier) {
		NetworkEvent.Context context = supplier.get();
		context.enqueueWork(() ->{
			//サーバーサイドで実行される
			ServerPlayer player = context.getSender();
			GeneratorMenu gm = (GeneratorMenu)player.containerMenu;
			switch(tire) {
			case 0:
				gm.data.set(45, Item.getId(update.getItem()));
				gm.data.set(50, update.getCount());
				break;
			case 1:
				gm.data.set(15, Item.getId(item1.getItem()));
				gm.data.set(16, Item.getId(item2.getItem()));
				gm.data.set(17, Item.getId(item3.getItem()));
				gm.data.set(30, item1.getCount());
				gm.data.set(31, item2.getCount());
				gm.data.set(32, item3.getCount());
				gm.data.set(46, Item.getId(update.getItem()));
				gm.data.set(51, update.getCount());
				break;
			case 2:
				gm.data.set(18, Item.getId(item1.getItem()));
				gm.data.set(19, Item.getId(item2.getItem()));
				gm.data.set(20, Item.getId(item3.getItem()));
				gm.data.set(33, item1.getCount());
				gm.data.set(34, item2.getCount());
				gm.data.set(35, item3.getCount());
				gm.data.set(47, Item.getId(update.getItem()));
				gm.data.set(52, update.getCount());
				break;
			case 3:
				gm.data.set(21, Item.getId(item1.getItem()));
				gm.data.set(22, Item.getId(item2.getItem()));
				gm.data.set(23, Item.getId(item3.getItem()));
				gm.data.set(36, item1.getCount());
				gm.data.set(37, item2.getCount());
				gm.data.set(38, item3.getCount());
				gm.data.set(48, Item.getId(update.getItem()));
				gm.data.set(53, update.getCount());
				break;
			case 4:
				gm.data.set(24, Item.getId(item1.getItem()));
				gm.data.set(25, Item.getId(item2.getItem()));
				gm.data.set(26, Item.getId(item3.getItem()));
				gm.data.set(39, item1.getCount());
				gm.data.set(40, item2.getCount());
				gm.data.set(41, item3.getCount());
				gm.data.set(49, Item.getId(update.getItem()));
				gm.data.set(54, update.getCount());
				break;
			case 5:
				gm.data.set(27, Item.getId(item1.getItem()));
				gm.data.set(28, Item.getId(item2.getItem()));
				gm.data.set(29, Item.getId(item3.getItem()));
				gm.data.set(42, item1.getCount());
				gm.data.set(43, item2.getCount());
				gm.data.set(44, item3.getCount());
				
				break;
				
			}
		});
		return true;
	}

}
