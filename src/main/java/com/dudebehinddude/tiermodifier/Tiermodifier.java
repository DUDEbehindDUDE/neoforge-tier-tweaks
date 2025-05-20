package com.dudebehinddude.tiermodifier;

import com.dudebehinddude.tiermodifier.config.ConfigHandler;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Tiermodifier.MODID)
public class Tiermodifier {
    public static final String MODID = "nftiertweaks";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Tiermodifier(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        ConfigHandler.loadConfig();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Common Setup
    }
}
