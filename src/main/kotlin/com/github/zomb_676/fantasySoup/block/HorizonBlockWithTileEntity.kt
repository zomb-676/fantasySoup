package com.github.zomb_676.fantasySoup.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.BlockItemUseContext
import net.minecraft.state.StateContainer
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.util.Direction

abstract class HorizonBlockWithTileEntity(properties: Properties) : BlockWithTileEntity(properties) {
    init {
        this.defaultState = this.defaultState.with(BlockStateProperties.HORIZONTAL_FACING,Direction.WEST)
    }
    override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>) {
        super.fillStateContainer(builder.add(BlockStateProperties.HORIZONTAL_FACING))
    }

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState? {
        val direction = context.placementHorizontalFacing.opposite
        return super.getStateForPlacement(context)?.with(BlockStateProperties.HORIZONTAL_FACING,direction)
    }
}