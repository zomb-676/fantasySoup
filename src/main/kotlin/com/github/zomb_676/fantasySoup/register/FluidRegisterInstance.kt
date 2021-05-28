package com.github.zomb_676.fantasySoup.register

import net.minecraft.client.renderer.RenderType
import net.minecraft.fluid.Fluid
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fluids.FluidAttributes
import net.minecraftforge.fluids.ForgeFlowingFluid
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.registries.DeferredRegister

class FluidRegisterInstance(registerHandleInstance: RegisterHandle, register: DeferredRegister<Fluid>) :
    RegisterInstance<Fluid>(registerHandleInstance, register) {
    fun fluid(
        fluidName: String,
        stillTexture: ResourceLocation,
        flowingTexture: ResourceLocation,
        builderSet: (FluidAttributes.Builder.() -> Unit)? = null,
        propertySet : (ForgeFlowingFluid.Properties.() ->Unit)? = null
    ): FluidPair {
        var pro: ForgeFlowingFluid.Properties? = null
        val still: RegistryObject<ForgeFlowingFluid.Source> =
            register.register(fluidName) { ForgeFlowingFluid.Source(pro) }
        val flowing: RegistryObject<ForgeFlowingFluid.Flowing> =
            register.register("flowing_".plus(fluidName)) { ForgeFlowingFluid.Flowing(pro) }
        val builder = FluidAttributes.builder(stillTexture, flowingTexture).apply { builderSet?.invoke(this)}
        pro = ForgeFlowingFluid.Properties(still, flowing, builder).apply { propertySet?.invoke(this) }
        return FluidPair(still, flowing)
    }

    fun fluid(
        fluidName: String, stillTexture: String, flowingTexture: String,
        builderSet: (FluidAttributes.Builder.() -> Unit)? = null,
        propertySet : (ForgeFlowingFluid.Properties.() ->Unit)? = null
    ): FluidPair =
        fluid(
            fluidName, ResourceLocation(registerHandleInstance.modID, stillTexture), ResourceLocation(
                registerHandleInstance.modID, flowingTexture
            ), builderSet,propertySet
        )
}

data class FluidPair(
    val still: RegistryObject<ForgeFlowingFluid.Source>, val flowing: RegistryObject<ForgeFlowingFluid.Flowing>
) {
    fun blindRenderType(renderType: RenderType): FluidPair {
        still.blindFluidRenderType(renderType)
        flowing.blindFluidRenderType(renderType)
        return this
    }
}