package com.github.zomb_676.fantasySoup.examples

import com.github.zomb_676.fantasySoup.FantasySoup
import com.github.zomb_676.fantasySoup.register.RegisterHandle
import com.github.zomb_676.fantasySoup.register.regItemBlock
import net.minecraft.world.item.Item
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod


object Regs{
    private val instance: RegisterHandle = RegisterHandle.gerOrCreate(FantasySoup.modId,FantasySoup.modName)
    private val blockRegister = instance.RegBlock()
    private val itemRegister = instance.RegItem()
    val exampleBlock = blockRegister.stringBlock("")
    val exampleItem = itemRegister.stringItem("")
}