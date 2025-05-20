package com.dudebehinddude.tiermodifier.mixin;

import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.dudebehinddude.tiermodifier.config.ConfigHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AetherItemTiers.class)
public class AetherItemTiersMixin {

    // --- Inject into getUses() to modify Max Durability ---
    @Inject(method = "getUses", at = @At("RETURN"), cancellable = true)
    private void modifyMaxUses(CallbackInfoReturnable<Integer> cir) {
        AetherItemTiers currentTier = (AetherItemTiers)(Object)this;
        int originalUses = cir.getReturnValue(); // Get original value as default

        // Use the helper method to get the configured value, falling back to originalUses
        int configuredUses = ConfigHandler.getAetherValue(currentTier, "maxUses", originalUses);

        // Only set the return value if the configured value is different
        if (configuredUses != originalUses) {
            cir.setReturnValue(configuredUses);
        }
        // If value is unchanged or config not found, original value is used implicitly.
    }

    // --- Inject into getSpeed() to modify Mining Speed (Efficiency) ---
    @Inject(method = "getSpeed", at = @At("RETURN"), cancellable = true)
    private void modifySpeed(CallbackInfoReturnable<Float> cir) {
        AetherItemTiers currentTier = (AetherItemTiers)(Object)this;
        float originalSpeed = cir.getReturnValue(); // Get original value as default

        // Use the helper method, falling back to originalSpeed
        float configuredSpeed = ConfigHandler.getAetherValue(currentTier, "efficiency", originalSpeed);

        if (configuredSpeed != originalSpeed) {
            cir.setReturnValue(configuredSpeed);
        }
    }

    // --- Inject into getAttackDamageBonus() to modify Attack Damage ---
    @Inject(method = "getAttackDamageBonus", at = @At("RETURN"), cancellable = true)
    private void modifyAttackDamageBonus(CallbackInfoReturnable<Float> cir) {
        AetherItemTiers currentTier = (AetherItemTiers)(Object)this;
        float originalDamage = cir.getReturnValue(); // Get original value as default

        // Use the helper method, falling back to originalDamage
        float configuredDamage = ConfigHandler.getAetherValue(currentTier, "attackDamage", originalDamage);

        if (configuredDamage != originalDamage) {
            cir.setReturnValue(configuredDamage);
        }
    }

    // --- Inject into getEnchantmentValue() to modify Enchantability ---
    @Inject(method = "getEnchantmentValue", at = @At("RETURN"), cancellable = true)
    private void modifyEnchantability(CallbackInfoReturnable<Integer> cir) {
        AetherItemTiers currentTier = (AetherItemTiers)(Object)this;
        int originalEnchantability = cir.getReturnValue(); // Get original value as default

        // Use the helper method, falling back to originalEnchantability
        int configuredEnchantability = ConfigHandler.getAetherValue(currentTier, "enchantability", originalEnchantability);

        if (configuredEnchantability != originalEnchantability) {
            cir.setReturnValue(configuredEnchantability);
        }
    }
}