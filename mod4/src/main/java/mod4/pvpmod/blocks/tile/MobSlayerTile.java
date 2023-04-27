package mod4.pvpmod.blocks.tile;


import java.util.List;

import mod4.pvpmod.init.TileEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class MobSlayerTile extends BlockEntity{
	
	int timer = 0;
	boolean isActive = true;

	public MobSlayerTile(BlockEntityType<?> blockEntity, BlockPos pos, BlockState state) {
		super(blockEntity, pos, state);
	}
	
	public MobSlayerTile(BlockPos pos, BlockState state) {
		super(TileEntityInit.MOB_SLAYER.get(), pos, state);
	}
	
	@Override
	protected void saveAdditional(CompoundTag nbt) {
		super.saveAdditional(nbt);
		nbt.putBoolean("active", isActive);
	}
	
	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		this.isActive = nbt.getBoolean("active");
	}
	
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		CompoundTag nbtTag = new CompoundTag();
		nbtTag.putBoolean("active", isActive);
		
		return super.getUpdatePacket();
	}
	
	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
		CompoundTag tag = pkt.getTag();
		isActive = tag.getBoolean("isActive");
		
	}

	public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T be) {
		MobSlayerTile tile = (MobSlayerTile) be;
		if(!level.isClientSide() && tile.isActive) {
			tile.timer++;
			if(tile.timer > 20) {
				tile.timer = 0;
				tile.hurtMobs();
			}
		}
	}
	
	public void toggel() {
		this.isActive = !this.isActive;
	}
	
	final int RANGE = 5;
	private void hurtMobs() {
		BlockPos topCorner = this.worldPosition.offset(RANGE, RANGE, RANGE);
		BlockPos bottomCorner = this.worldPosition.offset(-RANGE, -RANGE, -RANGE);
		AABB box = new AABB(topCorner, bottomCorner);
		
		List<Entity> entities = this.level.getEntities(null, box);
		for (Entity target : entities) {
			if(target instanceof LivingEntity && !(target instanceof Player)) {
				target.hurt(DamageSource.MAGIC, 2);
			}
		}
	}

}
