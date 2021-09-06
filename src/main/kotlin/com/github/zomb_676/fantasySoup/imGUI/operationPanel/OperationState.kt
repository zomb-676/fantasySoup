package com.github.zomb_676.fantasySoup.imGUI.operationPanel

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.gui.widget.prototype.IWidgetTypeInfo
import com.github.zomb_676.fantasySoup.imGUI.ImGuiMethods.wrapImGUIObject
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage.InternalMethods.drawTypeNameSameLine
import com.github.zomb_676.fantasySoup.render.graphic.texture.FileTexture
import com.github.zomb_676.fantasySoup.render.graphic.texture.Texture
import com.github.zomb_676.fantasySoup.utils.takeIfTrue
import imgui.ImGui
import java.io.File
import java.util.*
import kotlin.math.roundToInt

sealed class OperationStage {
    var isAvailable = true

    open fun tick(): OperationStage? = null
    open fun endProcess(): Boolean = false
    abstract fun draw(): Unit

    class NeedLoadStage : OperationStage() {
        var data: List<PicInfo>? = null
        override fun draw() {
            wrapImGUIObject {
                window("WidgetOperationPanel-NeedLoading") {
                    if (data == null) {
                        button("Load Fixed") {
                            val files = WidgetOperationPanel.loadAllTextureSelfRaw()
                            data = listOf(* files.map { PicInfo(it, FileTexture(it.path).genTexture()) }.toTypedArray())
                        }
                    }
                }
            }
        }

        override fun tick(): OperationStage? =
            if (data == null) null else ManualSpecifyStage(data!!)

    }

    class ManualSpecifyStage(val picFiles: List<PicInfo>) : OperationStage() {
        val widgetInfos = WidgetInfos()

        override fun draw() {
            wrapImGUIObject {
                window("WidgetOperationPanel-ManuallySpecifying") {
                    table("pictures", 2) {
                        tableHeader("pic name")
                        tableHeader("state")
                        for (widgetInfo in picFiles) {
                            val (file, texture) = widgetInfo
                            tableItem {
                                radioButton(file.name, widgetInfo.types.isNotEmpty()) {
                                    WidgetInfoSelector.setSelected(widgetInfo)
                                }
                                ImGui.isItemClicked(1).takeIfTrue {
                                    println(file.name)
                                }
                                tooltipHover {
                                    imageFlip(texture.textureID, texture.width.toFloat(), texture.height.toFloat())
                                }
                            }
                            tableItem {
                                drawTypeNameSameLine(widgetInfo)
                            }
                        }
                    }
                }
            }
            WidgetInfoSelector.draw(widgetInfos)
            AllWidgetPanel.draw(widgetInfos)
        }


    }

    object WidgetInfoSelector {
        var selectedTexture: PicInfo? = null
        val scale = floatArrayOf(1f)
        var needDraw = false
        fun setSelected(widgetInfo: PicInfo) {
            selectedTexture = widgetInfo
            needDraw = true
        }

        fun draw(widgetInfos: WidgetInfos) {
            if (!needDraw) return
            wrapImGUIObject {
                window("WidgetInfoSelector") {
                    val (file, texture) = selectedTexture!!
                    ImGui.text("file name:${file.name}")
                    val sizeX = texture.width.toFloat() * scale[0]
                    val sizeY = texture.height.toFloat() * scale[0]
                    ImGui.text("file size:[width:${texture.width},height:${texture.height}],render size:[width:${sizeX.roundToInt()},width:${sizeY.roundToInt()}]")
                    drawTypeNameSameLine(selectedTexture!!)
                    imageFlip(texture.textureID, sizeX, sizeY)
                    ImGui.sliderFloat("scale", scale, 0.5f, 10f)
                    ImGui.separator()
                    table("thonk", 3) {
                        ActualType.strictMap.forEach { (name, type) ->
                            tableItem {
                                button(name) {
                                    if (!selectedTexture!!.types.any { it == type }) {
                                        val widgetPicInfo = type.constructor.newInstance(WidgetInfoInitObject(file, texture))
                                        widgetInfos[type] = widgetPicInfo
                                        selectedTexture!!.widgetPicInfos.add(widgetPicInfo)
                                        selectedTexture!!.types.add(type)
                                    }
                                }
                                rightClickLast {
                                    widgetInfos.remove(selectedTexture!!.widgetPicInfos)
                                    val widgetPicInfo = type.constructor.newInstance(WidgetInfoInitObject(file, texture))
                                    widgetInfos[type] = widgetPicInfo
                                    selectedTexture!!.widgetPicInfo = widgetPicInfo
                                    selectedTexture!!.type = type
                                }
                                tooltipHover { ImGui.text("type:$name,descriptor:wip") }
                            }
                        }
                        tableItem {
                            button("unselect all") {
                                if ()
                                widgetInfos.remove(selectedTexture!!.widgetPicInfo!!)
                                selectedTexture!!.widgetPicInfo = null
                                selectedTexture!!.type = null
                            }
                        }
                    }
                }
            }
        }
    }

    object AllWidgetPanel {
        fun draw(widgetInfos: WidgetInfos) {
            if (widgetInfos.length() == 0) return
            wrapImGUIObject {
                window("GeneratedWidgets") {
                    for ((type, container) in widgetInfos.container) {
                        if (container.isEmpty()) continue
                        var index = 1
                        table(type.name, 2) {
                            tableHeader("widgetName")
                            tableHeader(type.roughName)
                            for (widgetInfo in container) {
                                val (file, texture) = widgetInfo.initialInfo
                                tableItem { ImGui.text("${type.roughName}:${index++}") }
                                tooltipHover {
                                    ImGui.text("name:${file.name}")
                                    imageFlip(texture.textureID, texture.width.toFloat(), texture.height.toFloat())
                                }
                                tableItem { ImGui.text(type.name) }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * wrap of file and texture , just for raw read
     */
    data class PicInfo(val file: File, val texture: Texture) {
        var types= mutableListOf<ActualType>()
        var widgetPicInfos = mutableListOf<IWidgetTypeInfo<*>>()
    }

    /**
     * the container of the read result
     */
    class WidgetInfos {
        val container: SortedMap<ActualType, MutableList<IWidgetTypeInfo<*>>> =
            sortedMapOf(
                { t1, t2 -> t1.compareTo(t2) },
                * ActualType.values().map { it to mutableListOf<IWidgetTypeInfo<*>>() }.toTypedArray()
            )

        fun add(type: ActualType, widgetInfo: IWidgetTypeInfo<*>) {
            this[type] = widgetInfo
        }

        fun contains(type: ActualType, file: File) {//slow
            container[type]!!.any { it.initialInfo.file == file }
        }

        fun contains(type: ActualType, texture: Texture) {//slow
            container[type]!!.any { it.initialInfo.texture == texture }
        }

        operator fun set(type: ActualType, widgetInfo: IWidgetTypeInfo<*>) =
            container[type]!!.add(widgetInfo)


        fun remove(widgetInfo: IWidgetTypeInfo<*>) {
            container[widgetInfo.getWidgetType()]!!.remove(widgetInfo)
        }

        fun length(): Int = container.values.sumOf { it.size }

    }

    /**
     * used for init a [IWidgetTypeInfo]
     */
    data class WidgetInfoInitObject(var file: File, var texture: Texture)

    /**
     *
     */
    object InternalMethods{
        internal fun drawTypeNameSameLine(widgetInfo: PicInfo) {
            if (widgetInfo.types.isNotEmpty()) {
                (1 until widgetInfo.types.size).forEach {
                    ImGui.text(widgetInfo.types[it].roughName)
                }
                ImGui.text(widgetInfo.types.last().roughName)
            } else {
                ImGui.text("unselected")
            }
        }
    }
}