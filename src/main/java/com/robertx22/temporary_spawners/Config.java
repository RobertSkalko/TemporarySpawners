package com.robertx22.temporary_spawners;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config {

    public static final String NAME = "config";
    public static final ForgeConfigSpec spec;
    public static final Config INSTANCE;

    static {
        final Pair<Config, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Config::new);
        spec = specPair.getRight();
        INSTANCE = specPair.getLeft();

    }

    public ForgeConfigSpec.IntValue MAXIMUM_MOB_SPAWNS_PER_SPAWNER;

    Config(ForgeConfigSpec.Builder builder) {
        builder.comment("Config").push(NAME);

        MAXIMUM_MOB_SPAWNS_PER_SPAWNER = builder.comment(".")
                .defineInRange("MAXIMUM_MOB_SPAWNS_PER_SPAWNER", 50, 0, Integer.MAX_VALUE);

        builder.pop();
    }

}