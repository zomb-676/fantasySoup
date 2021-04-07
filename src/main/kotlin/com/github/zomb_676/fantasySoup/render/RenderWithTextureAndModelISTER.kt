package com.github.zomb_676.fantasySoup.render

import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.model.ItemCameraTransforms
import net.minecraft.client.renderer.model.Model
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher
import net.minecraft.item.ItemStack
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.vector.Vector3f

class RenderWithTextureAndModelISTER(
    val texture: ResourceLocation,
    val model: Model,
) :
    ItemStackTileEntityRenderer() {
    override fun func_239207_a_(
        stack: ItemStack,
        p_239207_2_: ItemCameraTransforms.TransformType,
        matrixStackIn: MatrixStack,
        bufferIn: IRenderTypeBuffer,
        combinedLightIn: Int,
        combinedOverlayIn: Int
    ) {
        matrixStackIn.push()
        val renderType = RenderType.getEntityTranslucent(texture)
        val buffer = bufferIn.getBuffer(renderType)
        matrixStackIn.scale(1f, -1f, -1f)
        matrixStackIn.translate(0.5, -1.5, -0.5)
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90f))
        model.render(matrixStackIn, buffer, combinedLightIn, combinedOverlayIn, 1f, 1f, 1f, 1f)
        matrixStackIn.pop()
    }
}