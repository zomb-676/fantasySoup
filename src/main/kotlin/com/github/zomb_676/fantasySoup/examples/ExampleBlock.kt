package com.github.zomb_676.fantasySoup.examples

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Material

class ExampleBlock : Block(Properties.of(Material.STONE).noOcclusion()) , EntityBlock{
    override fun getRenderShape(arg: BlockState): RenderShape =RenderShape.ENTITYBLOCK_ANIMATED

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = ExampleBlockEntity(pos,state)

}