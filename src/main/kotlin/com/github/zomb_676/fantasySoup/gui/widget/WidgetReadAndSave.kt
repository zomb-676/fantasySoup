package com.github.zomb_676.fantasySoup.gui.widget

import com.github.zomb_676.fantasySoup.FantasySoup
import java.io.File


private val splitChar = '|';
private val emptyLineChar = '-';

fun analyzeMdFile(path:String) {
    val file = File(path)
    val analyzedData = mutableListOf<MdFileDescriptor>()
    var lineNumber = 0;
    FantasySoup.logger.info("start analyze md file , file path:${file.absolutePath}")
    file.reader().readLines().forEachIndexed {index,text->
        FantasySoup.logger.info("analyze line ${++lineNumber}")
        if (index==0 || !text.any { it.isLetter() }){
            FantasySoup.logger.info("empty info line $lineNumber")
            return@forEachIndexed
        }
        val splintedText=text.drop(1).split(splitChar)
            .map { it.replace(" ","")}
            .apply { FantasySoup.logger.info(this) }
        FantasySoup.logger.info("splinted data $splintedText")
        val widgetType = ActualType.roughMath(splintedText[0])
        val widgetName = splintedText[1]
        val picName = splintedText[2]
        val u :Int?
        val v :Int?
        splintedText[3].takeIf { it.contains(',') }?.split(",")?.map { it.toIntOrNull() }.apply {
            u = this?.component1()
            v = this?.component2()
        }
        val width = splintedText[4].toIntOrNull()
        val height = splintedText[5].toIntOrNull()
        val dataType = splintedText[6]
        val extraData = splintedText[7]
        val dataInstance = MdFileDescriptor(widgetType,widgetName,picName,u,v,width,height,dataType,extraData)
        FantasySoup.logger.info("data:")
        FantasySoup.logger.info(dataInstance)
        FantasySoup.logger.info("analyze line $lineNumber finished")
    }

}

private data class MdFileDescriptor
    (
    val widgetType: ActualType?, val widgetName:String?,val fileName: String?,
    val u: Int?, val v: Int?, val width: Int?, val height: Int?, val dataType: String?, val data: String?
)

fun main() {
    analyzeMdFile("./src/main/resources/assets/fantasy_soup/textures/gui/line_style/README.md")
}