package com.dudebehinddude.tiermodifier.mixin;

import quek.undergarden.registry.UGItemTiers;
import com.dudebehinddude.tiermodifier.config.ConfigHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(UGItemTiers.class)
public class UGItemTiersMixin {
    // --- Inject into getUses() to modify Max Durability ---
    @Inject(method = "getUses", at = @At("RETURN"), cancellable = true)
    private void modifyMaxUses(CallbackInfoReturnable<Integer> cir) {
        UGItemTiers currentTier = (UGItemTiers)(Object)this;
        int originalUses = cir.getReturnValue();

        int configuredUses = ConfigHandler.getUGItemValue(currentTier, "durability", originalUses);

        if (configuredUses != originalUses) {
            cir.setReturnValue(configuredUses);
        }
    }

    // --- Inject into getSpeed() to modify Mining Speed (Efficiency) ---
    @Inject(method = "getSpeed", at = @At("RETURN"), cancellable = true)
    private void modifySpeed(CallbackInfoReturnable<Float> cir) {
        UGItemTiers currentTier = (UGItemTiers)(Object)this;
        float originalSpeed = cir.getReturnValue();

        float configuredSpeed = ConfigHandler.getUGItemValue(currentTier, "speed", originalSpeed);

        if (configuredSpeed != originalSpeed) {
            cir.setReturnValue(configuredSpeed);
        }
    }

    // --- Inject into getAttackDamageBonus() to modify Attack Damage ---
    @Inject(method = "getAttackDamageBonus", at = @At("RETURN"), cancellable = true)
    private void modifyAttackDamageBonus(CallbackInfoReturnable<Float> cir) {
        UGItemTiers currentTier = (UGItemTiers)(Object)this;
        float originalDamage = cir.getReturnValue();

        float configuredDamage = ConfigHandler.getUGItemValue(currentTier, "damage", originalDamage);

        if (configuredDamage != originalDamage) {
            cir.setReturnValue(configuredDamage);
        }
    }

    // --- Inject into getEnchantmentValue() to modify Enchantability ---
    @Inject(method = "getEnchantmentValue", at = @At("RETURN"), cancellable = true)
    private void modifyEnchantability(CallbackInfoReturnable<Integer> cir) {
        UGItemTiers currentTier = (UGItemTiers)(Object)this;
        int originalEnchantability = cir.getReturnValue();

        int configuredEnchantability = ConfigHandler.getUGItemValue(currentTier, "enchantmentValue", originalEnchantability);

        if (configuredEnchantability != originalEnchantability) {
            cir.setReturnValue(configuredEnchantability);
        }
    }
}
