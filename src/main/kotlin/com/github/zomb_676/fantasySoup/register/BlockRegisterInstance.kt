package com.github.zomb_676.fantasySoup.register

import com.github.zomb_676.fantasySoup.render.trySetISTER
import net.minecraft.block.AbstractBlock.Properties
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.block.material.MaterialColor
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraftforge.client.model.generators.ModelFile
import net.minecraftforge.fml.RegistryObject
import net.minecraftforge.registries.DeferredRegister


class BlockRegisterInstance(registerInstance: RegisterHandle, register: DeferredRegister<Block>) :
    RegisterInstance<Block>(registerInstance, register) {
    @JvmOverloads
    fun simpleBlock(name: String, material: Material = Material.IRON, MapColor: MaterialColor = material.color):
            RegistryObject<Block> = register.register(name) {
        Block(Properties.create(material, MapColor))
    }
    @JvmOverloads
    fun simpleBlockAndItem(
        BlockName: String, itemName: String = BlockName, itemItemGroup: ItemGroup,
        material: Material = Material.IRON, MapColor: MaterialColor = material.color,
    ): BlockItemPair<BlockItem, Block> {
        val block = simpleBlock(BlockName, material, MapColor)
        val item = registerHandleInstance.registerItem().blockItem(itemName, block, itemItemGroup)
        return BlockItemPair(item, block)
    }
    @JvmOverloads
    fun complexBlock(
        name: String, material: Material = Material.IRON, MapColor: MaterialColor = material.color,
        codeBlock: (Properties) -> Properties,
    ): RegistryObject<Block> =
        register.register(name) {
            Block(codeBlock(Properties.create(material, MapColor)))
        }
    @JvmOverloads
    fun complexBlockAndItem(
        name: String, material: Material = Material.IRON, MapColor: MaterialColor = material.color,
        itemName: String, itemItemGroup: ItemGroup,
        codeBlock: (Properties) -> Properties,
        ister: ItemStackTileEntityRenderer? = null
    ): BlockItemPair<BlockItem, Block> {
        val block = complexBlock(name, material, MapColor, codeBlock)
        val item = registerHandleInstance.registerItem()
            .blockItem(itemName, block) { group(itemItemGroup).trySetISTER { ister } }
        return BlockItemPair(item, block)
    }

    @JvmOverloads
    fun <T : Block> block(clazz: Class<T>, name: String = fixer(clazz.simpleName)): RegistryObject<T> =
        register.register(name) {
            clazz.getConstructor().newInstance()
        }
    @JvmOverloads
    fun <B : Block> blockWithItem(
        clazz: Class<B>, blockName: String = fixer(clazz.simpleName),
        itemGroup: ItemGroup, itemName: String = blockName, ister: ItemStackTileEntityRenderer? = null
    ): BlockItemPair<BlockItem, B> {
        val block = block(clazz, blockName)
        val item: RegistryObject<BlockItem> = registerHandleInstance.registerItem()
            .blockItem(itemName, block) { group(itemGroup).trySetISTER { ister } }
        return BlockItemPair(item = item, block = block)
    }

    private fun fixer(string: String): String {
        if (string.any { it.isUpperCase() and !it.isDigit() and (it != '_') }) {
            val stringBuilder = StringBuilder()
            string.mapIndexed { index: Int, char: Char ->
                if (char.isLowerCase()) {
                    stringBuilder.append(char)
                } else if (char.isDigit()) {
                    if (index == 0) {
                        stringBuilder.append(char)
                    } else {
                        stringBuilder.append("_".plus(char))
                    }
                } else {
                    if (index == 0) {
                        stringBuilder.append(char.toLowerCase())
                    } else if (index == string.length - 1) {
                        stringBuilder.append(char.toLowerCase())
                    } else if (string[index + 1].isLowerCase()) {
                        stringBuilder.append("_".plus(char.toLowerCase()))
                    } else if (string[index - 1].isLowerCase() and string[index + 1].isUpperCase()) {
                        stringBuilder.append("_".plus(char.toLowerCase()))
                    } else if (string[index - 1].isUpperCase() and (string[index + 1].isUpperCase() or string[index + 1].isDigit())) {
                        stringBuilder.append(char.toLowerCase())
                    } else {
                        stringBuilder.append(char)
                    }
                }
            }
            return stringBuilder.toString()
        } else {
            return string
        }

    }

}

data class BlockItemPair<I : Item, B : Block>(val item: RegistryObject<I>, val block: RegistryObject<B>) {
    inline fun block(codeBlock: (RegistryObject<B>).() -> Unit): BlockItemPair<I, B> = apply { codeBlock(block) }
    inline fun blockFile(crossinline codeBlock: (RegistryObject<B>).() -> () -> () -> ModelFile?): ModelBlockItemPair<I, B> =
        ModelBlockItemPair(this, codeBlock(block))

    inline fun item(codeBlock: (RegistryObject<I>).() -> Unit): BlockItemPair<I, B> = apply { codeBlock(item) }
}

data class ModelBlockItemPair<I : Item, B : Block>(
    val blockItemPair: BlockItemPair<I, B>,
    val modelFile: () -> () -> ModelFile?
) {
    inline fun useModel(codeBlock: (() -> () -> ModelFile?).(BlockItemPair<I, B>) -> Unit): BlockItemPair<I, B> {
        codeBlock(modelFile, blockItemPair)
        return blockItemPair
    }
}