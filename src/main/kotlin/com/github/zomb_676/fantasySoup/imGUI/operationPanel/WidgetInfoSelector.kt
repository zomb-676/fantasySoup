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
    private val widgetScale = floatArrayOf(1f)
    var needDraw = false

    var existWidgetSelect: Pair<ActualType, OperationStage.PicInfo>? = null
    var enableExistWidgetSelectPopup = false

    var selectedTypeInfo: IWidgetTypeInfo<*>? = null
    var enablePicTypePopup = false

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
                val sizeX = texture.width.toFloat() * widgetScale[0]
                val sizeY = texture.height.toFloat() * widgetScale[0]
                ImGui.text("file size:[width:${texture.width},height:${texture.height}],render size:[width:${sizeX.roundToInt()},width:${sizeY.roundToInt()}]")
                imageFlip(texture.textureID, sizeX, sizeY)
                ImGui.sliderFloat("widgetScale", widgetScale, 0.5f, 10f)
                ImGui.separator()
                table("WidgetSelector", 3) {
                    widgetSelectorDraw(selectedTexture!!, widgetInfos)
                }
            }
            if (enablePicTypePopup) {
                setPopupEnable("selectPicType")
                enablePicTypePopup = false
            }
            selectedTypeInfo?.takeIfNotNull {
                popup("selectPicType") {
                    selectedTypeInfo!!.drawSelectPicTypeInfo(widgetInfos)
                }
            }
            if (enableExistWidgetSelectPopup) {
                setPopupEnable("selectExistWidget")
                enableExistWidgetSelectPopup = false
            }
            existWidgetSelect?.takeIfNotNull {
                popup("selectExistWidget") {
                    drawSpecificExistWidgetPopup(existWidgetSelect!!, widgetInfos)
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
                var hasDoubleClickTrigged = false
                doubleLeftClickClickLast {
                    hasDoubleClickTrigged = true
                    selectedPicInfo[type].clear()
                    widgetInfos[type].removeAll { it.contains(file) }
                }
                if (!hasDoubleClickTrigged) {
                    leftClickLast {
                        val newTypeInfo = newTypeInfo(type, file, texture)
                        selectedPicInfo.add(type, newTypeInfo)
                        widgetInfos.add(type, newTypeInfo)
                    }
                    rightClickLast {
                        val typeInfo: IWidgetTypeInfo<*>
                        if (widgetInfos[type].isEmpty()) {
                            typeInfo = newTypeInfo(type, file, texture)
                            selectedPicInfo.add(type, typeInfo)
                            widgetInfos.add(type, typeInfo)
                        } else {
                            existWidgetSelect = Pair(type, selectedPicInfo)
                            enableExistWidgetSelectPopup = true
                            return@rightClickLast
                        }
                        selectedTypeInfo = typeInfo
                        enablePicTypePopup = true
                    }
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

    private fun drawSpecificExistWidgetPopup(
        typeAndPicInfos: Pair<ActualType, OperationStage.PicInfo>,
        widgetInfos: OperationStage.WidgetInfos
    ) {
        val (type, picInf) = typeAndPicInfos
        wrapImGUIObject {
            text("widget type:${type.roughName}")
            ImGui.separator()
            if (GlobalSetting.mergedWidgetSelect) {
                for (widgetInfo in widgetInfos[type]) {
                    text(widgetInfo.widgetName)
                    widgetInfo.drawComponentInfo()
                }
            } else {
                widgetInfos[type].forEach {
                    if (it.hasRequiredComplete()) {
                        if (it.hasFullComplete()) {
                            radioButton(it.widgetName, true) {
                                selectedTypeInfo = it
                                enablePicTypePopup = true
                            }
                        } else {
                            coloredRadioButton(it.widgetName, true, GlobalSetting.redRadioColor) {
                                selectedTypeInfo = it
                                enablePicTypePopup = true
                            }
                        }
                    } else {
                        radioButton(it.widgetName, false) {
                            selectedTypeInfo = it
                            enablePicTypePopup = true
                        }
                    }
                }
            }
        }
    }
}