package com.dudebehinddude.tiermodifier.mixin;

import com.dudebehinddude.tiermodifier.config.ConfigHandler;
import io.github.razordevs.deep_aether.item.gear.DAArmorMaterials;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

@Mixin(DAArmorMaterials.class)
public class DAArmorMaterialsMixin {
    @Unique
    private static final String REGISTER_TARGET = "Lnet/neoforged/neoforge/registries/DeferredRegister;register(Ljava/lang/String;Ljava/util/function/Supplier;)Lnet/neoforged/neoforge/registries/DeferredHolder;";

    @Unique
    private static void tierModifier$modifyArmorArgs(Args args) {
        String name = args.get(0);
        Supplier<ArmorMaterial> originalSupplier = args.get(1);
        ArmorMaterial material = originalSupplier.get();

        // Create a NEW mutable map for defense values
        Map<ArmorItem.Type, Integer> newDefense = new EnumMap<>(ArmorItem.Type.class);
        material.defense().forEach((armorType, defenseValue) -> {
            // Get the configured defense value and put it into the new map
            newDefense.put(armorType, ConfigHandler.getAetherArmorDefenseValue(name, armorType, defenseValue));
        });

        Supplier<ArmorMaterial> newSupplier = () -> new ArmorMaterial(
                newDefense, // Pass the new, modified defense map
                ConfigHandler.getAetherArmorValue(name, "enchantability", material.enchantmentValue()),
                material.equipSound(),
                material.repairIngredient(),
                material.layers(),
                ConfigHandler.getAetherArmorValue(name, "toughness", material.toughness()),
                ConfigHandler.getAetherArmorValue(name, "knockbackResistance", material.knockbackResistance())
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
}
