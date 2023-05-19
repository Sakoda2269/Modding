package mod4.pvpmod.networking;

import mod4.pvpmod.PVPmod;
import mod4.pvpmod.networking.packet.C2SPacket;
import mod4.pvpmod.networking.packet.InsertItemPacketC2S;
import mod4.pvpmod.networking.packet.ItemPacketC2S;
import mod4.pvpmod.networking.packet.SepPacketC2S;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
	
	private static SimpleChannel INSTANCE;
	
	private static int packetId = 0;
	
	private static int id() {
		return packetId++;
	}
	
	public static void register() {
		SimpleChannel net = NetworkRegistry.ChannelBuilder
				.named(new ResourceLocation(PVPmod.MOD_ID, "messages"))
				.networkProtocolVersion(() -> "1.0")
				.clientAcceptedVersions(s -> true)
				.serverAcceptedVersions(s -> true)
				.simpleChannel();
		
		INSTANCE = net;
		
		net.messageBuilder(InsertItemPacketC2S.class, id(), NetworkDirection.PLAY_TO_SERVER)
			.decoder(InsertItemPacketC2S :: new)
			.encoder(InsertItemPacketC2S :: toBytes)
			.consumerMainThread(InsertItemPacketC2S :: handle)
			.add();
		
		net.messageBuilder(SepPacketC2S.class, id(), NetworkDirection.PLAY_TO_SERVER)
			.decoder(SepPacketC2S :: new)
			.encoder(SepPacketC2S :: toBytes)
			.consumerMainThread(SepPacketC2S :: handle)
			.add();
		
		net.messageBuilder(ItemPacketC2S.class, id(), NetworkDirection.PLAY_TO_SERVER)
		.decoder(ItemPacketC2S :: new)
		.encoder(ItemPacketC2S :: toBytes)
		.consumerMainThread(ItemPacketC2S :: handle)
		.add();
		
		net.messageBuilder(C2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
			.decoder(C2SPacket :: new)
			.encoder(C2SPacket :: toBytes)
			.consumerMainThread(C2SPacket :: handle)
			.add();
			
		
		
	}
	
	public static <MSG> void sendToServer(MSG message) {
		INSTANCE.sendToServer(message);
	}
	
	public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
		INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
	}
	
}
