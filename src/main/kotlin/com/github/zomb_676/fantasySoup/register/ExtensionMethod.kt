package com.github.zomb_676.fantasySoup.register

import com.github.zomb_676.fantasySoup.dataGen.IDataHandle
import net.minecraft.client.gui.IHasContainer
import net.minecraft.client.gui.ScreenManager
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.settings.KeyBinding
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

fun <I : Item> RegistryObject<I>.setCustomModel(
    handle: IDataHandle,
    model: (IDataHandle) -> Lazy<(BlockStateProvider) -> Unit>
): RegistryObject<I> {
    handle.toInitTask.add { handle.dataHandle.addBlockModel { model.invoke(handle).value } }
    return this
}

fun KeyBinding.register(): KeyBinding {
    RegisterHandle.keyBindings.add(this)
    return this
}