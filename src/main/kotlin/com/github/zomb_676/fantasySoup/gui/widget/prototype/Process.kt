package com.github.zomb_676.fantasySoup.gui.widget.prototype

import com.github.zomb_676.fantasySoup.gui.widget.ActualType
import com.github.zomb_676.fantasySoup.gui.widget.WidgetPicHolder
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage
import com.github.zomb_676.fantasySoup.render.graphic.texture.Texture
import java.io.File

class Process(initialInfo: OperationStage.WidgetInfoInitObject) : IWidgetTypeInfo<Process>(initialInfo) {

    private var fill : WidgetPicHolder? = null
    private var hover : WidgetPicHolder? = null

    override fun getWidgetType(): ActualType = ActualType.PROCESS

    override fun merge(another: Process) {

    }

    override fun needMultiPicType(): Boolean = true

    override fun drawComponentCore() {
        super.drawComponentCore()
        drawComponent("hover", hover)
    }

    override fun contains(texture: Texture): Boolean =
        super.contains(texture) || fill?.texture == texture|| hover?.texture == texture

    override fun contains(file: File): Boolean =
        super.contains(file) || fill?.file == file ||hover?.file == file

    override fun contains(widgetPicHolder: WidgetPicHolder): Boolean =
        super.contains(widgetPicHolder) || fill==widgetPicHolder|| hover == widgetPicHolder
}