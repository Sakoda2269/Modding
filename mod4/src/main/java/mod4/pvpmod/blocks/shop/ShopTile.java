package mod4.pvpmod.blocks.shop;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import mod4.pvpmod.init.TileEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

@SuppressWarnings("removal")
public class ShopTile extends BlockEntity implements MenuProvider{
	
	private final ItemStackHandler itemHandler = new ItemStackHandler(40) {
		@Override
		protected void onContentsChanged(int slot) {
			setChanged();
		}
	};
	
	protected final ContainerData data;
	
	private static final int SELL_ITEM_INDEX = 120;
	private static final int SELL_NUM_INDEX = 240;
	private static final int COST_ITEM_INDEX = 360;
	private static final int COST_NUM_INDEX = 480;
	private static final int PAGE_INDEX = 480;
	private static final int COUNT = 481;
	
	private int[][] sellItem = new int[6][20];
	private int[][] sellNum = new int[6][20];
	private int[][] costItem = new int[6][20];
	private int[][] costNum = new int[6][20];
	private int page = 0;
	
	
	private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

	public ShopTile(BlockPos pos, BlockState state) {
		super(TileEntityInit.SHOP.get(), pos, state);
		this.data = new ContainerData() {

			@Override
			public int get(int index) {
				if(index < SELL_ITEM_INDEX) {
					return sellItem[(int)(index / 20)][index % 20];
				} else if(index < SELL_NUM_INDEX) {
					return sellNum[(int)((index - 120) / 20)][index % 20];
				} else if(index < COST_ITEM_INDEX) {
					return costItem[(int)((index - 240) / 20)][index % 20];
				} else if(index < COST_NUM_INDEX) {
					return costNum[(int)((index - 360) / 20)][index % 20];
				} else if(index == PAGE_INDEX) {
					return page;
				}
				return 0;
			}

			@Override
			public void set(int index, int value) {
				if(index < SELL_ITEM_INDEX) {
					sellItem[(int)(index / 20)][index % 20] = value;
				} else if(index < SELL_NUM_INDEX) {
					sellNum[(int)(index / 20)][index % 20] = value;
				} else if(index < COST_ITEM_INDEX) {
					costItem[(int)(index / 20)][index % 20] = value;
				} else if(index < COST_NUM_INDEX) {
					costNum[(int)(index / 20)][index % 20] = value;
				} else if(index == PAGE_INDEX) {
					page = value;
				}
			}
			
			@Override
			public int getCount() {
				// TODO 自動生成されたメソッド・スタブ
				return COUNT;
			}
			
		};
	}
	
	
	
	@Override
	public void load(CompoundTag nbt) {
		itemHandler.deserializeNBT(nbt.getCompound("inventory"));
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 20; j++) {
				sellItem[i][j] = nbt.getInt("sellItems" + i + "" +  j);
				sellNum[i][j] = nbt.getInt("sellNums" + i + "" + j);
				costItem[i][j] = nbt.getInt("costItems" + i + "" +  j);
				costNum[i][j] = nbt.getInt("costlNums" + i + "" + j);
			}
		}
		page = nbt.getInt("page");
		super.load(nbt);
	}


	@Override
	protected void saveAdditional(CompoundTag nbt) {
		nbt.put("inventory", itemHandler.serializeNBT());
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 20; j++) {
				nbt.putInt("sellItems" + i + "" +  j, sellItem[i][j]);
				nbt.putInt("sellNums" + i + "" + j, sellNum[i][j]);
				nbt.putInt("costItems" + i + "" +  j, costItem[i][j]);
				nbt.putInt("costlNums" + i + "" + j, costNum[i][j]);
			}
		}
		nbt.putInt("page", page);
		super.saveAdditional(nbt);
	}



	@Override
	public void onLoad() {//必要
		super.onLoad();
		lazyItemHandler = LazyOptional.of(() -> itemHandler);
	}
	
	@Override
	public void invalidateCaps() {//必要
		super.invalidateCaps();
		lazyItemHandler.invalidate();
	}
	
	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {//必要

		if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return lazyItemHandler.cast();
		}

		return super.getCapability(cap, side);
	}
	
	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inventoty, Player player) {
		/*if(player.isCreative()) {
			System.out.println("aaa");
			return new ShopMenu(id, inventoty, this, this.data);
		} else {
			System.out.println("iii");
			return new ShopMenu2(id, inventoty, this, this.data);
		}*/
		return new ShopMenu(id, inventoty, this, this.data);
		
	}

	@Override
	public Component getDisplayName() {
		return Component.literal("shop");
	}

}
