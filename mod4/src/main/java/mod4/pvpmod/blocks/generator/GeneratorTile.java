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
	
	protected final ContainerData data;
	

	private int sep11 = -1;
	private int sep12 = 4;
	private int sep13 = 3;
	private int sep14 = 2;
	private int sep15 = 1;
	private int sep21 = 5;
	private int sep22 = 4;
	private int sep23 = 3;
	private int sep24 = 2;
	private int sep25 = 1;
	private int sep31 = 5;
	private int sep32 = 4;
	private int sep33 = 3;
	private int sep34 = 2;
	private int sep35 = 1;
	
	private int maxUpgrade = 5;
	
	
	
	private final ItemStackHandler itemHandler = new ItemStackHandler(4) { //保有できるアイテムの数（カスタムスロット数）
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
				case 0 -> GeneratorTile.this.sep11;
				case 1 -> GeneratorTile.this.sep12;
				case 2 -> GeneratorTile.this.sep13;
				case 3 -> GeneratorTile.this.sep14;
				case 4 -> GeneratorTile.this.sep15;
				case 5 -> GeneratorTile.this.sep21;
				case 6 -> GeneratorTile.this.sep22;
				case 7 -> GeneratorTile.this.sep23;
				case 8 -> GeneratorTile.this.sep24;
				case 9 -> GeneratorTile.this.sep25;
				case 10 -> GeneratorTile.this.sep31;
				case 11 -> GeneratorTile.this.sep32;
				case 12 -> GeneratorTile.this.sep33;
				case 13 -> GeneratorTile.this.sep34;
				case 14 -> GeneratorTile.this.sep35;
				case 15 -> GeneratorTile.this.maxUpgrade;
				default -> 0;
				};
			}

			@Override
			public void set(int index, int value) {//データを設定
				switch (index) {
				case 0 -> GeneratorTile.this.sep11 = value;
				case 1 -> GeneratorTile.this.sep12 = value;
				case 2 -> GeneratorTile.this.sep13 = value;
				case 3 -> GeneratorTile.this.sep14 = value;
				case 4 -> GeneratorTile.this.sep15 = value;
				case 5 -> GeneratorTile.this.sep21 = value;
				case 6 -> GeneratorTile.this.sep22 = value;
				case 7 -> GeneratorTile.this.sep23 = value;
				case 8 -> GeneratorTile.this.sep24 = value;
				case 9 -> GeneratorTile.this.sep25 = value;
				case 10 -> GeneratorTile.this.sep31 = value;
				case 11 -> GeneratorTile.this.sep32 = value;
				case 12 -> GeneratorTile.this.sep33 = value;
				case 13 -> GeneratorTile.this.sep34 = value;
				case 14 -> GeneratorTile.this.sep35 = value;
				case 15 -> GeneratorTile.this.maxUpgrade = value;
				}
			}

			@Override
			public int getCount() {//管理するデータの個数
				return 16;
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

		sep11 = nbt.getInt("sep11");
		sep12 = nbt.getInt("sep12");
		sep13 = nbt.getInt("sep13");
		sep14 = nbt.getInt("sep14");
		sep15 = nbt.getInt("sep15");
		sep21 = nbt.getInt("sep21");
		sep22 = nbt.getInt("sep22");
		sep23 = nbt.getInt("sep23");
		sep24 = nbt.getInt("sep24");
		sep25 = nbt.getInt("sep25");
		sep31 = nbt.getInt("sep31");
		sep32 = nbt.getInt("sep32");
		sep33 = nbt.getInt("sep33");
		sep34 = nbt.getInt("sep34");
		sep35 = nbt.getInt("sep35");
		itemHandler.deserializeNBT(nbt.getCompound("inventory"));
	}

	@Override
	protected void saveAdditional(CompoundTag nbt) {//スロットのアイテム情報の保存
		super.saveAdditional(nbt);

		nbt.putInt("sep11", sep11);
		nbt.putInt("sep12", sep12);
		nbt.putInt("sep13", sep13);
		nbt.putInt("sep14", sep14);
		nbt.putInt("sep15", sep15);
		nbt.putInt("sep21", sep21);
		nbt.putInt("sep22", sep22);
		nbt.putInt("sep23", sep23);
		nbt.putInt("sep24", sep24);
		nbt.putInt("sep25", sep25);
		nbt.putInt("sep31", sep31);
		nbt.putInt("sep32", sep32);
		nbt.putInt("sep33", sep33);
		nbt.putInt("sep34", sep34);
		nbt.putInt("sep35", sep35);
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
