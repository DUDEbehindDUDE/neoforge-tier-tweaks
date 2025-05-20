package com.dudebehinddude.tiermodifier.config;

import com.aetherteam.aether.item.combat.AetherItemTiers;
import com.dudebehinddude.tiermodifier.Tiermodifier;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.mojang.logging.LogUtils;
import net.minecraft.world.item.ArmorItem;
import net.neoforged.fml.loading.FMLPaths;
import org.slf4j.Logger;
import quek.undergarden.registry.UGItemTiers;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Look man, I didn't want to deal with custom JSON configs (the forge ones load,
 * too late to use), so I had Gemini write like 90% of this file. I'll rewrite it
 * at some point (tm).
 */
public class ConfigHandler {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_FILE_PATH = FMLPaths.CONFIGDIR.get().resolve(Tiermodifier.MODID + ".json");

    private static Map<String, Map<String, JsonElement>> loadedConfigData = null;

    public static void loadConfig() {
        LOGGER.info("Loading configuration file: {}", CONFIG_FILE_PATH);
        if (!Files.exists(CONFIG_FILE_PATH)) {
            LOGGER.info("Config file not found, generating default: {}", CONFIG_FILE_PATH);
            generateDefaultConfig();
        }

        try (Reader reader = Files.newBufferedReader(CONFIG_FILE_PATH)) {
            loadedConfigData = GSON.fromJson(reader, new TypeToken<Map<String, Map<String, JsonElement>>>(){}.getType());
            LOGGER.info("Successfully loaded configuration file.");
        } catch (JsonParseException e) {
            LOGGER.error("Error parsing configuration file: {}", CONFIG_FILE_PATH, e);
            loadedConfigData = new HashMap<>();
        } catch (Exception e) {
            LOGGER.error("Error reading configuration file: {}", CONFIG_FILE_PATH, e);
            loadedConfigData = new HashMap<>();
        }

        if (loadedConfigData == null) {
            loadedConfigData = new HashMap<>();
        }
    }

    private static void generateDefaultConfig() {
        Map<String, Object> defaultStructure = new HashMap<>();

        // --- Aether Tiers Defaults ---
        Map<String, Object> aetherTiersSection = new HashMap<>();
        for (AetherItemTiers tier : AetherItemTiers.values()) {
            String tierName = tier.name();
            Map<String, Object> tierProperties = new HashMap<>();
            tierProperties.put("maxUses", tier.getUses());
            tierProperties.put("efficiency", tier.getSpeed());
            tierProperties.put("attackDamage", tier.getAttackDamageBonus());
            tierProperties.put("enchantability", tier.getEnchantmentValue());
            aetherTiersSection.put(tierName, tierProperties);
        }
        defaultStructure.put("aether_tiers", aetherTiersSection);

        // --- Twilight Forest Armor Material Defaults ---
        Map<String, Object> tfArmorSection = new HashMap<>();

        Function<int[], Map<String, Integer>> createDefenseMap = (defenses) -> {
            Map<String, Integer> defenseMap = new HashMap<>();
            ArmorItem.Type[] types = ArmorItem.Type.values();
            for(int i = 0; i < defenses.length && i < types.length; i++) {
                defenseMap.put(types[i].name(), defenses[i]);
            }
            return defenseMap;
        };

        Map<String, Object> nagaProperties = new HashMap<>();
        nagaProperties.put("defense", createDefenseMap.apply(new int[]{3, 6, 7, 2, 8}));
        nagaProperties.put("enchantability", 15);
        nagaProperties.put("toughness", 0.5F);
        nagaProperties.put("knockbackResistance", 0.0F);
        tfArmorSection.put("naga_scale", nagaProperties);

        Map<String, Object> ironwoodProperties = new HashMap<>();
        ironwoodProperties.put("defense", createDefenseMap.apply(new int[]{2, 5, 7, 2, 5}));
        ironwoodProperties.put("enchantability", 15);
        ironwoodProperties.put("toughness", 0.0F);
        ironwoodProperties.put("knockbackResistance", 0.0F);
        tfArmorSection.put("ironwood", ironwoodProperties);

        Map<String, Object> fieryProperties = new HashMap<>();
        fieryProperties.put("defense", createDefenseMap.apply(new int[]{4, 7, 9, 4, 13}));
        fieryProperties.put("enchantability", 10);
        fieryProperties.put("toughness", 1.5F);
        fieryProperties.put("knockbackResistance", 0.0F);
        tfArmorSection.put("fiery", fieryProperties);

        Map<String, Object> steeleafProperties = new HashMap<>();
        steeleafProperties.put("defense", createDefenseMap.apply(new int[]{3, 6, 8, 3, 11}));
        steeleafProperties.put("enchantability", 9);
        steeleafProperties.put("toughness", 0.0F);
        steeleafProperties.put("knockbackResistance", 0.0F);
        tfArmorSection.put("steeleaf", steeleafProperties);

        Map<String, Object> knightmetalProperties = new HashMap<>();
        knightmetalProperties.put("defense", createDefenseMap.apply(new int[]{3, 6, 8, 3, 11}));
        knightmetalProperties.put("enchantability", 8);
        knightmetalProperties.put("toughness", 1.0F);
        knightmetalProperties.put("knockbackResistance", 0.0F);
        tfArmorSection.put("knightmetal", knightmetalProperties);

        Map<String, Object> phantomProperties = new HashMap<>();
        phantomProperties.put("defense", createDefenseMap.apply(new int[]{3, 6, 8, 3, 10}));
        phantomProperties.put("enchantability", 8);
        phantomProperties.put("toughness", 2.5F);
        phantomProperties.put("knockbackResistance", 0.0F);
        tfArmorSection.put("phantom", phantomProperties);

        Map<String, Object> yetiProperties = new HashMap<>();
        yetiProperties.put("defense", createDefenseMap.apply(new int[]{3, 6, 7, 4, 11}));
        yetiProperties.put("enchantability", 15);
        yetiProperties.put("toughness", 3.0F);
        yetiProperties.put("knockbackResistance", 0.0F);
        tfArmorSection.put("yeti", yetiProperties);

        Map<String, Object> arcticProperties = new HashMap<>();
        arcticProperties.put("defense", createDefenseMap.apply(new int[]{2, 5, 7, 2, 7}));
        arcticProperties.put("enchantability", 8);
        arcticProperties.put("toughness", 2.0F);
        arcticProperties.put("knockbackResistance", 0.0F);
        tfArmorSection.put("arctic", arcticProperties);


        defaultStructure.put("twilightforest_armor_materials", tfArmorSection);

        // --- Twilight Forest Tool Material Defaults ---
        Map<String, Object> tfToolSection = new HashMap<>();

        Map<String, Object> ironwoodToolProperties = new HashMap<>();
        ironwoodToolProperties.put("uses", 512);
        ironwoodToolProperties.put("speed", 6.5F);
        ironwoodToolProperties.put("attackDamageBonus", 2.0F);
        ironwoodToolProperties.put("enchantability", 25);
        tfToolSection.put("IRONWOOD", ironwoodToolProperties);

        Map<String, Object> fieryToolProperties = new HashMap<>();
        fieryToolProperties.put("uses", 1024);
        fieryToolProperties.put("speed", 9.0F);
        fieryToolProperties.put("attackDamageBonus", 4.0F);
        fieryToolProperties.put("enchantability", 10);
        tfToolSection.put("FIERY", fieryToolProperties);

        Map<String, Object> steeleafToolProperties = new HashMap<>();
        steeleafToolProperties.put("uses", 131);
        steeleafToolProperties.put("speed", 8.0F);
        steeleafToolProperties.put("attackDamageBonus", 3.0F);
        steeleafToolProperties.put("enchantability", 9);
        tfToolSection.put("STEELEAF", steeleafToolProperties);

        Map<String, Object> knightmetalToolProperties = new HashMap<>();
        knightmetalToolProperties.put("uses", 512);
        knightmetalToolProperties.put("speed", 8.0F);
        knightmetalToolProperties.put("attackDamageBonus", 3.0F);
        knightmetalToolProperties.put("enchantability", 8);
        tfToolSection.put("KNIGHTMETAL", knightmetalToolProperties);

        Map<String, Object> giantToolProperties = new HashMap<>();
        giantToolProperties.put("uses", 1024);
        giantToolProperties.put("speed", 4.0F);
        giantToolProperties.put("attackDamageBonus", 1.0F);
        giantToolProperties.put("enchantability", 5);
        tfToolSection.put("GIANT", giantToolProperties);

        Map<String, Object> iceToolProperties = new HashMap<>();
        iceToolProperties.put("uses", 32);
        iceToolProperties.put("speed", 1.0F);
        iceToolProperties.put("attackDamageBonus", 3.5F);
        iceToolProperties.put("enchantability", 5);
        tfToolSection.put("ICE", iceToolProperties);

        Map<String, Object> glassToolProperties = new HashMap<>();
        glassToolProperties.put("uses", 1);
        glassToolProperties.put("speed", 1.0F);
        glassToolProperties.put("attackDamageBonus", 36.0F);
        glassToolProperties.put("enchantability", 30);
        tfToolSection.put("GLASS", glassToolProperties);

        defaultStructure.put("twilightforest_tool_materials", tfToolSection);


        // --- Undergarden Armor Material Defaults ---
        Map<String, Object> ugArmorSection = new HashMap<>();

        Map<String, Object> cloggrumProperties = new HashMap<>();
        cloggrumProperties.put("defense", createDefenseMap.apply(new int[]{1, 5, 6, 2, 0}));
        cloggrumProperties.put("enchantability", 10);
        cloggrumProperties.put("toughness", 1.0F);
        cloggrumProperties.put("knockbackResistance", 0.0F);
        ugArmorSection.put("cloggrum", cloggrumProperties);

        Map<String, Object> froststeelProperties = new HashMap<>();
        froststeelProperties.put("defense", createDefenseMap.apply(new int[]{2, 6, 7, 3, 0}));
        froststeelProperties.put("enchantability", 15);
        froststeelProperties.put("toughness", 4.0F);
        froststeelProperties.put("knockbackResistance", 0.05F);
        ugArmorSection.put("froststeel", froststeelProperties);

        Map<String, Object> utheriumProperties = new HashMap<>();
        utheriumProperties.put("defense", createDefenseMap.apply(new int[]{3, 6, 8, 3, 0}));
        utheriumProperties.put("enchantability", 13);
        utheriumProperties.put("toughness", 3.0F);
        utheriumProperties.put("knockbackResistance", 0.0F);
        ugArmorSection.put("utherium", utheriumProperties);

        Map<String, Object> ancientProperties = new HashMap<>();
        ancientProperties.put("defense", createDefenseMap.apply(new int[]{2, 5, 6, 2, 0}));
        ancientProperties.put("enchantability", 0);
        ancientProperties.put("toughness", 0.0F);
        ancientProperties.put("knockbackResistance", 0.0F);
        ugArmorSection.put("ancient", ancientProperties);

        defaultStructure.put("undergarden_armor_materials", ugArmorSection);

        // --- Undergarden Item Tier Defaults (New Section) ---
        Map<String, Object> ugItemSection = new HashMap<>();

        // Hardcoded values for UGItemTiers enum constants
        // Names are uppercase to match the enum constant names
        Map<String, Object> cloggrumItemProperties = new HashMap<>();
        cloggrumItemProperties.put("durability", 286); // Maps to getUses()
        cloggrumItemProperties.put("speed", 6.0F);     // Maps to getSpeed()
        cloggrumItemProperties.put("damage", 3.0F);    // Maps to getAttackDamageBonus()
        cloggrumItemProperties.put("enchantmentValue", 8); // Maps to getEnchantmentValue()
        ugItemSection.put("CLOGGRUM", cloggrumItemProperties);

        Map<String, Object> froststeelItemProperties = new HashMap<>();
        froststeelItemProperties.put("durability", 575);
        froststeelItemProperties.put("speed", 7.0F);
        froststeelItemProperties.put("damage", 2.0F);
        froststeelItemProperties.put("enchantmentValue", 20);
        ugItemSection.put("FROSTSTEEL", froststeelItemProperties);

        Map<String, Object> utheriumItemProperties = new HashMap<>();
        utheriumItemProperties.put("durability", 1279);
        utheriumItemProperties.put("speed", 8.5F);
        utheriumItemProperties.put("damage", 3.5F);
        utheriumItemProperties.put("enchantmentValue", 17);
        ugItemSection.put("UTHERIUM", utheriumItemProperties);

        Map<String, Object> forgottenItemProperties = new HashMap<>();
        forgottenItemProperties.put("durability", 1876);
        forgottenItemProperties.put("speed", 8.0F);
        forgottenItemProperties.put("damage", 3.0F);
        forgottenItemProperties.put("enchantmentValue", 2);
        ugItemSection.put("FORGOTTEN", forgottenItemProperties);

        defaultStructure.put("undergarden_item_tiers", ugItemSection);


        try (Writer writer = Files.newBufferedWriter(CONFIG_FILE_PATH)) {
            GSON.toJson(defaultStructure, writer);
            LOGGER.info("Default config file generated: {}", CONFIG_FILE_PATH);
        } catch (Exception e) {
            LOGGER.error("Error writing default configuration file: {}", CONFIG_FILE_PATH, e);
        }
    }

    // --- Helper method to get a value for Aether tiers ---
    public static <T extends Number> T getAetherValue(AetherItemTiers tier, String key, T defaultValue) {
        if (loadedConfigData == null || !loadedConfigData.containsKey("aether_tiers")) {
            return defaultValue;
        }
        Map<String, JsonElement> tierSection = null;
        JsonElement tierElement = loadedConfigData.get("aether_tiers").get(tier.name());
        if(tierElement != null && tierElement.isJsonObject()){
            tierSection = tierElement.getAsJsonObject().asMap();
        }

        if (tierSection != null && tierSection.containsKey(key)) {
            JsonElement valueElement = tierSection.get(key);
            try {
                if (defaultValue instanceof Integer) { return (T) Integer.valueOf(valueElement.getAsInt()); }
                else if (defaultValue instanceof Float) { return (T) Float.valueOf(valueElement.getAsFloat()); }
                else if (defaultValue instanceof Double) { return (T) Double.valueOf(valueElement.getAsDouble()); }
                LOGGER.warn("Unhandled number type {} for config key {}. Returning default.", defaultValue.getClass().getName(), key);
                return defaultValue;

            } catch (NumberFormatException | IllegalStateException e) {
                LOGGER.warn("Invalid number format for key '{}' in tier '{}'. Using default value '{}'.", key, tier.name(), defaultValue, e);
                return defaultValue;
            }
        }
        return defaultValue;
    }

    // --- Helper method to get a simple numeric/float value for TF Armor Materials ---
    // Use this for enchantability, toughness, knockbackResistance
    public static <T extends Number> T getTFArmorValue(String materialName, String key, T defaultValue) {
        if (loadedConfigData == null || !loadedConfigData.containsKey("twilightforest_armor_materials")) {
            return defaultValue;
        }
        Map<String, JsonElement> materialSection = null;
        JsonElement materialElement = loadedConfigData.get("twilightforest_armor_materials").get(materialName);
        if(materialElement != null && materialElement.isJsonObject()){
            materialSection = materialElement.getAsJsonObject().asMap();
        }

        if (materialSection != null && materialSection.containsKey(key)) {
            JsonElement valueElement = materialSection.get(key);
            try {
                if (defaultValue instanceof Integer) { return (T) Integer.valueOf(valueElement.getAsInt()); }
                else if (defaultValue instanceof Float) { return (T) Float.valueOf(valueElement.getAsFloat()); }
                else if (defaultValue instanceof Double) { return (T) Double.valueOf(valueElement.getAsDouble()); }
                LOGGER.warn("Unhandled number type {} for config key {}. Returning default.", defaultValue.getClass().getName(), key);
                return defaultValue;

            } catch (NumberFormatException | IllegalStateException e) {
                LOGGER.warn("Invalid number format for key '{}' in material '{}'. Using default value '{}'.", key, materialName, defaultValue, e);
                return defaultValue;
            }
        }
        return defaultValue;
    }

    // --- Helper method to get a Defense value for a specific ArmorItem.Type for TF Armor ---
    public static int getTFArmorDefenseValue(String materialName, ArmorItem.Type type, int defaultValue) {
        if (loadedConfigData == null || !loadedConfigData.containsKey("twilightforest_armor_materials")) {
            return defaultValue;
        }
        Map<String, JsonElement> materialSection = null;
        JsonElement materialElement = loadedConfigData.get("twilightforest_armor_materials").get(materialName);
        if(materialElement != null && materialElement.isJsonObject()){
            materialSection = materialElement.getAsJsonObject().asMap();
        }

        if (materialSection != null && materialSection.containsKey("defense")) {
            JsonElement defenseElement = materialSection.get("defense");
            if (defenseElement != null && defenseElement.isJsonObject()) {
                Map<String, JsonElement> defenseMap = defenseElement.getAsJsonObject().asMap();
                String typeName = type.name();
                if (defenseMap.containsKey(typeName)) {
                    JsonElement valueElement = defenseMap.get(typeName);
                    try {
                        return valueElement.getAsInt();
                    } catch (NumberFormatException | IllegalStateException e) {
                        LOGGER.warn("Invalid number format for defense key '{}' in material '{}'. Using default value '{}'.", typeName, materialName, defaultValue, e);
                        return defaultValue;
                    }
                }
            } else {
                LOGGER.warn("Expected 'defense' for material '{}' to be a JSON object, but it wasn't. Using default value '{}'.", materialName, defaultValue);
            }
        }
        return defaultValue;
    }

    // --- Helper method to get a value for TF Tool Materials ---
    public static <T extends Number> T getTFToolValue(String materialName, String key, T defaultValue) {
        if (loadedConfigData == null || !loadedConfigData.containsKey("twilightforest_tool_materials")) {
            return defaultValue;
        }
        Map<String, JsonElement> materialSection = null;
        JsonElement materialElement = loadedConfigData.get("twilightforest_tool_materials").get(materialName);
        if(materialElement != null && materialElement.isJsonObject()){
            materialSection = materialElement.getAsJsonObject().asMap();
        }

        if (materialSection != null && materialSection.containsKey(key)) {
            JsonElement valueElement = materialSection.get(key);
            try {
                if (defaultValue instanceof Integer) { return (T) Integer.valueOf(valueElement.getAsInt()); }
                else if (defaultValue instanceof Float) { return (T) Float.valueOf(valueElement.getAsFloat()); }
                else if (defaultValue instanceof Double) { return (T) Double.valueOf(valueElement.getAsDouble()); }
                LOGGER.warn("Unhandled number type {} for config key {}. Returning default.", defaultValue.getClass().getName(), key);
                return defaultValue;

            } catch (NumberFormatException | IllegalStateException e) {
                LOGGER.warn("Invalid number format for key '{}' in tool material '{}'. Using default value '{}'.", key, materialName, defaultValue, e);
                return defaultValue;
            }
        }
        return defaultValue;
    }

    // --- Helper method to get a simple numeric/float value for UG Armor Materials ---
    // Use this for enchantability, toughness, knockbackResistance
    public static <T extends Number> T getUGArmorValue(String materialName, String key, T defaultValue) {
        if (loadedConfigData == null || !loadedConfigData.containsKey("undergarden_armor_materials")) {
            return defaultValue;
        }
        Map<String, JsonElement> materialSection = null;
        JsonElement materialElement = loadedConfigData.get("undergarden_armor_materials").get(materialName);
        if(materialElement != null && materialElement.isJsonObject()){
            materialSection = materialElement.getAsJsonObject().asMap();
        }

        if (materialSection != null && materialSection.containsKey(key)) {
            JsonElement valueElement = materialSection.get(key);
            try {
                if (defaultValue instanceof Integer) { return (T) Integer.valueOf(valueElement.getAsInt()); }
                else if (defaultValue instanceof Float) { return (T) Float.valueOf(valueElement.getAsFloat()); }
                else if (defaultValue instanceof Double) { return (T) Double.valueOf(valueElement.getAsDouble()); }
                LOGGER.warn("Unhandled number type {} for config key {}. Returning default.", defaultValue.getClass().getName(), key);
                return defaultValue;

            } catch (NumberFormatException | IllegalStateException e) {
                LOGGER.warn("Invalid number format for key '{}' in UG material '{}'. Using default value '{}'.", key, materialName, defaultValue, e);
                return defaultValue;
            }
        }
        return defaultValue;
    }

    // --- Helper method to get a Defense value for a specific ArmorItem.Type for UG Armor ---
    public static int getUGArmorDefenseValue(String materialName, ArmorItem.Type type, int defaultValue) {
        if (loadedConfigData == null || !loadedConfigData.containsKey("undergarden_armor_materials")) {
            return defaultValue;
        }
        Map<String, JsonElement> materialSection = null;
        JsonElement materialElement = loadedConfigData.get("undergarden_armor_materials").get(materialName);
        if(materialElement != null && materialElement.isJsonObject()){
            materialSection = materialElement.getAsJsonObject().asMap();
        }

        if (materialSection != null && materialSection.containsKey("defense")) {
            JsonElement defenseElement = materialSection.get("defense");
            if (defenseElement != null && defenseElement.isJsonObject()) {
                Map<String, JsonElement> defenseMap = defenseElement.getAsJsonObject().asMap();
                String typeName = type.name();
                if (defenseMap.containsKey(typeName)) {
                    JsonElement valueElement = defenseMap.get(typeName);
                    try {
                        return valueElement.getAsInt();
                    } catch (NumberFormatException | IllegalStateException e) {
                        LOGGER.warn("Invalid number format for defense key '{}' in UG material '{}'. Using default value '{}'.", typeName, materialName, defaultValue, e);
                        return defaultValue;
                    }
                }
            } else {
                LOGGER.warn("Expected 'defense' for UG material '{}' to be a JSON object, but it wasn't. Using default value '{}'.", materialName, defaultValue);
            }
        }
        return defaultValue;
    }

    // --- Helper method to get a value for UG Item Tiers (New) ---
    // Use this for durability, speed, damage, enchantmentValue
    public static <T extends Number> T getUGItemValue(UGItemTiers tier, String key, T defaultValue) {
        // Check if config data was loaded successfully and contains the UG item section
        if (loadedConfigData == null || !loadedConfigData.containsKey("undergarden_item_tiers")) {
            return defaultValue; // Fallback if config not loaded or section missing
        }

        // Get the section for the specific item tier (e.g., "CLOGGRUM")
        Map<String, JsonElement> tierSection = null;
        JsonElement tierElement = loadedConfigData.get("undergarden_item_tiers").get(tier.name()); // Use enum name for key
        if(tierElement != null && tierElement.isJsonObject()){
            tierSection = tierElement.getAsJsonObject().asMap();
        }

        // Check if the tier section and the specific key exist
        if (tierSection != null && tierSection.containsKey(key)) {
            JsonElement valueElement = tierSection.get(key);
            try {
                if (defaultValue instanceof Integer) { return (T) Integer.valueOf(valueElement.getAsInt()); }
                else if (defaultValue instanceof Float) { return (T) Float.valueOf(valueElement.getAsFloat()); }
                else if (defaultValue instanceof Double) { return (T) Double.valueOf(valueElement.getAsDouble()); }
                LOGGER.warn("Unhandled number type {} for config key {}. Returning default.", defaultValue.getClass().getName(), key);
                return defaultValue;

            } catch (NumberFormatException | IllegalStateException e) {
                LOGGER.warn("Invalid number format for key '{}' in UG item tier '{}'. Using default value '{}'.", key, tier.name(), defaultValue, e);
                return defaultValue; // Return default on parsing error
            }
        }

        // If tier section or key is missing, return the default value
        return defaultValue;
    }
}