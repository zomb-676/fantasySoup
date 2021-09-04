package com.github.zomb_676.fantasySoup

import com.github.zomb_676.fantasySoup.FantasySoup.Companion.logger
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.renderer.GameRenderer
import org.lwjgl.opengl.GL43

fun delegateCustomRenderLevel(gameRenderer: GameRenderer, partialTicks: Float, nanoTime: Long, poseStack: PoseStack) {
    logger.fatal("success")
    gameRenderer.renderLevel(partialTicks, nanoTime, poseStack)
}