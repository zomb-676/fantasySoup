package com.github.zomb_676.fantasySoup.examples

import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.MenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Material
import net.minecraft.world.phys.BlockHitResult
import net.minecraftforge.fmllegacy.network.NetworkHooks

class ExampleBlock : Block(Properties.of(Material.STONE).noOcclusion()), EntityBlock {
    override fun getRenderShape(arg: BlockState): RenderShape = RenderShape.ENTITYBLOCK_ANIMATED

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = ExampleBlockEntity(pos, state)

    override fun use(
        blockstate: BlockState,
        level: Level,
        pos: BlockPos,
        player: Player,
        hand: InteractionHand,
        blockHitResult: BlockHitResult
    ): InteractionResult {
        if (!level.isClientSide()) {
            val blockEntity = level.getBlockEntity(pos)
            if (blockEntity is ExampleBlockEntity) {
                val provider = object: MenuProvider {
                    override fun createMenu(windowId: Int, inventory: Inventory, player: Player): AbstractContainerMenu=
                        ExampleContainer(windowId,level,pos,inventory,player)

                    override fun getDisplayName(): Component = TextComponent("example container")
                }
                NetworkHooks.openGui(player as ServerPlayer,provider,blockEntity.blockPos)
            }
        }
        return InteractionResult.SUCCESS
    }
}