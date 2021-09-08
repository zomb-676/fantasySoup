package com.github.zomb_676.fantasySoup.imGUI.operationPanel

import com.github.zomb_676.fantasySoup.imGUI.ImGuiMethods.wrapImGUIObject
import com.github.zomb_676.fantasySoup.imGUI.operationPanel.OperationStage.InternalMethods.drawTypeNameSameLine

class ManualSpecifyStage(val picFiles: List<PicInfo>) : OperationStage() {
    val widgetInfos = WidgetInfos(picFiles)

    override fun draw() {
        wrapImGUIObject {
            window("WidgetOperationPanel-ManuallySpecifying") {
                table("pictures", 2) {
                    tableHeader("pic name")
                    tableHeader("state")
                    for (picInfo in picFiles) {
                        val (file, texture) = picInfo
                        tableItem {
                            radioButton(file.name, picInfo.length() != 0) {
                                WidgetInfoSelector.setSelected(picInfo)
                            }
                            tooltipHover {
                                imageFlip(texture.textureID, texture.width.toFloat(), texture.height.toFloat())
                            }
                        }
                        tableItem {
                            drawTypeNameSameLine(picInfo)
                        }
                    }
                }
            }
        }
        AllWidgetPanel.draw(widgetInfos)
        WidgetInfoSelector.draw(widgetInfos)
    }

}