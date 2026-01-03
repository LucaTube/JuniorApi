package com.luca.juniorapi;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(JuniorApi.MOD_ID)
public class JuniorApi {
    public static final String MOD_ID = "juniorapi";
    public static final Logger LOGGER = LogManager.getLogger();

    public JuniorApi() {
        var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        LOGGER.info("Junior API - Animation System Initialized");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Junior API Common Setup");
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("Junior API Client Setup");
    }
}