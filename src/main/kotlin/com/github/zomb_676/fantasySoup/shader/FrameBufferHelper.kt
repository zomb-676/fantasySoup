package com.github.zomb_676.fantasySoup.shader

import com.github.zomb_676.fantasySoup.gui.Canvas
import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.WorldVertexBufferUploader
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.client.shader.Framebuffer
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import org.lwjgl.opengl.GL43

//@Mod.EventBusSubscriber
//object FrameBufferHelper {
//    val repeatFramebuffer = Framebuffer(
//        Minecraft.getInstance().mainWindow.width, Minecraft.getInstance().mainWindow.height,
//        true, Minecraft.IS_RUNNING_ON_MAC
//    )
//
//    fun changeSize() {
//        repeatFramebuffer.framebufferWidth = Minecraft.getInstance().mainWindow.width
//        repeatFramebuffer.framebufferHeight = Minecraft.getInstance().mainWindow.height
//    }
//
//    @SubscribeEvent
//    @JvmStatic
//    fun blur(event: RenderWorldLastEvent) {
//        val mc = Minecraft.getInstance()
//        val width = Minecraft.getInstance().mainWindow.width.toDouble()
//        val height = Minecraft.getInstance().mainWindow.height.toDouble()
//        val builder = Tessellator.getInstance().buffer
//        val r = 1
//        val g = 1
//        val b = 1
//        val a = 1
//        builder.begin(GL43.GL_QUADS, DefaultVertexFormats.POSITION_COLOR)
//        builder.pos(0.0, height, 0.0).color(r, g, b, a).endVertex()
//        builder.pos(width, height, 0.0).color(r, g, b, a).endVertex()
//        builder.pos(width, 0.0, 0.0).color(r, g, b, a).endVertex()
//        builder.pos(0.0, 0.0, 0.0).color(r, g, b, a).endVertex()
//        builder.finishDrawing()
//
//        repeatFramebuffer.framebufferClear(Minecraft.IS_RUNNING_ON_MAC)
//        repeatFramebuffer.bindFramebuffer(false)
////        GlStateManager.blitFramebuffer()
//    }
//
//
//}