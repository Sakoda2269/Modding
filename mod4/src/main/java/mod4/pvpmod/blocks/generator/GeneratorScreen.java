package mod4.pvpmod.blocks.generator;

import java.util.function.Predicate;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import mod4.pvpmod.PVPmod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GeneratorScreen extends AbstractContainerScreen<GeneratorMenu> implements OnPress{

	private static final ResourceLocation TEXTURE = new ResourceLocation(PVPmod.MOD_ID, "textures/gui/generator.png");
	
	Component edi = Component.translatable(PVPmod.MOD_ID + ".editbox.edit");
	EditBox num1;
	EditBox num2;
	EditBox num3;
	
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
		this.inventoryLabelY = 120;
		this.imageHeight = 230;
		this.imageWidth = 235;
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
		
		Button btn = new Button(0, 0, 50, 20, Component.literal("aaa"), this);
		
		this.addRenderableWidget(btn);
		
		//this.setInitialFocus(num1);
		//rebuildWidgets();
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
		//this.rebuildWidgets();
		
//		num1.setFocus(true);
//		this.setFocused(num1);
	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
		renderBackground(poseStack);
		super.render(poseStack, mouseX, mouseY, delta);
		renderTooltip(poseStack, mouseX, mouseY);
	}
	
	
	@Override
	protected void renderLabels(PoseStack poseStack, int p1, int p2) {
		super.renderLabels(poseStack, p1, p2);
		poseStack.pushPose();
		poseStack.scale(1.5f, 1.5f, 1.5f);
		//drawString(poseStack, font, Component.literal("/"), 30, 30, 0x000000);
		//drawCenteredString(poseStack, font, "/", 20, 20, 0x000000);
		//this.font.draw(poseStack, Component.literal("per"), 20, 20, 4210752);
		poseStack.popPose();
		this.font.draw(poseStack, Component.literal("per"), 32, 35, 4210752);
		this.font.draw(poseStack, Component.literal("s"), 73, 39, 4210752);
		
		this.font.draw(poseStack, Component.literal("per"), 32, 65, 4210752);
		this.font.draw(poseStack, Component.literal("s"), 73, 69, 4210752);
		
		this.font.draw(poseStack, Component.literal("per"), 32, 95, 4210752);
		this.font.draw(poseStack, Component.literal("s"), 73, 99, 4210752);
	}

	@Override
	public boolean keyPressed(int p1, int p2, int p3) {
		System.out.println(p1 + "," + p2 + "," + p3);
		if((p1 == 256 && p2 == 1) || (p1 == 259 && p2 == 14)) {//escキーで画面を閉じる、バックスペースを有効
			num1.setEditable(true);
			num2.setEditable(true);
			num3.setEditable(true);
			return super.keyPressed(p1, p2, p3);
		}
		if(48 <= p1 && p1 <= 57 && 2 <= p2 && p2 <= 11) {
			num1.setEditable(true);
			num2.setEditable(true);
			num3.setEditable(true);
			
		}
		else if(num1.isFocused() || num2.isFocused() || num3.isFocused()) {//数字以外は入力されないようにする
			/*num1.setEditable(false);
			num3.setEditable(false);
			num2.setEditable(false);*/
			return false;
		}
		//return super.keyPressed(0, 0, 0);
		return super.keyPressed(p1, p2, p3);
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
	public void onClose() {
		opened = false;
		super.onClose();
	}

	@SuppressWarnings("resource")
	@Override
	public void onPress(Button btn) {
		Minecraft.getInstance().gameMode.handleInventoryButtonClick(this.menu.containerId, 0);
		//System.out.println(this.menu.getSlot(0));
		//ModMessages.sendToServer(new GeneratorTile.packet());
	
	}
	
	

}
