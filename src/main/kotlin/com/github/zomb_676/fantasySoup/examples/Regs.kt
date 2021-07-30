package com.github.zomb_676.fantasySoup.examples

import com.github.zomb_676.fantasySoup.FantasySoup
import com.github.zomb_676.fantasySoup.register.AllInMethods
import com.github.zomb_676.fantasySoup.register.RegisterHandle
import net.minecraft.world.item.CreativeModeTab


object Regs :AllInMethods{
    private val instance: RegisterHandle = RegisterHandle.gerOrCreate(FantasySoup.modId, FantasySoup.modName)
    val item = instance.classItem(ExampleItem::class.java, "example item",){tab(CreativeModeTab.TAB_BREWING)}
    val block = instance.classBlockWithItem(ExampleBlock::class.java, "example block"){tab(CreativeModeTab.TAB_BREWING)}
    val block2 = instance.stringBlockWithItem("rua block"){tab(CreativeModeTab.TAB_BREWING)}

    //        .useItem {  }
    val blockEntityType = instance
        .regBlockEntityType(
            "example",
            ExampleBlockEntity::class.java,
            {{{ _ ->  ExampleBlockEntityRender()}}},
            validBlocks = arrayOf(block.block, block2.block)
        )


}