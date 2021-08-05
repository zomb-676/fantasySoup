package com.github.zomb_676.fantasySoup.gui.widget

import com.github.zomb_676.fantasySoup.utils.rough
import java.util.HashMap

/**
 * All available additionDataType
 */
enum class AdditionDataType(private val typeName: String){
     CONTENT_OFFSET ("content_offset"),
     FILL_AREA("fill_area"),
     POINT_OFFSET("point_offset"),
     ROTATION_POINT("rotation_point");

    val roughName :String =typeName.rough()

    companion object{

        private val strictMap: HashMap<String, AdditionDataType> = hashMapOf(*values().map { it.typeName to it}.toTypedArray())
        private val roughMap: HashMap<String, AdditionDataType> = hashMapOf(*values().map { it.roughName to it}.toTypedArray())

        fun strictMath(name: String): AdditionDataType? = strictMap[name]

        fun roughMath(name:String): AdditionDataType? = roughMap[name]

    }
}