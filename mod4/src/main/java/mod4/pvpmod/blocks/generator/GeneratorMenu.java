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
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;

@SuppressWarnings("removal")
public class GeneratorMenu extends AbstractContainerMenu{
	
	public final GeneratorTile be;
	public final Level level;
	public final ContainerData data;
	
	private static final int HOTBAR_SLOT_COUNT = 9;
	private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
	private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
	private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT + PLAYER_INVENTORY_ROW_COUNT;
	private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
	private static final int VANILLA_FIRST_SLOT_INDEX = 0;
	private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
	
	private static final int TE_INVENTORY_SLOT_COUNT = 3;//GeneratorTileのitemHandlerの値と同じにする
	
	public GeneratorMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
		this(id, inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));//GeneratorTileのコンストラクターのgetCount()の値と同じにする
	}

	public GeneratorMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
		super(MenuInit.GENERATOR_MENU.get(), id);
		checkContainerSize(inv, 3);//GeneratorTileのitemHandlerの値と同じにする
		be = (GeneratorTile) entity;
		this.level = inv.player.level;
		this.data= data;
		
		addPlayerInventory(inv);//guiにプレイヤーのインベントリも表示する
		addPlayerHotbar(inv);//guiにプレイヤーのホットバーも表示する
		
		this.be.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(hendler -> {
			this.addSlot(new SlotItemHandler(hendler, 0, 12, 15));//index = 0,x = 12, y = 15にスロットを追加
			this.addSlot(new SlotItemHandler(hendler, 1, 86, 15));
			this.addSlot(new SlotItemHandler(hendler, 2, 86, 68));
		});
		
		addDataSlots(data);//アイテム以外のデータを管理
		
	}
	
	public boolean isCrafting() {
		return data.get(0) > 0;
	}
	
	public int getScaledProgress() {
		int progress = this.data.get(0);
		int maxProgress = this.data.get(1);
		int progressArrowSize = 26;
		return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
	}

	@Override
	public ItemStack quickMoveStack(Player playerIn, int index) {//必要
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
		// TODO 自動生成されたメソッド・スタブ
		return stillValid(ContainerLevelAccess.create(level, be.getBlockPos()), player, BlockInit.GENERATOR.get());
	}
	
	private void addPlayerInventory(Inventory playerInv) {//プレイヤーのインベントリの表示
		for(int i = 0; i < 3; ++i) {
			for(int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 86 + i * 18));
			}
		}
	}
	
	private void addPlayerHotbar(Inventory playerInv) {//プレイヤーのホットバーの表示
		for(int i = 0; i < 9; ++i) {
			this.addSlot(new Slot(playerInv, i, 8 + i * 18, 144));
		}
	}

}
