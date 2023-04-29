package mod4.pvpmod.init;

import mod4.pvpmod.PVPmod;
import mod4.pvpmod.blocks.generator.GeneratorTile;
import mod4.pvpmod.blocks.tile.MobSlayerTile;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TileEntityInit {
	
	public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, PVPmod.MOD_ID);
	
	public static final RegistryObject<BlockEntityType<MobSlayerTile>> MOB_SLAYER = TILE_ENTITY_TYPES.register("mob_slayer", 
			() -> BlockEntityType.Builder.of(MobSlayerTile :: new, BlockInit.MOB_SLAYER.get()).build(null));
	
	public static final RegistryObject<BlockEntityType<GeneratorTile>> GENERATOR = TILE_ENTITY_TYPES.register("generator", 
			() -> BlockEntityType.Builder.of(GeneratorTile :: new, BlockInit.GENERATOR.get()).build(null));
	
	public static void register(IEventBus eventBus) {
		TILE_ENTITY_TYPES.register(eventBus);
	}
	
}
