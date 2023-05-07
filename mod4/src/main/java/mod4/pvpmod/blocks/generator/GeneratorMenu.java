package mod4.pvpmod.blocks.generator;

import mod4.pvpmod.init.BlockInit;
import mod4.pvpmod.init.MenuInit;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

@SuppressWarnings("removal")
public class GeneratorMenu extends AbstractContainerMenu{
	
	public final GeneratorTile be;
	public final Level level;
	public final ContainerData data;
	
	public ItemSlot slot1;
	public ItemSlot slot2;
	public ItemSlot slot3;
	public ItemSlot slot4;
	
	private static final int HOTBAR_SLOT_COUNT = 9;
	private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
	private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
	private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT + PLAYER_INVENTORY_ROW_COUNT;
	private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
	private static final int VANILLA_FIRST_SLOT_INDEX = 0;
	private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
	
	private static final int TE_INVENTORY_SLOT_COUNT = 4;//GeneratorTileのitemHandlerの値と同じにする
	
	public GeneratorMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
		this(id, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(59));//GeneratorTileのコンストラクターのgetCount()の値と同じにする
	}

	public GeneratorMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
		super(MenuInit.GENERATOR_MENU.get(), id);
		checkContainerSize(inv, 4);//GeneratorTileのitemHandlerの値と同じにする
		be = (GeneratorTile) entity;
		this.level = inv.player.level;
		this.data= data;
		
		addPlayerInventory(inv);//guiにプレイヤーのインベントリも表示する
		addPlayerHotbar(inv);//guiにプレイヤーのホットバーも表示する
		
		slot1 = new ItemSlot(inv, 36, 10, 30, 0);
		this.addSlot(slot1);
		
		slot2 = new ItemSlot(inv, 37, 10, 60, 0);
		this.addSlot(slot2);
		
		slot3 = new ItemSlot(inv, 38, 10, 90, 0);
		this.addSlot(slot3);
		
		slot4 = new ItemSlot(inv, 39, 85, 60, 5);
		this.addSlot(slot4);
		
		
//		this.be.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
//			//this.addSlot(new SlotItemHandler(handler, 0, 10, 30));//index = 0,x = 12, y = 15にスロットを追加
//			this.addSlot(new SlotItemHandler(handler, 1, 10, 60));
//			this.addSlot(new SlotItemHandler(handler, 2, 10, 90));
//			this.addSlot(new SlotItemHandler(handler, 3, 85, 60));
//		});
		
		
		addDataSlots(data);//アイテム以外のデータを管理
		
	}
	
	
	
	@Override
	public boolean clickMenuButton(Player player, int id) { //guiボタンをクリックしたときの処理
//		System.out.println("-------------------------------------");
//		for(int i = 0; i < 58; i++) {
//			System.out.println(data.get(i) + "," + i);
//		}
		
		return super.clickMenuButton(player, id);
	}

	@Override
	public ItemStack quickMoveStack(Player playerIn, int index) {//必要(シフトクリック時の処理？)
		Slot sourceSlot = slots.get(index);
		if(sourceSlot == null || !sourceSlot.hasItem()) {
			return ItemStack.EMPTY;
		}
		ItemStack sourceStack = sourceSlot.getItem();
		ItemStack copyofSourceStack = sourceStack.copy();
		
		if(index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
			if(!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT, false));{
				return ItemStack.EMPTY;
			}
		} else if(index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
			if(!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
				return ItemStack.EMPTY;
			}
		} else {
			System.out.println("Invalid slotIndex" + index);
			return ItemStack.EMPTY;
		}
		
		if(sourceStack.getCount() == 0) {
			sourceSlot.set(ItemStack.EMPTY);
		} else {
			sourceSlot.setChanged();
		}
		sourceSlot.onTake(playerIn, sourceStack);
		return copyofSourceStack;
	}

	@Override
	public boolean stillValid(Player player) {//必要
		return stillValid(ContainerLevelAccess.create(level, be.getBlockPos()), player, BlockInit.GENERATOR.get());
	}
	
	private void addPlayerInventory(Inventory playerInv) {//プレイヤーのインベントリの表示
		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInv, j + i * 9 + 9, 36 + j * 18, 143 + i * 18));
			}
		}
	}
	
	private void addPlayerHotbar(Inventory playerInv) {//プレイヤーのホットバーの表示
		for(int i = 0; i < 9; ++i) {
			this.addSlot(new Slot(playerInv, i, 36 + i * 18, 201));
		}
	}

}
