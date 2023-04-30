package mod4.pvpmod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mod4.pvpmod.blocks.generator.GeneratorScreen;
import mod4.pvpmod.init.BlockInit;
import mod4.pvpmod.init.ItemInit;
import mod4.pvpmod.init.MenuInit;
import mod4.pvpmod.init.TileEntityInit;
import mod4.pvpmod.networking.ModMessages;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(PVPmod.MOD_ID)
public class PVPmod {
	public static final String MOD_ID = "pvpmod";
	public static final Logger LOGGER = LogManager.getLogger();
	
	public PVPmod() {
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addListener(this :: setup);
		ItemInit.ITEMS.register(modEventBus);
		BlockInit.BLOCKS.register(modEventBus);
		TileEntityInit.TILE_ENTITY_TYPES.register(modEventBus);
		MenuInit.MENUS.register(modEventBus);
		MinecraftForge.EVENT_BUS.register(this);
		
		ModMessages.register();
		
	}
	
	private void setup(final FMLCommonSetupEvent event) {
		
	}
	
	@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class ClientModEvent{
		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {
			MenuScreens.register(MenuInit.GENERATOR_MENU.get(), GeneratorScreen :: new);
		}
	}
	
}
