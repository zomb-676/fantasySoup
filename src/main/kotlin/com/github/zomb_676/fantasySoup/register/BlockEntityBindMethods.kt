package com.github.zomb_676.fantasySoup.register

import com.github.zomb_676.fantasySoup.utils.newInstanceForEmptyOrSpecificConstructor
import com.github.zomb_676.fantasySoup.utils.takeIfOnClientJar
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraftforge.fmllegacy.RegistryObject
import net.minecraft.world.level.block.state.BlockBehaviour

/**
 * implement this interface to access these extension methods , in order not to pollute the global variable
 *
 * if BlockEntityRender is set , make sure its [BlockBehaviour.Properties.noOcclusion] is set
 * or its [BlockBehaviour.Properties.canOcclude] is true
 * otherwise , [BlockEntityRenderer] will render block object , as its light is influenced by that
 */
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
        }.takeIfOnClientJar{ blockEntityRenderContext?.apply { BlockEntityRenderBlind.bind(it, this()(), modName)}}
}