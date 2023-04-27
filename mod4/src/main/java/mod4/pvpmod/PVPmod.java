package mod4.pvpmod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mod4.pvpmod.init.BlockInit;
import mod4.pvpmod.init.ItemInit;
import mod4.pvpmod.init.TileEntityInit;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
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
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	private void setup(final FMLCommonSetupEvent event) {
		
	}
	
}
