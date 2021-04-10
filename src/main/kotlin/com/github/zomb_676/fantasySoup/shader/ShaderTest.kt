package com.github.zomb_676.fantasySoup.shader

import com.github.zomb_676.fantasySoup.FantasySoup
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.WorldVertexBufferUploader
import net.minecraft.world.border.WorldBorder
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.event.TickEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import org.lwjgl.opengl.GL40
import org.lwjgl.opengl.GL43

@Mod.EventBusSubscriber(Dist.CLIENT,bus = Mod.EventBusSubscriber.Bus.FORGE)
class ShaderTest {
    companion object {
        private val list = mutableListOf<String>()

        @JvmStatic
        @SubscribeEvent
        fun tick(event: RenderWorldLastEvent) {

        }
    }
}