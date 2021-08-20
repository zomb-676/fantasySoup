package com.github.zomb_676.fantasySoup.examples

import com.github.zomb_676.fantasySoup.gui.IScreen
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.renderer.ShaderInstance
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class ExampleScreen(container: ExampleContainer,inventory: Inventory,pTitle: Component)
    : IScreen<ExampleContainer> (container,inventory,pTitle){
    override fun renderBg(poseStack: PoseStack, partialTicks: Float, mouseX: Int, mouseY: Int){
        val textureID = minecraft!!.mainRenderTarget.colorTextureId

    }

    override fun render(poseStack: PoseStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        renderBg(poseStack, partialTicks, mouseX, mouseY)
    }
}