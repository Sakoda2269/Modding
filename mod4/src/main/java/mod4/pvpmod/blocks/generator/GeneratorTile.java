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
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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

	private int[] sep1 = {5, 5, 5};
	private int[] sep2 = {4, 4, 4};
	private int[] sep3 = {3, 3, 3};
	private int[] sep4 = {2, 2, 2};
	private int[] sep5 = {1, 1, 1};

	private int[] item1 = {0, 0, 0};
	private int[] item2 = {0, 0, 0};
	private int[] item3 = {0, 0, 0};
	private int[] item4 = {0, 0, 0};
	private int[] item5 = {0, 0, 0};
	
	private int[] itemCount1 = {0, 0, 0};
	private int[] itemCount2 = {0, 0, 0};
	private int[] itemCount3 = {0, 0, 0};
	private int[] itemCount4 = {0, 0, 0};
	private int[] itemCount5 = {0, 0, 0};
	
	private int[] updateItem = {0, 0, 0, 0, 0};
	private int[] updateItemCount = {0, 0, 0, 0, 0};
	
	private int progress = 0;

	//	private int sep11 = -1;
	//	private int sep12 = 5;
	//	private int sep13 = 5;

	//	private int item11 = 1;
	//	private int item12 = 1;
	//	private int item13 = 1;

	
	private int editTire = 0;
	private int tire = 0;

	private int maxUpgrade = 5;

	public static final int EDIT_TIRE_INDEX = 56;
	public static final int TIRE_INDEX = 57;
	public static final int MAXUPGRADE_INDEX = 55;
	public static final int PROGRESS_INDEX = 58;

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
				case 0 -> GeneratorTile.this.sep1[0];
				case 1 -> GeneratorTile.this.sep1[1];
				case 2 -> GeneratorTile.this.sep1[2];
				case 3 -> GeneratorTile.this.sep2[0];
				case 4 -> GeneratorTile.this.sep2[1];
				case 5 -> GeneratorTile.this.sep2[2];
				case 6 -> GeneratorTile.this.sep3[0];
				case 7 -> GeneratorTile.this.sep3[1];
				case 8 -> GeneratorTile.this.sep3[2];
				case 9 -> GeneratorTile.this.sep4[0];
				case 10 -> GeneratorTile.this.sep4[1];
				case 11 -> GeneratorTile.this.sep4[2];
				case 12 -> GeneratorTile.this.sep5[0];
				case 13 -> GeneratorTile.this.sep5[1];
				case 14 -> GeneratorTile.this.sep5[2];

				case 15 -> GeneratorTile.this.item1[0];
				case 16 -> GeneratorTile.this.item1[1];
				case 17 -> GeneratorTile.this.item1[2];
				case 18 -> GeneratorTile.this.item2[0];
				case 19 -> GeneratorTile.this.item2[1];
				case 20 -> GeneratorTile.this.item2[2];
				case 21 -> GeneratorTile.this.item3[0];
				case 22 -> GeneratorTile.this.item3[1];
				case 23 -> GeneratorTile.this.item3[2];
				case 24 -> GeneratorTile.this.item4[0];
				case 25 -> GeneratorTile.this.item4[1];
				case 26 -> GeneratorTile.this.item4[2];
				case 27 -> GeneratorTile.this.item5[0];
				case 28 -> GeneratorTile.this.item5[1];
				case 29 -> GeneratorTile.this.item5[2];
				
				case 30 -> GeneratorTile.this.itemCount1[0];
				case 31 -> GeneratorTile.this.itemCount1[1];
				case 32 -> GeneratorTile.this.itemCount1[2];
				case 33 -> GeneratorTile.this.itemCount2[0];
				case 34 -> GeneratorTile.this.itemCount2[1];
				case 35 -> GeneratorTile.this.itemCount2[2];
				case 36 -> GeneratorTile.this.itemCount3[0];
				case 37 -> GeneratorTile.this.itemCount3[1];
				case 38 -> GeneratorTile.this.itemCount3[2];
				case 39 -> GeneratorTile.this.itemCount4[0];
				case 40 -> GeneratorTile.this.itemCount4[1];
				case 41 -> GeneratorTile.this.itemCount4[2];
				case 42 -> GeneratorTile.this.itemCount5[0];
				case 43 -> GeneratorTile.this.itemCount5[1];
				case 44 -> GeneratorTile.this.itemCount5[2];
				
				case 45 -> GeneratorTile.this.updateItem[0];
				case 46 -> GeneratorTile.this.updateItem[1];
				case 47 -> GeneratorTile.this.updateItem[2];
				case 48 -> GeneratorTile.this.updateItem[3];
				case 49 -> GeneratorTile.this.updateItem[4];
				
				case 50 -> GeneratorTile.this.updateItemCount[0];
				case 51 -> GeneratorTile.this.updateItemCount[1];
				case 52 -> GeneratorTile.this.updateItemCount[2];
				case 53 -> GeneratorTile.this.updateItemCount[3];
				case 54 -> GeneratorTile.this.updateItemCount[4];
				
				case 55 -> GeneratorTile.this.maxUpgrade;
				case 56 -> GeneratorTile.this.editTire;
				case 57 -> GeneratorTile.this.tire;
				case 58-> GeneratorTile.this.progress;
				
				default -> 0;
				};
			}

			@Override
			public void set(int index, int value) {//データを設定
				switch (index) {
				case 0 -> GeneratorTile.this.sep1[0] = value;
				case 1 -> GeneratorTile.this.sep1[1] = value;
				case 2 -> GeneratorTile.this.sep1[2] = value;
				case 3 -> GeneratorTile.this.sep2[0] = value;
				case 4 -> GeneratorTile.this.sep2[1] = value;
				case 5 -> GeneratorTile.this.sep2[2] = value;
				case 6 -> GeneratorTile.this.sep3[0] = value;
				case 7 -> GeneratorTile.this.sep3[1] = value;
				case 8 -> GeneratorTile.this.sep3[2] = value;
				case 9 -> GeneratorTile.this.sep4[0] = value;
				case 10 -> GeneratorTile.this.sep4[1] = value;
				case 11 -> GeneratorTile.this.sep4[2] = value;
				case 12 -> GeneratorTile.this.sep5[0] = value;
				case 13 -> GeneratorTile.this.sep5[1] = value;
				case 14 -> GeneratorTile.this.sep5[2] = value;

				case 15 -> GeneratorTile.this.item1[0] = value;
				case 16 -> GeneratorTile.this.item1[1] = value;
				case 17 -> GeneratorTile.this.item1[2] = value;
				case 18 -> GeneratorTile.this.item2[0] = value;
				case 19 -> GeneratorTile.this.item2[1] = value;
				case 20 -> GeneratorTile.this.item2[2] = value;
				case 21 -> GeneratorTile.this.item3[0] = value;
				case 22 -> GeneratorTile.this.item3[1] = value;
				case 23 -> GeneratorTile.this.item3[2] = value;
				case 24 -> GeneratorTile.this.item4[0] = value;
				case 25 -> GeneratorTile.this.item4[1] = value;
				case 26 -> GeneratorTile.this.item4[2] = value;
				case 27 -> GeneratorTile.this.item5[0] = value;
				case 28 -> GeneratorTile.this.item5[1] = value;
				case 29 -> GeneratorTile.this.item5[2] = value;

				case 30 -> GeneratorTile.this.itemCount1[0] = value;
				case 31 -> GeneratorTile.this.itemCount1[1] = value;
				case 32 -> GeneratorTile.this.itemCount1[2] = value;
				case 33 -> GeneratorTile.this.itemCount2[0] = value;
				case 34 -> GeneratorTile.this.itemCount2[1] = value;
				case 35 -> GeneratorTile.this.itemCount2[2] = value;
				case 36 -> GeneratorTile.this.itemCount3[0] = value;
				case 37 -> GeneratorTile.this.itemCount3[1] = value;
				case 38 -> GeneratorTile.this.itemCount3[2] = value;
				case 39 -> GeneratorTile.this.itemCount4[0] = value;
				case 40 -> GeneratorTile.this.itemCount4[1] = value;
				case 41 -> GeneratorTile.this.itemCount4[2] = value;
				case 42 -> GeneratorTile.this.itemCount5[0] = value;
				case 43 -> GeneratorTile.this.itemCount5[1] = value;
				case 44 -> GeneratorTile.this.itemCount5[2] = value;
				
				case 45 -> GeneratorTile.this.updateItem[0] = value;
				case 46 -> GeneratorTile.this.updateItem[1] = value;
				case 47 -> GeneratorTile.this.updateItem[2] = value;
				case 48 -> GeneratorTile.this.updateItem[3] = value;
				case 49 -> GeneratorTile.this.updateItem[4] = value;
				
				case 50 -> GeneratorTile.this.updateItemCount[0] = value;
				case 51 -> GeneratorTile.this.updateItemCount[1] = value;
				case 52 -> GeneratorTile.this.updateItemCount[2] = value;
				case 53 -> GeneratorTile.this.updateItemCount[3] = value;
				case 54 -> GeneratorTile.this.updateItemCount[4] = value;
				
				case 55 -> GeneratorTile.this.maxUpgrade = value;
				case 56 -> GeneratorTile.this.editTire = value;
				case 57 -> GeneratorTile.this.tire = value;
				case 58-> GeneratorTile.this.progress = value;
				
				}
			}

			@Override
			public int getCount() {//管理するデータの個数
				return 59;
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

		sep1[0] = nbt.getInt("sep10");
		sep1[1] = nbt.getInt("sep11");
		sep1[2] = nbt.getInt("sep12");
		sep2[0] = nbt.getInt("sep20");
		sep2[1] = nbt.getInt("sep21");
		sep2[2] = nbt.getInt("sep22");
		sep3[0] = nbt.getInt("sep30");
		sep3[1] = nbt.getInt("sep31");
		sep3[2] = nbt.getInt("sep32");
		sep4[0] = nbt.getInt("sep40");
		sep4[1] = nbt.getInt("sep41");
		sep4[2] = nbt.getInt("sep42");
		sep5[0] = nbt.getInt("sep50");
		sep5[1] = nbt.getInt("sep51");
		sep5[2] = nbt.getInt("sep52");
		
		item1[0] = nbt.getInt("item10");
		item1[1] = nbt.getInt("item11");
		item1[2] = nbt.getInt("item12");
		item2[0] = nbt.getInt("item20");
		item2[1] = nbt.getInt("item21");
		item2[2] = nbt.getInt("item22");
		item3[0] = nbt.getInt("item30");
		item3[1] = nbt.getInt("item31");
		item3[2] = nbt.getInt("item32");
		item4[0] = nbt.getInt("item40");
		item4[1] = nbt.getInt("item41");
		item4[2] = nbt.getInt("item42");
		item5[0] = nbt.getInt("item50");
		item5[1] = nbt.getInt("item51");
		item5[2] = nbt.getInt("item52");
		
		itemCount1[0] = nbt.getInt("itemCount10");
		itemCount1[1] = nbt.getInt("itemCount11");
		itemCount1[2] = nbt.getInt("itemCount12");
		itemCount2[0] = nbt.getInt("itemCount20");
		itemCount2[1] = nbt.getInt("itemCount21");
		itemCount2[2] = nbt.getInt("itemCount22");
		itemCount3[0] = nbt.getInt("itemCount30");
		itemCount3[1] = nbt.getInt("itemCount31");
		itemCount3[2] = nbt.getInt("itemCount32");
		itemCount4[0] = nbt.getInt("itemCount40");
		itemCount4[1] = nbt.getInt("itemCount41");
		itemCount4[2] = nbt.getInt("itemCount42");
		itemCount5[0] = nbt.getInt("itemCount50");
		itemCount5[1] = nbt.getInt("itemCount51");
		itemCount5[2] = nbt.getInt("itemCount52");
		
		updateItem[0] = nbt.getInt("updateItem0");
		updateItem[1] = nbt.getInt("updateItem1");
		updateItem[2] = nbt.getInt("updateItem2");
		updateItem[3] = nbt.getInt("updateItem3");
		updateItem[4] = nbt.getInt("updateItem4");
		
		updateItemCount[0] = nbt.getInt("updateItemCount0");
		updateItemCount[1] = nbt.getInt("updateItemCount1");
		updateItemCount[2] = nbt.getInt("updateItemCount2");
		updateItemCount[3] = nbt.getInt("updateItemCount3");
		updateItemCount[4] = nbt.getInt("updateItemCount4");
		
		editTire = nbt.getInt("editT");
		tire = nbt.getInt("tire");
		progress = nbt.getInt("progress");
		itemHandler.deserializeNBT(nbt.getCompound("inventory"));
	}

	@Override
	protected void saveAdditional(CompoundTag nbt) {//スロットのアイテム情報の保存
		super.saveAdditional(nbt);

		nbt.putInt("sep10", sep1[0]);
		nbt.putInt("sep11", sep1[1]);
		nbt.putInt("sep12", sep1[2]);
		nbt.putInt("sep20", sep2[0]);
		nbt.putInt("sep21", sep2[1]);
		nbt.putInt("sep22", sep2[2]);
		nbt.putInt("sep30", sep3[0]);
		nbt.putInt("sep31", sep3[1]);
		nbt.putInt("sep32", sep3[2]);
		nbt.putInt("sep40", sep4[0]);
		nbt.putInt("sep41", sep4[1]);
		nbt.putInt("sep42", sep4[2]);
		nbt.putInt("sep50", sep5[0]);
		nbt.putInt("sep51", sep5[1]);
		nbt.putInt("sep52", sep5[2]);
		
		nbt.putInt("item10", item1[0]);
		nbt.putInt("item11", item1[1]);
		nbt.putInt("item12", item1[2]);
		nbt.putInt("item20", item2[0]);
		nbt.putInt("item21", item2[1]);
		nbt.putInt("item22", item2[2]);
		nbt.putInt("item30", item3[0]);
		nbt.putInt("item31", item3[1]);
		nbt.putInt("item32", item3[2]);
		nbt.putInt("item40", item4[0]);
		nbt.putInt("item41", item4[1]);
		nbt.putInt("item42", item4[2]);
		nbt.putInt("item50", item5[0]);
		nbt.putInt("item51", item5[1]);
		nbt.putInt("item52", item5[2]);
		
		nbt.putInt("itemCount10", itemCount1[0]);
		nbt.putInt("itemCount11", itemCount1[1]);
		nbt.putInt("itemCount12", itemCount1[2]);
		nbt.putInt("itemCount20", itemCount2[0]);
		nbt.putInt("itemCount21", itemCount2[1]);
		nbt.putInt("itemCount22", itemCount2[2]);
		nbt.putInt("itemCount30", itemCount3[0]);
		nbt.putInt("itemCount31", itemCount3[1]);
		nbt.putInt("itemCount32", itemCount3[2]);
		nbt.putInt("itemCount40", itemCount4[0]);
		nbt.putInt("itemCount41", itemCount4[1]);
		nbt.putInt("itemCount42", itemCount4[2]);
		nbt.putInt("itemCount50", itemCount5[0]);
		nbt.putInt("itemCount51", itemCount5[1]);
		nbt.putInt("itemCount52", itemCount5[2]);
		
		nbt.putInt("updateItem0", updateItem[0]);
		nbt.putInt("updateItem1", updateItem[1]);
		nbt.putInt("updateItem2", updateItem[2]);
		nbt.putInt("updateItem3", updateItem[3]);
		nbt.putInt("updateItem4", updateItem[4]);
		
		nbt.putInt("updateItemCount0", updateItemCount[0]);
		nbt.putInt("updateItemCount1", updateItemCount[1]);
		nbt.putInt("updateItemCount2", updateItemCount[2]);
		nbt.putInt("updateItemCount3", updateItemCount[3]);
		nbt.putInt("updateItemCount4", updateItemCount[4]);
		
		nbt.putInt("editTire", editTire);
		nbt.putInt("tire", tire);
		nbt.putInt("progress", progress);
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
		int tire = entity.data.get(TIRE_INDEX);
		int progress = entity.data.get(PROGRESS_INDEX);
		if(tire > 0) {
			if(progress % (entity.data.get(0 + (tire - 1) * 3) * 20) == 0) {
				world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Item.byId(entity.data.get(15 + (tire - 1) * 3)), entity.data.get(30 + (tire - 1) * 3))));
			}
			if(progress % (entity.data.get(1 + (tire - 1) * 3) * 20) == 0){
				world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Item.byId(entity.data.get(16 + (tire - 1) * 3)), entity.data.get(31 + (tire - 1) * 3))));
			}
			if(progress % (entity.data.get(2 + (tire - 1) * 3) * 20) == 0){
				world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY() + 1, pos.getZ(), new ItemStack(Item.byId(entity.data.get(17 + (tire - 1) * 3)), entity.data.get(32 + (tire - 1) * 3))));
			}	
			if(progress >=(entity.data.get(1 + (tire - 1) * 3) * 20) * (entity.data.get(1 + (tire - 1) * 3) * 20) * (entity.data.get(2 + (tire - 1) * 3) * 20)){
				entity.data.set(PROGRESS_INDEX, 0);
			}else {
				entity.data.set(PROGRESS_INDEX, progress + 1);
			}
			
		}
		setChanged(world, pos, state);
		
	}

	public static void craftItem(GeneratorTile entity) {
		//entity.itemHandler.extractItem(1, 1, false);
		//entity.itemHandler.setStackInSlot(2, new ItemStack(Items.DIAMOND, entity.itemHandler.getStackInSlot(2).getCount() + 1));
	}
	
	public static void removeItem(GeneratorTile tile, int slotIndex) {
		tile.itemHandler.extractItem(slotIndex - 36, tile.itemHandler.getStackInSlot(slotIndex - 36).getCount(), false);
	}
	
	public static void insertItem(GeneratorTile tile, int slotIndex, ItemStack stack) {
		tile.itemHandler.insertItem(slotIndex - 36, stack, false);
	}


}
