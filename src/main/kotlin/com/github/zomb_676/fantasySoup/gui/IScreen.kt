package com.github.zomb_676.fantasySoup.gui

import com.github.zomb_676.fantasySoup.examples.ExampleContainer
import net.minecraft.client.gui.components.events.GuiEventListener
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.AbstractContainerMenu

abstract class  IScreen<T : AbstractContainerMenu>(container: T, inventory: Inventory, pTitle: Component)
    : AbstractContainerScreen<T>(container,inventory,pTitle) {
    override fun getFocused(): GuiEventListener? {
        return super.getFocused()
    }

    override fun setFocused(arg: GuiEventListener?) {
        super.setFocused(arg)
    }
}