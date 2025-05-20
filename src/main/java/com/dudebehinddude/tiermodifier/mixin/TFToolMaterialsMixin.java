package com.dudebehinddude.tiermodifier.mixin;

import com.dudebehinddude.tiermodifier.config.ConfigHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import twilightforest.util.TFToolMaterials;

@Mixin(TFToolMaterials.class)
public class TFToolMaterialsMixin {
    @Unique
    private static final String TIER_TARGET = "Lnet/neoforged/neoforge/common/SimpleTier;<init>(Lnet/minecraft/tags/TagKey;IFFILjava/util/function/Supplier;)V";

    @Unique
    private static void tierModifier$modifyToolMaterials(Args args, String material) {
        args.set(1, ConfigHandler.getTFToolValue(material, "uses", args.get(1)));
        args.set(2, ConfigHandler.getTFToolValue(material, "speed", args.get(2)));
        args.set(3, ConfigHandler.getTFToolValue(material, "attackDamageBonus", args.get(3)));
        args.set(4, ConfigHandler.getTFToolValue(material, "enchantability", args.get(4)));
    }

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = TIER_TARGET, ordinal = 0))
    private static void modifyToolRegistration0(Args args) {
        tierModifier$modifyToolMaterials(args, "IRONWOOD");
    }

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = TIER_TARGET, ordinal = 1))
    private static void modifyToolRegistration1(Args args) {
        tierModifier$modifyToolMaterials(args, "FIERY");
    }

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = TIER_TARGET, ordinal = 2))
    private static void modifyToolRegistration2(Args args) {
        tierModifier$modifyToolMaterials(args, "STEELEAF");
    }

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = TIER_TARGET, ordinal = 3))
    private static void modifyToolRegistration3(Args args) {
        tierModifier$modifyToolMaterials(args, "KNIGHTMETAL");
    }

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = TIER_TARGET, ordinal = 4))
    private static void modifyToolRegistration4(Args args) {
        tierModifier$modifyToolMaterials(args, "GIANT");
    }

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = TIER_TARGET, ordinal = 5))
    private static void modifyToolRegistration5(Args args) {
        tierModifier$modifyToolMaterials(args, "ICE");
    }

    @ModifyArgs(method = "<clinit>", at = @At(value = "INVOKE", target = TIER_TARGET, ordinal = 6))
    private static void modifyToolRegistration6(Args args) {
        tierModifier$modifyToolMaterials(args, "GLASS");
    }

}
