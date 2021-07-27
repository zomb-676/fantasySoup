package com.github.zomb_676.fantasySoup.examples

import net.minecraft.client.gui.screens.inventory.ContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.ChestMenu

class ExampleContainerScreen(pChestMenu: ChestMenu, pPlayerInventory: Inventory, pTitle: Component) : ContainerScreen(pChestMenu,
    pPlayerInventory, pTitle
) {

}