package com.github.zomb_676.fantasySoup.register

import com.github.zomb_676.fantasySoup.utils.BlockItemPair
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material
import net.minecraftforge.fmllegacy.RegistryObject

interface BlockRegisterMethods : ItemRegisterMethods {
    fun RegisterHandle.stringBlock(blockName: String, blockProperty: BlockProperty? = null): RegistryObject<Block> =
        registersHolder.blockRegister.register(blockName)
        { Block(blockProperty?.invoke(BlockBehaviour.Properties.of(Material.STONE)) ?:
        BlockBehaviour.Properties.of(Material.STONE)) }

    fun <T : Block> RegisterHandle.classBlock(blockClass: Class<T>, blockName: String? = null
               , blockProperty: BlockProperty? = null): RegistryObject<Block> =
        registersHolder.blockRegister.register(blockName ?: blockClass.simpleName.lowercase())
        { Block(blockProperty?.invoke(BlockBehaviour.Properties.of(Material.STONE)) ?:
        BlockBehaviour.Properties.of(Material.STONE)) }

    @Suppress("UNCHECKED_CAST")
    fun RegisterHandle.stringBlockWithItem(blockName: String, blockProperty: BlockProperty? = null
        ,itemName: String?=null,itemProperty: ItemProperty?=null) =
        stringBlock(blockName, blockProperty).run { BlockItemPair(this,
            supplierItem(itemName?:blockName
                ,{ BlockItem(this.get(),it) },itemProperty) as RegistryObject<BlockItem>)}

    @Suppress("UNCHECKED_CAST")
    fun <T : Block> RegisterHandle.classBlockWithItem(blockClass: Class<T>, blockName: String? = null
        , blockProperty: BlockProperty? = null, itemName: String?=null, itemProperty: ItemProperty?=null): BlockItemPair<Block, BlockItem> =
        registersHolder.blockRegister.register(blockName ?: blockClass.simpleName.lowercase())
        { Block(blockProperty?.invoke(BlockBehaviour.Properties.of(Material.STONE)) ?:
        BlockBehaviour.Properties.of(Material.STONE)) }.run { BlockItemPair(this,
            supplierItem(itemName?:blockName?:blockClass.simpleName.lowercase()
                ,{ BlockItem(this.get(),it) },itemProperty) as RegistryObject<BlockItem>) }
}