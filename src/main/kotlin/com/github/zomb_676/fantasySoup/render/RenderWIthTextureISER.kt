package com.github.zomb_676.fantasySoup.render

import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.model.ItemCameraTransforms
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ResourceLocation

class RenderWIthTextureISER<T : TileEntity>(
    tileEntity: Class<T>,
    val texture: ResourceLocation,
    vararg parameters: Pair<Class<*>, *>
) : ItemStackTileEntityRenderer() {
    private val instance = lazy {
        tileEntity.getConstructor(*parameters.map { it.first }.toTypedArray())
            .newInstance(*parameters.map { it.second }.toTypedArray())
    }

    // copy form vazkii.botania.client.render.tile.TEISR
    override fun func_239207_a_(
        stack: ItemStack,
        p_239207_2_: ItemCameraTransforms.TransformType,
        matrixStack: MatrixStack,
        buffer: IRenderTypeBuffer,
        combinedLight: Int,
        combinedOverlay: Int
    ) {
        val renderer: RenderWithTexture<T> =
            TileEntityRendererDispatcher.instance.getRenderer(instance.value) as RenderWithTexture<T>
        renderer.renderWithTexture(instance.value,texture,0f,matrixStack,buffer,combinedLight,combinedOverlay)
    }
}