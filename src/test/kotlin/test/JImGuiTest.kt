package test

import com.github.zomb_676.fantasySoup.FantasySoup
import com.github.zomb_676.fantasySoup.render.graphic.Program
import org.ice1000.jimgui.JImGui
import org.ice1000.jimgui.NativeBool
import org.ice1000.jimgui.NativeFloat
import org.ice1000.jimgui.util.JniLoader

object JImGuiTest {

    val imGui = kotlin.run {
        JniLoader.load()
        JImGui("JimGui Test")
    }
    val renderCalls: MutableList<() -> Unit> = mutableListOf()
    private fun renderCall(codeBloc: () -> Unit) = renderCalls.add(codeBloc)
    val blurDirX = NativeFloat().apply { this.modifyValue(5f) }
    val blurDirY = NativeFloat().apply { this.modifyValue(5f) }
    val radius = NativeFloat().apply { this.modifyValue(2f) }
    val isEnable = NativeBool().apply { this.modifyValue(false) }

    fun main() {
        while (!imGui.windowShouldClose()) {
            imGui.initNewFrame()
            if (Program.allPrograms.isNotEmpty()) {
                imGui.begin("programs")
                imGui.toggleButton("rua", isEnable)
                isEnable.accessValue().takeIfTrue {
                    for (program in Program.allPrograms) {
                        imGui.text(program.programName)
                        imGui.button("reload ${program.programName}").takeIfTrue {
                            renderCall { program.reloadProgram();Program.allPrograms.sortBy { it.programName } }
                        }
                    }
                    imGui.dragFloat("blurDirX", blurDirX)
                    imGui.dragFloat("blurDirY", blurDirY)
                    imGui.dragFloat("radius", radius)
                }
            }
            JImGui.end()
            imGui.render()
        }
    }
}


fun main() {
    JImGuiTest.main()
}

inline fun Boolean.takeIfTrue(codeBloc: () -> Unit) {
    if (this) codeBloc()
}

fun <T : Any?> T.print(): T {
    FantasySoup.logger.info(this.toString())
    return this
}