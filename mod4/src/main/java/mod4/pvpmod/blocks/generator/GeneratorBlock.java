package mod4.pvpmod.blocks.generator;

import mod4.pvpmod.PVPmod;
import mod4.pvpmod.init.TileEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

public class GeneratorBlock extends BaseEntityBlock{

	public GeneratorBlock(Properties props) {
		super(props);
	}
	

	@Override
	public RenderShape getRenderShape(BlockState state) {//必要
		// TODO 自動生成されたメソッド・スタブ
		return RenderShape.MODEL;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {//必要
		return TileEntityInit.GENERATOR.get().create(pos, state);
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player,
			InteractionHand hand, BlockHitResult result) {//右クリックした時ときの処理
		if(!world.isClientSide()) {
			BlockEntity be = world.getBlockEntity(pos);
			if(player.isCreative()) {
				if(be instanceof GeneratorTile) {
					NetworkHooks.openScreen(((ServerPlayer)player), (GeneratorTile)be, pos);//guiを開く
				}else {
					throw new IllegalStateException("Our Container provider is missing!");
				}
			}else if(!player.isSpectator()){
				GeneratorTile tile = (GeneratorTile)be;
				int tire = tile.data.get(GeneratorTile.TIRE_INDEX);
				Item upgradeItem = Item.byId(tile.data.get(45 + tire));
				int upgradeCount = tile.data.get(50 + tire);
				if(player.isShiftKeyDown()) {
					if(upgradeCount == 0) {
						tile.data.set(GeneratorTile.TIRE_INDEX, tire + 1);
					}
				} else {
					if(tire == tile.data.get(GeneratorTile.MAXUPGRADE_INDEX)) {
						player.displayClientMessage(Component.translatable("block." + PVPmod.MOD_ID + "generator_block.maxupgrade"), false);
					}else {
						if(upgradeCount == 0) {
							player.displayClientMessage(Component.translatable("block." + PVPmod.MOD_ID + ".generator_block.activate"), false);
						}else {
							if(tire == 0) {
								player.displayClientMessage(Component.translatable("block." + PVPmod.MOD_ID + ".generator_block.activate"), false);
								player.displayClientMessage(Component.literal("起動には" + Component.translatable(upgradeItem.getDescriptionId()).getString() + "が"
								+ upgradeCount + "個必要です"), false);
							}else {
								
							}
							
						}
					}
				}
				
			}
		}
		return InteractionResult.sidedSuccess(world.isClientSide());
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState,
			boolean isMoving) {//破壊された時の処理
		if(state.getBlock() != newState.getBlock() &&  !world.isClientSide()) {
			BlockEntity be = world.getBlockEntity(pos);
			if(be instanceof GeneratorTile) {
				((GeneratorTile) be).drops();//中にあるアイテムをドロップ
			}
		}
		
		super.onRemove(state, world, pos, newState, isMoving);
	}


	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state,
			BlockEntityType<T> type) {//tickをする処理
		return createTickerHelper(type, TileEntityInit.GENERATOR.get(), GeneratorTile :: tick);
	}
	
	
	

}
