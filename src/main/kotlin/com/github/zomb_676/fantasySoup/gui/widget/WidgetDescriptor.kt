package com.github.zomb_676.fantasySoup.gui.widget

open class WidgetDescriptor (val partName: String, val u: Int, val v: Int, val width: Int, val height: Int,
    private val container: StyleWidgetContainer){
    fun containerPath() = container.path
    fun containerWidth() = container.width
    fun containerHeight() = container.height
}