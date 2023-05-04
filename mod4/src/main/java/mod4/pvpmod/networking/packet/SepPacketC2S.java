package mod4.pvpmod.networking.packet;


import java.util.function.Supplier;

import mod4.pvpmod.blocks.generator.GeneratorMenu;
import mod4.pvpmod.blocks.generator.GeneratorTile;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public class SepPacketC2S {
	
	int tire;
	int num1;
	int num2;
	int num3;
	
	public SepPacketC2S(int tire, int value1, int value2, int value3) {
		this.tire = tire;
		this.num1 = value1;
		this.num2 = value2;
		this.num3 = value3;
	}
	
	public SepPacketC2S(FriendlyByteBuf buf) {
		this.tire = buf.readInt();
		this.num1 = buf.readInt();
		this.num2 = buf.readInt();
		this.num3 = buf.readInt();
	}
	
	public void toBytes(FriendlyByteBuf buf) {
		buf.writeInt(this.tire);
		buf.writeInt(this.num1);
		buf.writeInt(this.num2);
		buf.writeInt(this.num3);
	}
	
	public boolean handle(Supplier<NetworkEvent.Context > supplier) {
		NetworkEvent.Context context = supplier.get();
		context.enqueueWork(() ->{
			//サーバーサイドで実行される
			ServerPlayer player = context.getSender();
			GeneratorMenu menu = (GeneratorMenu)player.containerMenu;
			menu.data.set(GeneratorTile.EDIT_TIRE_INDEX, tire);
			switch(tire) {
			case 0:
				break;
			case 1:
				menu.data.set(0, num1);
				menu.data.set(1, num2);
				menu.data.set(2, num3);
				break;
			case 2:
				menu.data.set(3, num1);
				menu.data.set(4, num2);
				menu.data.set(5, num3);
				break;
			case 3:
				menu.data.set(6, num1);
				menu.data.set(7, num2);
				menu.data.set(8, num3);
				break;
			case 4:
				menu.data.set(9, num1);
				menu.data.set(10, num2);
				menu.data.set(11, num3);
				break;
			case 5:
				menu.data.set(12, num1);
				menu.data.set(13, num2);
				menu.data.set(14, num3);
				break;
			}
			
		});
		return true;
	}
	
}