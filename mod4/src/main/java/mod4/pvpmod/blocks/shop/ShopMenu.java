package mod4.pvpmod.blocks.shop;

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

@SuppressWarnings("removal")
public class ShopMenu extends AbstractContainerMenu{
	
	public final ShopTile be;
	public final Level level;
	public final ContainerData data;

	//aaa
	//aaa
	
	public ItemSlot[] mySlots = new ItemSlot[30];
	
	private static final int HOTBAR_SLOT_COUNT = 9;
	private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
	private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
	private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT + PLAYER_INVENTORY_ROW_COUNT;
	private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
	private static final int VANILLA_FIRST_SLOT_INDEX = 0;
	private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
	
	private static final int TE_INVENTORY_SLOT_COUNT = 30;
	
	public ShopMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
		this(id,  inv, inv.player.level.getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(1));
	}

	public ShopMenu(int id, Inventory inv, BlockEntity entity, ContainerData data) {
		super(MenuInit.SHOP_MENU.get(), id);
		checkContainerSize(inv, 30);//GeneratorTileのitemHandlerの値と同じにする
		be = (ShopTile) entity;
		this.level = inv.player.level;
		this.data= data;
		
		addPlayerInventory(inv);
		addPlayerHotbar(inv);
		
		this.be.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler ->{
			for(int i = 0; i < mySlots.length; i++) {
				int x;
				if(i <= 4) {
					x = 19;
				}else if(i <= 9) {
					x = 50;
				}else if(i <= 14) {
					x = 90;
				}else if(i <= 19) {
					x = 121;
				}else if(i <= 24) {
					x = 160;
				}else {
					x = 191;
				}
				int y = (i % 5) * 22 + 34;
				mySlots[i] = new ItemSlot(handler, i, x, y);
				this.addSlot(mySlots[i]);
			}
		});
		
		addDataSlots(data);
		
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
	public boolean stillValid(Player player) {
		return stillValid(ContainerLevelAccess.create(level, be.getBlockPos()), player, BlockInit.SHOP.get());
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
