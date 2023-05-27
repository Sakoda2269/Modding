package mod4.pvpmod.blocks.shop;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import mod4.pvpmod.PVPmod;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ShopScreen extends AbstractContainerScreen<ShopMenu> implements OnPress {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(PVPmod.MOD_ID, "textures/gui/shop.png");
	private static final ResourceLocation TEXTURE2 = new ResourceLocation(PVPmod.MOD_ID, "textures/gui/shop_right.png");

	ImageButton combat;
	ImageButton armor;
	ImageButton block;
	ImageButton food;
	ImageButton other1;
	ImageButton other2;
	

	public ShopScreen(ShopMenu menu, Inventory inv, Component component) {
		super(menu, inv, component);
	}
	
	@Override
	protected void init() {
		super.init();
		this.titleLabelX = -7;
		this.titleLabelY = -7;
		this.inventoryLabelX = -20;
		this.inventoryLabelY = -135;
		this.imageHeight = 230;
		this.imageWidth = 147;
		combat = new ImageButton(this.leftPos + 15, this.topPos + 5, 21, 21, 0, 0, new ResourceLocation(PVPmod.MOD_ID, "textures/gui/imgbtn.png"), this);
		this.addRenderableWidget(combat);
		
		armor = new ImageButton(this.leftPos + 50, this.topPos + 5, 21, 21, 0, 42, new ResourceLocation(PVPmod.MOD_ID, "textures/gui/imgbtn.png"), this);
		this.addRenderableWidget(armor);
		
		food = new ImageButton(this.leftPos + 85, this.topPos + 5, 21, 21, 0, 84, new ResourceLocation(PVPmod.MOD_ID, "textures/gui/imgbtn.png"), this);
		this.addRenderableWidget(food);
		
		block = new ImageButton(this.leftPos + 120, this.topPos + 5, 21, 21, 0, 126, new ResourceLocation(PVPmod.MOD_ID, "textures/gui/imgbtn.png"), this);
		this.addRenderableWidget(block);
		
		
		other1 = new ImageButton(this.leftPos + 155, this.topPos + 5, 21, 21, 0, 168, new ResourceLocation(PVPmod.MOD_ID, "textures/gui/imgbtn.png"), this);
		this.addRenderableWidget(other1);
		
		other2 = new ImageButton(this.leftPos + 190, this.topPos + 5, 21, 21, 0, 210, new ResourceLocation(PVPmod.MOD_ID, "textures/gui/imgbtn.png"), this);
		this.addRenderableWidget(other2);
		
	}

	@Override
	protected void renderBg(PoseStack stack, float partialTick, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer :: getPositionTexShader);
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.setShaderTexture(0, TEXTURE);
		
		int x = (width - 295) / 2;
		int y = (height - imageHeight) / 2;
		
		this.blit(stack, x, y, 0, 0, imageWidth, imageHeight);
		
		RenderSystem.setShaderTexture(0, TEXTURE2);
		this.blit(stack, x + imageWidth, y, 0, 0, imageWidth, imageHeight);
		
	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
		renderBackground(poseStack);
		super.render(poseStack, mouseX, mouseY, delta);
		renderTooltip(poseStack, mouseX, mouseY);
	}
	
	

	
	private boolean opend = false;
	@Override
	protected void containerTick() {
		super.containerTick();
		if(!opend) {
			opend = true;
			rebuildWidgets();
		}
	}
	
	@Override
	public void onClose() {
		opend = false;
		super.onClose();
	}

	@Override
	public void onPress(Button btn) {
	}
	
	

}
