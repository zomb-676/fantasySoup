package com.github.zomb_676.fantasySoup.gui

import com.github.zomb_676.fantasySoup.examples.Regs
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.level.Level

abstract class IContainer(menuType:MenuType<*>,windowId: Int, level: Level, pos: BlockPos, playerInventory: Inventory, player: Player) :
    AbstractContainerMenu(menuType, windowId) {



}