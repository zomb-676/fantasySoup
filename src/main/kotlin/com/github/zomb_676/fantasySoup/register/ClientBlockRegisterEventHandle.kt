package com.github.zomb_676.fantasySoup.register

import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import java.util.concurrent.CompletableFuture

@Mod.EventBusSubscriber(Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
class ClientBlockRegisterEventHandle {
    companion object {
        private val tasks = arrayListOf<() -> Unit>()

         fun addTask(codeBlock:()->Unit)= tasks.add(codeBlock)

        @SubscribeEvent
        fun renderTypeBind(event: FMLClientSetupEvent): CompletableFuture<Void> =
            event.enqueueWork { tasks.forEach{it.invoke()} }
    }
}