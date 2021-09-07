package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.gui.widget.WidgetPicHolder
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage
import com.github.zomb_676.fantasySoup.render.graphic.texture.Texture
import java.io.File

class Button(initialInfo: OperationStage.WidgetInfoInitObject) : IWidgetTypeInfo<Button>(initialInfo) {
    private val hover: WidgetPicHolder? = null
    private val pressed: WidgetPicHolder? = null

    override fun getWidgetType(): ActualType = ActualType.BUTTON

    override fun needMultiPicType(): Boolean = true

    override fun merge(another: Button) {

    }

    override fun drawComponentCore() {
        drawComponent("default", default)
        drawComponent("hover", hover)
        drawComponent("pressed", pressed)
    }

    override fun contains(texture: Texture): Boolean =
        default.texture == texture || hover?.texture == texture || pressed?.texture == texture

    override fun contains(file: File): Boolean =
        default.file == file || hover?.file == file || pressed?.file == file

    override fun contains(widgetPicHolder: WidgetPicHolder): Boolean =
        default == widgetPicHolder || hover == widgetPicHolder || pressed == widgetPicHolder

}