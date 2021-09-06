package com.github.zomb_676.fantasySoup.imGUI.operationPanel

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.gui.widget.prototype.IWidgetTypeInfo
import com.github.zomb_676.fantasySoup.imGUI.ImGuiMethods.wrapImGUIObject
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
                                radioButton(file.name, widgetInfo.type != null) {
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
                                ImGui.text(widgetInfo.type?.roughName ?: "unselected")
                            }
                        }
                    }
                }
            }
            WidgetInfoSelector.draw(widgetInfos)
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
                    ImGui.text("type:${selectedTexture?.type ?: "unselected"}")
                    imageFlip(texture.textureID, sizeX, sizeY)
                    ImGui.sliderFloat("scale", scale, 0.5f, 10f)
                    ImGui.separator()
                    table("thonk", 3) {
                        ActualType.strictMap.forEach { (name, type) ->
                            tableItem {
                                button(name) {
                                    selectedTexture!!.type = type
                                }
                                tooltipHover { ImGui.text("type:$name,descriptor:wip") }
                            }
                        }
                        tableItem { button("unselect") { selectedTexture!!.type = null } }
                    }
                }
            }
        }
    }

    object AllWidgetPanel{
        fun draw(widgetInfos: WidgetInfos){
            if (widgetInfos.length() == 0) return
            wrapImGUIObject {
                window("GeneratedWidgets"){

                }
            }
        }
    }

    data class PicInfo(val file: File, val texture: Texture) {
        var type: ActualType? = null
    }

    class WidgetInfos() {
        val container: SortedMap<ActualType, MutableList<IWidgetTypeInfo<*>>> =
            sortedMapOf({ t1, t2 -> t1.compareTo(t2) }
                ,* ActualType.values().map { it to mutableListOf<IWidgetTypeInfo<*>>() }.toTypedArray())

        fun add(type: ActualType,widgetInfo: IWidgetTypeInfo<*>) {
            this[type] = widgetInfo
        }

        operator fun set(type: ActualType,widgetInfo: IWidgetTypeInfo<*>) =
            container[type]!!.add(widgetInfo)


        fun remove(widgetInfo: IWidgetTypeInfo<*>){
            container[widgetInfo.getWidgetType()]!!.remove(widgetInfo)
        }

        fun length():Int = container.values.sumOf { it.size }

    }
}