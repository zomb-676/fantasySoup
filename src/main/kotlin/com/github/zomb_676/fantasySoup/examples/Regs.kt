package com.github.zomb_676.fantasySoup.examples

import com.github.zomb_676.fantasySoup.FantasySoup
import com.github.zomb_676.fantasySoup.register.RegisterHandle
import net.minecraft.world.item.Item
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod


object Regs{
    private val instance: RegisterHandle = RegisterHandle.gerOrCreate(FantasySoup.modId,FantasySoup.modName)

}