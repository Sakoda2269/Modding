package mod4.pvpmod.init;

import mod4.pvpmod.PVPmod;
import mod4.pvpmod.blocks.generator.GeneratorMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuInit {
	
	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, PVPmod.MOD_ID);
	
	public static final RegistryObject<MenuType<GeneratorMenu>> GENERATOR_MENU = registerMenuType(GeneratorMenu::new, "generator_menu");
	
	private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name){
		return MENUS.register(name, () -> IForgeMenuType.create(factory));
	}

}
