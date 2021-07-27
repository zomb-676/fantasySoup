package com.github.zomb_676.fantasySoup.register

import com.github.zomb_676.fantasySoup.FantasySoup
import com.github.zomb_676.fantasySoup.utils.runOnClientJar
import net.minecraft.client.renderer.ItemBlockRenderTypes
import net.minecraft.client.renderer.RenderType
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.material.Fluid
import net.minecraftforge.fmllegacy.RegistryObject

@JvmName("bindRenderTypeBlock")
fun <B : Block> RegistryObject<B>.bindRenderType(renderType: () -> () -> RenderType): RegistryObject<B> {
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

@JvmName("bindRenderTypeFluid")
fun <F : Fluid> RegistryObject<F>.bindRenderType(renderType: () -> () -> RenderType): RegistryObject<F> {
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