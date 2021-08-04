package com.github.zomb_676.fantasySoup.register

import net.minecraft.client.gui.screens.MenuScreens
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.gui.screens.inventory.MenuAccess
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.MenuType
import net.minecraftforge.common.extensions.IForgeContainerType
import net.minecraftforge.fmllegacy.RegistryObject

/**
 * implement this interface to access these extension methods , in order not to pollute the global variable
 */
interface ContainerRegisterMethods {
    fun <T : AbstractContainerMenu> RegisterHandle.regContainer(
        containerName: String, factory: (Int, Inventory, FriendlyByteBuf) -> T
    ): RegistryObject<MenuType<T>> =
        registersHolder.containerRegister.register(containerName) { IForgeContainerType.create(factory) }

    fun <M, U> RegistryObject<MenuType<M>>.bind(constructor: MenuScreens.ScreenConstructor<M, U>)
        : RegistryObject<MenuType<M>> where M : AbstractContainerMenu, U : Screen, U : MenuAccess<M> {
        ClientRegisterEventHandle.addTask {
            MenuScreens.register(this.get(), constructor)
        }
        return this
    }

    fun <M, U> RegistryObject<MenuType<M>>.bind(factory:(M,Inventory,Component)->U)
            : RegistryObject<MenuType<M>> where M : AbstractContainerMenu, U : Screen, U : MenuAccess<M> {
        ClientRegisterEventHandle.addTask {
            MenuScreens.register(this.get(),factory)
        }
        return this
    }
}