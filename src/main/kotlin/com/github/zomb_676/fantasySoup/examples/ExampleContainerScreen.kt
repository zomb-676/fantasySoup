package com.github.zomb_676.fantasySoup.examples

import net.minecraft.client.gui.screens.inventory.ContainerScreen
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.ChestMenu
import net.minecraftforge.common.ForgeHooks
import net.minecraftforge.fmllegacy.network.NetworkHooks

class ExampleContainerScreen(pChestMenu: ChestMenu, pPlayerInventory: Inventory) : ContainerScreen(pChestMenu,
    pPlayerInventory, TextComponent("test container screen")
) {

}