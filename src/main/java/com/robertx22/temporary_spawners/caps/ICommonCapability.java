package com.robertx22.temporary_spawners.caps;

import net.minecraft.nbt.CompoundNBT;

public interface ICommonCapability {

    CompoundNBT getNBT();

    void setNBT(CompoundNBT value);
}