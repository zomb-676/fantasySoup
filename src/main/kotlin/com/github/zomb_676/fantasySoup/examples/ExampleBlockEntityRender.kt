package com.github.zomb_676.fantasySoup.examples

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.world.level.block.Blocks
import net.minecraftforge.client.model.data.EmptyModelData
import net.minecraftforge.client.model.data.IModelData
import net.minecraftforge.common.Tags

class ExampleBlockEntityRender :BlockEntityRenderer<ExampleBlockEntity>{
    override fun render(
        pBlockEntity: ExampleBlockEntity,
        pPartialTicks: Float,
        pMatrixStack: PoseStack,
        pBuffer: MultiBufferSource,
        pCombinedLight: Int,
        pCombinedOverlay: Int
    ) {
        Minecraft.getInstance().blockRenderer.renderSingleBlock(Blocks.DIAMOND_BLOCK.defaultBlockState(),pMatrixStack,pBuffer,pCombinedLight,pCombinedOverlay,EmptyModelData.INSTANCE)
    }
}