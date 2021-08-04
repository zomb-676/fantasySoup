package com.github.zomb_676.fantasySoup.register

import com.github.zomb_676.fantasySoup.FantasySoup
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent
import java.util.concurrent.CompletableFuture

@Mod.EventBusSubscriber(Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
class ClientRegisterEventHandle {
    companion object {
        private val tasks = arrayListOf<() -> Unit>()

         fun addTask(codeBlock:()->Unit)= tasks.add(codeBlock)

        @JvmStatic
        @SubscribeEvent
        fun renderTypeBind(event: FMLClientSetupEvent): CompletableFuture<Void> =
            event.enqueueWork { tasks.forEach{it.invoke()} }

        @JvmStatic
        @SubscribeEvent
        fun registerCheck(event: FMLLoadCompleteEvent){
            FantasySoup.logger.debug(RegisterHandle.registerMarker,"fml load complete")
        }
    }
}