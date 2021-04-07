package com.github.zomb_676.fantasySoup.capability

import com.github.zomb_676.fantasySoup.capability.HeatCapability.HeatCapabilityContent
import net.minecraft.nbt.INBT
import net.minecraft.util.Direction
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import java.util.concurrent.Callable

@InnerClass(HeatCapabilityContent::class)
object HeatCapability : ICapability<HeatCapabilityContent> {
    class HeatCapabilityContent

    @JvmField
    @CapabilityInject(HeatCapabilityContent::class)
    var HEAT_CAPABILITY_INSTANCE: Capability<HeatCapabilityContent>? = null

    override fun storageGuide(): Capability.IStorage<HeatCapabilityContent> {
        return object : Capability.IStorage<HeatCapabilityContent> {
            override fun writeNBT(
                capability: Capability<HeatCapabilityContent>?,
                instance: HeatCapabilityContent?,
                side: Direction?,
            ): INBT? {
                TODO("Not yet implemented")
            }

            override fun readNBT(
                capability: Capability<HeatCapabilityContent>?,
                instance: HeatCapabilityContent?,
                side: Direction?,
                nbt: INBT?,
            ) {
                TODO("Not yet implemented")
            }

        }
    }

    override fun factory(): Callable<HeatCapabilityContent> {
        return Callable { HeatCapabilityContent() }
    }

}


