package com.github.zomb_676.fantasySoup.register

import com.github.zomb_676.fantasySoup.FantasySoup
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.registries.DeferredRegister

class ItemRegisterInstance(registerHandleInstance: RegisterHandle, register: DeferredRegister<Item>) :
    RegisterInstance<Item>(registerHandleInstance, register) {

    fun simpleItem(name: String, itemGroup: ItemGroup): RegistryObject<Item> =
        register.register(name) {
            Item(Item.Properties().group(itemGroup))
        }

    fun <T : Item> item(clazz: Class<T>, name: String = classNameToValid(clazz.simpleName)): RegistryObject<T> =
        register.register(name) {
            clazz.getDeclaredConstructor().newInstance()
        }

    fun complexItem(name: String, block: (Item.Properties).() -> Item.Properties): RegistryObject<Item> =
        register.register(name) { Item(block(Item.Properties())) }

    fun  <T:Block> blockItem(itemName: String, block: RegistryObject<T>, itemGroup: ItemGroup): RegistryObject<BlockItem> =
        blockItem(itemName, block) { group(itemGroup) }

    fun <T:Block> blockItem(
        itemName: String,
        block: RegistryObject<T>,
        codeBlock: (Item.Properties).() -> Item.Properties
    ): RegistryObject<BlockItem> =
        register.register(itemName) { BlockItem(block.get(), codeBlock(Item.Properties())) }


    /**
     * fix invalid name , and log an error
     */
    private fun classNameToValid(string: String): String {
        if (string.any { it.isUpperCase() or (it != '_') }) {
            val stringBuilder = StringBuilder()
            if (string[0].isUpperCase()) {
                stringBuilder.append(string[0].toLowerCase())
            }
            (1 until string.length).forEach {
                val char = string[it]
                if (char.isUpperCase()) {
                    stringBuilder.append("_${char.toLowerCase()}")
                } else {
                    stringBuilder.append(char)
                }
            }
            FantasySoup.logger.error("${registerHandleInstance.modID} has an invalid name $string , auto fix to $stringBuilder")
            return stringBuilder.toString()
        } else {
            return string
        }

    }


}


