package com.github.zomb_676.fantasySoup.examples

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Material

class ExampleBlock : Block(Properties.of(Material.STONE)) , EntityBlock{
    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = ExampleBlockEntity(pos,state)

}