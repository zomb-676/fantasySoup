package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.gui.widget.WidgetPicHolder
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage
import com.github.zomb_676.fantasySoup.render.graphic.texture.Texture
import java.io.File

class CheckBox(initialInfo: OperationStage.WidgetInfoInitObject) : IWidgetTypeInfo<CheckBox>(initialInfo) {
    var checked : WidgetPicHolder? = null
    var banned : WidgetPicHolder? = null

    override fun getWidgetType(): ActualType = ActualType.CHECKBOX

    override fun merge(another: CheckBox) {

    }

    override fun needMultiPicType(): Boolean = true

    override fun drawComponentCore() {
        super.drawComponentCore()
        drawComponent("checked", checked)
        drawComponent("banned", banned)
    }

    override fun contains(texture: Texture): Boolean =
        super.contains(texture) || checked?.texture == texture|| banned?.texture == texture


    override fun contains(file: File): Boolean =
        super.contains(file) || checked?.file == file || banned?.file == file

    override fun contains(widgetPicHolder: WidgetPicHolder): Boolean =
        super.contains(widgetPicHolder) || checked == widgetPicHolder || banned == widgetPicHolder
}