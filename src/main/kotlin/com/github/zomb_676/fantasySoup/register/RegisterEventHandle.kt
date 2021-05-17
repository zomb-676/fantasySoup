package com.github.zomb_676.fantasySoup.register

import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent

@Mod.EventBusSubscriber(Dist.CLIENT , bus = Mod.EventBusSubscriber.Bus.MOD)
object ClientRegisterEventHandle {
    @SubscribeEvent
    @JvmStatic
    fun registerAllKeyBindings(event: FMLClientSetupEvent) {
        event.enqueueWork { RegisterHandle.keyBindings.forEach(ClientRegistry::registerKeyBinding) }
    }
}

@Mod.EventBusSubscriber(Dist.DEDICATED_SERVER , bus = Mod.EventBusSubscriber.Bus.MOD)
object ServerRegisterEventHandle {
    @SubscribeEvent
    @JvmStatic
    fun registerAllKeyBindings() {
    }
}