package mod4.pvpmod.init;

import mod4.pvpmod.PVPmod;
import mod4.pvpmod.items.JumpBoots;
import mod4.pvpmod.items.Lod;
import mod4.pvpmod.items.ModArmorMaterial;
import mod4.pvpmod.items.ModCreativeTab;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PVPmod.MOD_ID);
	public static final RegistryObject<Item> SMILE = ITEMS.register("smile", () -> new Item(new Item.Properties().tab(ModCreativeTab.instance)));
	public static final RegistryObject<Item> LOD = ITEMS.register("lod", () -> new Lod(new Item.Properties().tab(ModCreativeTab.instance)));
	public static final RegistryObject<Item> JUMP_BOOTS = ITEMS.register("jump_boots",
			() -> new JumpBoots(ModArmorMaterial.PINK, EquipmentSlot.FEET, new Item.Properties().tab(ModCreativeTab.instance)));
}
