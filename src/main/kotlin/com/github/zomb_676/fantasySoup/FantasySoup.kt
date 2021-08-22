package com.github.zomb_676.fantasySoup

import com.github.zomb_676.fantasySoup.examples.Regs
import com.github.zomb_676.fantasySoup.utils.manuallyInitClass
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.fml.common.Mod
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.apache.logging.log4j.Marker
import org.apache.logging.log4j.MarkerManager

@Mod(FantasySoup.modId)
class FantasySoup {

    init {
        Regs::class.manuallyInitClass()
    }

    companion object {
        const val modName: String = "FantasySoup"
        const val modId: String = "fantasy_soup"
        val coreMarker: Marker = MarkerManager.getMarker("core")
        val logger: Logger = LogManager.getLogger(modName)

        fun modResourcesLocation(path:String) = ResourceLocation(FantasySoup.modId,path)
    }
}