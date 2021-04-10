package com.github.zomb_676.fantasySoup.gui

import net.minecraft.client.Minecraft
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
class GuiDrawTest {
    companion object{
        @SubscribeEvent
        @JvmStatic
        fun a(event : FMLLoadCompleteEvent){
            Minecraft.getInstance().resourceManager
        }
    }
}