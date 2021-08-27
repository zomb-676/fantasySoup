package com.github.zomb_676.fantasySoup

import com.github.zomb_676.fantasySoup.examples.ExampleScreen
import com.github.zomb_676.fantasySoup.render.graphic.OpenglFunctions
import com.github.zomb_676.fantasySoup.utils.manuallyInitClass
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
object EventHandle {
    @JvmStatic
    @SubscribeEvent
    fun initGraphicObjects(event:FMLLoadCompleteEvent){
        event.enqueueWork{ExampleScreen::class.manuallyInitClass()}
    }

    @JvmStatic
    @SubscribeEvent
    fun initGraphicDebugSetting(event: FMLCommonSetupEvent){
        event.enqueueWork{OpenglFunctions.addGlDebugMessageCallback(this.hashCode().toLong())}
    }

}