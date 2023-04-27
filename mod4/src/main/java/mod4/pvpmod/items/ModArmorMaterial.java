package mod4.pvpmod.items;

import com.google.common.base.Supplier;

import mod4.pvpmod.PVPmod;
import mod4.pvpmod.init.ItemInit;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

@SuppressWarnings("deprecation")
public enum ModArmorMaterial implements ArmorMaterial{
	PINK(PVPmod.MOD_ID + ":pink", 20, new int[] {4, 7, 9, 4}, 50, SoundEvents.ARMOR_EQUIP_DIAMOND, 3.0f, 0.1f, ()->{
		return Ingredient.of(ItemInit.SMILE.get());
	});
	
	private static final int[] HEALTH_PER_SLOT = new int[] {13, 15, 16, 11};
	private final String name;
	private final int durabilityMuliplier;
	private final int[] slotProtections;
	private final int enchantmentValue;
	private final SoundEvent sound;
	private final float toughness;
	private final float knockbackRegistance;
	private final LazyLoadedValue<Ingredient> repairIngerdient;
	
	ModArmorMaterial(String name, int durability, int[] protection, int enchantability, SoundEvent sound, float toughness, float knockbackRegistance, Supplier<Ingredient> repariIngredient){
		this.name = name;
		this.durabilityMuliplier = durability;
		this.slotProtections = protection;
		this.enchantmentValue = enchantability;
		this.sound = sound;
		this.toughness = toughness;
		this.knockbackRegistance = knockbackRegistance;
		this.repairIngerdient = new LazyLoadedValue<>(repariIngredient);
	}

	@Override
	public int getDurabilityForSlot(EquipmentSlot slot) {
		return HEALTH_PER_SLOT[slot.getIndex()] * this.durabilityMuliplier;
	}

	@Override
	public int getDefenseForSlot(EquipmentSlot slot) {
		return this.slotProtections[slot.getIndex()];
	}

	@Override
	public int getEnchantmentValue() {
		return this.enchantmentValue;
	}

	@Override
	public SoundEvent getEquipSound() {
		return this.sound;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return this.repairIngerdient.get();
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public float getToughness() {
		return this.toughness;
	}

	@Override
	public float getKnockbackResistance() {
		return this.knockbackRegistance;
	}

}
