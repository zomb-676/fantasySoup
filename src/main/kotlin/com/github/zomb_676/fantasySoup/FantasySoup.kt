package com.github.zomb_676.fantasySoup

import net.minecraftforge.fml.common.Mod
import org.apache.logging.log4j.LogManager

@Mod(FantasySoup.modId)
class FantasySoup {

    companion object {
        const val modName: String = "FantasySoup"
        const val modId: String = "fantasy_soup"
        val logger = LogManager.getLogger(modName)
    }
}