package com.github.zomb_676.fantasySoup.examples

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
import net.minecraft.client.gui.screens.inventory.ContainerScreen
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.ChestMenu
import net.minecraft.world.level.Level
import net.minecraftforge.common.ForgeHooks
import net.minecraftforge.fmllegacy.network.NetworkHooks

class ExampleContainer(windowId:Int, level: Level, pos:BlockPos, playerInventory: Inventory, player:Player)
    : AbstractContainerMenu(Regs.container.get(),windowId) {
    override fun stillValid(arg: Player): Boolean = true
}