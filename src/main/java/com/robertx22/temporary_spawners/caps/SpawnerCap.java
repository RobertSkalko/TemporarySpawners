package com.robertx22.temporary_spawners.caps;

import com.robertx22.temporary_spawners.Config;
import com.robertx22.temporary_spawners.Main;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class SpawnerCap {

    public static final ResourceLocation RESOURCE = new ResourceLocation(Main.ID, "spawnercap");

    @CapabilityInject(ISpawnerCap.class)
    public static final Capability<ISpawnerCap> Data = null;

    static final String MOB_SPAWNS = "MOB_SPAWNS";

    public interface ISpawnerCap extends ICommonCapability {

        void onMobSpawn(BlockPos pos, LivingSpawnEvent.CheckSpawn event);

    }

    @Mod.EventBusSubscriber
    public static class EventHandler {

        @SubscribeEvent
        public static void onEntityConstruct(AttachCapabilitiesEvent<TileEntity> event) {

            if (event.getObject() instanceof MobSpawnerTileEntity) {
                event.addCapability(RESOURCE, new Provider());
            }
        }

    }

    public static class Provider extends BaseProvider<ISpawnerCap> {

        @Override
        public ISpawnerCap defaultImpl() {
            return new DefaultImpl();
        }

        @Override
        public Capability<ISpawnerCap> dataInstance() {
            return Data;
        }
    }

    public static class DefaultImpl implements ISpawnerCap {

        int mobSpawns = 0;

        @Override
        public CompoundNBT getNBT() {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putInt(MOB_SPAWNS, mobSpawns);
            return nbt;

        }

        @Override
        public void setNBT(CompoundNBT nbt) {
            this.mobSpawns = nbt.getInt(MOB_SPAWNS);
        }

        @Override
        public void onMobSpawn(BlockPos pos, LivingSpawnEvent.CheckSpawn event) {
            this.mobSpawns++;

            if (this.mobSpawns > Config.INSTANCE.MAXIMUM_MOB_SPAWNS_PER_SPAWNER.get()) {
                destroySpawner(pos, event);
            }

        }

        public void destroySpawner(BlockPos pos, LivingSpawnEvent.CheckSpawn event) {

            event.getWorld().setBlockState(pos, Blocks.AIR.getDefaultState(), 2);

        }

    }

    public static class Storage extends BaseStorage<ISpawnerCap> {

    }

}