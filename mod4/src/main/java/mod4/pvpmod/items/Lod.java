package mod4.pvpmod.items;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

@SuppressWarnings("unused")
public class Lod extends Item{

	public Lod(Properties p_41383_) {
		super(p_41383_);
	}

	
	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		
		BlockHitResult ray = reyTrace(world, player, ClipContext.Fluid.NONE);
		BlockPos lookPos = ray.getBlockPos().relative(ray.getDirection());
		
		player.setDeltaMovement(new Vec3(0, 2f, 0));
		
		if(!world.isClientSide()) {
			//world.explode(null, lookPos.getX(),  lookPos.getY(),  lookPos.getZ(), 10, Explosion.BlockInteraction.NONE);
			
			//world.addFreshEntity(new LightningBolt(EntityType.LIGHTNING_BOLT, world));
			
			
			
			/*LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(world);
			bolt.moveTo(new Vec3(lookPos.getX(), lookPos.getY(), lookPos.getZ()));
			world.addFreshEntity(bolt);*/
			
		}
		//player.setPos(lookPos.getX(), lookPos.getY(), lookPos.getZ());
		
		//player.jumpFromGround();
		
		return super.use(world, player, hand);
	}
	
	protected static BlockHitResult reyTrace(Level world, Player player, ClipContext.Fluid fluidMode) {
		double range = 60;
		
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
