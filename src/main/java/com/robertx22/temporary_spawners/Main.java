package com.robertx22.temporary_spawners;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.robertx22.temporary_spawners.caps.SpawnerCap;
import com.robertx22.temporary_spawners.parts.OnSpawn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

@Mod(Main.ID)
public class Main {

    private static final Logger LOGGER = LogManager.getLogger();

    public static final String ID = "temporary_spawners";

    public Main() {

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.spec);
        loadConfig(Config.spec, FMLPaths.CONFIGDIR.get().resolve(ID + "-common.toml"));

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(OnSpawn.class);

        MinecraftForge.EVENT_BUS.register(this);

    }

    private static void loadConfig(ForgeConfigSpec spec, Path path) {

        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();

        spec.setConfig(configData);
    }

    private void setup(final FMLCommonSetupEvent event) {

        CapabilityManager.INSTANCE.register(SpawnerCap.ISpawnerCap.class, new SpawnerCap.Storage(), SpawnerCap.DefaultImpl::new);

    }

    private void doClientStuff(final FMLClientSetupEvent event) {

    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
    }

    private void processIMC(final InterModProcessEvent event) {

    }

}
