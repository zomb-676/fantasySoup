package com.github.zomb_676.fantasySoup.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.BlockItemUseContext
import net.minecraft.state.StateContainer
import net.minecraft.util.Direction

abstract class RelationBlockWithTileEntity(properties: Properties) : BlockWithTileEntity(properties) {
    init {
        this.defaultState.with(Vertical.verticalRelation, Vertical.SAME)
    }

    override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>) {
        super.fillStateContainer(builder.add(Vertical.verticalRelation))
    }

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState? =
        when (context.nearestLookingDirection) {
            Direction.DOWN -> defaultState.with(Vertical.verticalRelation, Vertical.DOWN)
            Direction.UP -> defaultState.with(Vertical.verticalRelation, Vertical.UP)
            else -> defaultState.with(Vertical.verticalRelation, Vertical.SAME)
        }

}