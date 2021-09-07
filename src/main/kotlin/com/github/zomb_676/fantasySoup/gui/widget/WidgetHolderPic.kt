package com.github.zomb_676.fantasySoup.gui.widget

import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage
import com.github.zomb_676.fantasySoup.render.graphic.texture.Texture
import java.io.File

data class WidgetHolderPic(var file: File,var texture: Texture) {
    constructor(initObject: OperationStage.WidgetInfoInitObject):this(initObject.file,initObject.texture)
}