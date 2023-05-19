package mod4.pvpmod.blocks.generator;

import java.util.function.Predicate;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import mod4.pvpmod.PVPmod;
import mod4.pvpmod.networking.ModMessages;
import mod4.pvpmod.networking.packet.InsertItemPacketC2S;
import mod4.pvpmod.networking.packet.ItemPacketC2S;
import mod4.pvpmod.networking.packet.SepPacketC2S;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;

public class GeneratorScreen extends AbstractContainerScreen<GeneratorMenu> implements OnPress{

	private static final ResourceLocation TEXTURE = new ResourceLocation(PVPmod.MOD_ID, "textures/gui/generator.png");
	
	
	Component edi = Component.translatable(PVPmod.MOD_ID + ".editbox.edit");
	EditBox num1;
	EditBox num2;
	EditBox num3;
	
	Button confirm;
	Button back;
	
	int editTire = 0;
	
	public GeneratorScreen(GeneratorMenu menu, Inventory inventory, Component component) {
		super(menu, inventory, component);
	}

	@Override
	protected void init() {
		super.init();
		this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
		this.titleLabelX = 7;
		this.titleLabelY = 7;
		this.inventoryLabelX = 20;
		this.inventoryLabelY = 135;
		this.imageHeight = 230;
		this.imageWidth = 235;
		this.editTire = 0;
		Predicate<String> check = str -> checker(str);
		
		num1 = new EditBox(font, this.leftPos + 50, this.topPos + 30, 20, 15, edi);
		this.addRenderableWidget(num1);
		num1.setMaxLength(2);
		num1.setEditable(true);
		num1.setFilter(check);
		
		num2 = new EditBox(font, this.leftPos + 50, this.topPos + 60, 20, 15, edi);
		this.addRenderableWidget(num2);
		num2.setMaxLength(2);
		num2.setEditable(true);
		num2.setFilter(check);
		
		num3 = new EditBox(font, this.leftPos + 50, this.topPos + 90, 20, 15, edi);
		this.addRenderableWidget(num3);
		num3.setMaxLength(2);
		num3.setEditable(true);
		num2.setFilter(check);
		
		confirm = new Button(this.leftPos + 160, this.topPos + 115, 50, 20, Component.translatable("gui." + PVPmod.MOD_ID + ".generator.confirm_button"), this);
		back = new Button(this.leftPos + 36, this.topPos + 115, 50, 20 , Component.translatable("gui." + PVPmod.MOD_ID + ".generator.back_button"), this);
		
		this.addRenderableWidget(confirm);
		this.addRenderableWidget(back);
		ModMessages.sendToServer(new InsertItemPacketC2S(editTire));
		
		if(editTire > 0) {
			num1.setValue(String.valueOf(menu.data.get(0 + (editTire - 1) * 3)));
			num2.setValue(String.valueOf(menu.data.get(1 + (editTire - 1) * 3)));
			num3.setValue(String.valueOf(menu.data.get(2 + (editTire - 1) * 3)));
		}
		
		//this.setInitialFocus(num1);
		//rebuildWidgets();
	}

	

	@Override
	public boolean mouseClicked(double x, double y, int p_97750_) {
		if(editTire == 0) {
			if(x >= leftPos + 9 && x <= leftPos + 27) {
				if(y >= topPos + 29 && y <= topPos + 47) {
					return false;
				}
				if(y >= topPos + 59 && y <= topPos + 77) {
					return false;
				}
				if(y >= topPos + 89 && y <= topPos + 107) {
					return false;
				}
			}
		}
		if(editTire == 5) {
			if(x >= 84 && x <= 102) {
				if(y >= 59 && y <= 77) {
					return false;
				}
			}
		}
		return super.mouseClicked(x, y, p_97750_);
	}

	@Override
	public int getSlotColor(int index) {
		if(editTire == 0 && (index == 36 || index == 37 || index == 38)) {
			return 0xc6c6c6;
		} else if(editTire == 5 && index == 39) {
			return 0xc6c6c6;
		}
		return super.getSlotColor(index);
	}

	private boolean checker(String str) {//入力が数字で100以下か、何もなければtrue
		boolean isNum = true;
		if(str.length() == 0) {
			return true;
		}
		for(int i = 0; i < str.length(); i++) {
			if(!Character.isDigit(str.charAt(i))) {
				isNum = false;
				break;
			}
		}
		
		if(isNum && str.length() >= 1) {
			return Integer.parseInt(str) < 100;
		}
		return false;
	}
	
	

	@Override
	protected void renderBg(PoseStack stack, float partialTick, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.setShaderTexture(0, TEXTURE);
		
		int x = (width - imageWidth) / 2;
		int y = (height - imageHeight) / 2;
		
		this.blit(stack, x, y, 0, 0, imageWidth, imageHeight);
		
		if(editTire > 0) {
			this.blit(stack, this.leftPos + 9, this.topPos + 29, 35, 142, 17, 17);
			this.blit(stack, this.leftPos + 9, this.topPos + 59, 35, 142, 17, 17);
			this.blit(stack, this.leftPos + 9, this.topPos + 89, 35, 142, 17, 17);
		}
		if(editTire < 5) {
			this.blit(stack, this.leftPos + 84, this.topPos + 59, 35, 142, 17, 17);
		}
		
		//this.rebuildWidgets();
		
//		num1.setFocus(true);
//		this.setFocused(num1);
	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
		renderBackground(poseStack);
		super.render(poseStack, mouseX, mouseY, delta);
		renderTooltip(poseStack, mouseX, mouseY);
		if(editTire == 0) {
			back.visible = false;
			back.active = false;
			num1.setEditable(false);
			num2.setEditable(false);
			num3.setEditable(false);
			num1.visible = false;
			num2.visible = false;
			num3.visible = false;
		}else {
			back.visible = true;
			back.active = true;
			num1.setEditable(true);
			num2.setEditable(true);
			num3.setEditable(true);
			num1.visible = true;
			num2.visible = true;
			num3.visible = true;
		}
		if(editTire == 5) {
			confirm.visible = false;
			confirm.active = false;
		}else {
			confirm.visible = true;
			confirm.active = true;
		}
	}
	
	
	@Override
	protected void renderLabels(PoseStack poseStack, int p1, int p2) {
		super.renderLabels(poseStack, p1, p2);
		
		if(editTire > 0) {
			this.font.draw(poseStack, Component.literal("per"), 32, 35, 4210752);
			this.font.draw(poseStack, Component.literal("s"), 73, 39, 4210752);
			
			this.font.draw(poseStack, Component.literal("per"), 32, 65, 4210752);
			this.font.draw(poseStack, Component.literal("s"), 73, 69, 4210752);
			
			this.font.draw(poseStack, Component.literal("per"), 32, 95, 4210752);
			this.font.draw(poseStack, Component.literal("s"), 73, 99, 4210752);
		}
		if(editTire == 0) {
			this.font.draw(poseStack, Component.translatable("gui." + PVPmod.MOD_ID + ".generator.activate"), 110, 65, 4210752);
		}
		else if(editTire < 5) {
			this.font.draw(poseStack, Component.translatable("gui." + PVPmod.MOD_ID + ".generator.upgread"), 110, 65, 4210752);
		}
		
		this.font.draw(poseStack, Component.literal("Tire" + editTire), 100, 15, 4210752);
	}

	private boolean opened = false;

	@Override
	protected void containerTick() {
		super.containerTick();
		//edit.tick();
		if(!opened) {
			rebuildWidgets();
			opened = true;
		}
		if(num1.isFocused()) {
			num2.setFocus(false);
			num3.setFocus(false);
		}
		
		num1.tick();
		num2.tick();
		num3.tick();
	}
	
	
	
	@Override
	protected void slotClicked(Slot slot, int p_97779_, int p_97780_, ClickType p_97781_) {
		if(slot != null) {
			System.out.println(slot.getSlotIndex());
		}
		super.slotClicked(slot, p_97779_, p_97780_, p_97781_);
	}

	@Override
	public void onClose() {
		opened = false;
		ModMessages.sendToServer(new ItemPacketC2S(editTire,
				GeneratorTile.getItemStack(menu.be, GeneratorMenu.SLOT1),//menu.getSlot(GeneratorMenu.SLOT1).getItem(),
				GeneratorTile.getItemStack(menu.be, GeneratorMenu.SLOT2),
				GeneratorTile.getItemStack(menu.be, GeneratorMenu.SLOT3),
				GeneratorTile.getItemStack(menu.be, GeneratorMenu.SLOT4)));
		if(editTire > 0) {
			ModMessages.sendToServer(new SepPacketC2S(editTire, stringToInt(num1.getValue()), stringToInt(num2.getValue()), stringToInt(num3.getValue())));
		}
		super.onClose();
	}
	
	@SuppressWarnings("resource")
	@Override
	public void onPress(Button btn) {
		Minecraft.getInstance().gameMode.handleInventoryButtonClick(this.menu.containerId, 0);
		ModMessages.sendToServer(new ItemPacketC2S(editTire,
				GeneratorTile.getItemStack(menu.be, GeneratorMenu.SLOT1),//menu.getSlot(GeneratorMenu.SLOT1).getItem(),
				GeneratorTile.getItemStack(menu.be, GeneratorMenu.SLOT2),
				GeneratorTile.getItemStack(menu.be, GeneratorMenu.SLOT3),
				GeneratorTile.getItemStack(menu.be, GeneratorMenu.SLOT4)));
		if(editTire > 0) {
			ModMessages.sendToServer(new SepPacketC2S(editTire, stringToInt(num1.getValue()), stringToInt(num2.getValue()), stringToInt(num3.getValue())));
			
		}
		
		if(btn.equals(confirm)) {
			editTire += editTire < 5 ? 1 : 0;
		} else if(btn.equals(back)) {
			editTire -= editTire > 0 ? 1 : 0;
		}
		if(editTire > 0) {
			num1.setValue(String.valueOf(menu.data.get(0 + (editTire - 1) * 3)));
			num2.setValue(String.valueOf(menu.data.get(1 + (editTire - 1) * 3)));
			num3.setValue(String.valueOf(menu.data.get(2 + (editTire - 1) * 3)));
		}
		ModMessages.sendToServer(new InsertItemPacketC2S(editTire));
	}
	
	
	
	public int stringToInt(String str) {
		if(str.length() == 0) {
			return -1;
		}
		return Integer.parseInt(str);
	}
	
	

}
