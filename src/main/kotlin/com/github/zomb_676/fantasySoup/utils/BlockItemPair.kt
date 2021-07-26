package com.github.zomb_676.fantasySoup.utils

import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.Block
import net.minecraftforge.fmllegacy.RegistryObject


data class BlockItemPair<B:Block,I:BlockItem>(val block: RegistryObject<B>,val blockItem :RegistryObject<I>) {
    inline fun useBlOck(codeBlock : RegistryObject<B>.()->Unit){codeBlock(block)}
    inline fun useItem(codeBlock : RegistryObject<I>.()->Unit){codeBlock(blockItem)}
}