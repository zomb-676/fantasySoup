package com.github.zomb_676.fantasySoup.imGUI.operationPanel

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.imGUI.ImGuiMethods.wrapImGUIObject
import com.github.zomb_676.fantasySoup.render.graphic.texture.FileTexture
import com.github.zomb_676.fantasySoup.render.graphic.texture.Texture
import com.github.zomb_676.fantasySoup.utils.takeIfNotNull
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
        var data: List<WidgetInfo>? = null
        override fun draw() {
            wrapImGUIObject {
                window("WidgetOperationPanel-NeedLoading") {
                    if (data == null) {
                        button("Load Fixed") {
                            val files = WidgetOperationPanel.loadAllTextureSelfRaw()
                            data = listOf( * files.map { WidgetInfo(it,FileTexture(it.path).genTexture()) }.toTypedArray())
                        }
                    }
                }
            }
        }

        override fun tick(): OperationStage? =
            if (data == null) null else ManualSpecifyStage(data!!)

    }

    class ManualSpecifyStage(val widgetFiles: List<WidgetInfo>) : OperationStage() {
        override fun draw() {
            wrapImGUIObject {
                window("WidgetOperationPanel-ManuallySpecifying") {
                    for (widgetInfo in widgetFiles) {
                        val (file, texture) = widgetInfo
                        radioButton(file.name,widgetInfo.type!=null) {
                            WidgetInfoSelector.setSelected(widgetInfo)
                        }
                        tooltipHover {
                            imageFlip(texture.textureID, texture.width.toFloat(), texture.height.toFloat())
                        }
                        widgetInfo.type.takeIfNotNull {
                            ImGui.sameLine()
                            ImGui.text(widgetInfo.type!!.roughName)
                        }
                    }
                }
                WidgetInfoSelector.draw()
            }
        }

    }

    object WidgetInfoSelector {
        var selectedTexture: WidgetInfo? = null
        val scale = floatArrayOf(1f)
        var needDraw = false
        fun setSelected(widgetInfo:WidgetInfo) {
            selectedTexture = widgetInfo
            needDraw = true
        }

        fun draw() {
            if (!needDraw) return
            wrapImGUIObject {
                window("WidgetInfoSelector"){
                    val (file,texture) = selectedTexture!!
                    ImGui.text("file name:${file.name}")
                    val sizeX = texture.width.toFloat() * scale[0]
                    val sizeY = texture.height.toFloat() * scale[0]
                    ImGui.text("file size:[width:${texture.width},height:${texture.height}],render size:[width:${sizeX.roundToInt()},width:${sizeY.roundToInt()}]")
                    ImGui.text("type:${selectedTexture?.type ?: "unselected"}")
                    imageFlip(texture.textureID, sizeX, sizeY)
                    ImGui.sliderFloat("scale", scale,0.5f ,10f )
                    ImGui.separator()
                    table("thonk",3){
                        ActualType.strictMap.forEach{ (name, type) ->
                            tableItem {
                                button(name){
                                    selectedTexture!!.type = type
                                }
                                tooltipHover { ImGui.text("type:$name,descriptor:wip") }
                            }
                        }
                        tableItem{button("unselect"){ selectedTexture!!.type = null}}
                    }
                }
            }
        }
    }

    data class WidgetInfo(val file:File,val texture: Texture){
        var type:ActualType? = null
    }
}