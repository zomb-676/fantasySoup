package com.github.zomb_676.fantasySoup.imGUI.operationPanel

import imgui.ImColor

object GlobalSetting {
    var mergedWidgetSelect = false
    data class RadioColor(val innerColor:Int,val outerColor:Int,val hoverColor:Int)

    /**
     * same as [ImColor.intToColor] ABGR instead of RGBA
     */
    fun combine(r:Int,g:Int,b:Int,a:Int): Int =
        a.shl(24) + b.shl(16) + g.shl(8) + r

    val redRadioColor = RadioColor(
        combine(250,5,26,255),
        combine(248,90,90,100),
        combine(255,74,74,255)
    )
}