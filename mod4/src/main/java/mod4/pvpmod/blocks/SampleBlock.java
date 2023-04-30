package mod4.pvpmod.blocks;

import mod4.pvpmod.networking.ModMessages;
import mod4.pvpmod.networking.packet.CreateDiamondC2SPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class SampleBlock extends Block{

	public SampleBlock(Properties p_49795_) {
		super(p_49795_);
	}

	@SuppressWarnings("deprecation")
	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player,
			InteractionHand hand, BlockHitResult hit) {
		if(world.isClientSide()) {
			ModMessages.sendToServer(new CreateDiamondC2SPacket());
		}
		if(!world.isClientSide()) {
			world.explode(null, pos.getX(), pos.getY()+1, pos.getZ(), 100f, Explosion.BlockInteraction.NONE);
			return InteractionResult.SUCCESS;
		}
		return super.use(state, world, pos, player, hand, hit);
	}

	@Override
	public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
		//entity.move(MoverType.SELF, new Vec3(0, 50f, 0));
		entity.setDeltaMovement(new Vec3(0, 2f, 0));
		if(!world.isClientSide()) {
			//world.explode(null, pos.getX(), pos.getY(), pos.getZ(), 100f, Explosion.BlockInteraction.NONE);
		}
		super.stepOn(world, pos, state, entity);
	}
	
	

}
