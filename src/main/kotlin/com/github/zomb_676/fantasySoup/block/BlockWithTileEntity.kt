package com.github.zomb_676.fantasySoup.block

import net.minecraft.block.Block
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.item.BlockItemUseContext
import net.minecraft.state.StateContainer

abstract class BlockWithTileEntity(properties: Properties) : Block(properties.notSolid()) {
    override fun hasTileEntity(state: BlockState) = true
    override fun getRenderType(state: BlockState): BlockRenderType = BlockRenderType.ENTITYBLOCK_ANIMATED

}