package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.gui.widget.WidgetHolderPic
import com.github.zomb_676.fantasySoup.imGUI.ImGuiMethods
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage

class Button(initialInfo: OperationStage.WidgetInfoInitObject) : IWidgetTypeInfo<Button>(initialInfo) {
    private val main: WidgetHolderPic = WidgetHolderPic(initialInfo)
    private val hover: WidgetHolderPic? = null
    private val pressed: WidgetHolderPic? = null

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
                    button(main.file.name){}
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

    override fun drawComponentToolTip() {

    }
}