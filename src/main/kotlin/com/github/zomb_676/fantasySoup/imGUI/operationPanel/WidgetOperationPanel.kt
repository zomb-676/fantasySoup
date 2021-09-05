package com.github.zomb_676.fantasySoup.imGUI.operationPanel

import com.github.zomb_676.fantasySoup.imGUI.IImGUI
import com.github.zomb_676.fantasySoup.utils.assert
import com.github.zomb_676.fantasySoup.utils.getOrThrow
import com.github.zomb_676.fantasySoup.utils.rough
import com.github.zomb_676.fantasySoup.utils.takeIfNotNull
import imgui.ImFont
import imgui.ImFontAtlas
import imgui.ImGui
import java.io.File

object WidgetOperationPanel {

    var currentStage : OperationStage = OperationStage.NeedLoadStage()

    /**
     * independent launch
     */
    @JvmStatic
    fun main(args: Array<String>) {
        IImGUI.initWithGL(1600, 900)
        val imGuiIO = ImGui.getIO()
        val font = imGuiIO.fonts.addFontFromFileTTF(getSystemFontDir() + "comic.ttf", 20f)
        IImGUI.State.imGuiGl3.updateFontsTexture()
        IImGUI.wrapShouldClose {
            IImGUI.wrapRunWithGL {
                ImGui.pushFont(font)
                currentStage.tick().takeIfNotNull { currentStage = it}
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

    @Throws(IllegalArgumentException::class)
    fun getSystemFontDir() :String {
        val systemName = System.getProperty("os.name").rough()
        return if ("windows" in systemName){
            "C://Windows/Fonts/"
        }else if ("linux" in systemName){
            "/usr/share/fonts"
        }else{
            throw IllegalArgumentException("can't get font file form os $systemName")
        }
    }

}