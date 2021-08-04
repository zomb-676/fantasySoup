package com.github.zomb_676.fantasySoup.examples

import com.github.zomb_676.fantasySoup.gui.IContainer
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level

class ExampleContainer(windowId: Int, level: Level, pos: BlockPos, playerInventory: Inventory, player: Player) :
    IContainer(Regs.container.get(), windowId, level, pos, playerInventory, player) {

    override fun stillValid(player: Player): Boolean = true

}