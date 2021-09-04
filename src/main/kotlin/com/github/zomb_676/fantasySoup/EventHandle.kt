package com.github.zomb_676.fantasySoup

import com.github.zomb_676.fantasySoup.examples.ExampleScreen
import com.github.zomb_676.fantasySoup.render.graphic.OpenglFunctions
import com.github.zomb_676.fantasySoup.utils.manuallyInitClass
import com.mojang.blaze3d.vertex.BufferBuilder
import com.mojang.blaze3d.vertex.BufferUploader
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat
import net.minecraft.client.Minecraft
import net.minecraft.client.model.BookModel
import net.minecraft.client.model.geom.ModelLayers
import net.minecraft.client.renderer.LightTexture
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers
import net.minecraft.client.renderer.blockentity.EnchantTableRenderer
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent
import java.awt.image.BufferStrategy

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
object EventHandle {
    @JvmStatic
    @SubscribeEvent
    fun initGraphicObjects(event:FMLLoadCompleteEvent){
        event.enqueueWork(ExampleScreen::class::manuallyInitClass)
    }

    @JvmStatic
    @SubscribeEvent
    fun initGraphicDebugSetting(event: FMLCommonSetupEvent){
        event.enqueueWork{OpenglFunctions.addGlDebugMessageCallback(this.hashCode().toLong())}
    }

}