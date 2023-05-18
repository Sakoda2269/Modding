package mod4.pvpmod.blocks.shop;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import mod4.pvpmod.PVPmod;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ShopScreen extends AbstractContainerScreen<ShopMenu> {
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(PVPmod.MOD_ID, "textures/gui/shop.png");

	
	@Override
	protected void init() {
		super.init();
	}

	public ShopScreen(ShopMenu menu, Inventory inv, Component component) {
		super(menu, inv, component);
	}

	@Override
	protected void renderBg(PoseStack stack, float partialTick, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer :: getPositionTexShader);
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.setShaderTexture(0, TEXTURE);
		
		int x = (width - imageWidth) / 2;
		int y = (height - imageHeight) / 2;
		
		this.blit(stack, x, y, 0, 0, imageWidth, imageHeight);
		
	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
		renderBackground(poseStack);
		super.render(poseStack, mouseX, mouseY, delta);
		renderTooltip(poseStack, mouseX, mouseY);
		super.render(poseStack, mouseX, mouseY, delta);
	}
	
	

}
