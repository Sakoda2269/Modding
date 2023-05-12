package mod4.pvpmod.blocks.generator;

import mod4.pvpmod.PVPmod;
import mod4.pvpmod.init.TileEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
			if(player.isCreative()) {//クリエイティブの場合設定画面を開く
				if(be instanceof GeneratorTile) {
					NetworkHooks.openScreen(((ServerPlayer)player), (GeneratorTile)be, pos);//guiを開く
				}else {
					throw new IllegalStateException("Our Container provider is missing!");
				}
			}else if(!player.isSpectator()){//サバイバルなどの場合アップグレードなどの処理
				GeneratorTile tile = (GeneratorTile)be;
				int tire = tile.data.get(GeneratorTile.TIRE_INDEX);
				Item upgradeItem = Item.byId(tile.data.get(45 + tire));
				int upgradeCount = tile.data.get(50 + tire);
				if(tire == tile.data.get(GeneratorTile.MAXUPGRADE_INDEX)) {
					player.displayClientMessage(Component.translatable("block." + PVPmod.MOD_ID + ".generator_block.maxupgrade"), false);
				} else {
					if(player.isShiftKeyDown()) {
						if(upgradeCount == 0) {
							tile.data.set(GeneratorTile.TIRE_INDEX, tire + 1);
							String msgHead = Component.translatable("block." + PVPmod.MOD_ID + ".generator_block.generator").getString();
							String msgTail = Component.translatable("block." + PVPmod.MOD_ID + ".generator_block.upgrade_done").getString();;
							player.displayClientMessage(Component.literal(msgHead + (tire + 1) + msgTail), false);
							for(int i = 0; i < 10; i++) {
								world.addFreshEntity(new FireworkRocketEntity(world, pos.getX() + Math.random(), pos.getY() + 1, pos.getZ() + Math.random(), new ItemStack(Items.FIREWORK_ROCKET)));
							}
						} else {
							int need = 0;
							for(Slot slot : player.inventoryMenu.slots) {
								if(slot.getItem().getItem().equals(upgradeItem)) {
									need += slot.getItem().getCount();
								}
							}
							if(need >= upgradeCount) {
								for(Slot slot : player.inventoryMenu.slots) {
									if(upgradeCount <= 0) {
										break;
									}
									if(slot.getItem().getItem().equals(upgradeItem)) {
										int left = slot.getItem().getCount();
										if(upgradeCount >= left) {
											upgradeCount -= left;
											slot.set(ItemStack.EMPTY);
										}else {
											slot.set(new ItemStack(upgradeItem, left - upgradeCount));
											upgradeCount = 0;
											break;
										}
									}
								}
								tile.data.set(GeneratorTile.TIRE_INDEX, tire + 1);
								world.addFreshEntity(new FireworkRocketEntity(world, new ItemStack(Items.FIREWORK_STAR), player));
								String msgHead = Component.translatable("block." + PVPmod.MOD_ID + ".generator_block.generator").getString();
								String msgTail = Component.translatable("block." + PVPmod.MOD_ID + ".generator_block.upgrade_done").getString();;
								player.displayClientMessage(Component.literal(msgHead + (tire + 1) + msgTail), false);
								for(int i = 0; i < 10; i++) {
									world.addFreshEntity(new FireworkRocketEntity(world, pos.getX() + Math.random(), pos.getY() + 1, pos.getZ() + Math.random(), new ItemStack(Items.FIREWORK_STAR)));
								}
								
							} else {
								player.displayClientMessage(Component.translatable("block." + PVPmod.MOD_ID + ".generator_block.cant_upgrade"), false);
							}
						}
					} else {
						if(tire == tile.data.get(GeneratorTile.MAXUPGRADE_INDEX)) {
							player.displayClientMessage(Component.translatable("block." + PVPmod.MOD_ID + ".generator_block.maxupgrade"), false);
						}else {
							if(upgradeCount == 0) {
								if(tire == 0) {
									player.displayClientMessage(Component.translatable("block." + PVPmod.MOD_ID + ".generator_block.activate"), false);
								} else {
									player.displayClientMessage(Component.translatable("block." + PVPmod.MOD_ID + ".generator_block.can_upgrade_free"), false);
								}
							}else {
								if(tire == 0) {
									player.displayClientMessage(Component.translatable("block." + PVPmod.MOD_ID + ".generator_block.activate"), false);
									player.displayClientMessage(Component.literal("起動には" + Component.translatable(upgradeItem.getDescriptionId()).getString() + "が"
											+ upgradeCount + "個必要です"), false);
								}else {
									player.displayClientMessage(Component.translatable("block." + PVPmod.MOD_ID + ".generator_block.can_upgrade_free"), false);
									player.displayClientMessage(Component.literal("アップグレードには" + Component.translatable(upgradeItem.getDescriptionId()).getString() + "が"
											+ upgradeCount + "個必要です"), false);
								}

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
