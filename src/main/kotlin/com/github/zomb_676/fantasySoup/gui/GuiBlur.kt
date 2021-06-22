package com.github.zomb_676.fantasySoup.gui

import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.GuiOpenEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(Dist.CLIENT)
object GuiBlur {
    @SubscribeEvent
    @JvmStatic
    fun blur(event : GuiOpenEvent){

    }
}