package mod4.pvpmod.networking.packet;


import java.util.function.Supplier;

import mod4.pvpmod.blocks.generator.GeneratorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

public class SepSenderC2S {
	
	BlockPos pos;
	byte a = 1;
	String value;
	int num;
	
	public SepSenderC2S(int value) {
		num = value;
	}
	
	public SepSenderC2S(FriendlyByteBuf buf) {
		num = buf.readInt();
	}
	
	public void toBytes(FriendlyByteBuf buf) {
		buf.writeInt(num);
	}
	
	public boolean handle(Supplier<NetworkEvent.Context > supplier) {
		NetworkEvent.Context context = supplier.get();
		context.enqueueWork(() ->{
			//サーバーサイドで実行される
			ServerPlayer player = context.getSender();
			System.out.println(num);
			GeneratorMenu menu = (GeneratorMenu)player.containerMenu;
			menu.data.set(0, num);
			//System.out.println(player.containerMenu.containerId);
			
		});
		return true;
	}
	
	protected static BlockHitResult reyTrace(Level world, Player player, ClipContext.Fluid fluidMode) {
		double range = 5;
		
		float f = player.getXRot();
		float f1 = player.getYRot();
		Vec3 vector3d = player.getEyePosition(1.0f);
		float f2 = Mth.cos(-f1 * ((float)Math.PI / 180f) - (float)Math.PI);
		float f3 = Mth.sin(-f1 * ((float)Math.PI / 180f) - (float)Math.PI);
		float f4 = -Mth.cos(-f * ((float)Math.PI / 180f));
		float f5 = Mth.sin(-f * ((float)Math.PI / 180f));
		float f6 = f3 * f4;
		float f7 = f2 * f4;
		Vec3 vector3d1 = vector3d.add((double)f6 * range, (double)f5 * range, (double)f7 * range);
		return world.clip(new ClipContext(vector3d, vector3d1, ClipContext.Block.OUTLINE, fluidMode, player));
	}
	
}