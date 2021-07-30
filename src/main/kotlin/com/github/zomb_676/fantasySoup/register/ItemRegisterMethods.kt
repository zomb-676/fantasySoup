package com.github.zomb_676.fantasySoup.register

import com.github.zomb_676.fantasySoup.utils.newInstanceForEmptyOrSpecificConstructor
import net.minecraft.world.item.Item
import net.minecraftforge.fmllegacy.RegistryObject
import java.util.*

/**
 * implement this interface to access these extension methods , in order not to pollute the global variable
 */
interface ItemRegisterMethods {
    fun RegisterHandle.stringItem(itemName: String, itemProperty: ItemProperty? = null): RegistryObject<Item> =
        registersHolder.itemRegister.register(itemName)
        { Item(itemProperty?.invoke(Item.Properties()) ?: Item.Properties()) }

    fun RegisterHandle.supplierItem(itemName: String, item:(Item.Properties)-> Item, itemProperty: ItemProperty? = null): RegistryObject<Item> =
        registersHolder.itemRegister.register(itemName){item.invoke(itemProperty?.invoke(Item.Properties())?: Item.Properties())}

    fun <T : Item> RegisterHandle.classItem(itemClass: Class<T>, itemName: String? = null, itemProperty: ItemProperty? = null): RegistryObject<T> =
        registersHolder.itemRegister.register(itemName ?: itemClass.simpleName.lowercase(Locale.getDefault()))
        {  itemClass.newInstanceForEmptyOrSpecificConstructor(itemProperty?.invoke(Item.Properties()) ?: Item.Properties()) }
}