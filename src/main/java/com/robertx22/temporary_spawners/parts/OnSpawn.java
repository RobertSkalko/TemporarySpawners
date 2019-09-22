package com.robertx22.temporary_spawners.parts;

import com.robertx22.temporary_spawners.caps.SpawnerCap;
import net.minecraft.entity.SpawnReason;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class OnSpawn {

    @SubscribeEvent
    public static void onSpawn(LivingSpawnEvent.CheckSpawn event) {

        try {

            if (event.isSpawner() && event.getSpawnReason().equals(SpawnReason.SPAWNER)) {

                BlockPos pos = getSpawnerPos(event);

                if (pos != null) {

                    TileEntity en = event.getEntity().world.getTileEntity(pos);

                    if (en != null) {
                        en.getCapability(SpawnerCap.Data)
                                .ifPresent(x -> x.onMobSpawn(pos, event));

                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BlockPos getSpawnerPos(LivingSpawnEvent.CheckSpawn event) {

        try {
            BlockPos pos = null;
            try {
                pos = event.getSpawner().getSpawnerPosition();
            } catch (Exception e) {
            }

            if (pos != null) {
                return pos;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

}
