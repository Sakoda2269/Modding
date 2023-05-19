package mod4.pvpmod.networking.packet;


import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class C2SPacket {
	
	public C2SPacket() {
		
	}
	
	public C2SPacket(FriendlyByteBuf buf) {
		
	}
	
	public void toBytes(FriendlyByteBuf buf) {
		
	}
	
	public boolean handle(Supplier<NetworkEvent.Context > supplier) {
		NetworkEvent.Context context = supplier.get();
		context.enqueueWork(() ->{
			//サーバーサイドで実行される
			//ServerPlayer player = context.getSender();
			//ServerLevel level = player.getLevel();
		});
		return true;
	}

}
