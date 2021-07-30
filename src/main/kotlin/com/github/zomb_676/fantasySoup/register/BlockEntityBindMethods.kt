package com.github.zomb_676.fantasySoup.register

import com.github.zomb_676.fantasySoup.utils.newInstanceForEmptyOrSpecificConstructor
import com.github.zomb_676.fantasySoup.utils.takeIfOnClient
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraftforge.fmllegacy.RegistryObject

interface BlockEntityBindMethods {
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")//safe
    fun <T : BlockEntity> RegisterHandle.regBlockEntityType(
        blockEntityName: String,
        blockEntityTypeClass: Class<T>,
        blockEntityRenderContext: (()->()->(BlockEntityRendererProvider.Context) -> BlockEntityRenderer<T>)? = null,
        vararg validBlocks: RegistryObject<out Block>
    ): RegistryObject<BlockEntityType<T>> =
        registersHolder.tileEntityTypeRegister.register(blockEntityName) {
            BlockEntityType.Builder.of({ pos, state
                -> blockEntityTypeClass.newInstanceForEmptyOrSpecificConstructor(pos, state) },
                *(validBlocks.map { it.get() }.toTypedArray())
            ).build(null)
        }.takeIfOnClient{ blockEntityRenderContext?.apply { BlockEntityRenderBlind.bind(it, this()(), modName)}}
}