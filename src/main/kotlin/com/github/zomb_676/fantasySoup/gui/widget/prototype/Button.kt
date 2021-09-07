package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.gui.widget.WidgetPicHolder
import com.github.zomb_676.fantasySoup.imGUI.ImGuiMethods
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage
import com.github.zomb_676.fantasySoup.utils.takeIfNotNull

class Button(initialInfo: OperationStage.WidgetInfoInitObject) : IWidgetTypeInfo<Button>(initialInfo) {
    private val main: WidgetPicHolder = WidgetPicHolder(initialInfo)
    private val hover: WidgetPicHolder? = null
    private val pressed: WidgetPicHolder? = null

    override fun getWidgetType(): ActualType = ActualType.BUTTON

    override fun needMultiPicType(): Boolean = true

    override fun merge(another: Button) {

    }

    override fun drawSelectPicTypeInfo() {
        ImGuiMethods.wrapImGUIObject {
            table("pic type selector", 2) {
                tableHeader("pic type")
                tableHeader("select status")
                tableItem { text("main") }
                tableItem {
                    button(main.file.name) {}
                }
                tableItem { text("hover") }
                tableItem {
                }
                tableItem { text("pressed") }
                tableItem {

                }
            }
        }
    }

    override fun drawComponentInfo() {
        ImGuiMethods.wrapImGUIObject {
            table("widget component", 2) {
                tableHeader("pic type")
                tableHeader("status")
                drawComponent("main", main)
                drawComponent("hover", hover)
                drawComponent("pressed", pressed)
            }
        }
    }

    private fun drawComponent(picTypeName: String, widgetPicHolder: WidgetPicHolder?) {
        ImGuiMethods.wrapImGUIObject {
            tableItem { text(picTypeName) }
            tableItem { text(widgetPicHolder?.file?.name ?: "unspecific") }
            widgetPicHolder.takeIfNotNull { tooltipHover { imageFlip(it.texture) } }
        }
    }
}