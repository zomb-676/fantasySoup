package test

import java.io.File

fun main() {
    val file = File("./src/main/resources/assets/fantasy_soup/textures/gui/line_style/README.md")
    val content = file.readLines().map { it.replace(" ", "") }
    val data = mutableListOf<WidgetDescribe>()
    var currentInfo : String? = null
    (3 until content.size).map {
        try {
            val info = content[it]
            currentInfo = info
            if (info.length != 8 && info.isNotEmpty()) {
                val splited = info.split("|").filter { s -> s.isNotEmpty() }.dropLast(1).filter { s -> s.isNotEmpty() }
                val uv = splited[1].split(",").map { s -> s.toInt() }
                data.add(
                    WidgetDescribe(
                        splited[0],
                        uv[0], uv[1],
                        splited[2].toInt(), splited[3].toInt()
                    )
                )
            }
        }catch (e : NumberFormatException){
            e.addSuppressed(RuntimeException("failed to analyse $currentInfo"))
        }
    }
//    println("picture size = width:${} height:")
    data.forEach {
        println("val ${it.partName} = WidgetDescriptor(\"${it.partName}\",${it.u},${it.v},${it.width},${it.height},this)")
    }
    val picInfo = content[2].split("|").filter { s->s.isNotEmpty() }.dropLast(1).filter { s -> s.isNotEmpty() }
    println("pic info width:${picInfo[2]} , height:${picInfo[3]} , check this if it needs check")
}

data class WidgetDescribe(val partName: String, val u: Int, val v: Int, val width: Int, val height: Int)