package com.github.zomb_676.fantasySoup.examples

import com.github.zomb_676.fantasySoup.FantasySoup
import com.github.zomb_676.fantasySoup.register.AllInMethods
import com.github.zomb_676.fantasySoup.register.RegisterHandle
import net.minecraft.world.item.CreativeModeTab
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext


object Regs :AllInMethods{
    private val instance: RegisterHandle = RegisterHandle.gerOrCreate(FantasySoup.modId, FantasySoup.modName)
    val a = instance.registerAllRegistersToEvent(FMLJavaModLoadingContext.get().modEventBus)
    val item = instance.classItem(ExampleItem::class.java, "example_item",){tab(CreativeModeTab.TAB_BREWING)}
    val block = instance.classBlockWithItem(ExampleBlock::class.java, "example_block"){tab(CreativeModeTab.TAB_BREWING)}
    val block2 = instance.stringBlockWithItem("rua_block"){tab(CreativeModeTab.TAB_BREWING)}

    //        .useItem {  }
    val blockEntityType = instance
        .regBlockEntityType(
            "example",
            ExampleBlockEntity::class.java,
            {{{ _ ->  ExampleBlockEntityRender()}}},
            validBlocks = arrayOf(block.block, block2.block)
        )

//    val container = instance


}