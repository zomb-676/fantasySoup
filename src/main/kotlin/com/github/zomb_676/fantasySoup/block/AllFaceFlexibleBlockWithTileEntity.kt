package com.github.zomb_676.fantasySoup.block

import com.github.zomb_676.fantasySoup.block.Vertical.Companion.verticalRelation
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.BlockItemUseContext
import net.minecraft.state.EnumProperty
import net.minecraft.state.StateContainer
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.util.Direction

abstract class AllFaceFlexibleBlockWithTileEntity(properties: Properties) : HorizonBlockWithTileEntity(properties) {

    init {
        this.defaultState = this.defaultState.with(verticalRelation, Vertical.SAME)
    }

    override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>) {
        super.fillStateContainer(builder.add(verticalRelation))
    }

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState? {
        val horizon =context.placementHorizontalFacing.opposite
        val vertical =context.nearestLookingDirection.opposite
        return if (horizon == vertical){
            defaultState.with(BlockStateProperties.HORIZONTAL_FACING,horizon).with(verticalRelation,Vertical.SAME)
        }else{
            when(vertical){
                Direction.DOWN -> {
                    defaultState.with(BlockStateProperties.HORIZONTAL_FACING,horizon).with(verticalRelation,Vertical.DOWN)
                }
                Direction.UP -> {
                    defaultState.with(BlockStateProperties.HORIZONTAL_FACING,horizon).with(verticalRelation,Vertical.UP)
                }
                else -> throw RuntimeException()
            }
        }
    }


}