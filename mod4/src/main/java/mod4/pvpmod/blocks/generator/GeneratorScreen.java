package mod4.pvpmod.blocks.generator;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import mod4.pvpmod.PVPmod;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GeneratorScreen extends AbstractContainerScreen<GeneratorMenu> implements OnPress{

	private static final ResourceLocation TEXTURE = new ResourceLocation(PVPmod.MOD_ID, "textures/gui/generator.png");
	
	
	public GeneratorScreen(GeneratorMenu menu, Inventory inventory, Component component) {
		super(menu, inventory, component);
	}

	@Override
	protected void init() {
		super.init();
	}
	
	private void renderProgressArrow(PoseStack poseStack, int x, int y) {
		if(menu.isCrafting()) {
			blit(poseStack, x + 105, y + 33, 176, 0, 8, menu.getScaledProgress());//第二引数,第三引数 = 実際に矢印が進む場所の左上、第四引数,第五引数 = 矢印のテクスチャの左上、第六引数,第七引数 = 幅、高さ
		}
	}

	@Override
	protected void renderBg(PoseStack stack, float partialTick, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
		RenderSystem.setShaderTexture(0, TEXTURE);
		int x = (width - imageWidth) / 2;
		int y = (height - imageHeight) / 2;
		
		this.blit(stack, x, y, 0, 0, imageWidth, imageHeight);
		renderProgressArrow(stack, x, y);
		Component text = Component.literal("アップグレード");
		Button btn = new Button(10 ,10 ,50 ,20, text, this);
		this.addRenderableWidget(btn);
		
	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
		renderBackground(poseStack);
		super.render(poseStack, mouseX, mouseY, delta);
		renderTooltip(poseStack, mouseX, mouseY);
	}

	@Override
	public void onPress(Button btn) {
		GeneratorTile.craftItem(menu.be);
	}
	
	
	
	

}
