package com.github.zomb_676.fantasySoup.examples

import com.github.zomb_676.fantasySoup.FantasySoup
import com.github.zomb_676.fantasySoup.register.AllInMethods
import com.github.zomb_676.fantasySoup.register.RegisterHandle
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.AbstractContainerMenu
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.item.CreativeModeTab
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.fmllegacy.RegistryObject


object Regs :AllInMethods{
     val instance: RegisterHandle = RegisterHandle.gerOrCreate(FantasySoup.modId, FantasySoup.modName)
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

    val container: RegistryObject<MenuType<ExampleContainer>> = instance
        .regContainer("example_container"){ windowId: Int, inventory: Inventory, data: FriendlyByteBuf ->
            val pos = data.readBlockPos()
            val level = inventory.player.commandSenderWorld
            ExampleContainer(windowId,level,pos,inventory,inventory.player)
        }.bind(::ExampleScreen)


}