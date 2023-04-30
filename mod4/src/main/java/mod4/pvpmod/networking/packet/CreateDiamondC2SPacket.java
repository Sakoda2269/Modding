package mod4.pvpmod.networking.packet;


import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraftforge.network.NetworkEvent;

public class CreateDiamondC2SPacket {
	
	public CreateDiamondC2SPacket() {
		
	}
	
	public CreateDiamondC2SPacket(FriendlyByteBuf buf) {
		
	}
	
	public void toBytes(FriendlyByteBuf buf) {
		
	}
	
	public boolean handle(Supplier<NetworkEvent.Context > supplier) {
		NetworkEvent.Context context = supplier.get();
		context.enqueueWork(() ->{
			//サーバーサイドで実行される
			ServerPlayer player = context.getSender();
			ServerLevel level = player.getLevel();
			
			EntityType.COW.spawn(level, null, null, player.blockPosition(), MobSpawnType.COMMAND, true, false);
		});
		return true;
	}

}
