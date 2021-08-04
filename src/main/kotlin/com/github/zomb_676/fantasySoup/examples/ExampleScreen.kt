package com.github.zomb_676.fantasySoup.examples

import com.github.zomb_676.fantasySoup.gui.IScreen
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory

class ExampleScreen(container: ExampleContainer,inventory: Inventory,pTitle: Component)
    : IScreen<ExampleContainer> (container,inventory,pTitle){
    override fun renderBg(arg: PoseStack, f: Float, i: Int, j: Int){}

    override fun render(arg: PoseStack, m: Int, n: Int, g: Float) {
        super.render(arg, m, n, g)
    }
}