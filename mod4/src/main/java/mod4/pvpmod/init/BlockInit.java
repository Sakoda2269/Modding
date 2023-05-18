package mod4.pvpmod.init;

import com.google.common.base.Supplier;

import mod4.pvpmod.PVPmod;
import mod4.pvpmod.blocks.SampleBlock;
import mod4.pvpmod.blocks.generator.GeneratorBlock;
import mod4.pvpmod.blocks.shop.ShopBlock;
import mod4.pvpmod.blocks.tile.MobSlayerBlock;
import mod4.pvpmod.items.ModCreativeTab;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockInit {

	public static final DeferredRegister<Block> BLOCKS  = DeferredRegister.create(ForgeRegistries.BLOCKS, PVPmod.MOD_ID);
	
	public static final RegistryObject<Block> SMILE_BLOCK = BLOCKS.register("smile_block",
			() -> new Block(Block.Properties
					.of(Material.STONE)
					.strength(4f, 1200f)
					.lightLevel((state) -> 15)
					));
	
	public static final RegistryObject<Block> SAMPLE_BLOCK = BLOCKS.register("sample_block", () -> new SampleBlock(Block.Properties.copy(Blocks.IRON_BLOCK)));
	
	public static final RegistryObject<Block> MOB_SLAYER = BLOCKS.register("mob_slayer", () -> new MobSlayerBlock(Block.Properties.copy(Blocks.IRON_BLOCK)));
	
	public static final RegistryObject<Block> GENERATOR = BLOCKS.register("generator", () -> new GeneratorBlock(Block.Properties.copy(Blocks.IRON_BLOCK)));
	
	public static final RegistryObject<Block> SHOP = BLOCKS.register("shop", () -> new ShopBlock(Block.Properties.copy(Blocks.IRON_BLOCK)));
	
	@SubscribeEvent
	public static void onRegisterItems(final RegisterEvent event) {
		if(event.getRegistryKey().equals(ForgeRegistries.Keys.ITEMS)) {
			BLOCKS.getEntries().forEach((blockRegistryObject) ->{
				Block block = blockRegistryObject.get();
				Item.Properties properties = new Item.Properties().tab(ModCreativeTab.instance);
				Supplier<Item> blockItemFactory = () -> new BlockItem(block, properties);
				event.register(ForgeRegistries.Keys.ITEMS, blockRegistryObject.getId(), blockItemFactory);
			});
		}
	}
	
}
