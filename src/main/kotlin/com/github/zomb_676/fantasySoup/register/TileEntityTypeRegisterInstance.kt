package com.github.zomb_676.fantasySoup.register

import com.github.zomb_676.fantasySoup.FantasySoup
import net.minecraft.block.Block
import net.minecraft.client.renderer.tileentity.TileEntityRenderer
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher
import net.minecraft.tileentity.TileEntity
import net.minecraft.tileentity.TileEntityType
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.registries.DeferredRegister

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
class TileEntityTypeRegisterInstance(
    registerHandleInstance: RegisterHandle,
    register: DeferredRegister<TileEntityType<*>>,
) : RegisterInstance<TileEntityType<*>>(registerHandleInstance, register) {

    fun <T : TileEntity> tileEntityBlind(
        tileEntityTypeName: String,
        clazz: Class<T>,
        render: Class<out TileEntityRenderer<T>>? = null,
        vararg validBlocks: RegistryObject<out Block>,
    ): RegistryObject<TileEntityType<T>> {
        val result = register.register(tileEntityTypeName) {
            TileEntityType.Builder.create(
                { clazz.getConstructor().newInstance() },
                *(validBlocks.map { it.get() }.toTypedArray())
            )
                .build(null)
        }
        if (render != null) {
            TileEntityRenderHandle.add(result, render)
        }
        return result
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    class TileEntityRenderHandle {

        data class DataCache<T : TileEntity>(
            val tileEntityType: RegistryObject<TileEntityType<T>>,
            val render: Class<out TileEntityRenderer<T>>,
        ) {
            fun blind() {
                try {
                    ClientRegistry.bindTileEntityRenderer(tileEntityType.get()) {
                        render.getConstructor(TileEntityRendererDispatcher::class.java).newInstance(it)
                    }
                } catch (e: ExceptionInInitializerError) {
                    FantasySoup.logger.error(e.cause)
                    throw e
                }
            }
        }

        companion object {
            private val allDataCache = mutableListOf<DataCache<*>>()

            fun <T : TileEntity> add(
                tileEntityType: RegistryObject<TileEntityType<T>>,
                render: Class<out TileEntityRenderer<T>>,
            ) =
                allDataCache.add(DataCache(tileEntityType, render))

            @JvmStatic
            @SubscribeEvent
            fun onClientEvent(event: FMLClientSetupEvent) {
                allDataCache.forEach { it.blind() }
            }
        }
    }
}