package com.github.zomb_676.fantasySoup.imGUI.operationPanel

import com.github.zomb_676.fantasySoup.imGUI.ImGuiMethods.wrapImGUIObject
import imgui.ImGui
import imgui.flag.ImGuiWindowFlags

object AllWidgetPanel {
    fun draw(widgetInfos: OperationStage.WidgetInfos) {
        if (widgetInfos.length() == 0) return
        wrapImGUIObject {
            window("GeneratedWidgets", ImGuiWindowFlags.MenuBar) {
                menuBar {
                    text("widget name")
                    ImGui.sameLine(ImGui.getWindowWidth() / 2)
                    text("|widget info")
                }
                ImGui.separator()
                for ((type, container) in widgetInfos.container) {
                    if (container.isEmpty()) continue
                    var index = 1
                    for (widgetInfo in container) {
                        table("all widget info",2){
                            tableHeader(widgetInfo.widgetName)
                            tableHeader(widgetInfo.getWidgetType().roughName)
                            widgetInfo.drawComponentCore()
                        }
                        ImGui.separator()
                    }
                }
            }
        }
    }
}