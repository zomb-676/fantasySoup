package com.github.zomb_676.fantasySoup.imGUI.operationPanel

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.gui.widget.prototype.IWidgetTypeInfo
import com.github.zomb_676.fantasySoup.render.graphic.texture.Texture
import imgui.ImGui
import java.io.File
import java.util.*

sealed class OperationStage {
    var isAvailable = true

    open fun tick(): OperationStage? = null
    open fun endProcess(): Boolean = false
    abstract fun draw(): Unit

    /**
     * wrap for a single texture file
     */
    data class PicInfo(val file: File, val texture: Texture) : WidgetInfos() {

    }

    /**
     * the container of the read result
     */
    open class WidgetInfos {
        val container: SortedMap<ActualType, MutableList<IWidgetTypeInfo<*>>> =
            sortedMapOf(
                { t1, t2 -> t1.compareTo(t2) },
                * ActualType.values().map { it to mutableListOf<IWidgetTypeInfo<*>>() }.toTypedArray()
            )

        fun add(type: ActualType, widgetInfo: IWidgetTypeInfo<*>) {
            this[type] = widgetInfo
        }

        fun isNotEmpty() = container.values.any { it.isNotEmpty() }

        fun isNotEmpty(type: ActualType) = container[type]!!.isNotEmpty()

        fun isEmpty() = container.values.all { it.isEmpty() }

        fun isEmpty(type: ActualType) = container[type]!!.isEmpty()

        fun contains(type: ActualType, file: File) {//slow
            container[type]!!.any { it.initialInfo.file == file }
        }

        fun contains(type: ActualType, texture: Texture) {//slow
            container[type]!!.any { it.initialInfo.texture == texture }
        }

        operator fun set(type: ActualType, widgetInfo: IWidgetTypeInfo<*>) =
            container[type]!!.add(widgetInfo)

        operator fun get(type: ActualType) = container[type]!!

        fun remove(widgetInfo: IWidgetTypeInfo<*>) {
            container[widgetInfo.getWidgetType()]!!.remove(widgetInfo)
        }

        fun length(): Int = container.values.sumOf { it.size }

    }

    /**
     * used for init a [IWidgetTypeInfo]
     */
    data class WidgetInfoInitObject(var file: File, var texture: Texture)

    object InternalMethods {
        internal fun drawTypeNameSameLine(widgetInfo: PicInfo) {
            var hasFirstDraw = false
            for ((type, list) in widgetInfo.container) {
                if (list.isNotEmpty()) {
                    if (hasFirstDraw) {
                        ImGui.sameLine()
                    } else {
                        hasFirstDraw = true
                    }
                    ImGui.text(type.roughName)
                }
            }
            if (!hasFirstDraw) ImGui.text("unselected")
        }
    }
}