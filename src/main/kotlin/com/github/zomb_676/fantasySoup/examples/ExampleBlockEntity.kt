package com.github.zomb_676.fantasySoup.examples

import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class ExampleBlockEntity(pos: BlockPos, state: BlockState) :BlockEntity(Regs.blockEntityType.get(), pos,state){

}