package com.github.zomb_676.fantasySoup.register

import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.IForgeRegistryEntry

open class RegisterInstance<T : IForgeRegistryEntry<T>>(
    val registerHandleInstance: RegisterHandle,
    val register: DeferredRegister<T>,
)


