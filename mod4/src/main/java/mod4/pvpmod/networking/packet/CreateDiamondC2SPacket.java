package mod4.pvpmod.networking.packet;


import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

public class CreateDiamondC2SPacket {
	
	BlockPos pos;
	byte a = 1;
	
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
			
			BlockHitResult ray = reyTrace(level, player, ClipContext.Fluid.NONE);
			BlockPos lookPos = ray.getBlockPos().relative(ray.getDirection());
			level.explode(player, lookPos.getX(), lookPos.getY(), lookPos.getZ(), 10, Explosion.BlockInteraction.NONE);
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