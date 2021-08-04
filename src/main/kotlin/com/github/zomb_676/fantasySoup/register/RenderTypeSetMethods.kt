package com.github.zomb_676.fantasySoup.register

import com.github.zomb_676.fantasySoup.FantasySoup
import com.github.zomb_676.fantasySoup.utils.runOnClientJar
import net.minecraft.client.renderer.ItemBlockRenderTypes
import net.minecraft.client.renderer.RenderType
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Fluid
import net.minecraftforge.fmllegacy.RegistryObject
import java.util.function.Function

/**
 * implement this interface to access these extension methods , in order not to pollute the global variable
 *
 * * this configuration determines whether this block is transparent or not.
 *
 * * configuration which determines whether neighbour's face should be clipped is at
 * [BlockBehaviour.BlockStateBase.canOcclude] and [BlockBehaviour.Properties.noOcclusion] first vertical line
 * @see <img src="https://raw.githubusercontent.com/zomb-676/fantasySoup/master/image/block.png" alt=""
 *      width=320 height=180 > made by 3t
 */
interface RenderTypeSetMethods{
//    @JvmName("bindRenderTypeBlock")
    /**
     * @param renderType double wrapper in case of wrong side class load
     */
    fun <B : Block> RegistryObject<B>.bindBlockRenderType(renderType: () -> () -> RenderType): RegistryObject<B> {
        runOnClientJar {
            ClientRegisterEventHandle.addTask {
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
    /**
     * @param renderType double wrapper in case of wrong side class load
     */
    fun <F : Fluid> RegistryObject<F>.bindLiquidRenderType(renderType: () -> () -> RenderType): RegistryObject<F> {
        runOnClientJar {
            ClientRegisterEventHandle.addTask {
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