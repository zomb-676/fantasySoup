package com.github.zomb_676.fantasySoup.render

import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.tileentity.TileEntityRenderer
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ResourceLocation

abstract class RenderWithTexture<T : TileEntity>(dispatcher: TileEntityRendererDispatcher) : TileEntityRenderer<T>(
    dispatcher
) {
    abstract fun renderWithTexture(
        tileEntityIn: T,
        texture: ResourceLocation,
        partialTicks: Float,
        matrixStackIn: MatrixStack,
        bufferIn: IRenderTypeBuffer,
        combinedLightIn: Int,
        combinedOverlayIn: Int,
    )
}