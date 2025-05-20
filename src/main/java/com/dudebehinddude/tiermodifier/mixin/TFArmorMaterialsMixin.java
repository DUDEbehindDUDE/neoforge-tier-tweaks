package com.dudebehinddude.tiermodifier.mixin;

import com.dudebehinddude.tiermodifier.config.ConfigHandler;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import twilightforest.init.TFArmorMaterials;

import java.util.Map;
import java.util.function.Supplier;

@Mixin(value = TFArmorMaterials.class)
public class TFArmorMaterialsMixin {
    @Unique
    private static final String REGISTER_TARGET = "Lnet/neoforged/neoforge/registries/DeferredRegister;register(Ljava/lang/String;Ljava/util/function/Supplier;)Lnet/neoforged/neoforge/registries/DeferredHolder;";

    @Unique
    private static void tierModifier$modifyArmorArgs(Args args) {
        String name = args.get(0);
        Supplier<ArmorMaterial> originalSupplier = args.get(1);
        ArmorMaterial material = originalSupplier.get();
        Map<ArmorItem.Type, Integer> defense = material.defense();
        defense.forEach((armorType, defenseValue) -> {
            defenseValue = ConfigHandler.getTFArmorDefenseValue(name, armorType, defenseValue);
            defense.replace(armorType, defenseValue);
        });

        Supplier<ArmorMaterial> newSupplier = () -> new ArmorMaterial(
                defense,
                ConfigHandler.getTFArmorValue(name, "enchantability", material.enchantmentValue()),
                material.equipSound(),
                material.repairIngredient(),
                material.layers(),
                ConfigHandler.getTFArmorValue(name, "toughness", material.toughness()),
                ConfigHandler.getTFArmorValue(name, "knockbackResistance", material.knockbackResistance())
        );
        args.set(1, newSupplier);
    }

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = REGISTER_TARGET, ordinal = 0))
    private static void modifyArmorRegistration0(Args args) {
        tierModifier$modifyArmorArgs(args);
    }

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = REGISTER_TARGET, ordinal = 1))
    private static void modifyArmorRegistration1(Args args) {
        tierModifier$modifyArmorArgs(args);
    }

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = REGISTER_TARGET, ordinal = 2))
    private static void modifyArmorRegistration2(Args args) {
        tierModifier$modifyArmorArgs(args);
    }

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = REGISTER_TARGET, ordinal = 3))
    private static void modifyArmorRegistration3(Args args) {
        tierModifier$modifyArmorArgs(args);
    }

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = REGISTER_TARGET, ordinal = 4))
    private static void modifyArmorRegistration4(Args args) {
        tierModifier$modifyArmorArgs(args);
    }

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = REGISTER_TARGET, ordinal = 5))
    private static void modifyArmorRegistration5(Args args) {
        tierModifier$modifyArmorArgs(args);
    }

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = REGISTER_TARGET, ordinal = 6))
    private static void modifyArmorRegistration6(Args args) {
        tierModifier$modifyArmorArgs(args);
    }

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = REGISTER_TARGET, ordinal = 7))
    private static void modifyArmorRegistration7(Args args) {
        tierModifier$modifyArmorArgs(args);
    }
}
