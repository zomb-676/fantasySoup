package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.gui.widget.WidgetPicHolder
import com.github.zomb_676.fantasySoup.imGUI.ImGuiMethods
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage.WidgetInfos
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.WidgetInfoSelector
import com.github.zomb_676.fantasySoup.render.graphic.texture.Texture
import com.github.zomb_676.fantasySoup.utils.takeIfNotNull
import java.io.File

sealed class IWidgetTypeInfo<T : IWidgetTypeInfo<T>>(initialInfo: OperationStage.WidgetInfoInitObject) {

    companion object {
        var totalIndex = 0
    }

    private val index = ++totalIndex

    protected val default: WidgetPicHolder = WidgetPicHolder(initialInfo)

    abstract fun getWidgetType(): ActualType

    var widgetName: String = "${getWidgetType().roughName}:$index"

    /**
     * return false if merge failed , which means conflict exist
     * remove another from [WidgetInfos]
     */
    abstract fun merge(another: T)

    open fun needMultiPicType(): Boolean = false

    open fun contains(texture: Texture): Boolean = default.texture == texture

    open fun contains(file: File): Boolean = default.file == file

    open fun contains(widgetPicHolder: WidgetPicHolder): Boolean = default == widgetPicHolder

    open fun getWidgetPicHolder(texture: Texture): WidgetPicHolder? = if (default.texture == texture) default else null

    fun clearPicHolderWithSpecificTexture(texture: Texture) {
        getWidgetPicHolder(texture)?.takeIfNotNull {
            it.clear()
            clearPicHolderWithSpecificTexture(texture)
        }
    }

    fun drawComponentInfo() {
        ImGuiMethods.wrapImGUIObject {
            table("widget component", 2) {
                tableHeader("pic type")
                tableHeader("status")
                drawComponentCore()
            }
        }
    }

    /**
     * wrap in scope outside
     */
    fun drawSelectPicTypeInfo(widgetInfos: WidgetInfos) {
        ImGuiMethods.wrapImGUIObject {
            table("pic type selector", 3) {
                tableHeader("pic type")
                tableHeader("select status")
                tableHeader("operation")
                drawComponentWithSelectButtonCore(widgetInfos)
            }
        }
    }

    open fun hasFullComplete() = default.isNotEmpty()

    open fun hasRequiredComplete() = default.isNotEmpty()

    protected fun drawComponent(picTypeName: String, widgetPicHolder: WidgetPicHolder) {
        ImGuiMethods.wrapImGUIObject {
            tableItem { text(picTypeName) }
            tableItem { text(widgetPicHolder.file?.name ?: "unspecific") }
            widgetPicHolder.takeIfNotEmpty { tooltipHover { imageFlip(it.texture!!) } }
        }
    }

    /**
     * @param picTypeName pic type name
     * @param drawingPicType the drawing pic type instance
     * @param widgetInfos the generating widgets collection
     */
    protected fun drawComponentWithSelectButton(
        picTypeName: String,
        drawingPicType: WidgetPicHolder,
        widgetInfos: WidgetInfos
    ) {

        /**
         * call after change
         */
        fun IWidgetTypeInfo<T>.tryRemoveFromExist(
            operatingPic: OperationStage.PicInfo?
        ) {
            if (operatingPic != null && !contains(operatingPic.texture)) {
                widgetInfos.remove(this@IWidgetTypeInfo)
                operatingPic.remove(this@IWidgetTypeInfo)
            }
        }

        /**
         * call before change
         */
        fun IWidgetTypeInfo<T>.tryAddNew(
            operatingPic: OperationStage.PicInfo?
        ) {
            if (operatingPic != null && !contains(operatingPic.texture)
                && !widgetInfos.container[this.getWidgetType()]!!.contains(this)) {
                widgetInfos.add(this@IWidgetTypeInfo)
                operatingPic.add(this@IWidgetTypeInfo)
            }
        }

        ImGuiMethods.wrapImGUIObject {
            //the changing picture
            val selectedPic = WidgetInfoSelector.selectedTexture!!
            tableItem { text(picTypeName) }
            tableItem { text(drawingPicType.file?.name ?: "unspecific") }
            drawingPicType.takeIfNotEmpty { tooltipHover { imageFlip(it.texture!!) } }
            tableItem {
                if (drawingPicType.texture == selectedPic.texture) {
                    button("remove self") {
                        drawingPicType.clear()
                        tryRemoveFromExist(selectedPic)
                    }
                    return@tableItem
                }
                val replacedPicInfo = widgetInfos.rawPicDat!!.find { it.texture == drawingPicType.texture }
                val hasInited = drawingPicType.isNotEmpty()
                val alreadyUsedInThisWidget = contains(selectedPic.texture)
                if (alreadyUsedInThisWidget) {
                    if (replacedPicInfo != null){
                        button("cover") {
                            drawingPicType.set(selectedPic)
                            tryRemoveFromExist(replacedPicInfo)
                        }
                    }else{
                        button("set"){
                            drawingPicType.set(selectedPic)
                        }
                    }
                    sameLine()
                    button("switch") {
                        clearPicHolderWithSpecificTexture(selectedPic.texture)
                        drawingPicType.set(selectedPic)
                    }
                } else {
                    if (selectedPic.texture != drawingPicType.texture && drawingPicType.isNotEmpty()){
                        button("cover"){
                            tryAddNew(selectedPic)
                            drawingPicType.set(selectedPic)
                            tryRemoveFromExist(replacedPicInfo)
                        }
                    }else{
                        button("set") {
                            tryAddNew(selectedPic)
                            drawingPicType.set(selectedPic)
                            tryRemoveFromExist(replacedPicInfo)
                        }
                    }
                }
                if (drawingPicType.isNotEmpty()) {
                    sameLine()
                    button("remove") {
                        drawingPicType.clear()
                        tryRemoveFromExist(selectedPic)
                    }
                }
            }
        }
    }

    open fun drawComponentCore() = drawComponent("default", default)

    open fun drawComponentWithSelectButtonCore(widgetInfos: WidgetInfos) {
        ImGuiMethods.pushId(1) { drawComponentWithSelectButton("default", default, widgetInfos) }
    }

}