package com.github.zomb_676.fantasySoup.register

import com.github.zomb_676.fantasySoup.FantasySoup
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fmllegacy.RegistryObject

@Mod.EventBusSubscriber(Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
object BlockEntityRenderBlind {
    private val data = mutableListOf<BindData<*>>()

    /**
     * @param
     */
    fun <T : BlockEntity> bind(
        blockEntityType: RegistryObject<BlockEntityType<T>>,
        blockEntityRenderer: BlockEntityRendererProvider<T>,
        modName: String
    ) = data.add(BindData(blockEntityType, blockEntityRenderer, modName))

    @JvmStatic
    @SubscribeEvent
    fun bind(event: FMLClientSetupEvent) = data.forEach(BindData<*>::bind)

    private data class BindData<T : BlockEntity>(
        val blockEntityType: RegistryObject<BlockEntityType<T>>,
        val blockEntityRenderer: BlockEntityRendererProvider<T>,
        val modName: String
    ) {
        fun bind() {
            BlockEntityRenderers.register(blockEntityType.get(), blockEntityRenderer)
            FantasySoup.logger.debug(
                RegisterHandle.registerMarker,
                "successfully bind blockEntityType:${blockEntityType} to blockEntityRender:$blockEntityRenderer for mod:$modName"
            )
        }
    }
}