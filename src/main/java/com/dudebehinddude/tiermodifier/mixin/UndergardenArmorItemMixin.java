package com.dudebehinddude.tiermodifier.mixin;

import com.dudebehinddude.tiermodifier.config.ConfigHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import quek.undergarden.item.armor.UndergardenArmorItem;

@Mixin(UndergardenArmorItem.class)
public class UndergardenArmorItemMixin {
    @Unique
    private static final String ATTRIBUTE_MODIFIER_TARGET = "(Lnet/minecraft/resources/ResourceLocation;DLnet/minecraft/world/entity/ai/attributes/AttributeModifier$Operation;)Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;";

    @Redirect(method = "createFroststeelAttributes", at = @At(
            value = "NEW",
            target = ATTRIBUTE_MODIFIER_TARGET,
            ordinal = 0,
            args = "0"
    ))
    private static AttributeModifier redirectFroststeelArmor(
            ResourceLocation id,
            double armor,
            AttributeModifier.Operation operation,
            ArmorItem.Type type
    ) {
        double configuredToughness = ConfigHandler.getUGArmorDefenseValue("froststeel", type, (int) armor);
        return new AttributeModifier(id, configuredToughness, operation);
    }

    @Redirect(method = "createFroststeelAttributes", at = @At(
            value = "NEW",
            target = ATTRIBUTE_MODIFIER_TARGET,
            ordinal = 1
    ))
    private static AttributeModifier redirectFroststeelArmorToughness(
            ResourceLocation id,
            double amount,
            AttributeModifier.Operation operation
    ) {
        double configuredToughness = ConfigHandler.getUGArmorValue("froststeel", "toughness", (float) amount).doubleValue();
        return new AttributeModifier(id, configuredToughness, operation);
    }

    @Redirect(method = "createFroststeelAttributes", at = @At(
            value = "NEW",
            target = ATTRIBUTE_MODIFIER_TARGET,
            ordinal = 2
    ))
    private static AttributeModifier redirectFroststeelKnockbackResistance(
            ResourceLocation id,
            double amount,
            AttributeModifier.Operation operation
    ) {
        double configuredToughness = ConfigHandler.getUGArmorValue("froststeel", "knockbackResistance", (float) amount).doubleValue();
        return new AttributeModifier(id, configuredToughness, operation);
    }
}
