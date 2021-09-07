package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.gui.widget.WidgetPicHolder
import com.github.zomb_676.fantasySoup.imGUI.ImGuiMethods
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage.WidgetInfos
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
    fun drawSelectPicTypeInfo() {
        ImGuiMethods.wrapImGUIObject {
            table("pic type selector", 2) {
                tableHeader("pic type")
                tableHeader("select status")
                drawComponentCore()
            }
        }
    }

    protected fun drawComponent(picTypeName: String, widgetPicHolder: WidgetPicHolder?) {
        ImGuiMethods.wrapImGUIObject {
            tableItem { text(picTypeName) }
            tableItem { text(widgetPicHolder?.file?.name ?: "unspecific") }
            widgetPicHolder.takeIfNotNull { tooltipHover { imageFlip(it.texture) } }
        }
    }

    open fun drawComponentCore() =drawComponent("default",default)
}