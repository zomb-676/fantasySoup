package com.github.zomb_676.fantasySoup.register

import com.github.zomb_676.fantasySoup.dataGen.IDataHandle
import net.minecraft.block.Block
import net.minecraft.client.gui.IHasContainer
import net.minecraft.client.gui.ScreenManager
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.settings.KeyBinding
import net.minecraft.fluid.Fluid
import net.minecraft.inventory.container.Container
import net.minecraft.inventory.container.ContainerType
import net.minecraft.item.Item
import net.minecraftforge.client.model.generators.BlockStateProvider
import net.minecraftforge.fml.RegistryObject

fun <T ,U> ContainerType<T>.bindToScreen(screenFactory : ScreenManager.IScreenFactory<T,U>): ContainerType<T>
        where T:Container, U : Screen, U: IHasContainer<T> {
    ScreenManager.registerFactory(this,screenFactory)
    return this
}

fun KeyBinding.register(): KeyBinding {
    RegisterHandle.keyBindings.add(this)
    return this
}

fun <T:Block> RegistryObject<T>.blindBlockRenderType(renderType: ()->()->RenderType): RegistryObject<T> {
    RegisterHandle.blindBlockRenderType(this,renderType)
    return this
}

fun <T: Fluid> RegistryObject<T>.blindFluidRenderType(renderType: ()->()->RenderType): RegistryObject<T> {
    RegisterHandle.blindFluidRenderType(this,renderType)
    return this
}