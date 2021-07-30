package com.github.zomb_676.fantasySoup.register

import com.github.zomb_676.fantasySoup.FantasySoup
import com.github.zomb_676.fantasySoup.utils.runOnClientJar
import net.minecraft.client.renderer.ItemBlockRenderTypes
import net.minecraft.client.renderer.RenderType
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.material.Fluid
import net.minecraftforge.fmllegacy.RegistryObject

/**
 * implement this interface to access these extension methods , in order not to pollute the global variable
 */
interface RenderTypeSetMethods{
//    @JvmName("bindRenderTypeBlock")
    fun <B : Block> RegistryObject<B>.bindBlockRenderType(renderType: () -> () -> RenderType): RegistryObject<B> {
        runOnClientJar {
            ClientBlockRegisterEventHandle.addTask {
                ItemBlockRenderTypes.setRenderLayer(this.get(), renderType.invoke().invoke())
                FantasySoup.logger.debug(
                    RegisterHandle.registerMarker,
                    "set render type:$renderType for block:${this.get().registryName} in mod:${this.id.namespace}"
                )
            }
        }
        return this
    }

//    @JvmName("bindRenderTypeFluid")
    fun <F : Fluid> RegistryObject<F>.bindLiquidRenderType(renderType: () -> () -> RenderType): RegistryObject<F> {
        runOnClientJar {
            ClientBlockRegisterEventHandle.addTask {
                ItemBlockRenderTypes.setRenderLayer(this.get(), renderType.invoke().invoke())
                FantasySoup.logger.debug(
                    RegisterHandle.registerMarker,
                    "set render type:$renderType for fluid:${this.get().registryName} in mod:${this.id.namespace}"
                )
            }
        }
        return this
    }
}