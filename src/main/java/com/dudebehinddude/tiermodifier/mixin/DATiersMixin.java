package com.dudebehinddude.tiermodifier.mixin;

import com.dudebehinddude.tiermodifier.config.ConfigHandler;
import io.github.razordevs.deep_aether.init.DATiers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DATiers.class)
public class DATiersMixin {

    // --- Inject into getUses() to modify Max Durability ---
    @Inject(method = "getUses", at = @At("RETURN"), cancellable = true)
    private void modifyMaxUses(CallbackInfoReturnable<Integer> cir) {
        DATiers currentTier = (DATiers)(Object)this;
        int originalUses = cir.getReturnValue();

        int configuredUses = ConfigHandler.getDeepAetherValue(currentTier, "maxUses", originalUses);

        if (configuredUses != originalUses) {
            cir.setReturnValue(configuredUses);
        }
    }

    // --- Inject into getSpeed() to modify Mining Speed (Efficiency) ---
    @Inject(method = "getSpeed", at = @At("RETURN"), cancellable = true)
    private void modifySpeed(CallbackInfoReturnable<Float> cir) {
        DATiers currentTier = (DATiers)(Object)this;
        float originalSpeed = cir.getReturnValue(); // Get original value as default

        float configuredSpeed = ConfigHandler.getDeepAetherValue(currentTier, "efficiency", originalSpeed);

        if (configuredSpeed != originalSpeed) {
            cir.setReturnValue(configuredSpeed);
        }
    }

    // --- Inject into getAttackDamageBonus() to modify Attack Damage ---
    @Inject(method = "getAttackDamageBonus", at = @At("RETURN"), cancellable = true)
    private void modifyAttackDamageBonus(CallbackInfoReturnable<Float> cir) {
        DATiers currentTier = (DATiers)(Object)this;
        float originalDamage = cir.getReturnValue(); // Get original value as default

        // Use the helper method, falling back to originalDamage
        float configuredDamage = ConfigHandler.getDeepAetherValue(currentTier, "attackDamage", originalDamage);

        if (configuredDamage != originalDamage) {
            cir.setReturnValue(configuredDamage);
        }
    }

    // --- Inject into getEnchantmentValue() to modify Enchantability ---
    @Inject(method = "getEnchantmentValue", at = @At("RETURN"), cancellable = true)
    private void modifyEnchantability(CallbackInfoReturnable<Integer> cir) {
        DATiers currentTier = (DATiers)(Object)this;
        int originalEnchantability = cir.getReturnValue(); // Get original value as default

        // Use the helper method, falling back to originalEnchantability
        int configuredEnchantability = ConfigHandler.getDeepAetherValue(currentTier, "enchantability", originalEnchantability);

        if (configuredEnchantability != originalEnchantability) {
            cir.setReturnValue(configuredEnchantability);
        }
    }
}