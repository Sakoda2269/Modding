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
	
	private final ItemStackHandler itemHandler = new ItemStackHandler(2) {
		@Override
		protected void onContentsChanged(int slot) {
			setChanged();
		}
	};
	
	protected final ContainerData data;
	
	private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

	public ShopTile(BlockPos pos, BlockState state) {
		super(TileEntityInit.SHOP.get(), pos, state);
		this.data = new ContainerData() {

			@Override
			public int get(int index) {
				// TODO 自動生成されたメソッド・スタブ
				return 0;
			}

			@Override
			public void set(int index, int value) {
				// TODO 自動生成されたメソッド・スタブ
				
			}

			@Override
			public int getCount() {
				// TODO 自動生成されたメソッド・スタブ
				return 0;
			}
			
		};
	}
	
	
	
	@Override
	public void load(CompoundTag nbt) {
		itemHandler.deserializeNBT(nbt.getCompound("inventory"));
		super.load(nbt);
	}


	@Override
	protected void saveAdditional(CompoundTag nbt) {
		nbt.put("inventory", itemHandler.serializeNBT());
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
		return new ShopMenu(id, inventoty, this, this.data);
	}

	@Override
	public Component getDisplayName() {
		return Component.literal("shop");
	}

}
