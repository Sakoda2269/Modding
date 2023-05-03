package mod4.pvpmod.blocks.generator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import mod4.pvpmod.init.TileEntityInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

@SuppressWarnings("removal")
public class GeneratorTile extends BlockEntity implements MenuProvider{
	
	private int  progress = 0;
	private int maxProgress = 70;
	protected final ContainerData data;
	
	
	
	private final ItemStackHandler itemHandler = new ItemStackHandler(9) { //保有できるアイテムの数（カスタムスロット数）
		@Override
		protected void onContentsChanged(int slot) {
			setChanged();
		}
	};
	
	private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
	
	
	public GeneratorTile(BlockPos pos, BlockState state) {
		super(TileEntityInit.GENERATOR.get(), pos, state);
		this.data = new ContainerData() {//アイテム以外のデータを管理

			@Override
			public int get(int index) {//データを取得
				return switch(index) {
				case 0 -> GeneratorTile.this.progress;
				case 1 -> GeneratorTile.this.maxProgress;
				default -> 0;
				};
			}

			@Override
			public void set(int index, int value) {//データを設定
				switch (index) {
				case 0 -> GeneratorTile.this.progress = value;
				case 1-> GeneratorTile.this.maxProgress = value;
				}
			}

			@Override
			public int getCount() {//管理するデータの個数
				return 2;
			}
			
		};
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
	public void load(CompoundTag nbt) {//スロットのアイテム情報の読み込み
		super.load(nbt);
		System.out.println("loaded!");
		itemHandler.deserializeNBT(nbt.getCompound("inventory"));
	}

	@Override
	protected void saveAdditional(CompoundTag nbt) {//スロットのアイテム情報の保存
		super.saveAdditional(nbt);
		System.out.println("added");
		nbt.put("inventory", itemHandler.serializeNBT());
	}
	
	
	
	public void drops() {//破壊された時の処理
		SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
		for(int i = 0; i < itemHandler.getSlots(); i++) {
			inventory.setItem(i, itemHandler.getStackInSlot(i));
		}
		Containers.dropContents(this.level, this.worldPosition,  inventory);//アイテムをドロップ
	}

	@Override
	public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {//必要
		
		if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return lazyItemHandler.cast();
		}
		
		return super.getCapability(cap, side);
	}

	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {//Menuを紐づける
		return new GeneratorMenu(id, inventory, this, this.data);
	}

	@Override
	public Component getDisplayName() {//guiに表示される名前
		return Component.literal("Generator");
	}
	
	
	
	public static void tick(Level world, BlockPos pos, BlockState state, GeneratorTile entity) {//1/20秒ごとに処理をする
		if(world.isClientSide) {
			return;
		}
		setChanged(world, pos, state);
	}
	
	
	public static void craftItem(GeneratorTile entity) {
			//entity.itemHandler.extractItem(1, 1, false);
			//entity.itemHandler.setStackInSlot(2, new ItemStack(Items.DIAMOND, entity.itemHandler.getStackInSlot(2).getCount() + 1));
	}
	
	
	

	

}
