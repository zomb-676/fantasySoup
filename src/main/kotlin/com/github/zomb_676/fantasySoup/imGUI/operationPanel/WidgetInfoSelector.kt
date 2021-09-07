package com.github.zomb_676.fantasySoup.imGUI.operationPanel

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.gui.widget.prototype.IWidgetTypeInfo
import com.github.zomb_676.fantasySoup.imGUI.ImGuiMethods
import com.github.zomb_676.fantasySoup.imGUI.ImGuiMethods.wrapImGUIObject
import com.github.zomb_676.fantasySoup.render.graphic.texture.Texture
import com.github.zomb_676.fantasySoup.utils.takeIfNotNull
import imgui.ImGui
import java.io.File
import kotlin.math.roundToInt

object WidgetInfoSelector {
    var selectedTexture: OperationStage.PicInfo? = null
    val scale = floatArrayOf(1f)
    var needDraw = false
    var typeInfo: IWidgetTypeInfo<*>? = null
    var enablePopup = false

    fun setSelected(widgetInfo: OperationStage.PicInfo) {
        selectedTexture = widgetInfo
        needDraw = true
    }

    fun draw(widgetInfos: OperationStage.WidgetInfos) {
        if (!needDraw) return
        wrapImGUIObject {
            window("WidgetInfoSelector") {
                val (file, texture) = selectedTexture!!
                ImGui.text("file name:${file.name}")
                val sizeX = texture.width.toFloat() * scale[0]
                val sizeY = texture.height.toFloat() * scale[0]
                ImGui.text("file size:[width:${texture.width},height:${texture.height}],render size:[width:${sizeX.roundToInt()},width:${sizeY.roundToInt()}]")
                imageFlip(texture.textureID, sizeX, sizeY)
                ImGui.sliderFloat("scale", scale, 0.5f, 10f)
                ImGui.separator()
                table("WidgetSelector", 3) {
                    widgetSelectorDraw(selectedTexture!!, widgetInfos)
                }
            }
            if (enablePopup) {
                setPopupEnable("select")
                enablePopup = false
            }
            typeInfo?.takeIfNotNull {
                popup("select") {
                    typeInfo!!.drawSelectPicTypeInfo()
                }
            }
        }
    }

    /**
     * left : just mark widget type and create an [IWidgetTypeInfo] , create new
     *
     * right : mark type if necessary , specific pic type or just add to in a compound [IWidgetTypeInfo] in a new menu , modify
     *
     * middle : remove this from the type of all [IWidgetTypeInfo] remove specific
     *
     * double click : remove this from exist this type [IWidgetTypeInfo] in a new menu , all included , remove all
     */
    private fun ImGuiMethods.widgetSelectorDraw(
        selectedPicInfo: OperationStage.PicInfo,
        widgetInfos: OperationStage.WidgetInfos
    ) {

        fun newTypeInfo(
            type: ActualType,
            file: File,
            texture: Texture
        ) = type.constructor.newInstance(OperationStage.WidgetInfoInitObject(file, texture))

        val (file, texture) = selectedPicInfo
        ActualType.strictMap.forEach { (name, type) ->
            tableItem {
                button(name) {}
                leftClickLast {
                    val newTypeInfo = newTypeInfo(type, file, texture)
                    selectedPicInfo.add(type, newTypeInfo)
                    widgetInfos.add(type, newTypeInfo)
                }
                rightClickLast {
                    val typeInfo: IWidgetTypeInfo<*>
                    if (widgetInfos.isEmpty(type)) {
                        typeInfo = newTypeInfo(type, file, texture)
                        selectedPicInfo.add(type, typeInfo)
                        widgetInfos.add(type, typeInfo)
                    } else if (widgetInfos.container[type]!!.size == 1) {
                        typeInfo = widgetInfos.container[type]!!.first()
                    } else {
                        typeInfo = newTypeInfo(type, file, texture)
                    }
                    WidgetInfoSelector.typeInfo = typeInfo
                    enablePopup = true
                }
                doubleLeftClickClickLast {

                }
                tooltipHover { ImGui.text("type:$name,descriptor:wip") }
            }
        }
        tableItem {
            button("unselect all") {
                selectedPicInfo.container.forEach { (_, widgetInfoList) ->
                    val iterator = widgetInfoList.iterator()
                    while (iterator.hasNext()) {
                        widgetInfos.remove(iterator.next())
                        iterator.remove()
                    }
                }
            }
        }
    }
}