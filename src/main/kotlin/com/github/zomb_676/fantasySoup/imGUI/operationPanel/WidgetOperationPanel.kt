package com.github.zomb_676.fantasySoup.imGUI.operationPanel

import com.github.zomb_676.fantasySoup.imGUI.IImGUI
import com.github.zomb_676.fantasySoup.imGUI.ImGuiMethods
import com.github.zomb_676.fantasySoup.utils.assert
import com.github.zomb_676.fantasySoup.utils.takeIfNotNull
import imgui.ImGui
import java.io.File

object WidgetOperationPanel {

    val space = '\u200C'
    var currentStage: OperationStage = NeedLoadStage()

    fun spaceN(n:Int):String{
        var base = ""
        repeat(n){
            base+= space
        }
        return base
    }

    /**
     * independent launch
     */
    @JvmStatic
    fun main(args: Array<String>) {
        IImGUI.initWithGL(1600, 900)
        val font = ImGuiMethods.getFontFromSysTTFDir("comic.ttf")
        IImGUI.wrapShouldClose {
            IImGUI.wrapRunWithGL {
                ImGui.pushFont(font)
                currentStage.tick().takeIfNotNull { currentStage = it }
                currentStage.draw()
                ImGui.popFont()
            }

        }
        IImGUI.disposeWithGl()
    }

    /**
     * init gl by third party
     */
    fun launchFromThirdParty(window: Long): () -> Unit {
        IImGUI.initFromOther(window)
        return {
            IImGUI.wrapRunImGUIOnly {

            }
        }
    }

    fun loadAllTextureSelfRaw(): List<File> {
        val path = File("src/main/resources/assets/fantasy_soup/textures/gui/line_style").assert { isDirectory }
        return path.listFiles()!!.filter { it.extension == "png" }
    }

}