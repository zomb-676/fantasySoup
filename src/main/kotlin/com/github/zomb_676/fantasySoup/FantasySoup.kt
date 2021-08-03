package com.github.zomb_676.fantasySoup

import net.minecraftforge.fml.common.Mod
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.apache.logging.log4j.Marker
import org.apache.logging.log4j.MarkerManager
import com.github.zomb_676.fantasySoup.examples.Regs
import com.github.zomb_676.fantasySoup.utils.manuallyInitClass

@Mod(FantasySoup.modId)
class FantasySoup {

    init {
        Regs::class.manuallyInitClass()
        Regs.javaClass
    }

    companion object {
        const val modName: String = "FantasySoup"
        const val modId: String = "fantasy_soup"
        val coreMarker: Marker = MarkerManager.getMarker("core")
        val logger: Logger = LogManager.getLogger(modName)
    }
}